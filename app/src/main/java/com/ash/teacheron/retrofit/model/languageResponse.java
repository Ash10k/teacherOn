package com.ash.teacheron.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class languageResponse
{

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public List<Lang> language;

    public  class Lang {
        @SerializedName("id")
        public int id;

        @SerializedName("title")
        public String title;

        @SerializedName("code")
        public String code;

        @SerializedName("symbol")
        public String symbol;

        @SerializedName("name")
        public String name;




    }


}
