package com.ash.teacheron.viewmodel.teacherVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ash.teacheron.repository.teacherRep.saveStep3Repo;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.saveResponse;
import com.ash.teacheron.retrofit.model.teaacherModel.step3teacher;

import java.util.List;


public class step3VModel extends AndroidViewModel {

    private saveStep3Repo repository;

    public step3VModel(@NonNull Application application) {
        super(application);
        repository = new saveStep3Repo();
    }

    public LiveData<saveResponse> startLogin(List<step3teacher.Degree> subjects, String userId, String token) {
        return repository.saveData(subjects, userId, token);
    }

    public LiveData<ErrorData> getErrorMessage() {
        // Assuming you have implemented error handling in the repository
        return repository.getErrorMessage();
    }

}
