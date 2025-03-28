package com.ash.teacheron.retrofit.model.teaacherModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class step2teacher {
    public static class SubjectSelection {
        @SerializedName("subject_id") public int subjectId;
        @SerializedName("from_level_id") public int fromLevelId;
        @SerializedName("to_level_id") public int toLevelId;

        public SubjectSelection(int subjectId, int fromLevelId, int toLevelId) {
            this.subjectId = subjectId;
            this.fromLevelId = fromLevelId;
            this.toLevelId = toLevelId;
        }
    }

    public static class SubjectRequestBody {
        @SerializedName("subjects") public List<SubjectSelection> subjects;
        @SerializedName("user_id") public String user_id;
        public SubjectRequestBody(List<SubjectSelection> subjects) {
            this.subjects = subjects;
        }

        public SubjectRequestBody(List<SubjectSelection> subjects, String user_id) {
            this.subjects = subjects;
            this.user_id = user_id;
        }
    }

}
