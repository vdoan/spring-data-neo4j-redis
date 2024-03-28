package com.tutorial.rediscache.service;

import com.tutorial.rediscache.constant.ContactTypeEnum;
import com.tutorial.rediscache.dao.entity.contact.Contact;
import com.tutorial.rediscache.dao.entity.party.User;
import com.tutorial.rediscache.repository.UserRepository;
import com.tutorial.rediscache.web.form.GenericSearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void before() {
        userRepository.deleteAll();
    }


    @Test
    void testSaveUser() {
        User added = userService.add(getTestUser("John", "Doe", null));

        assertThat(added.getId(), notNullValue());

        Optional<User> byId = userService.findById(added.getId());

        assertThat(byId.isPresent(), is(true));
        assertThat(added == byId.get(), is(false));
    }

    @Test
    void testGetByTagname() {
        String tagName = "simpleTag";

        User user = getTestUser("first", "second", tagName);
        user.setTagname(tagName);
        User saved = userService.save(user);

        Optional<User> byTagname = userService.findByTagname(tagName);
        assertThat(byTagname.isPresent(), is(true));
        assertThat(byTagname.get().getTagname(), is(tagName));
    }

    @Test
    void testSearchUserByCriteria() {
        userService.add(getTestUser("Ada", "Lovelace", null));
        userService.add(getTestUser("Albert", "Einstein", null));
        userService.add(getTestUser("Alan", "Guth", null));
        userService.add(getTestUser("Alan", "Turing", null));

        {
            GenericSearchCriteria criteria = new GenericSearchCriteria();
            criteria.setFirstName("Alan");
            Page<User> users = userService.searchUser(criteria, Pageable.ofSize(10));

            assertThat(users, notNullValue());
            assertThat(users.getContent(), hasSize(2));
            assertThat(users.getContent().stream().map(User::getFirstName).toList(), hasItem("Alan"));
        }
        {
            GenericSearchCriteria criteria = new GenericSearchCriteria();
            criteria.setFirstName("Alan");
            criteria.setLastName("ring");
            Page<User> users = userService.searchUser(criteria, Pageable.ofSize(10));

            assertThat(users, notNullValue());
            assertThat(users.getContent(), hasSize(1));
            assertThat(users.getContent().get(0).getFirstName(), is("Alan"));
            assertThat(users.getContent().get(0).getLastName(), is("Turing"));
        }
        {
            GenericSearchCriteria criteria = new GenericSearchCriteria();
            criteria.setLastName("e");
            Page<User> users = userService.searchUser(criteria, Pageable.ofSize(10));

            assertThat(users, notNullValue());
            assertThat(users.getContent(), hasSize(2));
            assertThat(users.getContent().stream().map(User::getLastName).toList(), hasItems("Lovelace", "Einstein"));
        }
    }


    @Test
    void testGetContacts() {
        User user = getTestUser("first", "second", "tagName");
        user.setContacts(List.of(
                Contact.create(ContactTypeEnum.EMAIL_ADDRESS, "first@second.com", true, "note"),
                Contact.create(ContactTypeEnum.TELECOM_NUMBER, "911", false, "note"),
                Contact.create(ContactTypeEnum.ELECTRONIC_ADDRESS, "email@email.com", false, "note"),
                Contact.create(ContactTypeEnum.DOMAIN_NAME, "google.com", false, "note")
        ));
        user = userService.save(user);
        List<Contact> userContacts = userService.getUserContacts(user.getId());

        assertThat(userContacts, notNullValue());
        assertThat(userContacts, hasSize(4));
        assertThat(userContacts.stream().map(Contact::getValue).toList(), hasItems("911", "first@second.com"));

        Contact contact = userService.getContact(user.getId(), user.getContacts().get(1).getId());
        assertThat(contact.getType(), is(ContactTypeEnum.TELECOM_NUMBER));

        // add contact
        Contact manuallyAdded = userService.addContact(user.getId(), Contact.create(ContactTypeEnum.POSTAL_ADDRESS, "Area 51, Nevada", false, "manually added"));
        Optional<User> withAddedContact = userService.findById(user.getId());

        assertThat(withAddedContact.get().getContacts(), hasSize(5));
        Contact theOneAdded = withAddedContact.get().getContacts().stream().filter(c -> c.getType().equals(ContactTypeEnum.POSTAL_ADDRESS)).findAny().orElse(null);
        assertThat(theOneAdded.getType(), is(ContactTypeEnum.POSTAL_ADDRESS));
        assertThat(theOneAdded.getValue(), is("Area 51, Nevada"));


        userService.removeContact(user.getId(), manuallyAdded.getId());

        Contact afterDeletion = userService.getContact(user.getId(), manuallyAdded.getId());

        assertThat(afterDeletion, nullValue());
    }

    private User getTestUser(String firstName, String lastName, String tagName) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setTagname(tagName);
        return user;
    }
}
