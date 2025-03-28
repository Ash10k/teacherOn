package com.ash.teacheron.repository.studentRep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.registerResponseStep2;
import com.ash.teacheron.retrofit.model.studentModel.step1student;
import com.ash.teacheron.retrofit.model.studentModel.step2student;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class saveStudentStep2Repo {

    private AuthAPI sendData;
    private MutableLiveData<ErrorData> errorMessage = new MutableLiveData<>();

    public saveStudentStep2Repo() {
        sendData = RetrofitBuilder.build().create(AuthAPI.class);
    }

    public LiveData<registerResponseStep2> saveData(String user_id, String requirement_type, String tutor_option, String travel_limit, String budget, String budget_type, String gender_preference, String tutor_type, String budget_currency_id, String communicate_language_id, String tutor_from_country_id,String password) {
        MutableLiveData<registerResponseStep2> userSessionLiveData = new MutableLiveData<>();
        Call<registerResponseStep2> myCall = sendData.saveStep2Student(new step2student(   user_id,   requirement_type,   tutor_option,   travel_limit,   budget,   budget_type,   gender_preference,   tutor_type,   budget_currency_id,   communicate_language_id,   tutor_from_country_id,password));
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




}
