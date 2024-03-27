package com.tutorial.rediscache.service;

import com.tutorial.rediscache.dao.entity.contact.Contact;
import com.tutorial.rediscache.dao.entity.party.*;
import com.tutorial.rediscache.dao.entity.preferences.PartyPreference;
import com.tutorial.rediscache.exception.*;
import com.tutorial.rediscache.repository.*;
import com.tutorial.rediscache.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@EnableCaching
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {


    private ObjectMapper mapper = new ObjectMapper();
    private Gson gson = new Gson();

    private final MessageSource messageSource;
    @Autowired
    private final UserRepository userRepository;



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
        // ToDo: Look up cache first, if not in cache, fetch from DB and store in cache
        Optional<User> oUser = userRepository.findById(id);
        return oUser;
    }


    @Override
    @Cacheable(value = "user", key = "#tagname")
    public Optional<User> findByTagname(String tagName){
        // ToDo: Look up cache first, if not in cache, fetch from DB and store in cache
        Optional<User> oUser = userRepository.findByTagname(tagName);
        return oUser;
    }

    @Override
    public User updateUser(Long id, User form){
        // ToDo: Update DB and Cache
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
        user.setFirstName(form.getFirstName());
        user.setMiddleName(form.getMiddleName());
        user.setLastName(form.getLastName());
        user.setAbout(form.getAbout());
        user.setHeadline(form.getHeadline());
        user.setDob(user.getDob());

        user = save(user);
        return user;
    }


    @Override
    public void updateContact(Long id, Long contactId, Contact contact){
        // ToDo: Update DB & Cache Contact Table
    }
}
