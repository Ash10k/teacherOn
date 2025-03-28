package com.ash.teacheron.viewmodel.teacherVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ash.teacheron.repository.teacherRep.saveStep6Repo;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.saveResponse;


public class step6VModel extends AndroidViewModel {

    private saveStep6Repo repository;
    public step6VModel(@NonNull Application application) {
        super(application);
        repository = new saveStep6Repo();
    }
    public LiveData<saveResponse> startLogin(String userId, String profile_intro,String profile_description,String privacy_accepted,String token) {
        return repository.saveData(  userId,   profile_intro,  profile_description,  privacy_accepted,  token );
    }
    public LiveData<ErrorData> getErrorMessage() {

        return repository.getErrorMessage();
    }
}
