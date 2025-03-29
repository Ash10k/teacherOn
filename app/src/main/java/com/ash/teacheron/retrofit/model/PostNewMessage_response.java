package com.ash.teacheron.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostNewMessage_response
{
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private Message data; // Change to match the JSON structure

    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public Message getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class Message {
        @SerializedName("id")
        @Expose
        private int id;

        @SerializedName("sender_id")
        @Expose
        private int senderId;

        @SerializedName("receiver_id")
        @Expose
        private int receiverId;

        @SerializedName("student_meta_id")
        @Expose
        private int studentMetaId;

        @SerializedName("message")
        @Expose
        private String message;

        @SerializedName("created_at")
        @Expose
        private String createdAt;

        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public int getId() {
            return id;
        }

        public int getSenderId() {
            return senderId;
        }

        public int getReceiverId() {
            return receiverId;
        }

        public int getStudentMetaId() {
            return studentMetaId;
        }

        public String getMessage() {
            return message;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }
}
