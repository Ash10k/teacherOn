package com.ash.teacheron.repository.studentRep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.recommendedRequest;
import com.ash.teacheron.retrofit.model.recommendedTeacherResponse;
import com.ash.teacheron.retrofit.model.userRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendedRepo {

    private AuthAPI sendData;
    private MutableLiveData<ErrorData> errorMessage = new MutableLiveData<>();

    public RecommendedRepo() {
        sendData = RetrofitBuilder.build().create(AuthAPI.class);
    }

    public LiveData<recommendedTeacherResponse> saveData( String token,String requirement_id, String subject_id, String subject, String from_level_id, String to_level_id, String location) {
        MutableLiveData<recommendedTeacherResponse> userSessionLiveData = new MutableLiveData<>();
        Call<recommendedTeacherResponse> myCall = sendData.getRecommendedTeacher(token,new recommendedRequest(  requirement_id,   subject_id,   subject,   from_level_id,   to_level_id,   location,1));
        myCall.enqueue(new Callback<recommendedTeacherResponse>() {
            @Override
            public void onResponse(Call<recommendedTeacherResponse> call, Response<recommendedTeacherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userSessionLiveData.setValue(response.body());
                } else {
                    errorMessage.setValue(new ErrorData("Error: Response not successful", response.code()));
                    userSessionLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<recommendedTeacherResponse> call, Throwable t) {
                errorMessage.setValue(new ErrorData("Error: " + t.getMessage(),9));
                userSessionLiveData.setValue(null);
            }
        });

        return userSessionLiveData;
    }

    public LiveData<ErrorData> getErrorMessage() {
        return errorMessage;
    }


    public LiveData<recommendedTeacherResponse> getSearchTeacher( String token,String requirement_id, String subject_id, String subject, String from_level_id, String to_level_id, String location,int page) {
        MutableLiveData<recommendedTeacherResponse> userSessionLiveData = new MutableLiveData<>();
        Call<recommendedTeacherResponse> myCall = sendData.searchTeacher(token,new recommendedRequest(  requirement_id,   subject_id,   subject,   from_level_id,   to_level_id,   location,page));
        myCall.enqueue(new Callback<recommendedTeacherResponse>() {
            @Override
            public void onResponse(Call<recommendedTeacherResponse> call, Response<recommendedTeacherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userSessionLiveData.setValue(response.body());
                } else {
                    errorMessage.setValue(new ErrorData("Error: Response not successful", response.code()));
                    userSessionLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<recommendedTeacherResponse> call, Throwable t) {
                errorMessage.setValue(new ErrorData("Error: " + t.getMessage(),9));
                userSessionLiveData.setValue(null);
            }
        });

        return userSessionLiveData;
    }
    public LiveData<recommendedTeacherResponse> srchreq( String token,String requirement_id, String subject_id, String subject, String from_level_id, String to_level_id, String location,int page) {
        MutableLiveData<recommendedTeacherResponse> userSessionLiveData = new MutableLiveData<>();
        Call<recommendedTeacherResponse> myCall = sendData.srchReqm(token,new recommendedRequest(  requirement_id,   subject_id,   subject,   from_level_id,   to_level_id,   location,page));
        myCall.enqueue(new Callback<recommendedTeacherResponse>() {
            @Override
            public void onResponse(Call<recommendedTeacherResponse> call, Response<recommendedTeacherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userSessionLiveData.setValue(response.body());
                } else {
                    errorMessage.setValue(new ErrorData("Error: Response not successful", response.code()));
                    userSessionLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<recommendedTeacherResponse> call, Throwable t) {
                errorMessage.setValue(new ErrorData("Error: " + t.getMessage(),9));
                userSessionLiveData.setValue(null);
            }
        });

        return userSessionLiveData;
    }


}
