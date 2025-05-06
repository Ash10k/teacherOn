package com.ash.teacheron.viewmodel.studentVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ash.teacheron.repository.studentRep.saveStudentStep1Repo;
import com.ash.teacheron.repository.teacherRep.saveStep2Repo;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.registerResponseStud;
import com.ash.teacheron.retrofit.model.teaacherModel.step2teacher;

import java.util.List;


public class step1VModelStudent extends AndroidViewModel
{
    private saveStudentStep1Repo repository;
    public step1VModelStudent(@NonNull Application application)
    {
        super(application);
        repository = new saveStudentStep1Repo();
    }
    public LiveData<registerResponseStud> startLogin(String email, String name, String phone, String location, String subject_id, String level_id, String user_type, String requirements, String password, String latitude, String longitude,String deviceType,String deviceId)
    {
        return repository.saveData(   email,   name,   phone,   location,   subject_id,   level_id,   user_type,   requirements,   password,   latitude,   longitude,deviceType,deviceId);
    }
    public LiveData<ErrorData> getErrorMessage() {
        // Assuming you have implemented error handling in the repository
        return repository.getErrorMessage();
    }
}
