package com.ash.teacheron.retrofit.api;


import com.ash.teacheron.retrofit.model.ChatResponseDataModel;
import com.ash.teacheron.retrofit.model.ChatResponseDataModel1;
import com.ash.teacheron.retrofit.model.PostNewMessage_response;
import com.ash.teacheron.retrofit.model.SendPostMsg;
import com.ash.teacheron.retrofit.model.appOptionsResponse;
import com.ash.teacheron.retrofit.model.languageResponse;
import com.ash.teacheron.retrofit.model.loginRequest;
import com.ash.teacheron.retrofit.model.loginResponse;
import com.ash.teacheron.retrofit.model.recommendedProfile;
import com.ash.teacheron.retrofit.model.recommendedRequest;
import com.ash.teacheron.retrofit.model.recommendedTeacherResponse;
import com.ash.teacheron.retrofit.model.registerResponseStep2;
import com.ash.teacheron.retrofit.model.registerResponseStud;
import com.ash.teacheron.retrofit.model.requirementResponse;
import com.ash.teacheron.retrofit.model.requirementResponseconnected;
import com.ash.teacheron.retrofit.model.studentModel.step1student;
import com.ash.teacheron.retrofit.model.studentModel.step2student;
import com.ash.teacheron.retrofit.model.teaacherModel.registerRequest;
import com.ash.teacheron.retrofit.model.teaacherModel.registerResponse;
import com.ash.teacheron.retrofit.model.saveResponse;
import com.ash.teacheron.retrofit.model.teaacherModel.step2teacher;
import com.ash.teacheron.retrofit.model.teaacherModel.step3teacher;
import com.ash.teacheron.retrofit.model.teaacherModel.step4teacher;
import com.ash.teacheron.retrofit.model.teaacherModel.step5teacher;
import com.ash.teacheron.retrofit.model.teaacherModel.step6teacher;
import com.ash.teacheron.retrofit.model.userRequest;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AuthAPI {

    @Headers("Content-Type: application/json")
    @POST("login")
    Call<loginResponse> performLogin(
            @Body loginRequest request
    );

    @Headers("Content-Type: application/json")
    @POST("login")
    Call<loginResponse> performLoginStudent(
            @Body loginRequest request
    );

    @Headers("Content-Type: application/json")
    @POST("teacher-register-step-1")
    Call<registerResponse> performRegister(
            @Body registerRequest request
    );

    @Headers("Content-Type: application/json")
    @GET("get-app-options")
    Call<appOptionsResponse> getAppOptions(
            @Header("Authorization") String token
    );

    @Headers("Content-Type: application/json")
    @GET("get-languages")
    Call<languageResponse> getLanguage(
            @Header("Authorization") String token
    );

    @Headers("Content-Type: application/json")
    @GET("get-countries")
    Call<languageResponse> getCountry(
            @Header("Authorization") String token
    );
    @Headers("Content-Type: application/json")
    @GET("get-currencies")
    Call<languageResponse> getCurrency(
            @Header("Authorization") String token
    );

    @Headers("Content-Type: application/json")
    @POST("teacher-register-step-2")
    Call<saveResponse> saveStep2(
            @Header("Authorization") String token,
            @Body step2teacher.SubjectRequestBody request
    );

    @Headers("Content-Type: application/json")
    @POST("teacher-register-step-3")
    Call<saveResponse> saveStep3(
            @Header("Authorization") String token,
            @Body step3teacher.DegreeRequestBody request
    );

    @Headers("Content-Type: application/json")
    @POST("teacher-register-step-4")
    Call<saveResponse> saveStep4(
            @Header("Authorization") String token,
            @Body step4teacher.DegreeRequestBody request
    );


    @Headers("Content-Type: application/json")
    @POST("teacher-register-step-5")
    Call<saveResponse> saveStep5(
            @Header("Authorization") String token,
            @Body step5teacher.DegreeRequestBody request
    );


    @Headers("Content-Type: application/json")
    @POST("teacher-register-step-6")
    Call<saveResponse> saveStep6(
            @Header("Authorization") String token,
            @Body step6teacher.DegreeRequestBody request
    );

    //Student

    @Headers("Content-Type: application/json")
    @POST("student-register-step-1")
    Call<registerResponseStud> saveStep1Student(

            @Body step1student request
    );

    @Multipart
    @POST("student-register-step-2-upload-image")
    Call<saveResponse> saveImageStudent(
            @Part MultipartBody.Part uidPart,
            @Part MultipartBody.Part image
    );

    @Headers("Content-Type: application/json")
    @POST("student-register-step-2")
    Call<registerResponseStep2> saveStep2Student(
            @Body step2student request
    );

    @Headers("Content-Type: application/json")
    @POST("student-add-requirement")
    Call<registerResponseStep2> saveNewReq(
            @Header("Authorization") String token,
            @Body step2student request
    );

    @Headers("Content-Type: application/json")
    @POST("student-requirement-list")
    Call<requirementResponse> getRequirement(
            @Header("Authorization") String token,
            @Body userRequest request
    );

    @Headers("Content-Type: application/json")
    @POST("matching-jobs-for-teacher")
    Call<requirementResponse> getMatchingJob(
            @Header("Authorization") String token,
            @Body userRequest request
    );

    @Headers("Content-Type: application/json")
    @POST("matching-jobs-with-student-poke")
    Call<requirementResponseconnected> getConnected(
            @Header("Authorization") String token,
            @Body userRequest request
    );



    @Headers("Content-Type: application/json")
    @POST("matching-teachers-by-requirement")
    Call<recommendedTeacherResponse> getRecommendedTeacher(
            @Header("Authorization") String token,
            @Body recommendedRequest request
    );

    @Headers("Content-Type: application/json")
    @POST("search-requirements?page=1")
    Call<recommendedTeacherResponse> srchReqm(
            @Header("Authorization") String token,
            @Body recommendedRequest request
    );

    @Headers("Content-Type: application/json")
    @POST("search-teacher")
    Call<recommendedTeacherResponse> searchTeacher(
            @Header("Authorization") String token,
            @Body recommendedRequest request
    );

    @Headers({"Accept: application/json"})
    @POST("get-all-distinct-chat-users")
    Call<ChatResponseDataModel> get_chat_room(@Header("Authorization")String token,  @Body recommendedRequest request );


    @Headers({"Accept: application/json"})
    @POST("get-distinct-sender-for-post")
    Call<ChatResponseDataModel> get_chat_message(@Header("Authorization")String token ,  @Body  recommendedRequest req );

    @Headers({"Accept: application/json"})
    @POST("send-message-for-post")
    Call<PostNewMessage_response> post_chat_message(@Header("Authorization")String token ,   @Body SendPostMsg loginSendAPIModel );


    @Headers("Content-Type: application/json")
    @POST("get-teacher-profile")
    Call<recommendedProfile> getProfile(
            @Header("Authorization") String token
    );


}
