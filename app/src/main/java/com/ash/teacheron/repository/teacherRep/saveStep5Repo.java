package com.ash.teacheron.repository.teacherRep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.saveResponse;
import com.ash.teacheron.retrofit.model.teaacherModel.step5teacher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class saveStep5Repo {

    private AuthAPI sendData;
    private MutableLiveData<ErrorData> errorMessage = new MutableLiveData<>();

    public saveStep5Repo() {
        sendData = RetrofitBuilder.build().create(AuthAPI.class);
    }

    public LiveData<saveResponse> saveData(String user_id, String fee_schedule, String fee_amount, String total_experience, String willing_to_travel, String available_for_online, String help_with_homework, String currently_employed, String interested_association, String fee_details, String currency_id,String token) {
        MutableLiveData<saveResponse> userSessionLiveData = new MutableLiveData<>();
        Call<saveResponse> myCall = sendData.saveStep5(token,new step5teacher.DegreeRequestBody(  user_id,   fee_schedule,   fee_amount,   total_experience,   willing_to_travel,   available_for_online,   help_with_homework,   currently_employed,   interested_association,   fee_details,   currency_id));

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
