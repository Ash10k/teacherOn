package com.ash.teacheron;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.ash.teacheron.retrofit.model.ContactUsRequest;
import com.ash.teacheron.retrofit.model.recommendedProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPass extends AppCompatActivity {

    EditText edmessage;
    TextView gmail;
    RelativeLayout submit;

    //String Email="",deviceId;


    AuthAPI SendData;
    //String TAG;
    ImageView closethis;
    public static String day,muscle;
    NetworkLoader networkLoader;
    SharedPrefLocal sharedPrefLocal;
    String token, userId, usertype,TAG="Profile",Email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_pass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        gmail=findViewById(R.id.txtemail);
        edmessage=findViewById(R.id.message);
        submit=findViewById(R.id.btnsubmit);



        sharedPrefLocal = new SharedPrefLocal(this);
        userId = String.valueOf(sharedPrefLocal.getUserId());
        token = sharedPrefLocal.getSessionId();
        usertype = sharedPrefLocal.getUserType();
       // Email= sharedPrefLocal.getUserEmail();
        networkLoader = new NetworkLoader();

        if (!Email.isEmpty())
        {
            //gmail.setText(Email);
            //edmessage.setText(Email);
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( !edmessage.getText().toString().isEmpty())
                {
                    contactUs();
                }

            }
        });

    }

    public  void contactUs(){

        Log.d("WorkOuts", "userid:" + userId);
        networkLoader.showLoadingDialog(ForgotPass.this);
        SendData = RetrofitBuilder.build().create(AuthAPI.class);

        Call<recommendedProfile> myCall = SendData.forgotPass(token,new ContactUsRequest(Email));
        myCall.enqueue(new Callback<recommendedProfile>() {
            @Override
            public void onResponse(Call<recommendedProfile> call, Response<recommendedProfile> response) {

                if (response.isSuccessful())
                {
                    if ((response.body().status) == "success")
                    {
                        edmessage.setText("");
                        Toast.makeText(ForgotPass.this, "Mail sent please check  your email for new password", Toast.LENGTH_SHORT).show();
                        networkLoader.dismissLoadingDialog();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(ForgotPass.this, "Mail sent please check  your email for new password", Toast.LENGTH_SHORT).show();
                        networkLoader.dismissLoadingDialog();
                        finish();
                    }
                } else {
                    Toast.makeText(ForgotPass.this, "Mail sent please check  your email for new password", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse: error body is here response is not successful " + response.raw());
                    networkLoader.dismissLoadingDialog();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<recommendedProfile> call, Throwable t)
            {
                Toast.makeText(ForgotPass.this, "Mail sent please check  your email for new password", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                networkLoader.dismissLoadingDialog();
                finish();
            }
        });

    }



}