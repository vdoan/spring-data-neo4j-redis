package com.tutorial.rediscache.service;

import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanOperation;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimplePath;
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
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
        return userRepository.save(user);
    }

    @Override
    @CacheEvict(value = RedisConfig.USER_CACHE_NAME, key = "#result.id")
    public User save(User user) {
        return userRepository.save(user);
    }


    @Override
    @Cacheable(value = RedisConfig.USER_CACHE_NAME, key = "#id", unless = "#result == null")
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


    @Override
    @Cacheable(value = RedisConfig.USER_CACHE_NAME, key = "#tagName", unless = "#result == null")
    public Optional<User> findByTagname(String tagName) {
        Optional<User> oUser = userRepository.findByTagname(tagName);
        return oUser;
    }

    @Override
    @CacheEvict(value = RedisConfig.USER_CACHE_NAME, key = "#id", beforeInvocation = true)
    public User updateUser(Long id, User form) {
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
        user.setFirstName(form.getFirstName());
        user.setMiddleName(form.getMiddleName());
        user.setLastName(form.getLastName());
        user.setAbout(form.getAbout());
        user.setHeadline(form.getHeadline());
        user.setDob(form.getDob());

        return save(user);
    }

    @Override
    public Page<User> searchUser(GenericSearchCriteria criteria, Pageable pageable) {
        User searchExample = User.createForSearchExample();

        searchExample.setFirstName(criteria.getFirstName());
        searchExample.setLastName(criteria.getLastName());
        searchExample.setTagname(criteria.getTagname());

        Path<User> userPath = Expressions.path(User.class, "user");
        SimplePath<User> firstNamePath = Expressions.path(User.class, userPath, "firstName");
        SimplePath<User> countryPath = Expressions.path(User.class, userPath, "country");
        List<BooleanExpression> expressions = new ArrayList<>();

        if (criteria.getFirstName() != null) {
            expressions.add(Expressions.predicate(Ops.LIKE, firstNamePath, Expressions.asString(criteria.getFirstName())));
        }
        if (criteria.getCountry() != null && !criteria.getCountry().isEmpty()) {
            expressions.add(
                    generateSearchExpressionFromCollectionParameter(
                            criteria.getCountry(),
                            countryPath));
        }

        Iterable<User> all = userRepository.findAll(
                Expressions.allOf(expressions.toArray(new BooleanExpression[]{}))
        );

        ArrayList<User> users = new ArrayList<>();
        all.iterator().forEachRemaining(users::add);
        return new PageImpl<>(users);
    }

    private static BooleanExpression generateSearchExpressionFromCollectionParameter(List<String> searchParams, SimplePath<User> userPath) {
        return Expressions.anyOf(searchParams.stream().map(country ->
                Expressions.predicate(Ops.EQ, userPath, Expressions.asSimple(country))
        ).toArray(BooleanOperation[]::new));
    }

    @Override
    public List<Contact> getUserContacts(Long userId) {
        return selfReference.findById(userId).map(User::getContacts).orElse(null);
    }

    @Override
    public Contact getContact(Long userId, Long contactId) {
        List<Contact> userContacts = getUserContacts(userId);
        return userContacts == null
                ? null
                : userContacts.stream()
                .filter(contact -> contact.getId().equals(contactId))
                .findFirst()
                .orElse(null);
    }

    @Override
    @CacheEvict(value = RedisConfig.USER_CACHE_NAME, key = "#id")
    public Contact addContact(Long id, Contact contact) {
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));

        if (user.getContacts() == null) {
            user.setContacts(new ArrayList<>());
        }
        user.getContacts().add(contact);
        save(user);

        return contact;
    }

    @Override
    @CacheEvict(value = RedisConfig.USER_CACHE_NAME, key = "#id")
    public void updateContact(Long id, Long contactId, Contact contact) {
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
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

    @Override
    @CacheEvict(value = RedisConfig.USER_CACHE_NAME, key = "#id", beforeInvocation = true)
    public void removeContact(Long id, Long contactId) {
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
        if (user.getContacts() == null) {
            user.setContacts(new ArrayList<>());
        }
        user.getContacts().removeIf(con -> con.getId().equals(contactId));

        save(user);
    }
}
