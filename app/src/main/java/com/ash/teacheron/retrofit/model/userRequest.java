package com.ash.teacheron.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class userRequest {

    @SerializedName("user_id")
    public String user_id;

    public userRequest(String user_id) {
        this.user_id = user_id;
    }
}
