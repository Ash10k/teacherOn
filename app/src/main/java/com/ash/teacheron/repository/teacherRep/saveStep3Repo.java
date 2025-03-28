package com.ash.teacheron.repository.teacherRep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.saveResponse;
import com.ash.teacheron.retrofit.model.teaacherModel.step3teacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class saveStep3Repo {

    private AuthAPI sendData;
    private MutableLiveData<ErrorData> errorMessage = new MutableLiveData<>();

    public saveStep3Repo() {
        sendData = RetrofitBuilder.build().create(AuthAPI.class);
    }

    public LiveData<saveResponse> saveData(List<step3teacher.Degree> subjects, String userId,String token) {
        MutableLiveData<saveResponse> userSessionLiveData = new MutableLiveData<>();
        Call<saveResponse> myCall = sendData.saveStep3(token,new step3teacher.DegreeRequestBody(subjects, userId));

        myCall.enqueue(new Callback<saveResponse>() {
            @Override
            public void onResponse(Call<saveResponse> call, Response<saveResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userSessionLiveData.setValue(response.body());
                } else {
                    errorMessage.setValue(new ErrorData("Error: Response not successful", response.code()));
                    userSessionLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<saveResponse> call, Throwable t) {
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
