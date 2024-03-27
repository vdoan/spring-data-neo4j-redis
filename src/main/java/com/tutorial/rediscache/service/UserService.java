package com.tutorial.rediscache.service;

import com.tutorial.rediscache.dao.entity.contact.Contact;
import com.tutorial.rediscache.dao.entity.party.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
public interface UserService {

    User add(User user);

    User save(User user);

    Optional<User> findById(Long id);
    Optional<User> findByTagname(String tagname);

    User updateUser(Long id, User user);
    void updateContact(Long id, Long contactId, Contact contact);
}
