package com.tutorial.rediscache.service;

import com.tutorial.rediscache.dao.entity.contact.Contact;
import com.tutorial.rediscache.dao.entity.party.*;
import com.tutorial.rediscache.web.form.GenericSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<User> searchUser(GenericSearchCriteria criteria, Pageable pageable);

    public List<Contact> getUserContacts(Long id);
    public Contact getContact(Long id, Long contactId);
    public Contact addContact(Long id, Contact contact);
    void updateContact(Long id, Long contactId, Contact contact);
    void removeContact(Long id, Long contactId);
}
