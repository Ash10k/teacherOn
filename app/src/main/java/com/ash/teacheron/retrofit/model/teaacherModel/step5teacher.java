package com.ash.teacheron.retrofit.model.teaacherModel;

import com.google.gson.annotations.SerializedName;

public class step5teacher {

    public static class DegreeRequestBody {

        @SerializedName("user_id") public String user_id;
        @SerializedName("fee_schedule") public String fee_schedule;
        @SerializedName("fee_amount") public String fee_amount;
        @SerializedName("total_experience") public String total_experience;
        @SerializedName("willing_to_travel") public String willing_to_travel;

        @SerializedName("available_for_online") public String available_for_online;
        @SerializedName("help_with_homework") public String help_with_homework;
        @SerializedName("currently_employed") public String currently_employed;
        @SerializedName("interested_association") public String interested_association;
        @SerializedName("fee_details") public String fee_details;

        @SerializedName("currency_id") public String currency_id;

        public DegreeRequestBody(String user_id, String fee_schedule, String fee_amount, String total_experience, String willing_to_travel, String available_for_online, String help_with_homework, String currently_employed, String interested_association, String fee_details, String currency_id) {
            this.user_id = user_id;
            this.fee_schedule = fee_schedule;
            this.fee_amount = fee_amount;
            this.total_experience = total_experience;
            this.willing_to_travel = willing_to_travel;
            this.available_for_online = available_for_online;
            this.help_with_homework = help_with_homework;
            this.currently_employed = currently_employed;
            this.interested_association = interested_association;
            this.fee_details = fee_details;
            this.currency_id = currency_id;
        }
    }


}
