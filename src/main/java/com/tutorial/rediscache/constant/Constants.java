/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tutorial.rediscache.constant;

import java.time.ZoneId;
import java.util.regex.Pattern;

/**
 *
 * @Viet Doan
 */
public interface Constants {

    public final static String SUCCESS = "Success";
    public final static String UNIQUE_ID_ALREADY_EXIST = "Unique id already exist";
    public final static String USER_NOT_FOUND = "User not found";
    public final static String DEVICE_NOT_FOUND = "User not found";
    public final static String INTERNAL_SERVER_ERROR = "Internal server error";
    public final static String EXCEPTION_FAILED = "Exception failed";
    public final static String NOT_FOUND = "Not Found";
    public final static String FORBIDDEN = "Forbidden";
    public final static String USER_PROFILE_NOT_UPDATED = "User profile not updated";

    String DEFAULT_LOCALE = "en";

    //Pagination
    public final static String DEFAULT_DIRECTION = "DESC";
    public final static int DEFAULT_PAGE_SIZE = 10;
    public final static int DEFAULT_PAGE = 0;

    //  Default locations
    public final static String DEFAULT_CITY = "San Jose";
    public final static String DEFAULT_STATE = "California";
    public final static String DEFAULT_COUNTRY = "United States";

    public final static String BUCKET = "accessed";

    int TITLE_IN_URL_MAX_LENGTH = 100;

    ZoneId TIMEZONE = ZoneId.of("America/Los_Angeles");


}
