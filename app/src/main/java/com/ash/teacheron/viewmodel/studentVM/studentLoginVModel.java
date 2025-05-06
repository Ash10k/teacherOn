package com.ash.teacheron.viewmodel.studentVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ash.teacheron.repository.teacherRep.loginRepo;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.loginResponse;


public class studentLoginVModel extends AndroidViewModel {

    private loginRepo repository;

    public studentLoginVModel(@NonNull Application application) {
        super(application);
        repository = new loginRepo();
    }

    public LiveData<loginResponse> startLogin(String email, String pass, String fid,String deviceType, String deviceId) {
        return repository.performLogin(email, pass, fid,  deviceType,   deviceId);
    }

    public LiveData<ErrorData> getErrorMessage() {
        // Assuming you have implemented error handling in the repository
        return repository.getErrorMessage();
    }

}
