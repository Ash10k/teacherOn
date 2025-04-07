package com.ash.teacheron.viewmodel.registerVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.ash.teacheron.repository.teacherRep.registerRepoTeacher;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.teaacherModel.registerResponse;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class RegisterVModel extends AndroidViewModel {

   // private registerRepo repository;
    private registerRepoTeacher repository_teach;

    public RegisterVModel(@NonNull Application application) {
        super(application);
        //repository = new registerRepo();
        repository_teach=new registerRepoTeacher();
    }


    public LiveData<registerResponse> startRegisterStep1( String email, String password, String dob,String name,String phone,
                                                          String location, String latitude, String longitude,String user_type,String post_code,
                                                          String speciality, String gender) {
        return repository_teach.performLogin(  email,   password,   dob,  name,  phone, location, latitude,   longitude,  user_type,  post_code,speciality,   gender);
    }



    public LiveData<ErrorData> getErrorMessage() {
        // Assuming you have implemented error handling in the repository
        return repository_teach.getErrorMessage();
    }
  /*  public LiveData<registerResponse> updateStudentProfile(String token, String email,  String name,String phone,
                                                          String location, String latitude, String longitude,String user_type
                                                           ) {
        return repository_teach.updateStudentPro( token, email,  name,  phone, location, latitude,   longitude,  user_type );
    }*/






}
