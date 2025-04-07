package com.ash.teacheron.repository.studentRep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.createNewPost;
import com.ash.teacheron.retrofit.model.passwordResponse;
import com.ash.teacheron.retrofit.model.registerResponseStep2;
import com.ash.teacheron.retrofit.model.studentModel.step1student;
import com.ash.teacheron.retrofit.model.studentModel.step2student;
import com.ash.teacheron.retrofit.model.studentModel.step2studentR;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class saveStudentStep2Repo {

    private AuthAPI sendData;
    private MutableLiveData<ErrorData> errorMessage = new MutableLiveData<>();

    public saveStudentStep2Repo() {
        sendData = RetrofitBuilder.build().create(AuthAPI.class);
    }

    public LiveData<registerResponseStep2> saveData(String user_id, String requirement_type, String tutor_option, String travel_limit, String budget, String budget_type, String gender_preference, String tutor_type, String budget_currency_id, String communicate_language_id, String tutor_from_country_id,String password,String d,String l) {
        MutableLiveData<registerResponseStep2> userSessionLiveData = new MutableLiveData<>();
        Call<registerResponseStep2> myCall = sendData.saveStep2Student(new step2studentR(   user_id,   requirement_type,   tutor_option,   travel_limit,   budget,   budget_type,   gender_preference,   tutor_type,   budget_currency_id,   communicate_language_id,   tutor_from_country_id,password,d,l));
        myCall.enqueue(new Callback<registerResponseStep2>() {
            @Override
            public void onResponse(Call<registerResponseStep2> call, Response<registerResponseStep2> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userSessionLiveData.setValue(response.body());
                } else {
                    errorMessage.setValue(new ErrorData("Error: Response not successful", response.code()));
                    userSessionLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<registerResponseStep2> call, Throwable t) {
                errorMessage.setValue(new ErrorData("Error: " + t.getMessage(),9));
                userSessionLiveData.setValue(null);
            }
        });

        return userSessionLiveData;
    }

    public LiveData<ErrorData> getErrorMessage() {
        return errorMessage;
    }


    public LiveData<registerResponseStep2> newDataCreate(String sub,String lev,String user_id, String requirement_type, String tutor_option, String travel_limit, String budget, String budget_type, String gender_preference, String tutor_type, String budget_currency_id, String communicate_language_id, String tutor_from_country_id,String password,String token,String location) {
        MutableLiveData<registerResponseStep2> userSessionLiveData = new MutableLiveData<>();
        Call<registerResponseStep2> myCall = sendData.saveNewReq(token,new createNewPost( sub,lev,  user_id,   requirement_type,   tutor_option,   travel_limit,   budget,   budget_type,   gender_preference,   tutor_type,   budget_currency_id,   communicate_language_id,   tutor_from_country_id,password,location));
        myCall.enqueue(new Callback<registerResponseStep2>() {
            @Override
            public void onResponse(Call<registerResponseStep2> call, Response<registerResponseStep2> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userSessionLiveData.setValue(response.body());
                } else {
                    errorMessage.setValue(new ErrorData("Error: Response not successful", response.code()));
                    userSessionLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<registerResponseStep2> call, Throwable t) {
                errorMessage.setValue(new ErrorData("Error: " + t.getMessage(),9));
                userSessionLiveData.setValue(null);
            }
        });

        return userSessionLiveData;
    }

    public LiveData<passwordResponse> EditPOst(String sub,String lev,String user_id, String requirement_type, String tutor_option, String travel_limit, String budget, String budget_type, String gender_preference, String tutor_type, String budget_currency_id, String communicate_language_id, String tutor_from_country_id,String password,String token,String location,String id) {
        MutableLiveData<passwordResponse> userSessionLiveData = new MutableLiveData<>();
        Call<passwordResponse> myCall = sendData.editPost(token,new createNewPost( sub,lev,  user_id,   requirement_type,   tutor_option,   travel_limit,   budget,   budget_type,   gender_preference,   tutor_type,   budget_currency_id,   communicate_language_id,   tutor_from_country_id,password,location,id));
        myCall.enqueue(new Callback<passwordResponse>() {
            @Override
            public void onResponse(Call<passwordResponse> call, Response<passwordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userSessionLiveData.setValue(response.body());
                } else {
                    errorMessage.setValue(new ErrorData("Error: Response not successful", response.code()));
                    userSessionLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<passwordResponse> call, Throwable t) {
                errorMessage.setValue(new ErrorData("Error: " + t.getMessage(),9));
                userSessionLiveData.setValue(null);
            }
        });

        return userSessionLiveData;
    }


}
