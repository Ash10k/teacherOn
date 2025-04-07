package com.ash.teacheron.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class requirementResponse {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("data")
        public List<TutorRequest> dataList;


    }
    
    public class TutorRequest {

        @SerializedName("id")
        public int id;

        @SerializedName("user_id")
        public int userId;

        @SerializedName("level_id")
        public int levelId;

        @SerializedName("requirements")
        public String requirements;

        @SerializedName("subject_id")
        public int subjectId;

        @SerializedName("requirement_type")
        public String requirementType;

        @SerializedName("tutor_option")
        public String tutorOption;

        @SerializedName("travel_limit")
        public int travelLimit;

        @SerializedName("budget")
        public String budget;

        @SerializedName("budget_type")
        public String budgetType;

        @SerializedName("gender_preference")
        public String genderPreference;

        @SerializedName("tutor_type")
        public String tutorType;

        @SerializedName("location")
        public String location;

        @SerializedName("latitude")
        public String latitude;

        @SerializedName("longitude")
        public String longitude;

        @SerializedName("budget_currency_id")
        public String budgetCurrencyId;

        @SerializedName("communicate_language_id")
        public String communicateLanguageId;

        @SerializedName("tutor_from_country_id")
        public String tutorFromCountryId;

        @SerializedName("created_at")
        public String createdAt;

        @SerializedName("updated_at")
        public String updatedAt;

        @SerializedName("subject")
        public Subject subject;

        @SerializedName("level")
        public Level level;

        @SerializedName("user")
        public User user;

        @SerializedName("country")
        public Object country;

        @SerializedName("language")
        public Object language;

        @SerializedName("currency")
        public Object currency;

        public   class Subject {
            @SerializedName("id")
            public int id;

            @SerializedName("title")
            public String title;

            @SerializedName("created_at")
            public Object createdAt;

            @SerializedName("updated_at")
            public Object updatedAt;
        }

        public   class Level {
            @SerializedName("id")
            public int id;

            @SerializedName("title")
            public String title;

            @SerializedName("created_at")
            public Object createdAt;

            @SerializedName("updated_at")
            public Object updatedAt;
        }

        public   class User {
            @SerializedName("id")
            public int id;

            @SerializedName("name")
            public String name;

            @SerializedName("email")
            public String email;

            @SerializedName("phone")
            public String phone;

            @SerializedName("location")
            public String location;

            @SerializedName("latitude")
            public String latitude;

            @SerializedName("longitude")
            public String longitude;

            @SerializedName("user_role")
            public String userRole;

            @SerializedName("user_type")
            public String userType;

            @SerializedName("profile_active")
            public String profileActive;

            @SerializedName("sign_up_completed")
            public String signUpCompleted;

            @SerializedName("profile_image")
            public String profileImage;

            @SerializedName("profile_intro")
            public Object profileIntro;

            @SerializedName("profile_description")
            public Object profileDescription;

            @SerializedName("otp")
            public String otp;

            @SerializedName("privacy_accepted")
            public String privacyAccepted;

            @SerializedName("created_at")
            public String createdAt;

            @SerializedName("updated_at")
            public String updatedAt;

            @SerializedName("deleted_at")
            public Object deletedAt;

            @SerializedName("profile_image_url")
            public String profileImageUrl;
        }
    }



}
