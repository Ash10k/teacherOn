package com.ash.teacheron.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class ChatResponseDataModel {


    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("data")
    @Expose
    public List<ChatUser> data;

    public String getStatus() {
        return status;
    }

    public List<ChatUser> getData() {
        return data;
    }

    public static class ChatUser {
        @SerializedName("id")
        @Expose
        public int id;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("profile_image")
        @Expose
        public String profileImage;

        @SerializedName("student_meta_id")
        @Expose
        public String studentMetaId;

        @SerializedName("meta_details")
        @Expose
        public Map<String, Object> metaDetails;

        @SerializedName("recent_message")
        @Expose
        public String recentMessage;

        @SerializedName("recent_message_human_time")
        @Expose
        public String recentMessageHumanTime;

        @SerializedName("recent_message_time")
        @Expose
        public String recentMessageTime;

        @SerializedName("seen_status")
        @Expose
        public int seenStatus;

        @SerializedName("user_details")
        @Expose
        public UserDetails userDetails;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public String getStudentMetaId() {
            return studentMetaId;
        }

        public Map<String, Object> getMetaDetails() {
            return metaDetails;
        }

        public String getRecentMessage() {
            return recentMessage;
        }

        public String getRecentMessageHumanTime() {
            return recentMessageHumanTime;
        }

        public String getRecentMessageTime() {
            return recentMessageTime;
        }

        public int getSeenStatus() {
            return seenStatus;
        }

        public UserDetails getUserDetails() {
            return userDetails;
        }
    }

    public static class UserDetails {
        @SerializedName("id")
        @Expose
        public int id;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("phone")
        @Expose
        public String phone;

        @SerializedName("location")
        @Expose
        public String location;

        @SerializedName("profile_image_url")
        @Expose
        public String profileImageUrl;

        @SerializedName("teacher_certification")
        @Expose
        public List<TeacherCertification> teacherCertification;

        @SerializedName("teacher_subject")
        @Expose
        public List<TeacherSubject> teacherSubject;

        @SerializedName("teacher_meta")
        @Expose
        public TeacherMeta teacherMeta;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getLocation() {
            return location;
        }

        public String getProfileImageUrl() {
            return profileImageUrl;
        }

        public List<TeacherCertification> getTeacherCertification() {
            return teacherCertification;
        }

        public List<TeacherSubject> getTeacherSubject() {
            return teacherSubject;
        }

        public TeacherMeta getTeacherMeta() {
            return teacherMeta;
        }
    }

    public static class TeacherCertification {
        @SerializedName("id")
        @Expose
        public int id;

        @SerializedName("institution_name")
        @Expose
        public String institutionName;

        @SerializedName("degree_name")
        @Expose
        public String degreeName;

        public int getId() {
            return id;
        }

        public String getInstitutionName() {
            return institutionName;
        }

        public String getDegreeName() {
            return degreeName;
        }
    }

    public static class TeacherSubject {
        @SerializedName("id")
        @Expose
        public int id;

        @SerializedName("option_subject")
        @Expose
        public Subject optionSubject;

        public int getId() {
            return id;
        }

        public Subject getOptionSubject() {
            return optionSubject;
        }
    }

    public static class Subject {
        @SerializedName("id")
        @Expose
        public int id;

        @SerializedName("title")
        @Expose
        public String title;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }

    public static class TeacherMeta {
        @SerializedName("id")
        @Expose
        public int id;

        @SerializedName("speciality")
        @Expose
        public String speciality;

        @SerializedName("gender")
        @Expose
        public String gender;

        public int getId() {
            return id;
        }

        public String getSpeciality() {
            return speciality;
        }

        public String getGender() {
            return gender;
        }
    }
}


 
