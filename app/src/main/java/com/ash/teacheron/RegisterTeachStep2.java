package com.ash.teacheron;

import static com.ash.teacheron.constants.Contants.Login_credentials;
import static com.ash.teacheron.constants.Contants.SERVER_ERROR;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.ash.teacheron.retrofit.model.saveResponse;
import com.ash.teacheron.retrofit.model.teaacherModel.step2teacher;
import com.ash.teacheron.viewmodel.teacherVM.step2VModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterTeachStep2 extends AppCompatActivity {

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
    private Spinner subjectSpinner, fromLevelSpinner, toLevelSpinner;
    private Button addButton,savestep2;
    private List<appOptionsResponse.Subject> subjectsList = new ArrayList<>();
    private List<appOptionsResponse.Level> levelsList = new ArrayList<>();
    private List<step2teacher.SubjectSelection> selectedSubjects = new ArrayList<>();
    private Set<Integer> addedSubjectIds = new HashSet<>(); // To prevent duplicates
    private ChipGroup chipGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_teach_step2);
         ;
        addButton = findViewById(R.id.addu);
        savestep2=findViewById(R.id.savestep2);
        subjectSpinner = findViewById(R.id.subjectSpinner);
        fromLevelSpinner = findViewById(R.id.fromLevelSpinner);
        toLevelSpinner = findViewById(R.id.toLevelSpinner);
        chipGroup = findViewById(R.id.chipGroup);
        //itemClick=this;


        try {
            option = getIntent().getStringExtra("Option");
            if (option.equals("Edit")) {
                topBar.setVisibility(View.GONE);
                btn.setText("Close");
            }
        } catch (Exception ex) {
            option = "";
        }


        networkLoader = new NetworkLoader();

        SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(RegisterTeachStep2.this);
        userId= String.valueOf(sharedPrefLocal.getUserId());

        sharedPreferences = getSharedPreferences(Login_credentials, MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSubject();
            }
        });
        savestep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                step2VModel viewModel = new ViewModelProvider(RegisterTeachStep2.this).get(step2VModel.class);
                networkLoader.showLoadingDialog(RegisterTeachStep2.this);
                viewModel.startLogin(selectedSubjects, userId, token).observe(RegisterTeachStep2.this, new Observer<saveResponse>() {
                    @Override
                    public void onChanged(saveResponse loginResponse) {
                        if (loginResponse != null) {
                            Log.d("framg", "" + new Gson().toJson(loginResponse));

                            Toast.makeText(RegisterTeachStep2.this, "" + loginResponse.message, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RegisterTeachStep2.this,Step3Teacher.class);
                            startActivity(intent);
                        } else {
                            // Handle null response here if needed
                            Toast.makeText(RegisterTeachStep2.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                        }

                        networkLoader.dismissLoadingDialog();

                    }
                });
                viewModel.getErrorMessage().observe(RegisterTeachStep2.this, new Observer<ErrorData>() {
                    @Override
                    public void onChanged(ErrorData errorData) {
                        // Display error message
                        Toast.makeText(RegisterTeachStep2.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                        Log.d("Error", errorData.getMessage());
                        networkLoader.dismissLoadingDialog();
                    }
                });

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


            networkLoader.showLoadingDialog(RegisterTeachStep2.this);
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
                                recyclerView.setLayoutManager(new LinearLayoutManager(RegisterTeachStep2.this, LinearLayoutManager.VERTICAL, false));

                                if (step2EducationAdapter == null) {



                                } else {

                                    step2EducationAdapter.notifyDataSetChanged();
                                }

                            }
                            else {

                            }
                            recyclerView.setAdapter(step2EducationAdapter);*/
                        } else {

                            Toast.makeText(RegisterTeachStep2.this, "" + response.body().message, Toast.LENGTH_SHORT).show();
                            networkLoader.dismissLoadingDialog();

                        }
                    } else {
                        Toast.makeText(RegisterTeachStep2.this, "Server Error" + response.raw(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onResponse: error body is here response is not successful " + response.raw());
                        networkLoader.dismissLoadingDialog();
                        educationlist.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<appOptionsResponse> call, Throwable t) {
                    Toast.makeText(RegisterTeachStep2.this, "Server Error", Toast.LENGTH_SHORT).show();
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


    /* @Override
     public void onItemClick(int position) {
         try {
             Intent intent=new Intent(StepFrom2.this,EditEducation.class);
             intent.putExtra("id",mylist.get(position).id);
             intent.putExtra("user_id",mylist.get(position).user_id);
             intent.putExtra("from",mylist.get(position).from_date);
             intent.putExtra("to",mylist.get(position).to_date);
             intent.putExtra("currently",mylist.get(position).current_attending);
             intent.putExtra("school",mylist.get(position).university);
             intent.putExtra("course",mylist.get(position).course_name);
             intent.putExtra("major",mylist.get(position).major);
             intent.putExtra("gpa",mylist.get(position).gpa);
             if (mylist.get(position).edj==null)
                 intent.putExtra("eduJ","");

             else
                 intent.putExtra("eduJ",mylist.get(position).edj.name);

             startActivity(intent);
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }


     }
 */
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


    private void addSubject() {
        int subjectIndex = subjectSpinner.getSelectedItemPosition();
        int fromLevelIndex = fromLevelSpinner.getSelectedItemPosition();
        int toLevelIndex = toLevelSpinner.getSelectedItemPosition();

        if (subjectIndex == -1 || fromLevelIndex == -1 || toLevelIndex == -1) {
            Toast.makeText(this, "Please select all values", Toast.LENGTH_SHORT).show();
            return;
        }

        appOptionsResponse.Subject selectedSubject = subjectsList.get(subjectIndex);
        appOptionsResponse.Level fromLevel = levelsList.get(fromLevelIndex);
        appOptionsResponse.Level toLevel = levelsList.get(toLevelIndex);

        // Prevent duplicate subjects
        if (addedSubjectIds.contains(selectedSubject.id)) {
            Toast.makeText(this, "This subject is already added", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add the selected subject
        step2teacher.SubjectSelection subjectSelection = new step2teacher.SubjectSelection(selectedSubject.id, fromLevel.id, toLevel.id);
        selectedSubjects.add(subjectSelection);
        addedSubjectIds.add(selectedSubject.id);

        // Display in ChipGroup
        addChip(selectedSubject.title, subjectSelection);

        Toast.makeText(this, "Subject added!", Toast.LENGTH_SHORT).show();

        // Print updated JSON
        printJsonData();
    }
    private void printJsonData() {
        Gson gson = new Gson();
        String json = gson.toJson(new step2teacher.SubjectRequestBody(selectedSubjects));
        System.out.println(json);
    }


    private void setupSpinners() {
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getSubjectNames());
        subjectSpinner.setAdapter(subjectAdapter);

        ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getLevelNames());
        fromLevelSpinner.setAdapter(levelAdapter);
        toLevelSpinner.setAdapter(levelAdapter);
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
    private void addChip(String subjectName, step2teacher.SubjectSelection subjectSelection) {
        Chip chip = new Chip(this);
        chip.setText(subjectName);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(view -> removeSubject(subjectSelection, chip));
        chipGroup.addView(chip);
    }

    private void removeSubject(step2teacher.SubjectSelection subjectSelection, Chip chip) {
        chipGroup.removeView(chip);
        selectedSubjects.remove(subjectSelection);
        addedSubjectIds.remove(subjectSelection.subjectId);

        Toast.makeText(this, "Removed: " + subjectSelection.subjectId, Toast.LENGTH_SHORT).show();

        // Print updated JSON
        printJsonData();
    }



}