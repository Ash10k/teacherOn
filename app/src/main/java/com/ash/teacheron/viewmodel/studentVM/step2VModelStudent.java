package com.ash.teacheron.viewmodel.studentVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ash.teacheron.repository.studentRep.saveStudentStep1Repo;
import com.ash.teacheron.repository.studentRep.saveStudentStep2Repo;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.passwordResponse;
import com.ash.teacheron.retrofit.model.registerResponseStep2;


public class step2VModelStudent extends AndroidViewModel
{
    private saveStudentStep2Repo repository;
    public step2VModelStudent(@NonNull Application application)
    {
        super(application);
        repository = new saveStudentStep2Repo();
    }
    public LiveData<registerResponseStep2> startLogin(String user_id, String requirement_type, String tutor_option, String travel_limit, String budget, String budget_type, String gender_preference, String tutor_type, String budget_currency_id, String communicate_language_id, String tutor_from_country_id,String password,String details,String lo)
    {
        return repository.saveData(    user_id,   requirement_type,   tutor_option,   travel_limit,   budget,   budget_type,   gender_preference,   tutor_type,   budget_currency_id,   communicate_language_id,   tutor_from_country_id,password,details,lo);
    }
    public LiveData<ErrorData> getErrorMessage()
    {
        // Assuming you have implemented error handling in the repository
        return repository.getErrorMessage();
    }
    public LiveData<registerResponseStep2> createPost(String sub,String lev,String user_id, String requirement_type, String tutor_option, String travel_limit, String budget, String budget_type, String gender_preference, String tutor_type, String budget_currency_id, String communicate_language_id, String tutor_from_country_id,String password,String token,String locat)
    {
        return repository.newDataCreate(  sub,lev,  user_id,   requirement_type,   tutor_option,   travel_limit,   budget,   budget_type,   gender_preference,   tutor_type,   budget_currency_id,   communicate_language_id,   tutor_from_country_id,password,token,locat);
    }

    public LiveData<passwordResponse> editPost(String sub, String lev, String user_id, String requirement_type, String tutor_option, String travel_limit, String budget, String budget_type, String gender_preference, String tutor_type, String budget_currency_id, String communicate_language_id, String tutor_from_country_id, String password, String token, String locat, String id)
    {
        return repository.EditPOst(  sub,lev,  user_id,   requirement_type,   tutor_option,   travel_limit,   budget,   budget_type,   gender_preference,   tutor_type,   budget_currency_id,   communicate_language_id,   tutor_from_country_id,password,token,locat,id);
    }

}
