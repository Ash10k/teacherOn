package com.ash.teacheron.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class recommendedRequest {

    @SerializedName("requirement_id")
    public String requirement_id;

    @SerializedName("subject_id")
    public String subject_id;

    @SerializedName("subject")
    public String subject;

    @SerializedName("from_level_id")
    public String from_level_id;

    @SerializedName("to_level_id")
    public String to_level_id;

    @SerializedName("location")
    public String location;

    public recommendedRequest(String requirement_id, String subject_id, String subject, String from_level_id, String to_level_id, String location) {
        this.requirement_id = requirement_id;
        this.subject_id = subject_id;
        this.subject = subject;
        this.from_level_id = from_level_id;
        this.to_level_id = to_level_id;
        this.location = location;
    }
}
