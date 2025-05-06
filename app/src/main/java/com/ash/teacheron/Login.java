package com.ash.teacheron;

import static com.ash.teacheron.constants.Contants.FAILED_TO_LOGIN;
import static com.ash.teacheron.constants.Contants.LOGIN_SUCCESSFUL;
import static com.ash.teacheron.constants.Contants.SERVER_ERROR;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ash.teacheron.commonComponents.NetworkLoader;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.loginResponse;
import com.ash.teacheron.viewmodel.loginVM.LoginVModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

public class Login extends AppCompatActivity {

    RelativeLayout login;
    TextInputEditText new_Email;
    String pass, mail, fid = "t6735rvv5ssv56";
    Context context;
    NetworkLoader networkLoader;
    TextView signuplink,forgotpagelink;
    EditText new_Password;
    String deviceType="android",deviceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getFirebaseMessagingToken();
        login =  findViewById(R.id.loginpage);
        new_Email =  findViewById(R.id.email);
        new_Password =  findViewById(R.id.password);
        signuplink=findViewById(R.id.signuplink);
        forgotpagelink=findViewById(R.id.forgotpagelink);
        context =Login.this;
        networkLoader = new NetworkLoader();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pass = new_Password.getText().toString();
                mail = new_Email.getText().toString();
                if (pass != null && !pass.isEmpty() && mail != null && !mail.isEmpty())
                {
                    LoginVModel viewModel = new ViewModelProvider(Login.this).get(LoginVModel.class);
                    networkLoader.showLoadingDialog(Login.this);
                    viewModel.startLogin(mail, pass, fid,deviceType,deviceId).observe(Login.this, new Observer<loginResponse>() {
                        @Override
                        public void onChanged(loginResponse loginResponse) {
                            if (loginResponse != null) {
                                Log.d("framg", "" + new Gson().toJson(loginResponse));
                                SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(Login.this);
                              /*  sharedPrefLocal.setUserId(loginResponse.data.user.id);
                                sharedPrefLocal.setUserName(loginResponse.data.user.name);
                                sharedPrefLocal.setUserEmail(loginResponse.data.user.email);
                                sharedPrefLocal.setUserType(loginResponse.data.user.userType);
                                sharedPrefLocal.setUserPhone(loginResponse.data.user.phone);
                                sharedPrefLocal.setProfileImage(loginResponse.data.user.profileImageUrl);

                                if(loginResponse.data.user.userType.equals("student"))
                                {
                                    sharedPrefLocal.setUserLocation(loginResponse.data.user.student_metaDetail.location);
                                    sharedPrefLocal.setUserDOB(loginResponse.data.user.student_metaDetail.tutor_type);
                                    sharedPrefLocal.setUserFEEDETAIL(loginResponse.data.user.student_metaDetail.budget_type);
                                    sharedPrefLocal.setUserFeeAmount(loginResponse.data.user.student_metaDetail.budget);
                                    sharedPrefLocal.setUserExperience(loginResponse.data.user.student_metaDetail.requirement_type);
                                    sharedPrefLocal.setUserAssocia(loginResponse.data.user.student_metaDetail.requirement_type);
                                    sharedPrefLocal.setUserSchedule(loginResponse.data.user.student_metaDetail.tutor_option);
                                }
                                else {
                                    sharedPrefLocal.setUserLocation(loginResponse.data.user.location);
                                    sharedPrefLocal.setUserDOB(loginResponse.data.user.teacherDetail.availableForOnline);
                                    sharedPrefLocal.setUserFEEDETAIL(loginResponse.data.user.teacherDetail.feeDetails);
                                    sharedPrefLocal.setUserFeeAmount(loginResponse.data.user.teacherDetail.feeAmount);
                                    sharedPrefLocal.setUserExperience(loginResponse.data.user.teacherDetail.totalExperience);
                                    sharedPrefLocal.setUserAssocia(loginResponse.data.user.teacherDetail.interestedAssociation);
                                    sharedPrefLocal.setUserSchedule(loginResponse.data.user.teacherDetail.feeSchedule);

                                }*/
                                //Toast.makeText(context, ""+loginResponse.data.user.name, Toast.LENGTH_SHORT).show();
                                sharedPrefLocal.setUserId(loginResponse.data.user.id != 0 ? loginResponse.data.user.id : 0);
                                sharedPrefLocal.setUserName(loginResponse.data.user.name != null ? loginResponse.data.user.name : "");
                                sharedPrefLocal.setUserEmail(mail);
                                sharedPrefLocal.setUserType(loginResponse.data.user.userType != null ? loginResponse.data.user.userType : "");
                                sharedPrefLocal.setUserPhone(loginResponse.data.user.phone != null ? loginResponse.data.user.phone : "");
                                sharedPrefLocal.setProfileImage(loginResponse.data.user.profileImageUrl != null ? loginResponse.data.user.profileImageUrl : "");

                                if ("student".equals(loginResponse.data.user.userType)) {
                                    if (loginResponse.data.user.student_metaDetail != null) {
                                        sharedPrefLocal.setUserLocation(loginResponse.data.user.student_metaDetail.location != null ? loginResponse.data.user.student_metaDetail.location : "");
                                        sharedPrefLocal.setUserDOB(loginResponse.data.user.student_metaDetail.tutor_type != null ? loginResponse.data.user.student_metaDetail.tutor_type : "");
                                        sharedPrefLocal.setUserFEEDETAIL(loginResponse.data.user.student_metaDetail.budget_type != null ? loginResponse.data.user.student_metaDetail.budget_type : "");
                                        sharedPrefLocal.setUserFeeAmount(loginResponse.data.user.student_metaDetail.budget != null ? loginResponse.data.user.student_metaDetail.budget : "");
                                        sharedPrefLocal.setUserExperience(loginResponse.data.user.student_metaDetail.requirement_type != null ? loginResponse.data.user.student_metaDetail.requirement_type : "");
                                        sharedPrefLocal.setUserAssocia(loginResponse.data.user.student_metaDetail.requirement_type != null ? loginResponse.data.user.student_metaDetail.requirement_type : "");
                                        sharedPrefLocal.setUserSchedule(loginResponse.data.user.student_metaDetail.tutor_option != null ? loginResponse.data.user.student_metaDetail.tutor_option : "");
                                    }
                                } else {
                                    sharedPrefLocal.setUserLocation(loginResponse.data.user.location != null ? loginResponse.data.user.location : "");

                                    if (loginResponse.data.user.teacherDetail != null) {
                                        sharedPrefLocal.setUserDOB(loginResponse.data.user.teacherDetail.availableForOnline != null ? loginResponse.data.user.teacherDetail.availableForOnline : "");
                                        sharedPrefLocal.setUserFEEDETAIL(loginResponse.data.user.teacherDetail.feeDetails != null ? loginResponse.data.user.teacherDetail.feeDetails : "");
                                        sharedPrefLocal.setUserFeeAmount(loginResponse.data.user.teacherDetail.feeAmount != null ? loginResponse.data.user.teacherDetail.feeAmount : "");
                                        sharedPrefLocal.setUserExperience(loginResponse.data.user.teacherDetail.totalExperience != null ? loginResponse.data.user.teacherDetail.totalExperience : "");
                                        sharedPrefLocal.setUserAssocia(loginResponse.data.user.teacherDetail.interestedAssociation != null ? loginResponse.data.user.teacherDetail.interestedAssociation : "");
                                        sharedPrefLocal.setUserSchedule(loginResponse.data.user.teacherDetail.feeSchedule != null ? loginResponse.data.user.teacherDetail.feeSchedule : "");
                                    }
                                }

                                if (loginResponse.data.user.userType.equals("teacher"))
                                {
                                     startActivity(new Intent(Login.this, BottomNavTeacher.class));
                                }
                                else {

                                    startActivity(new Intent(Login.this, BothBottomAndSideNavigation.class));
                                }

                                //sharedPrefLocal.setUserDisplay(loginResponse.currentUser.dname);
                                sharedPrefLocal.setSessionId(""+loginResponse.data.tokenData.accessToken);

                               //Toast.makeText(context, LOGIN_SUCCESSFUL , Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle null response here if needed
                                Toast.makeText(context, "Invalid credentials or unauthorized access, we are not able to find this email", Toast.LENGTH_SHORT).show();
                            }

                            networkLoader.dismissLoadingDialog();

                        }
                    });
                    viewModel.getErrorMessage().observe(Login.this, new Observer<ErrorData>() {
                        @Override
                        public void onChanged(ErrorData errorData) {
                            // Display error message
                            Toast.makeText(context, "Invalid credentials or unauthorized access, we are not able to find this email", Toast.LENGTH_SHORT).show();
                            Log.d("Error", errorData.getMessage());
                            networkLoader.dismissLoadingDialog();
                        }
                    });
                }
            }
        });

        signuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(Login.this, Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
        });

        forgotpagelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent intent = new Intent(Login.this, ForgotPass.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
        });

    }

    private void getFirebaseMessagingToken() {

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Token", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        deviceId = task.getResult();
                        Log.d("Token is : ", deviceId);

                    }
                });
    }
}