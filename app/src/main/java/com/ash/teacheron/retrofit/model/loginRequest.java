package com.ash.teacheron.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class loginRequest
{

    @SerializedName("email") String email;

    @SerializedName("password") String password;

    @SerializedName("firebase_id") String firebase_id;


    public loginRequest(String email, String password, String firebase_id) {
        this.email = email;
        this.password = password;
        this.firebase_id = firebase_id;
    }


}
