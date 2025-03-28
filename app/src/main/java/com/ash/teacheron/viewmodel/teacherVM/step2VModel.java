package com.ash.teacheron.viewmodel.teacherVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ash.teacheron.repository.teacherRep.saveStep2Repo;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.saveResponse;
import com.ash.teacheron.retrofit.model.teaacherModel.step2teacher;

import java.util.List;


public class step2VModel extends AndroidViewModel {

    private saveStep2Repo repository;

    public step2VModel(@NonNull Application application) {
        super(application);
        repository = new saveStep2Repo();
    }

    public LiveData<saveResponse> startLogin(List<step2teacher.SubjectSelection> subjects, String userId, String token) {
        return repository.saveData(subjects, userId, token);
    }

    public LiveData<ErrorData> getErrorMessage() {
        // Assuming you have implemented error handling in the repository
        return repository.getErrorMessage();
    }

}
