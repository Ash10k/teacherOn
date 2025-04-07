package com.ash.teacheron;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.ash.teacheron.retrofit.model.saveResponse;
import com.ash.teacheron.retrofit.model.teaacherModel.registerRequest;
import com.ash.teacheron.retrofit.model.teaacherModel.registerResponse;
import com.ash.teacheron.viewmodel.registerVM.RegisterVModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEditTeacher extends AppCompatActivity {
    Button login;
    TextInputEditText new_Email, new_Password,phone,fname,speciality,postalcode;
    String pass, mail, selectedGender ,phn,fnm,lnme,spcl,location,path,TAG="Register",pstcode,token;
    SharedPrefLocal sharedPrefLocal;
    NetworkLoader networkLoader;
    CircleImageView profilePhoto;
    Uri fileUri;
    RequestBody requestBody = null;
    TextView date_choosen;
    AlertDialog mydialog;
    DatePickerDialog datePickerDialog;
    RelativeLayout requesttutor;
    LinearLayout alrdylogin,choosedb;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_edit_teacher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        

        networkLoader = new NetworkLoader();
        sharedPrefLocal = new SharedPrefLocal(this);
        userId =  (sharedPrefLocal.getUserId());
        token = sharedPrefLocal.getSessionId();
        login =  findViewById(R.id.registerbtn);
        new_Email =  findViewById(R.id.new_Email);

        phone= findViewById(R.id.phone);
        fname= findViewById(R.id.fname);
        date_choosen= findViewById(R.id.date_choosen);
        choosedb=findViewById(R.id.choosedb);
        speciality= findViewById(R.id.speciality);
        postalcode= findViewById(R.id.postalcode);
        Spinner genderSpinner = findViewById(R.id.choosegender);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.gender_options, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        genderSpinner.setAdapter(adapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle case where nothing is selected
            }
        });

        new_Email.setText(sharedPrefLocal.getUserEmail());
        fname.setText(sharedPrefLocal.getUserName());
        phone.setText(sharedPrefLocal.getUserPhone());

        //Toast.makeText(this, ""+userId, Toast.LENGTH_SHORT).show();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                // pass = new_Password.getText().toString();
                mail = new_Email.getText().toString();
                pstcode = postalcode.getText().toString();
                fnm = fname.getText().toString();
                lnme = date_choosen.getText().toString();
                spcl=speciality.getText().toString();
                phn=phone.getText().toString();


                if (  mail != null && !mail.isEmpty() &&
                        fnm != null && !fnm.isEmpty() &&
                        lnme != null && !lnme.isEmpty()
                )
                {
                        updateTecher();
                }
                else
                    Toast.makeText(ProfileEditTeacher.this, "Choose all the fields", Toast.LENGTH_SHORT).show();
            }
        });


        choosedb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDatePicker();
            }
        });

    }

    private boolean updateTecher() {
        // tried_username_pass(user, pass);
        try {
            networkLoader.showLoadingDialog(ProfileEditTeacher.this);
            AuthAPI SendData = RetrofitBuilder.build().create(AuthAPI.class);

            Call<saveResponse> myCall = SendData.updateTeacherPro(token,new registerRequest(userId,fnm,mail,"",phn,"India","0","0","teacher",pstcode,spcl,selectedGender,lnme));
            myCall.enqueue(new Callback<saveResponse>() {
                @Override
                public void onResponse(Call<saveResponse> call, Response<saveResponse> response) {
                    if (response.isSuccessful()) {

                        networkLoader.dismissLoadingDialog();
                        try {
                            finish();
                            Toast.makeText(ProfileEditTeacher.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        Toast.makeText(ProfileEditTeacher.this, "Server Error" + response.raw(), Toast.LENGTH_LONG).show();
                        //Log.d(TAG, "onResponse: error body is here response is not successful " + response.raw());
                        networkLoader.dismissLoadingDialog();
                    }
                }

                @Override
                public void onFailure(Call<saveResponse> call, Throwable t) {
                    Toast.makeText(ProfileEditTeacher.this, "Server Error", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    networkLoader.dismissLoadingDialog();
                }
            });
        } catch (Exception exception) {
            Toast.makeText(ProfileEditTeacher.this, "Server Error", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private  void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;
                String date=i+"-"+i1+"-"+i2;
                date_choosen.setText(date);
            }
        };

        Calendar cal =Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);


        datePickerDialog=new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener,year,month,day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();

    }
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}