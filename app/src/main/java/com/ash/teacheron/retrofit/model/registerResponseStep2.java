package com.ash.teacheron.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class registerResponseStep2 {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("token")
        public TokenData tokenData;
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

    }

    public static class TokenData {
        @SerializedName("token_type")
        public String tokenType;

        @SerializedName("expires_in")
        public int expiresIn;

        @SerializedName("access_token")
        public String accessToken;

        @SerializedName("refresh_token")
        public String refreshToken;
    }


}
