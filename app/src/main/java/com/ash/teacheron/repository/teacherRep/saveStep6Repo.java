package com.ash.teacheron.repository.teacherRep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.saveResponse;
import com.ash.teacheron.retrofit.model.teaacherModel.step6teacher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class saveStep6Repo
{
    private AuthAPI sendData;
    private MutableLiveData<ErrorData> errorMessage = new MutableLiveData<>();
    public saveStep6Repo() {
        sendData = RetrofitBuilder.build().create(AuthAPI.class);
    }
    public LiveData<saveResponse> saveData(String userId, String profile_intro,String profile_description,String privacy_accepted,String token) {
        MutableLiveData<saveResponse> userSessionLiveData = new MutableLiveData<>();

        Call<saveResponse> myCall = sendData.saveStep6(token,new step6teacher.DegreeRequestBody(userId,profile_intro,profile_description,privacy_accepted));

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
