package com.ash.teacheron;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ash.teacheron.commonComponents.NetworkLoader;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.recommendedProfile;
import com.ash.teacheron.retrofit.model.saveResponse;
import com.ash.teacheron.retrofit.model.teaacherModel.registerRequest;
import com.ash.teacheron.retrofit.model.teaacherModel.registerResponse;
import com.ash.teacheron.viewmodel.registerVM.RegisterVModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEdit extends AppCompatActivity {

    Spinner genderSpinner;

    Button login;
    TextInputEditText new_Email, new_Password,phone,fname,speciality,locat,postalcode;
    String pass, mail, userTpe ,location,fnm,lnme,cnf,path,TAG="Register",pstcode,userId,token;

    NetworkLoader networkLoader;
    CircleImageView profilePhoto;
    Uri fileUri;
    RequestBody requestBody = null;
    TextView date_choosen;
    AlertDialog mydialog;
    DatePickerDialog datePickerDialog;
    RelativeLayout requesttutor;
    LinearLayout alrdylogin,choosedb;
    SharedPrefLocal sharedPrefLocal;
    int prot=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        login =  findViewById(R.id.registerbtn);
        new_Email =  findViewById(R.id.new_Email);
        locat=findViewById(R.id.locat);
        phone= findViewById(R.id.phone);
        fname= findViewById(R.id.fname);
        genderSpinner = findViewById(R.id.choosegender);
        prot=getIntent().getIntExtra("prot",0);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.typeopti, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        genderSpinner.setAdapter(adapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userTpe = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle case where nothing is selected
            }
        });

        networkLoader = new NetworkLoader();

        sharedPrefLocal = new SharedPrefLocal(this);
        userId = String.valueOf(sharedPrefLocal.getUserId());
        token = sharedPrefLocal.getSessionId();
        new_Email.setText(sharedPrefLocal.getUserEmail());
        fname.setText(sharedPrefLocal.getUserName());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mail = new_Email.getText().toString();
                pstcode = phone.getText().toString();
                fnm = fname.getText().toString();
                location=locat.getText().toString();
                if (  mail != null && !mail.isEmpty() && fnm != null && !fnm.isEmpty())
                {
                    if (prot==56)
                        updateTecher();
                    else
                        getProfiledata();
                }
                else
                    Toast.makeText(ProfileEdit.this, "Choose all the fields", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean getProfiledata() {
        // tried_username_pass(user, pass);
        try {
            networkLoader.showLoadingDialog(ProfileEdit.this);
            AuthAPI SendData = RetrofitBuilder.build().create(AuthAPI.class);

            RequestBody m1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(location));
            RequestBody m2 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(fnm));
            RequestBody m3 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mail));
            RequestBody m4 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(pstcode));
            RequestBody m5 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userTpe));

            RequestBody lt1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf("0"));
            RequestBody ln1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf("0"));


            MultipartBody.Part t1 = MultipartBody.Part.createFormData("location", null, m1);
            MultipartBody.Part t2 = MultipartBody.Part.createFormData("name", null, m2);
            MultipartBody.Part t3 = MultipartBody.Part.createFormData("email", null, m3);
            MultipartBody.Part t4 = MultipartBody.Part.createFormData("phone", null, m4);
            MultipartBody.Part t5 = MultipartBody.Part.createFormData("user_type", null, m5);

            MultipartBody.Part lt = MultipartBody.Part.createFormData("latitude", null, lt1);
            MultipartBody.Part ln = MultipartBody.Part.createFormData("longitude", null, ln1);


            Call<saveResponse> myCall = SendData.updateStudentPro(token,t3,t2,t4,t1,lt,ln,t5);
            myCall.enqueue(new Callback<saveResponse>() {
                @Override
                public void onResponse(Call<saveResponse> call, Response<saveResponse> response) {
                    if (response.isSuccessful()) {

                            networkLoader.dismissLoadingDialog();
                            try {
                                finish();
                                Toast.makeText(ProfileEdit.this, "Profile updated", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                    } else {
                        Toast.makeText(ProfileEdit.this, "Server Error" + response.raw(), Toast.LENGTH_LONG).show();
                        //Log.d(TAG, "onResponse: error body is here response is not successful " + response.raw());
                        networkLoader.dismissLoadingDialog();
                    }
                }

                @Override
                public void onFailure(Call<saveResponse> call, Throwable t) {
                    Toast.makeText(ProfileEdit.this, "Server Error", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    networkLoader.dismissLoadingDialog();
                }
            });
        } catch (Exception exception) {
            Toast.makeText(ProfileEdit.this, "Some thing wrong", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean updateTecher() {
        // tried_username_pass(user, pass);
        try {
            networkLoader.showLoadingDialog(ProfileEdit.this);
            AuthAPI SendData = RetrofitBuilder.build().create(AuthAPI.class);
            Call<saveResponse> myCall = SendData.updateTeacherPro(token,new registerRequest(mail,fnm,pstcode,location,"0","0",userTpe,"","","","",""));
            myCall.enqueue(new Callback<saveResponse>() {
                @Override
                public void onResponse(Call<saveResponse> call, Response<saveResponse> response) {
                    if (response.isSuccessful()) {

                        networkLoader.dismissLoadingDialog();
                        try {
                            finish();
                            Toast.makeText(ProfileEdit.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        Toast.makeText(ProfileEdit.this, "Server Error" + response.raw(), Toast.LENGTH_LONG).show();
                        //Log.d(TAG, "onResponse: error body is here response is not successful " + response.raw());
                        networkLoader.dismissLoadingDialog();
                    }
                }

                @Override
                public void onFailure(Call<saveResponse> call, Throwable t) {
                    Toast.makeText(ProfileEdit.this, "Server Error", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    networkLoader.dismissLoadingDialog();
                }
            });
        } catch (Exception exception) {
            Toast.makeText(ProfileEdit.this, "Some thing wrong", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}