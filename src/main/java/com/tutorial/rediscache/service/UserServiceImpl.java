package com.tutorial.rediscache.service;

import com.tutorial.rediscache.config.RedisConfig;
import com.tutorial.rediscache.dao.entity.contact.Contact;
import com.tutorial.rediscache.dao.entity.party.User;
import com.tutorial.rediscache.dao.entity.preferences.PartyPreference;
import com.tutorial.rediscache.exception.RecordNotFoundException;
import com.tutorial.rediscache.repository.UserRepository;
import com.tutorial.rediscache.web.form.GenericSearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final MessageSource messageSource;
    private final UserRepository userRepository;
    private final CacheManager cacheManager;

    @Autowired
    private UserService selfReference;


    @Override
    public User add(User user) {
        PartyPreference preference = new PartyPreference();
        user.setPreferences(preference);
        return putUserToCache(userRepository.save(user));
    }

    @Override
    @CachePut(value = RedisConfig.USER_CACHE_NAME, key="#result.id")
    public User save(User user){
        return putUserToCache(userRepository.save(user));
    }


    @Override
    @Cacheable(value = RedisConfig.USER_CACHE_NAME, key = "#id", unless = "#result == null")
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }


    @Override
    @Cacheable(value = RedisConfig.USER_CACHE_NAME, key = "#tagName", unless = "#result == null")
    public Optional<User> findByTagname(String tagName){
        Optional<User> oUser = userRepository.findByTagname(tagName);
        return oUser;
    }

    @Override
    @CacheEvict(value = RedisConfig.USER_CACHE_NAME, key = "#id", beforeInvocation = true)
    public User updateUser(Long id, User form){
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
        user.setFirstName(form.getFirstName());
        user.setMiddleName(form.getMiddleName());
        user.setLastName(form.getLastName());
        user.setAbout(form.getAbout());
        user.setHeadline(form.getHeadline());
        user.setDob(form.getDob());

        user = save(user);
        return evictUserFromCache(user);
    }

    @Override
    public Page<User> searchUser(GenericSearchCriteria criteria, Pageable pageable) {
        User searchExample = User.createForSearchExample();

        searchExample.setFirstName(criteria.getFirstName());
        searchExample.setLastName(criteria.getLastName());
        searchExample.setTagname(criteria.getTagname());

        Example<User> example = Example.of(searchExample, ExampleMatcher.matching()
                .withMatcher("firstName", matcher -> matcher.ignoreCase().contains())
                .withMatcher("lastName", matcher -> matcher.ignoreCase().contains())
        );

        List<User> searchResult = userRepository.findAll(example);

        return new PageImpl<>(searchResult);
    }

    @Override
    public List<Contact> getUserContacts(Long id){
        return findById(id).map(User::getContacts).orElse(null);
    }

    @Override
    @Cacheable(value = RedisConfig.CONTACT_CACHE_NAME, key = "#contactId", unless = "#result == null")
    public Contact getContact(Long id, Long contactId){
        List<Contact> userContacts = getUserContacts(id);
        return userContacts == null
                ? null
                : userContacts.stream()
                        .filter(contact -> contact.getId().equals(contactId))
                        .findFirst()
                        .orElse(null);
    }

    @Override
    public Contact addContact(Long id, Contact contact){
        User user = selfReference.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));

        if (user.getContacts() == null) {
            user.setContacts(new ArrayList<>());
        }
        user.getContacts().add(contact);
        save(user);

        evictUserFromCache(user);
        putContactToCache(contact);

        return contact;
    }
    @Override
    public void updateContact(Long id, Long contactId, Contact contact){
        User user = findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
        if (user.getContacts() == null) {
            user.setContacts(new ArrayList<>());
        }
        Contact contactToUpdate = user.getContacts().stream().filter(con -> con.getId().equals(contactId)).findFirst().orElse(null);
        if (contactToUpdate == null) {
            return;
        }

        contactToUpdate.setType(contact.getType());
        contactToUpdate.setValue(contact.getValue());
        contactToUpdate.setNote(contact.getNote());
        contactToUpdate.setFromDate(contact.getFromDate());
        contactToUpdate.setThruDate(contact.getThruDate());
        contactToUpdate.setAllowSolicitation(contact.isAllowSolicitation());
        contactToUpdate.setExtension(contact.getExtension());
        contactToUpdate.setVerified(contact.isVerified());
        contactToUpdate.setPrimary(contact.getIsPrimary());
        contactToUpdate.setContactType(contact.getContactType());

        save(user);
    }

    private User evictUserFromCache(User user) {
        Cache userCache = cacheManager.getCache(RedisConfig.USER_CACHE_NAME);
        if (userCache == null) {
            return user;
        }
        userCache.evictIfPresent(user.getId());
        if (user.getTagname() != null) {
            userCache.evictIfPresent(user.getTagname());
        }
        if (user.getContacts() != null) {
            user.getContacts().forEach(this::evictContactFromCache);
        }
        return user;
    }

    private void evictContactFromCache(Contact contact) {
        Cache contactCache = cacheManager.getCache(RedisConfig.CONTACT_CACHE_NAME);
        if (contactCache == null) {
            return;
        }
        contactCache.evictIfPresent(contact.getId());
    }

    private void putContactToCache(Contact contact) {
        Cache contactCache = cacheManager.getCache(RedisConfig.CONTACT_CACHE_NAME);
        if (contactCache == null) {
            return;
        }
        contactCache.put(contact.getId(), contact);
    }
    private User putUserToCache(User user) {
        Cache userCache = cacheManager.getCache(RedisConfig.USER_CACHE_NAME);
        if (userCache != null) {
            userCache.put(user.getId(), user);
            if (user.getTagname() != null) {
                userCache.put(user.getTagname(), user);
            }
        }
        if (user.getContacts() != null) {
            user.getContacts().forEach(this::putContactToCache);
        }
        return user;
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = RedisConfig.CONTACT_CACHE_NAME, key = "#contactId", beforeInvocation = true),
        @CacheEvict(value = RedisConfig.USER_CACHE_NAME, key = "#id", beforeInvocation = true)
    })
    public void removeContact(Long id, Long contactId){
        User user = selfReference.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
        if (user.getContacts() == null) {
            user.setContacts(new ArrayList<>());
        }
        user.getContacts().removeIf(con -> con.getId().equals(contactId));

        save(user);
    }
}
