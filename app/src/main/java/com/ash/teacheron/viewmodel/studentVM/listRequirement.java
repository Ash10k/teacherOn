package com.ash.teacheron.viewmodel.studentVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ash.teacheron.repository.studentRep.listReqRM;
import com.ash.teacheron.repository.studentRep.saveStudentStep2Repo;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.registerResponseStep2;
import com.ash.teacheron.retrofit.model.requirementResponse;


public class listRequirement extends AndroidViewModel
{
    private listReqRM repository;
    public listRequirement(@NonNull Application application)
    {
        super(application);
        repository = new listReqRM();
    }
    public LiveData<requirementResponse> startLogin(String user_id, String token)
    {
        return repository.saveData(    user_id,   token);
    }
    public LiveData<ErrorData> getErrorMessage()
    {
        // Assuming you have implemented error handling in the repository
        return repository.getErrorMessage();
    }
}
