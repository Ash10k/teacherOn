package com.ash.teacheron.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class registerResponseStud {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("otp")
        public String otp;

        @SerializedName("id")
        public String id;
    }



}
