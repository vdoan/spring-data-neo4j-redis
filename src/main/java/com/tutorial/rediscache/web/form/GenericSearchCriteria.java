package com.tutorial.rediscache.web.form;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GenericSearchCriteria implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String firstName;
    private String lastName;
    private String tagname;
    private List<Long> ids;
    private List<String> country; // test this one
    private List<String> state;
    private List<String> city;
    private List<String> size;
    private List<Integer> rating;
    private List<String> industries;
    private List<String> categories;
    private List<Long> brands;
    private List<String> jobTitles;
}
