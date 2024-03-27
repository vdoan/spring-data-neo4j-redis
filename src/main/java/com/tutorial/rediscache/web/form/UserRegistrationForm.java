package com.tutorial.rediscache.web.form;

import com.tutorial.rediscache.dao.entity.contact.PostalAddress;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegistrationForm {
    @NotNull
    private String firstName;

    private String middleName;
    @NotNull
    private String lastName;
    private String phoneNumber;
    @NotNull
    private String email;
    private String jobTitle;
    private String headline;
    private String avatar;
    private String username;
    @NotNull
    private String password;
    private Long userId;
    private String appId;
    private String token;
    private PostalAddress primaryAddress;

}
