package com.ash.teacheron.repository.teacherRep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.teaacherModel.registerResponse;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registerRepo {
    MultipartBody.Part imagePart = null;

    private AuthAPI sendData;
    private MutableLiveData<ErrorData> errorMessage = new MutableLiveData<>();

    public registerRepo() {
        sendData = RetrofitBuilder.build().create(AuthAPI.class);
    }

    public LiveData<registerResponse> performLogin(String email, String pass, String first_name, String lastname, String display_name, String password_confirmation, RequestBody imageRequestBody, String filename)
    {

        imagePart = MultipartBody.Part.createFormData("image", filename, imageRequestBody);
        RequestBody m1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(first_name));
        RequestBody m2 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(lastname));
        RequestBody m3 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(pass));
        RequestBody m4 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(display_name));
        RequestBody m5 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(email));
        RequestBody m6 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(password_confirmation));


        MultipartBody.Part t1 = MultipartBody.Part.createFormData("first_name", null, m1);
        MultipartBody.Part t2 = MultipartBody.Part.createFormData("last_name", null, m2);
        MultipartBody.Part t3 = MultipartBody.Part.createFormData("display_name", null, m4);
        MultipartBody.Part t4 = MultipartBody.Part.createFormData("password", null, m3);
        MultipartBody.Part t5 = MultipartBody.Part.createFormData("email", null, m5);
        MultipartBody.Part t6 = MultipartBody.Part.createFormData("password_confirmation", null, m6);



        MutableLiveData<registerResponse> userSessionLiveData = new MutableLiveData<>();
       /* Call<registerResponse> myCall = sendData.performRegistration(t1, t2, t4, t3, t5,t6,
                imagePart);

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
        }); */

        return userSessionLiveData;
    }




    public LiveData<ErrorData> getErrorMessage() {
        return errorMessage;
    }




}
