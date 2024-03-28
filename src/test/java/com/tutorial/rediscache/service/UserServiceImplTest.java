package com.tutorial.rediscache.service;

import com.tutorial.rediscache.config.RedisConfig;
import com.tutorial.rediscache.constant.ContactType;
import com.tutorial.rediscache.constant.ContactTypeEnum;
import com.tutorial.rediscache.dao.entity.contact.Contact;
import com.tutorial.rediscache.dao.entity.party.User;
import com.tutorial.rediscache.repository.UserRepository;
import com.tutorial.rediscache.web.form.GenericSearchCriteria;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
public class UserServiceImplTest {

    public static GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:7-alpine"));
    public static GenericContainer neo4jContainer = new GenericContainer(DockerImageName.parse("neo4j:5"));



    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CacheManager cacheManager;

    @BeforeAll
    static void beforeAll() {
        redis.setPortBindings(List.of("6379:6379"));
        redis.start();

        neo4jContainer.setPortBindings(List.of("7474:7474", "7687:7687"));
        neo4jContainer.setEnv(
                List.of("NEO4J_AUTH=neo4j/neo4jneo4j", "NEO4J_dbms_security_auth__enabled=false"));
        neo4jContainer.withExposedPorts(7474, 7687).waitingFor(Wait.forListeningPort()).start();
    }

    @AfterAll
    static void afterAll() {
        redis.stop();
        neo4jContainer.stop();
    }

    @BeforeEach
    void before() {
        userRepository.deleteAll();
        cacheManager.getCacheNames().forEach(cacheName -> cacheManager.getCache(cacheName).invalidate());
    }

    @Test
    void testSaveUser() {
        User added = userService.add(getTestUser("John", "Doe", null));

        assertThat(added.getId(), notNullValue());

        Optional<User> byId = userService.findById(added.getId());

        assertThat("user is saved, id is present", byId.isPresent(), is(true));
        assertThat("User object found from service is not the same object that was persisted previously",added == byId.get(), is(false));
        assertThat("cache has User instance", cacheManager.getCache(RedisConfig.USER_CACHE_NAME).get(added.getId()), notNullValue());
    }

    @Test
    void testGetByTagname() {
        String tagName = "simpleTag";

        User user = getTestUser("first", "second", tagName);
        User saved = userService.save(user);

        Optional<User> byTagname = userService.findByTagname(tagName);
        assertThat(byTagname.isPresent(), is(true));
        assertThat(byTagname.get().getTagname(), is(tagName));
    }

    @Test
    void testUpdateUser() {
        User user = getTestUser("first", "second", "tagName");
        User saved = userService.save(user);

        assertThat("user is cached", cacheManager.getCache(RedisConfig.USER_CACHE_NAME).get(saved.getId()), notNullValue());

        // updating user
        String modifiedFirstName = "modifiedFirstName";
        String modifiedLastName = "modifiedLastName";
        User modifiedUser = getTestUser(modifiedFirstName, modifiedLastName, "tagName");
        String newHeadline = "newHeadline";
        modifiedUser.setHeadline(newHeadline);
        String newAbout = "newAbout";
        modifiedUser.setAbout(newAbout);
        String newDob = "newDob";
        modifiedUser.setDob(newDob);
        userService.updateUser(saved.getId(), modifiedUser);
        assertThat("user is removed from cache", cacheManager.getCache(RedisConfig.USER_CACHE_NAME).get(saved.getId()), nullValue());

        // verify update
        User updatedUserLoadedFromService = userService.findById(saved.getId()).get();
        assertThat("first name updated", updatedUserLoadedFromService.getFirstName(), is(modifiedFirstName));
        assertThat("last name updated", updatedUserLoadedFromService.getLastName(), is(modifiedLastName));
        assertThat("about  inserted", updatedUserLoadedFromService.getAbout(), is(newAbout));
        assertThat("headline  inserted", updatedUserLoadedFromService.getHeadline(), is(newHeadline));
        assertThat("DOB  inserted", updatedUserLoadedFromService.getDob(), is(newDob));


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
        User user = getTestUserWithContacts();
        user = userService.save(user);

        user.getContacts().forEach(contact -> {
            assertThat(
                    "contacts are present in the Contact cache once user is saved",
                    cacheManager.getCache(RedisConfig.CONTACT_CACHE_NAME).get(contact.getId()), notNullValue());
        });
    }

    @Test
    void testAddContact() {

        User user = getTestUserWithContacts();
        userService.save(user);

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
    }

    @Test
    void testUpdateContact() {
        User user = getTestUserWithContacts();
        userService.save(user);

        Contact contactToUpdate = new Contact();

        contactToUpdate.setType(ContactTypeEnum.POSTAL_ADDRESS);
        contactToUpdate.setValue("newValue");
        contactToUpdate.setNote("newNote");
        contactToUpdate.setFromDate(new Date().getTime());
        contactToUpdate.setThruDate(new Date().getTime());
        contactToUpdate.setAllowSolicitation(true);
        contactToUpdate.setExtension(42);
        contactToUpdate.setVerified(true);
        contactToUpdate.setPrimary(true);
        contactToUpdate.setContactType(ContactType.MOBILE);

        Long contactIdToUpdate = user.getContacts().get(0).getId();
        userService.updateContact(user.getId(), contactIdToUpdate, contactToUpdate);

        Contact retrievedContact = userService.getContact(user.getId(), contactIdToUpdate);
        assertThat(retrievedContact.getType(), is(ContactTypeEnum.POSTAL_ADDRESS));
        assertThat(retrievedContact.getValue(), is("newValue"));
        assertThat(retrievedContact.getNote(), is("newNote"));
        assertThat(retrievedContact.getExtension(), is(42));
        assertThat(retrievedContact.isAllowSolicitation(), is(true));

        Contact fromCache = (Contact) cacheManager.getCache(RedisConfig.CONTACT_CACHE_NAME).get(contactIdToUpdate).get();

        assertThat(fromCache.getType(), is(ContactTypeEnum.POSTAL_ADDRESS));
        assertThat(fromCache.getValue(), is("newValue"));
        assertThat(fromCache.getNote(), is("newNote"));
        assertThat(fromCache.getExtension(), is(42));
        assertThat(fromCache.isAllowSolicitation(), is(true));
    }

    @Test
    void testRemoveContact() {
        User user = getTestUserWithContacts();
        userService.save(user);

        // remove contact
        Long contactIdToRemove = user.getContacts().get(1).getId();
        userService.removeContact(user.getId(), contactIdToRemove);

        Contact afterDeletion = userService.getContact(user.getId(), contactIdToRemove);

        assertThat(afterDeletion, nullValue());
        assertThat("contact is removed from cache", cacheManager.getCache(RedisConfig.CONTACT_CACHE_NAME).get(contactIdToRemove), nullValue());
    }

    private User getTestUser(String firstName, String lastName, String tagName) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setTagname(tagName);
        return user;
    }

    @NotNull
    private User getTestUserWithContacts() {
        User user = getTestUser("first", "second", "tagName");
        user.setContacts(List.of(
                Contact.create(ContactTypeEnum.EMAIL_ADDRESS, "first@second.com", true, "note"),
                Contact.create(ContactTypeEnum.TELECOM_NUMBER, "911", false, "note"),
                Contact.create(ContactTypeEnum.ELECTRONIC_ADDRESS, "email@email.com", false, "note"),
                Contact.create(ContactTypeEnum.DOMAIN_NAME, "google.com", false, "note")
        ));
        return user;
    }
}
