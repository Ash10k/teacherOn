package com.ash.teacheron.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class recommendedProfile
{
    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public TutorRequest data;
    /*public   class Data {
        @SerializedName("data")
        public List<TutorRequest> dataList;
    }*/
    public class TutorRequest {

        @SerializedName("id")
        public int id;

        @SerializedName("name")
        public String name;

        @SerializedName("email")
        public String email;

        @SerializedName("phone")
        public String phone;

        @SerializedName("location")
        public String location;

        @SerializedName("latitude")
        public String latitude;

        @SerializedName("longitude")
        public String longitude;

        @SerializedName("user_role")
        public String userRole;

        @SerializedName("user_type")
        public String userType;

        @SerializedName("profile_active")
        public String profileActive;

        @SerializedName("sign_up_completed")
        public String signUpCompleted;

        @SerializedName("profile_image")
        public String profileImage;

        @SerializedName("profile_intro")
        public Object profileIntro;

        @SerializedName("profile_description")
        public Object profileDescription;

        @SerializedName("otp")
        public String otp;

        @SerializedName("privacy_accepted")
        public String privacyAccepted;

        @SerializedName("created_at")
        public String createdAt;

        @SerializedName("updated_at")
        public String updatedAt;

        @SerializedName("deleted_at")
        public Object deletedAt;

        @SerializedName("profile_image_url")
        public String profileImageUrl;

        @SerializedName("tutor_option")
        public String tutor_option;

        @SerializedName("requirement_type")
        public String requirement_type;

        @SerializedName("requirements")
        public String requirements;



        @SerializedName("travel_limit")
        public String travel_limit;



        @SerializedName("budget")
        public String budget;

        @SerializedName("budget_type")
        public String budget_type;


        @SerializedName("user")
        public Userin userinobj;

        public class Userin{
            @SerializedName("id")
            public String id;

            @SerializedName("name")
            public String name;

            @SerializedName("phone")
            public String phone;

            @SerializedName("profile_image_url")
            public String profile_image_url;

            @SerializedName("email")
            public String email;


        }


        @SerializedName("teacher_meta")
        public TeacherMeta teacherMeta;

        @SerializedName("teacher_certification")
        public List<TeacherCertification> teacherCertification;

        @SerializedName("teacher_experience")
        public List<TeacherExperience> teacherExperience;

        @SerializedName("teacher_detail")
        public TeacherDetail teacherDetail;

        @SerializedName("teacher_subject")
        public List<TeacherSubject> teacherSubject;

        public   class TeacherMeta {
            @SerializedName("id")
            public int id;

            @SerializedName("user_id")
            public int userId;

            @SerializedName("post_code")
            public String postCode;

            @SerializedName("speciality")
            public String speciality;

            @SerializedName("gender")
            public String gender;

            @SerializedName("dob")
            public String dob;

            @SerializedName("created_at")
            public String createdAt;

            @SerializedName("updated_at")
            public String updatedAt;
        }

        public   class TeacherCertification {
            @SerializedName("id")
            public int id;

            @SerializedName("user_id")
            public int userId;

            @SerializedName("institution_name")
            public String institutionName;

            @SerializedName("degree_type")
            public String degreeType;

            @SerializedName("degree_name")
            public String degreeName;

            @SerializedName("start_month")
            public String startMonth;

            @SerializedName("start_year")
            public int startYear;

            @SerializedName("end_month")
            public String endMonth;

            @SerializedName("end_year")
            public int endYear;

            @SerializedName("association")
            public String association;

            @SerializedName("created_at")
            public String createdAt;

            @SerializedName("updated_at")
            public String updatedAt;
        }

        public   class TeacherExperience {
            @SerializedName("id")
            public int id;

            @SerializedName("user_id")
            public int userId;

            @SerializedName("institution_name")
            public String institutionName;

            @SerializedName("designation")
            public String designation;

            @SerializedName("start_month")
            public String startMonth;

            @SerializedName("start_year")
            public int startYear;

            @SerializedName("end_month")
            public String endMonth;

            @SerializedName("end_year")
            public int endYear;

            @SerializedName("association")
            public String association;

            @SerializedName("job_description")
            public String jobDescription;

            @SerializedName("created_at")
            public String createdAt;

            @SerializedName("updated_at")
            public String updatedAt;
        }

        public   class TeacherDetail {
            @SerializedName("id")
            public int id;

            @SerializedName("user_id")
            public int userId;

            @SerializedName("fee_details")
            public Object feeDetails;

            @SerializedName("currency_id")
            public Object currencyId;

            @SerializedName("fee_schedule")
            public String feeSchedule;

            @SerializedName("fee_amount")
            public String feeAmount;

            @SerializedName("total_experience")
            public String totalExperience;

            @SerializedName("willing_to_travel")
            public String willingToTravel;

            @SerializedName("available_for_online")
            public String availableForOnline;

            @SerializedName("help_with_homework")
            public String helpWithHomework;

            @SerializedName("currently_employed")
            public String currentlyEmployed;

            @SerializedName("interested_association")
            public String interestedAssociation;

            @SerializedName("created_at")
            public String createdAt;

            @SerializedName("updated_at")
            public String updatedAt;
        }

        public   class TeacherSubject {
            @SerializedName("id")
            public int id;

            @SerializedName("user_id")
            public int userId;

            @SerializedName("subject_id")
            public int subjectId;

            @SerializedName("from_level_id")
            public int fromLevelId;

            @SerializedName("to_level_id")
            public int toLevelId;

            @SerializedName("created_at")
            public String createdAt;

            @SerializedName("updated_at")
            public String updatedAt;

            @SerializedName("from_level")
            public Level fromLevel;

            @SerializedName("to_level")
            public Level toLevel;

            @SerializedName("option_subject")
            public Subject optionSubject;
        }

        public   class Subject {
            @SerializedName("id")
            public int id;

            @SerializedName("title")
            public String title;
        }

        public   class Level {
            @SerializedName("id")
            public int id;

            @SerializedName("title")
            public String title;
        }
    }
}
