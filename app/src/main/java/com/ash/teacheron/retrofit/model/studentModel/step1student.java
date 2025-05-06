package com.ash.teacheron.retrofit.model.studentModel;

import com.google.gson.annotations.SerializedName;

public class step1student {
    @SerializedName("email") public String email;
    @SerializedName("name") public String name;
    @SerializedName("phone") public String phone;
    @SerializedName("location") public String location;
    @SerializedName("subject_id") public String subject_id;
    @SerializedName("level_id") public String level_id;
    @SerializedName("user_type") public String user_type;
    @SerializedName("requirements") public String requirements;
    @SerializedName("password") public String password;
    @SerializedName("latitude") public String latitude;
    @SerializedName("longitude") public String longitude;

    @SerializedName("device_type")
    String device_type;

    @SerializedName("udid")
    String deviceId;

    public step1student(String email, String name, String phone, String location, String subject_id, String level_id, String user_type, String requirements, String password, String latitude, String longitude,String deviceType, String deviceId) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.subject_id = subject_id;
        this.level_id = level_id;
        this.user_type = user_type;
        this.requirements = requirements;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
        this.device_type=deviceType;
        this.deviceId=deviceId;

    }
}
