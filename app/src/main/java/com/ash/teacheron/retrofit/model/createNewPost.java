package com.ash.teacheron.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class createNewPost {
    @SerializedName("user_id") public String user_id;
    @SerializedName("requirement_type") public String requirement_type;
    @SerializedName("tutor_option") public String tutor_option;
    @SerializedName("travel_limit") public String travel_limit;
    @SerializedName("budget") public String budget;
    @SerializedName("budget_type") public String budget_type;
    @SerializedName("gender_preference") public String gender_preference;
    @SerializedName("tutor_type") public String tutor_type;
    @SerializedName("budget_currency_id") public String budget_currency_id;
    @SerializedName("communicate_language_id") public String communicate_language_id;
    @SerializedName("tutor_from_country_id") public String tutor_from_country_id;
    @SerializedName("password") public String password;

    @SerializedName("level_id") public String level_id;
    @SerializedName("subject_id") public String subject_id;
    @SerializedName("requirements") public String requirements;
    @SerializedName("location") public String location;
    @SerializedName("id") public String id;




    public createNewPost(String sub, String lev, String user_id, String requirement_type, String tutor_option, String travel_limit, String budget, String budget_type, String gender_preference, String tutor_type, String budget_currency_id, String communicate_language_id, String tutor_from_country_id, String requirement,String location) {
        this.user_id = user_id;
        this.requirement_type = requirement_type;
        this.tutor_option = tutor_option;
        this.travel_limit = travel_limit;
        this.budget = budget;
        this.budget_type = budget_type;
        this.gender_preference = gender_preference;
        this.tutor_type = tutor_type;
        this.budget_currency_id = budget_currency_id;
        this.communicate_language_id = communicate_language_id;
        this.tutor_from_country_id = tutor_from_country_id;
        this.requirements=requirement;
        this.subject_id=sub;
        this.level_id=lev;
        this.location=location;
    }


    public createNewPost(String sub, String lev, String user_id, String requirement_type, String tutor_option, String travel_limit, String budget, String budget_type, String gender_preference, String tutor_type, String budget_currency_id, String communicate_language_id, String tutor_from_country_id, String requirement,String location,String id) {
        this.user_id = user_id;
        this.requirement_type = requirement_type;
        this.tutor_option = tutor_option;
        this.travel_limit = travel_limit;
        this.budget = budget;
        this.budget_type = budget_type;
        this.gender_preference = gender_preference;
        this.tutor_type = tutor_type;
        this.budget_currency_id = budget_currency_id;
        this.communicate_language_id = communicate_language_id;
        this.tutor_from_country_id = tutor_from_country_id;
        this.requirements=requirement;
        this.subject_id=sub;
        this.level_id=lev;
        this.location=location;
        this.id=id;
    }




}
