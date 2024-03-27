package com.tutorial.rediscache.web.controller;

import com.tutorial.rediscache.dao.entity.party.User;
import com.tutorial.rediscache.exception.*;
import com.tutorial.rediscache.service.*;
import com.tutorial.rediscache.dao.entity.contact.Contact;
import com.tutorial.rediscache.web.RestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@EnableCaching
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


    @PutMapping("{id}/contacts/{contactId}")
    public RestResponse updateContacts(@RequestHeader HttpHeaders header, @PathVariable Long id, @PathVariable Long contactId, @RequestBody Contact form) {
        Long userId = Long.parseLong(String.join("", header.get("UserId")));

        userService.updateContact(id, contactId, form);
        return RestResponse.builder(null).build();
    }


}
