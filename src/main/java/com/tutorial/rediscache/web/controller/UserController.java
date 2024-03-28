package com.tutorial.rediscache.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.rediscache.constant.Constants;
import com.tutorial.rediscache.dao.entity.contact.Contact;
import com.tutorial.rediscache.dao.entity.party.User;
import com.tutorial.rediscache.exception.RecordNotFoundException;
import com.tutorial.rediscache.service.UserService;
import com.tutorial.rediscache.web.RestResponse;
import com.tutorial.rediscache.web.form.GenericSearchCriteria;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Api(tags = {"user-controller"})
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private ObjectMapper mapper = new ObjectMapper();

    private final MessageSource messageSource;

    private HttpServletRequest request;

    private final UserService userService;

    @PostMapping("/register")
    public RestResponse register(@Valid @RequestBody User form) {

        ObjectMapper mapper = new ObjectMapper();

        User user = userService.add(form);
        return RestResponse.builder(user).build();
    }

    @GetMapping("{id}")
    public User getUserById(@RequestHeader HttpHeaders header, @PathVariable Long id, @RequestParam(required = false, defaultValue = "false") Boolean isMinimal) {
        User user = userService.findById(id).orElseThrow(() -> new RecordNotFoundException(messageSource.getMessage("NOTFOUND", null, LocaleContextHolder.getLocale())));;;
        return user;
    }

    @PutMapping("{id}")
    public RestResponse update(@RequestHeader HttpHeaders header, @PathVariable Long id, @RequestBody  User user) {
        Long userId = Long.parseLong(String.join("", header.get("UserId")));

        User saved = userService.updateUser(id, user);
        return RestResponse.builder(saved).build();
    }

    @GetMapping("/tag/{tagname}")
    public RestResponse getUserByTagname(@RequestHeader HttpHeaders header, @PathVariable String tagname) {
        Long userId = Long.parseLong(String.join("", header.get("UserId")));
        User user = userService.findByTagname(tagname).orElseThrow(() -> new RecordNotFoundException(messageSource.getMessage("NOTFOUND", null, LocaleContextHolder.getLocale())));;
        return RestResponse.builder(user).build();
    }

    @GetMapping("/search")
    public RestResponse searchUsers(@RequestParam(value = "sortBy", required = false, defaultValue = "createdDate") String sortBy,
                                    @RequestParam(value = "direction", required = false, defaultValue = Constants.DEFAULT_DIRECTION) String direction,
                                    @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                    @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                    @RequestBody GenericSearchCriteria criteria)  {
        Pageable pageable = PageRequest.of(page, size, Sort.by("mutual.firstName").ascending());
        Page<User> result = userService.searchUser(criteria, pageable);

        return RestResponse.builder(result).build();
    }

    @GetMapping("/{id}/contacts")
    public RestResponse searchUsers(@PathVariable Long id)  {
        List<Contact> result = userService.getUserContacts(id);

        return RestResponse.builder(result).build();
    }


    @PostMapping("{id}/contacts")
    public RestResponse updateContacts(@RequestHeader HttpHeaders header, @PathVariable Long id, @RequestBody Contact form) {
        userService.addContact(id, form);
        return RestResponse.builder(null).build();
    }

    @PutMapping("{id}/contacts/{contactId}")
    public RestResponse updateContacts(@RequestHeader HttpHeaders header, @PathVariable Long id, @PathVariable Long contactId, @RequestBody Contact form) {
        Long userId = Long.parseLong(String.join("", header.get("UserId")));

        userService.updateContact(id, contactId, form);
        return RestResponse.builder(null).build();
    }

    @DeleteMapping("{id}/contacts/{contactId}")
    public RestResponse removeContacts(@RequestHeader HttpHeaders header, @PathVariable Long id, @PathVariable Long contactId) {
        userService.removeContact(id, contactId);
        return RestResponse.builder(null).build();
    }


}
