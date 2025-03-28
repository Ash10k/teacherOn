package com.ash.teacheron.repository.studentRep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.requirementResponse;
import com.ash.teacheron.retrofit.model.studentModel.step2student;
import com.ash.teacheron.retrofit.model.userRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class listReqRM {

    private AuthAPI sendData;
    private MutableLiveData<ErrorData> errorMessage = new MutableLiveData<>();

    public listReqRM() {
        sendData = RetrofitBuilder.build().create(AuthAPI.class);
    }

    public LiveData<requirementResponse> saveData(String user_id, String token) {
        MutableLiveData<requirementResponse> userSessionLiveData = new MutableLiveData<>();
        Call<requirementResponse> myCall = sendData.getRequirement(token,new userRequest(user_id));
        myCall.enqueue(new Callback<requirementResponse>() {
            @Override
            public void onResponse(Call<requirementResponse> call, Response<requirementResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userSessionLiveData.setValue(response.body());
                } else {
                    errorMessage.setValue(new ErrorData("Error: Response not successful", response.code()));
                    userSessionLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<requirementResponse> call, Throwable t) {
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
