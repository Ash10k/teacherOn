package com.ash.teacheron.retrofit.model.teaacherModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class step3teacher {


    // Model Classes
    public static class Degree {
        @SerializedName("institution_name") public String institution_name;
        @SerializedName("degree_type") public String degreeType;
        @SerializedName("degree_name") public String degreeName;
        @SerializedName("start_month") public String startMonth;
        @SerializedName("start_year") public String startYear;
        @SerializedName("end_month") public String endMonth;
        @SerializedName("end_year") public String endYear;
        @SerializedName("association") public String association;

        public Degree(String degreeType, String degreeName, String startMonth, String startYear, String endMonth, String endYear, String association,String institution_name) {
            this.degreeType = degreeType;
            this.degreeName = degreeName;
            this.startMonth = startMonth;
            this.startYear = startYear;
            this.endMonth = endMonth;
            this.endYear = endYear;
            this.association = association;
            this.institution_name=institution_name;
        }
    }

    public static class DegreeRequestBody {
        @SerializedName("certifications") public List<Degree> degrees;
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
