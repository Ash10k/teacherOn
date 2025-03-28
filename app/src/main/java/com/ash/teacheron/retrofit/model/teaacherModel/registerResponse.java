package com.ash.teacheron.retrofit.model.teaacherModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class registerResponse
{

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public CurrentUser data;
    public static class CurrentUser {
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
        public String profileIntro;

        @SerializedName("profile_description")
        public String profileDescription;

        @SerializedName("otp")
        public String otp;

        @SerializedName("privacy_accepted")
        public String privacyAccepted;

        @SerializedName("created_at")
        public String createdAt;

        @SerializedName("updated_at")
        public String updatedAt;

        @SerializedName("deleted_at")
        public String deletedAt;

        @SerializedName("profile_image_url")
        public String profileImageUrl;

        @SerializedName("teacher_meta")
        public TeacherMeta teacherMeta;

        @SerializedName("teacher_certification")
        public List<Object> teacherCertification; // Assuming array

        @SerializedName("teacher_experience")
        public List<Object> teacherExperience; // Assuming array

        @SerializedName("teacher_detail")
        public Object teacherDetail; // Can be null

    }

    public static class TeacherMeta {
        @SerializedName("id")
        public int id;

        @SerializedName("user_id")
        public int userId;

        @SerializedName("post_code")
        public String postCode;

        @SerializedName("speciality")
        public String speciality;

        @SerializedName("gender")
        public String gender;

        @SerializedName("dob")
        public String dob;

        @SerializedName("created_at")
        public String createdAt;

        @SerializedName("updated_at")
        public String updatedAt;
    }

}
