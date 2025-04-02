package com.ash.teacheron;

import static com.ash.teacheron.constants.Contants.SERVER_ERROR;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ash.teacheron.commonComponents.NetworkLoader;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.appOptionsResponse;
import com.ash.teacheron.retrofit.model.registerResponseStud;
import com.ash.teacheron.retrofit.model.saveResponse;
import com.ash.teacheron.retrofit.model.teaacherModel.step2teacher;
import com.ash.teacheron.viewmodel.studentVM.step1VModelStudent;
import com.ash.teacheron.viewmodel.teacherVM.step2VModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRegisterStep1 extends AppCompatActivity {

    LinearLayout openstudlogin;
    RelativeLayout asateacher;

    RelativeLayout nextPage2, add_edi, topBar;
    TextView btn;
    AuthAPI SendData;
    String TAG = "StepFrom2", token;
    //List<appOptionsResponse.Mydata> mylist;
    RelativeLayout educationlist;
    SharedPreferences sharedPreferences;

    //Step2EducationAdapter.Step2EducationAdapter_ItemClick itemClick;

    RecyclerView recyclerView;
    //Step2EducationAdapter step2EducationAdapter;
    String option = "",userId;
    NetworkLoader networkLoader;
    private Spinner subjectSpinner, fromLevelSpinner, ima;
    private Button savestep2;
    private List<appOptionsResponse.Subject> subjectsList = new ArrayList<>();
    private List<appOptionsResponse.Level> levelsList = new ArrayList<>();
    private List<step2teacher.SubjectSelection> selectedSubjects = new ArrayList<>();
    private Set<Integer> addedSubjectIds = new HashSet<>(); // To prevent duplicates
    private ChipGroup chipGroup;
    TextInputEditText detailsof,new_Email,new_Password,fname,phone;

    String email,   name,   phonem,   location,   subject_id,   level_id,   user_type,   requirements,   password,   latitude,   longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_register_step1);
        asateacher=findViewById(R.id.asateacher);
        openstudlogin=findViewById(R.id.openstudlogin);
        detailsof=findViewById(R.id.detailsof);
        new_Email=findViewById(R.id.new_Email);
        new_Password=findViewById(R.id.new_Password);
        fname=findViewById(R.id.fname);
        phone=findViewById(R.id.phone);
        ima=findViewById(R.id.iamaa);

        TextInputLayout emailLayout = findViewById(R.id.emailLayout);
        new_Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isValidEmail(s.toString())) {
                    emailLayout.setError("Invalid email format");
                } else {
                    emailLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        asateacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(StudentRegisterStep1.this,Register.class));
            }
        });

        openstudlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(StudentRegisterStep1.this,Login.class));
            }
        });


        savestep2=findViewById(R.id.registerbtn);
        subjectSpinner = findViewById(R.id.subjectSpinner);
        fromLevelSpinner = findViewById(R.id.fromLevelSpinner);


        networkLoader = new NetworkLoader();


        savestep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = new_Email.getText().toString().trim();
                requirements = detailsof.getText().toString().trim();
                name = fname.getText().toString().trim();
                password = new_Password.getText().toString().trim();
                phonem= phone.getText().toString().trim();
                int subjectIndex = subjectSpinner.getSelectedItemPosition();
                int fromLevelIndex = fromLevelSpinner.getSelectedItemPosition();

                if (subjectIndex == -1 || fromLevelIndex == -1 ) {
                    Toast.makeText(StudentRegisterStep1.this, "Please select all values", Toast.LENGTH_SHORT).show();
                    return;
                }

                subject_id= String.valueOf(subjectsList.get(subjectIndex).id);
                level_id= String.valueOf(levelsList.get(fromLevelIndex).id);
                user_type=ima.getSelectedItem().toString();

                if (email.isEmpty()) {
                    Toast.makeText(StudentRegisterStep1.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else if (requirements.isEmpty()) {
                    Toast.makeText(StudentRegisterStep1.this, "Enter Requirements", Toast.LENGTH_SHORT).show();
                } else if (name.isEmpty()) {
                    Toast.makeText(StudentRegisterStep1.this, "Enter Name", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty() ) {
                    Toast.makeText(StudentRegisterStep1.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if (password.length() < 6) {
                    Toast.makeText(StudentRegisterStep1.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                }else if (phonem.isEmpty()) {
                    Toast.makeText(StudentRegisterStep1.this, "Enter Phone", Toast.LENGTH_SHORT).show();
                }else {
                    step1VModelStudent viewModel = new ViewModelProvider(StudentRegisterStep1.this).get(step1VModelStudent.class);
                    networkLoader.showLoadingDialog(StudentRegisterStep1.this);
                    viewModel.startLogin( email,   name,   phonem,   location,   subject_id,   level_id,   user_type,   requirements,   password,   latitude,   longitude).observe(StudentRegisterStep1.this, new Observer<registerResponseStud>() {
                        @Override
                        public void onChanged(registerResponseStud loginResponse) {
                            if (loginResponse != null) {
                                Log.d("framg", "" + new Gson().toJson(loginResponse));
                                SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(StudentRegisterStep1.this);
                                sharedPrefLocal.setUserId(Integer.parseInt(loginResponse.data.id));
                                sharedPrefLocal.setUserPassword(password);

                               // sharedPrefLocal.setUserName(loginResponse.data.user.name);
                                //sharedPrefLocal.setUserDisplay(loginResponse.currentUser.dname);
                               // sharedPrefLocal.setSessionId(""+loginResponse.data.tokenData.accessToken);

                                Toast.makeText(StudentRegisterStep1.this, "" + loginResponse.message, Toast.LENGTH_SHORT).show();
                                 Intent intent=new Intent(StudentRegisterStep1.this,StudentRegisterStep2.class);
                                 startActivity(intent);
                            } else {
                                // Handle null response here if needed
                                Toast.makeText(StudentRegisterStep1.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                            }

                            networkLoader.dismissLoadingDialog();

                        }
                    });
                    viewModel.getErrorMessage().observe(StudentRegisterStep1.this, new Observer<ErrorData>() {
                        @Override
                        public void onChanged(ErrorData errorData) {
                            // Display error message
                            Toast.makeText(StudentRegisterStep1.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                            Log.d("Error", errorData.getMessage());
                            networkLoader.dismissLoadingDialog();
                        }
                    });

                }


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        get_edu_journey();
    }

    private boolean get_edu_journey() {
        // tried_username_pass(user, pass);
        try {


            networkLoader.showLoadingDialog(StudentRegisterStep1.this);
            SendData = RetrofitBuilder.build().create(AuthAPI.class);
            // String first_name, String last_name, String email, String password_confirmation, String password
            Call<appOptionsResponse> myCall = SendData.getAppOptions("Bearer " + token);
            myCall.enqueue(new Callback<appOptionsResponse>() {
                @Override
                public void onResponse(Call<appOptionsResponse> call, Response<appOptionsResponse> response) {

                    if (response.isSuccessful()) {

                        if ((response.body().status).equals("success")) {
                            networkLoader.dismissLoadingDialog();
                            subjectsList = response.body().data.subjects;
                            levelsList = response.body().data.levels;
                            setupSpinners();
                            //mylist = response.body().data;
                            /*if (!mylist.isEmpty()) {

                                step2EducationAdapter = new Step2EducationAdapter(StepFrom2.this,mylist,itemClick);
                                recyclerView.setLayoutManager(new LinearLayoutManager(StudentRegisterStep1.this, LinearLayoutManager.VERTICAL, false));

                                if (step2EducationAdapter == null) {



                                } else {

                                    step2EducationAdapter.notifyDataSetChanged();
                                }

                            }
                            else {

                            }
                            recyclerView.setAdapter(step2EducationAdapter);*/
                        } else {

                            Toast.makeText(StudentRegisterStep1.this, "" + response.body().message, Toast.LENGTH_SHORT).show();
                            networkLoader.dismissLoadingDialog();

                        }
                    } else {
                        Toast.makeText(StudentRegisterStep1.this, "Server Error" + response.raw(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onResponse: error body is here response is not successful " + response.raw());
                        networkLoader.dismissLoadingDialog();
                        educationlist.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<appOptionsResponse> call, Throwable t) {
                    Toast.makeText(StudentRegisterStep1.this, "Server Error", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    networkLoader.dismissLoadingDialog();
                }
            });
        } catch (Exception exception) {
            Toast.makeText(this, "Some thing wrong", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    
    @Override
    public void finish() {
        super.finish();
        /*if (!option.equals("Edit"))
        {
            Intent intent = new Intent(StepFrom2.this, StepFrom1.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
        }*/
    }





    private void setupSpinners() {
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getSubjectNames());
        subjectSpinner.setAdapter(subjectAdapter);

        ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getLevelNames());
        fromLevelSpinner.setAdapter(levelAdapter);

        String[] months = {"Student", "Parent/Guardian", "Professional"};
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, months);
        ima.setAdapter(monthAdapter);
    }

    private List<String> getSubjectNames() {
        List<String> names = new ArrayList<>();
        for (appOptionsResponse.Subject subject : subjectsList) {
            names.add(subject.title);
        }
        return names;
    }

    private List<String> getLevelNames() {
        List<String> names = new ArrayList<>();
        for (appOptionsResponse.Level level : levelsList) {
            names.add(level.title);
        }
        return names;
    }
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}