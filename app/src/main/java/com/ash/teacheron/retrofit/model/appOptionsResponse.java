package com.ash.teacheron.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class appOptionsResponse
{

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public Data data;

    public   class Data {
        @SerializedName("levels")
        public List<Level> levels;

        @SerializedName("subjects")
        public List<Subject> subjects;
    }

    public  class Level {
        @SerializedName("id")
        public int id;

        @SerializedName("title")
        public String title;


    }

    public  class Subject {
        @SerializedName("id")
        public int id;

        @SerializedName("title")
        public String title;


    }
}
