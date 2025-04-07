package com.ash.teacheron.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class ContactUsRequest {

    @SerializedName("email") String email;
    @SerializedName("device_id") String device_id;
    @SerializedName("user_id") Integer user_id;
    @SerializedName("enquiry_type") String enquiry_type;
    @SerializedName("message") String message;

    public ContactUsRequest(String email, String device_id, Integer user_id, String enquiry_type, String message) {
        this.email = email;
        this.device_id = device_id;
        this.user_id = user_id;
        this.enquiry_type = enquiry_type;
        this.message = message;
    }

    public ContactUsRequest(String email) {
        this.email = email;
    }
}
