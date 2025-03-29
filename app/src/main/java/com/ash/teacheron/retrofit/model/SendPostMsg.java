package com.ash.teacheron.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class SendPostMsg {
    @SerializedName("receiver_id")
    public int receiver_id;

    @SerializedName("student_meta_id")
    public int student_meta_id;

    @SerializedName("message")
    public String message;

    public SendPostMsg(int receiver_id, int student_meta_id, String message) {
        this.receiver_id = receiver_id;
        this.student_meta_id = student_meta_id;
        this.message = message;
    }
}
