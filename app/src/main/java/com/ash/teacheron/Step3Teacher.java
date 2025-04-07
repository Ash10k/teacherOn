package com.ash.teacheron;

import static com.ash.teacheron.constants.Contants.SERVER_ERROR;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ash.teacheron.commonComponents.NetworkLoader;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.saveResponse;
import com.ash.teacheron.retrofit.model.teaacherModel.step3teacher;
import com.ash.teacheron.viewmodel.teacherVM.step3VModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
public class Step3Teacher extends AppCompatActivity {

    private Spinner degreeTypeSpinner, monthStartSpinner, yearStartSpinner, monthEndSpinner, yearEndSpinner, associationSpinner;
    private TextInputEditText degreeNameInput,instituteNameInput;
    private Button addButton,savestep2;
    private ChipGroup chipGroup;

    private List<step3teacher.Degree> degreeList = new ArrayList<>();
    private Set<String> addedDegreeSet = new HashSet<>(); // Prevent duplicates
    NetworkLoader networkLoader;
    String token,userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step3_teacher);
         ;
        degreeTypeSpinner = findViewById(R.id.degreeTYPESpinner);
        degreeNameInput = findViewById(R.id.degreeName);
        instituteNameInput=findViewById(R.id.institute);
        monthStartSpinner = findViewById(R.id.monthstart);
        yearStartSpinner = findViewById(R.id.yearstart);
        monthEndSpinner = findViewById(R.id.monthend);
        yearEndSpinner = findViewById(R.id.yearend);
        associationSpinner = findViewById(R.id.association);
        addButton = findViewById(R.id.addButton);
        savestep2=findViewById(R.id.savestep2);
        chipGroup = findViewById(R.id.chipGroup);
        loadDummyData();
        networkLoader = new NetworkLoader();

        SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(Step3Teacher.this);
        userId= String.valueOf(sharedPrefLocal.getUserId());
        token= sharedPrefLocal.getSessionId();
        

        addButton.setOnClickListener(view -> addDegree());
        savestep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (degreeList!=null) {
                    if (degreeList.size() > 0) {
                        step3VModel viewModel = new ViewModelProvider(Step3Teacher.this).get(step3VModel.class);
                        networkLoader.showLoadingDialog(Step3Teacher.this);
                        viewModel.startLogin(degreeList, userId, token).observe(Step3Teacher.this, new Observer<saveResponse>() {
                            @Override
                            public void onChanged(saveResponse loginResponse) {
                                if (loginResponse != null) {
                                    Log.d("framg", "" + new Gson().toJson(loginResponse));

                                    Toast.makeText(Step3Teacher.this, "" + loginResponse.message, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(Step3Teacher.this,Step4Teacher.class);
                                    startActivity(intent);
                                } else {
                                    // Handle null response here if needed
                                    Toast.makeText(Step3Teacher.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                                }

                                networkLoader.dismissLoadingDialog();

                            }
                        });
                        viewModel.getErrorMessage().observe(Step3Teacher.this, new Observer<ErrorData>() {
                            @Override
                            public void onChanged(ErrorData errorData) {

                                Toast.makeText(Step3Teacher.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                                Log.d("Error", errorData.getMessage());
                                networkLoader.dismissLoadingDialog();
                            }
                        });

                    }
                    else {
                        Toast.makeText(Step3Teacher.this, "Please add certifications to continue", Toast.LENGTH_SHORT).show();
                    }
                }
               }
        });

    }
    private void loadDummyData() {
        // Dummy degree types
        String[] degreeTypes = {"B.Tech", "M.Tech", "MBA", "B.Sc"};
        ArrayAdapter<String> degreeTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, degreeTypes);
        degreeTypeSpinner.setAdapter(degreeTypeAdapter);

        // Dummy months
        String[] months = {"January", "February", "March", "April"};
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, months);
        monthStartSpinner.setAdapter(monthAdapter);
        monthEndSpinner.setAdapter(monthAdapter);

        // Dummy years
        String[] years = {"2020", "2021", "2022", "2023"};
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);
        yearStartSpinner.setAdapter(yearAdapter);
        yearEndSpinner.setAdapter(yearAdapter);

        // Dummy associations
        String[] associations = {"Harvard", "MIT", "Stanford"};
        ArrayAdapter<String> associationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, associations);
        associationSpinner.setAdapter(associationAdapter);
    }

    private void addDegree() {
        String degreeType = degreeTypeSpinner.getSelectedItem().toString();
        String degreeName = degreeNameInput.getText().toString().trim();
        String monthStart = monthStartSpinner.getSelectedItem().toString();
        String yearStart = yearStartSpinner.getSelectedItem().toString();
        String monthEnd = monthEndSpinner.getSelectedItem().toString();
        String yearEnd = yearEndSpinner.getSelectedItem().toString();
        String association = associationSpinner.getSelectedItem().toString();
        String instituteName = instituteNameInput.getText().toString().trim();
        if (degreeName.isEmpty()) {
            Toast.makeText(this, "Enter Degree Name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Unique key to prevent duplicates
        String degreeKey = degreeType + "-" + degreeName + "-" + monthStart + "-" + yearStart + "-" + monthEnd + "-" + yearEnd + "-" + association + "-" + instituteName;

        if (addedDegreeSet.contains(degreeKey)) {
            Toast.makeText(this, "This degree is already added", Toast.LENGTH_SHORT).show();
            return;
        }

        step3teacher.Degree newDegree = new step3teacher.Degree(degreeType, degreeName, monthStart, yearStart, monthEnd, yearEnd, association,instituteName);
        degreeList.add(newDegree);
        addedDegreeSet.add(degreeKey);

        // Display Chip
        addChip(degreeName, newDegree, degreeKey);

        Toast.makeText(this, "Certification Added!", Toast.LENGTH_SHORT).show();

        // Print updated JSON
        printJsonData();
    }

    private void addChip(String degreeName, step3teacher.Degree degree, String degreeKey) {
        Chip chip = new Chip(this);
        chip.setText(degreeName);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(view -> removeDegree(degree, chip, degreeKey));

        chipGroup.addView(chip);
    }

    private void removeDegree(step3teacher.Degree degree, Chip chip, String degreeKey) {
        chipGroup.removeView(chip);
        degreeList.remove(degree);
        addedDegreeSet.remove(degreeKey);

        Toast.makeText(this, "Removed: " + degree.degreeName, Toast.LENGTH_SHORT).show();

        // Print updated JSON
        printJsonData();
    }

    private void printJsonData() {
        Gson gson = new Gson();
        String json = gson.toJson(new step3teacher.DegreeRequestBody(degreeList));
        System.out.println(json);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof TextInputEditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}