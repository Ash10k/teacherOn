package com.ash.teacheron.repository.studentRep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.registerResponseStud;
import com.ash.teacheron.retrofit.model.registerResponseStud;
import com.ash.teacheron.retrofit.model.studentModel.step1student;
import com.ash.teacheron.retrofit.model.teaacherModel.step2teacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class saveStudentStep1Repo {

    private AuthAPI sendData;
    private MutableLiveData<ErrorData> errorMessage = new MutableLiveData<>();

    public saveStudentStep1Repo() {
        sendData = RetrofitBuilder.build().create(AuthAPI.class);
    }

    public LiveData<registerResponseStud> saveData(String email, String name, String phone, String location, String subject_id, String level_id, String user_type, String requirements, String password, String latitude, String longitude) {
        MutableLiveData<registerResponseStud> userSessionLiveData = new MutableLiveData<>();
        Call<registerResponseStud> myCall = sendData.saveStep1Student(new step1student(  email,   name,   phone,   location,   subject_id,   level_id,   user_type,   requirements,   password,   latitude,   longitude));
        myCall.enqueue(new Callback<registerResponseStud>() {
            @Override
            public void onResponse(Call<registerResponseStud> call, Response<registerResponseStud> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userSessionLiveData.setValue(response.body());
                } else {
                    errorMessage.setValue(new ErrorData("Error: Response not successful", response.code()));
                    userSessionLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<registerResponseStud> call, Throwable t) {
                errorMessage.setValue(new ErrorData("Error: " + t.getMessage(),9));
                userSessionLiveData.setValue(null);
            }
        });

        return userSessionLiveData;
    }

    public LiveData<ErrorData> getErrorMessage() {
        return errorMessage;
    }




}
