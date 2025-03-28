package com.ash.teacheron.retrofit.model.teaacherModel;

import com.google.gson.annotations.SerializedName;

public class step6teacher {

    public static class DegreeRequestBody {

        @SerializedName("user_id") public String user_id;
        @SerializedName("profile_intro") public String profile_intro;
        @SerializedName("profile_description") public String profile_description;
        @SerializedName("privacy_accepted") public String privacy_accepted;

        public DegreeRequestBody(String user_id, String profile_intro, String profile_description, String privacy_accepted) {
            this.user_id = user_id;
            this.profile_intro = profile_intro;
            this.profile_description = profile_description;
            this.privacy_accepted = privacy_accepted;
        }
    }
}