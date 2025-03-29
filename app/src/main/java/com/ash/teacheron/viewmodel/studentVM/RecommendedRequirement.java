package com.ash.teacheron.viewmodel.studentVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ash.teacheron.repository.studentRep.RecommendedRepo;
import com.ash.teacheron.repository.studentRep.listReqRM;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.recommendedTeacherResponse;
import com.ash.teacheron.retrofit.model.requirementResponse;


public class RecommendedRequirement extends AndroidViewModel
{
    private RecommendedRepo repository;
    public RecommendedRequirement(@NonNull Application application)
    {
        super(application);
        repository = new RecommendedRepo();
    }
    public LiveData<recommendedTeacherResponse> startLogin(  String token,String requirement_id, String subject_id, String subject, String from_level_id, String to_level_id, String location)
    {
        return repository.saveData(  token,  requirement_id,   subject_id,   subject,   from_level_id,   to_level_id,   location);
    }
    public LiveData<ErrorData> getErrorMessage()
    {
        // Assuming you have implemented error handling in the repository
        return repository.getErrorMessage();
    }
    public LiveData<recommendedTeacherResponse> searchTeacher(  String token,String requirement_id, String subject_id, String subject, String from_level_id, String to_level_id, String location)
    {
        return repository.getSearchTeacher(  token,  requirement_id,   subject_id,   subject,   from_level_id,   to_level_id,   location);
    }

}
