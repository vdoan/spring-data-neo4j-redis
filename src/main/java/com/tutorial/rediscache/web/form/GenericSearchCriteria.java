package com.tutorial.rediscache.web.form;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class GenericSearchCriteria implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name="";
    private String tagname="";
    private List<Long> ids = new ArrayList<>();
    private List<String> country = new ArrayList<>();
    private List<String> state = new ArrayList<>();
    private List<String> city = new ArrayList<>();
    private List<String> size = new ArrayList<>();
    private List<Integer> rating = new ArrayList<>();
    private List<String> industries = new ArrayList<>();
    private List<String> categories = new ArrayList<>();
    private List<Long> brands = new ArrayList<>();
    private List<String> jobTitles = new ArrayList<>();
}
