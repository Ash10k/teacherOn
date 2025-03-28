package com.ash.teacheron.repository.teacherRep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.loginRequest;
import com.ash.teacheron.retrofit.model.loginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginRepo {

    private AuthAPI sendData;
    private MutableLiveData<ErrorData> errorMessage = new MutableLiveData<>();

    public loginRepo() {
        sendData = RetrofitBuilder.build().create(AuthAPI.class);
    }

    public LiveData<loginResponse> performLogin(String email, String password, String firebaseId) {
        MutableLiveData<loginResponse> userSessionLiveData = new MutableLiveData<>();
        Call<loginResponse> myCall = sendData.performLogin(new loginRequest(email, password, firebaseId));

        myCall.enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userSessionLiveData.setValue(response.body());
                } else {
                    errorMessage.setValue(new ErrorData("Error: Response not successful", response.code()));
                    userSessionLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {
                errorMessage.setValue(new ErrorData("Error: " + t.getMessage(),9));
                userSessionLiveData.setValue(null);
            }
        });

        return userSessionLiveData;
    }

    public LiveData<ErrorData> getErrorMessage() {
        return errorMessage;
    }


    public LiveData<loginResponse> performStudentLogin(String email, String password, String firebaseId) {
        MutableLiveData<loginResponse> userSessionLiveData = new MutableLiveData<>();
        Call<loginResponse> myCall = sendData.performLogin(new loginRequest(email, password, firebaseId));

        myCall.enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userSessionLiveData.setValue(response.body());
                } else {
                    errorMessage.setValue(new ErrorData("Error: Response not successful", response.code()));
                    userSessionLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {
                errorMessage.setValue(new ErrorData("Error: " + t.getMessage(),9));
                userSessionLiveData.setValue(null);
            }
        });

        return userSessionLiveData;
    }


}
