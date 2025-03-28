package com.ash.teacheron.repository.teacherRep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.teaacherModel.registerResponse;
import com.ash.teacheron.retrofit.model.teaacherModel.registerRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registerRepoTeacher {

    private AuthAPI sendData;
    private MutableLiveData<ErrorData> errorMessage = new MutableLiveData<>();

    public registerRepoTeacher() {
        sendData = RetrofitBuilder.build().create(AuthAPI.class);
    }

    public LiveData<registerResponse> performLogin(String email, String password, String dob,String name,String phone,
                                                String location, String latitude, String longitude,String user_type,String post_code,
                                                String speciality, String gender
                                                ) {
        MutableLiveData<registerResponse> userSessionLiveData = new MutableLiveData<>();
        Call<registerResponse> myCall = sendData.performRegister(new registerRequest(  name,   email,   password,   phone,   location,   latitude,   longitude,   user_type,   post_code,   speciality,   gender,   dob));

        myCall.enqueue(new Callback<registerResponse>() {
            @Override
            public void onResponse(Call<registerResponse> call, Response<registerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userSessionLiveData.setValue(response.body());
                } else {
                    errorMessage.setValue(new ErrorData("Error: Response not successful", response.code()));
                    userSessionLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<registerResponse> call, Throwable t) {
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
