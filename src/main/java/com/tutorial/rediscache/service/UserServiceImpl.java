package com.tutorial.rediscache.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tutorial.rediscache.dao.entity.contact.Contact;
import com.tutorial.rediscache.dao.entity.party.User;
import com.tutorial.rediscache.dao.entity.preferences.PartyPreference;
import com.tutorial.rediscache.exception.RecordNotFoundException;
import com.tutorial.rediscache.repository.UserRepository;
import com.tutorial.rediscache.web.form.GenericSearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@EnableCaching
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {


    private ObjectMapper mapper = new ObjectMapper();
    private Gson gson = new Gson();

    private final MessageSource messageSource;
    private final UserRepository userRepository;
    private final CacheManager cacheManager;



    @Override
    public User add(User user) {
        PartyPreference preference = new PartyPreference();
        user.setPreferences(preference);
        return userRepository.save(user);
    }

    @Override
    public User save(User user){
        return userRepository.save(user);
    }


    @Override
    @Cacheable(value = "user", key = "#id")
    public Optional<User> findById(Long id){
        Optional<User> oUser = userRepository.findById(id);
        return oUser;
    }


    @Override
    @Cacheable(value = "user", key = "#tagName")
    public Optional<User> findByTagname(String tagName){
        Optional<User> oUser = userRepository.findByTagname(tagName);
        return oUser;
    }

    @Override
    public User updateUser(Long id, User form){
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
        user.setFirstName(form.getFirstName());
        user.setMiddleName(form.getMiddleName());
        user.setLastName(form.getLastName());
        user.setAbout(form.getAbout());
        user.setHeadline(form.getHeadline());
        user.setDob(user.getDob());

        user = save(user);
        evictUserFromCache(user);
        putUserToCache(user);
        return user;
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
        User user = findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));

        if (user.getContacts() == null) {
            user.setContacts(new ArrayList<>());
        }
        user.getContacts().add(contact);
        save(user);

        evictUserFromCache(user);

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

        evictUserFromCache(user);
        save(user);
    }

    private void evictUserFromCache(User user) {
        Cache userCache = cacheManager.getCache("user");
        if (userCache != null) {
            userCache.evict(user.getId());
        }
    }

    private void putUserToCache(User user) {
        Cache userCache = cacheManager.getCache("user");
        if (userCache != null) {
            userCache.put(user.getId(), user);
        }
    }

    @Override
    public void removeContact(Long id, Long contactId){
        User user = findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
        if (user.getContacts() == null) {
            user.setContacts(new ArrayList<>());
        }
        Contact contactToUpdate = user.getContacts().stream().filter(con -> con.getId().equals(contactId)).findFirst().orElse(null);
        if (contactToUpdate == null) {
            return;
        }
        user.getContacts().remove(contactToUpdate);

        evictUserFromCache(user);
        save(user);
    }
}
