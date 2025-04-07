package com.ash.teacheron;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ash.teacheron.commonComponents.NetworkLoader;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.appOptionsResponse;
import com.ash.teacheron.retrofit.model.passwordResponse;
import com.ash.teacheron.retrofit.model.teaacherModel.registerRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

    NetworkLoader networkLoader;
    String token, userId, TAG = "ChangePassword",p1,p2,p3;
    AuthAPI SendData;
    EditText pass1,pass2,pass3;
    RelativeLayout loginpage;
    SharedPrefLocal sharedPrefLocal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedPrefLocal = new SharedPrefLocal(ChangePassword.this);

        token = sharedPrefLocal.getSessionId();

        loginpage=findViewById(R.id.loginpage);
        pass1=findViewById(R.id.password);
        pass2=findViewById(R.id.password2);
        pass3=findViewById(R.id.password3);
        networkLoader = new NetworkLoader();
        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                p1=pass1.getText().toString().trim();
                p2=pass2.getText().toString().trim();
                p3=pass3.getText().toString().trim();
                if (p1!=null && !p1.isEmpty() && p2!=null && !p2.isEmpty() && p3!=null && !p3.isEmpty() )
                {
                    if (p1.length() < 8 || p2.length() < 8  ) {
                        Toast.makeText(ChangePassword.this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (p1.equals(p2))
                        {
                            resetPass();
                        }
                        else {
                            Toast.makeText(ChangePassword.this, "New password & Confirm Password will be same", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else {
                    Toast.makeText(ChangePassword.this, "Enter all the password to continue", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private boolean resetPass() {
        // tried_username_pass(user, pass);
        try {


            networkLoader.showLoadingDialog(ChangePassword.this);
            AuthAPI SendData = RetrofitBuilder.build().create(AuthAPI.class);
            // String first_name, String last_name, String email, String password_confirmation, String password
            Call<passwordResponse> myCall = SendData.resetPass("" + token,new registerRequest(p3,p2));
            myCall.enqueue(new Callback<passwordResponse>() {
                @Override
                public void onResponse(Call<passwordResponse> call, Response<passwordResponse> response) {

                    if (response.isSuccessful()) {

                        if ((response.body().status).equals("success")) {
                            networkLoader.dismissLoadingDialog();
                            Toast.makeText(ChangePassword.this, "Password changed please relogin to continue" , Toast.LENGTH_SHORT).show();


                            sharedPrefLocal.clear();
                            Intent intent = new Intent(ChangePassword.this, Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        } else {

                            Toast.makeText(ChangePassword.this, "Old password is incorrect", Toast.LENGTH_SHORT).show();
                            networkLoader.dismissLoadingDialog();

                        }
                    } else {
                        Toast.makeText(ChangePassword.this, "Old password is incorrect", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onResponse: error body is here response is not successful " + response.raw());
                        networkLoader.dismissLoadingDialog();
                        // educationlist.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<passwordResponse> call, Throwable t) {
                    Toast.makeText(ChangePassword.this, "Server Error", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    networkLoader.dismissLoadingDialog();
                }
            });
        } catch (Exception exception) {
            networkLoader.dismissLoadingDialog();
            exception.printStackTrace();
            Toast.makeText(ChangePassword.this, "Some thing wrong", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }
}