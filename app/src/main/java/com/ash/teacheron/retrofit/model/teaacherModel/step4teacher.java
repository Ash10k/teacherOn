package com.ash.teacheron.retrofit.model.teaacherModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class step4teacher {


    // Model Classes
    public static class Degree {
        @SerializedName("institution_name") public String institution_name;
        @SerializedName("designation") public String designation;
        @SerializedName("job_description") public String job_description;
        @SerializedName("start_month") public String startMonth;
        @SerializedName("start_year") public String startYear;
        @SerializedName("end_month") public String endMonth;
        @SerializedName("end_year") public String endYear;
        @SerializedName("association") public String association;

        public Degree( String job_description,String designation, String startMonth, String startYear, String endMonth, String endYear, String association,String institution_name) {
            this.designation = designation;
            this.job_description = job_description;
            this.startMonth = startMonth;
            this.startYear = startYear;
            this.endMonth = endMonth;
            this.endYear = endYear;
            this.association = association;
            this.institution_name=institution_name;
        }
    }

    public static class DegreeRequestBody {
        @SerializedName("experiences") public List<Degree> degrees;
        @SerializedName("user_id") public String user_id;
        public DegreeRequestBody(List<Degree> degrees) {
            this.degrees = degrees;
        }

        public DegreeRequestBody(List<Degree> degrees, String user_id) {
            this.degrees = degrees;
            this.user_id = user_id;
        }
    }


}
