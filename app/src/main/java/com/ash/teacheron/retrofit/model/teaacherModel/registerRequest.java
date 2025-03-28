package com.ash.teacheron.retrofit.model.teaacherModel;

import com.google.gson.annotations.SerializedName;

public class registerRequest
{

    @SerializedName("name")
    String name;

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    @SerializedName("phone")
    String phone;

    @SerializedName("location")
    String location;

    @SerializedName("latitude")
    String latitude;

    @SerializedName("longitude")
    String longitude;

    @SerializedName("user_type")
    String userType;

    @SerializedName("post_code")
    String postCode;

    @SerializedName("speciality")
    String speciality;

    @SerializedName("gender")
    String gender;

    @SerializedName("dob")
    String dob;

    public registerRequest(String name, String email, String password, String phone, String location, String latitude, String longitude, String userType, String postCode, String speciality, String gender, String dob) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userType = userType;
        this.postCode = postCode;
        this.speciality = speciality;
        this.gender = gender;
        this.dob = dob;
    }
}
