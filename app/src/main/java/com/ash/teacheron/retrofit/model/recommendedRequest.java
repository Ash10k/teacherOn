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

    @SerializedName("student_meta_id")
    public String student_meta_id;

    @SerializedName("page")
    public int page;

    @SerializedName("other_user_id")
    public String other_user_id;



    public recommendedRequest(String requirement_id, String subject_id, String subject, String from_level_id, String to_level_id, String location,int page) {
        this.requirement_id = requirement_id;
        this.subject_id = subject_id;
        this.subject = subject;
        this.from_level_id = from_level_id;
        this.to_level_id = to_level_id;
        this.location = location;
        this.page=page;
    }

    public recommendedRequest(String student_meta_id) {
        this.student_meta_id = student_meta_id;
    }

    public recommendedRequest(String other_user_id, String student_meta_id, int page) {
        this.other_user_id = other_user_id;
        this.student_meta_id = student_meta_id;
        this.page = page;
    }
}
