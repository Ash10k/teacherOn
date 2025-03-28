package com.ash.teacheron;

import static com.ash.teacheron.constants.Contants.FAILED_TO_LOGIN;
import static com.ash.teacheron.constants.Contants.LOGIN_SUCCESSFUL;

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
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

public class StudentLogin extends AppCompatActivity {

    RelativeLayout login;
     EditText new_Email, new_Password;
    String pass, mail, fid = "t6735rvv5ssv56";
    Context context;
    NetworkLoader networkLoader;
    TextView signuplink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        login =  findViewById(R.id.loginpage);
        new_Email =  findViewById(R.id.email);
        new_Password =  findViewById(R.id.password);
        signuplink=findViewById(R.id.signuplink);
        context =StudentLogin.this;
        networkLoader = new NetworkLoader();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pass = new_Password.getText().toString();
                mail = new_Email.getText().toString();

                if (pass != null && !pass.isEmpty() && mail != null && !mail.isEmpty()) {

                    LoginVModel viewModel = new ViewModelProvider(StudentLogin.this).get(LoginVModel.class);

                    networkLoader.showLoadingDialog(StudentLogin.this);
                    viewModel.startLogin(mail, pass, fid).observe(StudentLogin.this, new Observer<loginResponse>() {
                        @Override
                        public void onChanged(loginResponse loginResponse) {
                            if (loginResponse != null) {
                                Log.d("framg", "" + new Gson().toJson(loginResponse));
                                SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(StudentLogin.this);
                                sharedPrefLocal.setUserId(loginResponse.data.user.id);

                                sharedPrefLocal.setUserName(loginResponse.data.user.name);
                                //sharedPrefLocal.setUserDisplay(loginResponse.currentUser.dname);
                                sharedPrefLocal.setSessionId(""+loginResponse.data.tokenData.accessToken);
                                // startActivity(new Intent(StudentLogin.this, BottomNav.class));
                                Toast.makeText(context, LOGIN_SUCCESSFUL , Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle null response here if needed
                                Toast.makeText(context, FAILED_TO_LOGIN, Toast.LENGTH_SHORT).show();
                            }

                            networkLoader.dismissLoadingDialog();

                        }
                    });


                    viewModel.getErrorMessage().observe(StudentLogin.this, new Observer<ErrorData>() {
                        @Override
                        public void onChanged(ErrorData errorData) {
                            // Display error message
                            Toast.makeText(context, FAILED_TO_LOGIN, Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(StudentLogin.this, Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
        });
    }
}