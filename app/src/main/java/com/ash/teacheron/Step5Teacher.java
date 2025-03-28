package com.ash.teacheron;

import static com.ash.teacheron.constants.Contants.SERVER_ERROR;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ash.teacheron.commonComponents.NetworkLoader;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.saveResponse;
import com.ash.teacheron.viewmodel.teacherVM.step5VModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

public class Step5Teacher extends AppCompatActivity {

    private Spinner   monthStartSpinner, yearStartSpinner, monthEndSpinner, yearEndSpinner, associationSpinner,partSpinner;
    private TextInputEditText degreeNameInput,instituteNameInput,jobdes;
    private Button  savestep2;

    NetworkLoader networkLoader;
    String token,userId;
    String    fee_schedule,   fee_amount,   total_experience,   willing_to_travel,   available_for_online,   help_with_homework,   currently_employed,   interested_association,   fee_details,   currency_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step5_teacher);
         ;
        jobdes = findViewById(R.id.jobdes);
        degreeNameInput = findViewById(R.id.degreeName);
        instituteNameInput=findViewById(R.id.institute);
        monthStartSpinner = findViewById(R.id.monthstart);
        yearStartSpinner = findViewById(R.id.yearstart);
        monthEndSpinner = findViewById(R.id.monthend);
        yearEndSpinner = findViewById(R.id.yearend);
        associationSpinner = findViewById(R.id.association);
        partSpinner = findViewById(R.id.parttm);

        savestep2=findViewById(R.id.savestep2);

        loadDummyData();
        networkLoader = new NetworkLoader();

        SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(Step5Teacher.this);
        userId= String.valueOf(sharedPrefLocal.getUserId());
        token= sharedPrefLocal.getSessionId();



        savestep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                fee_amount = jobdes.getText().toString();
                fee_details = degreeNameInput.getText().toString().trim();
                fee_schedule = monthStartSpinner.getSelectedItem().toString();
                willing_to_travel = yearStartSpinner.getSelectedItem().toString();
                available_for_online = monthEndSpinner.getSelectedItem().toString();
                help_with_homework = yearEndSpinner.getSelectedItem().toString();
                currently_employed = associationSpinner.getSelectedItem().toString();
                interested_association=partSpinner.getSelectedItem().toString();
                total_experience = instituteNameInput.getText().toString().trim();
                if (fee_amount.isEmpty()) {
                    Toast.makeText(Step5Teacher.this, "Enter Fee", Toast.LENGTH_SHORT).show();
                    return;
                }

                step5VModel viewModel = new ViewModelProvider(Step5Teacher.this).get(step5VModel.class);
                networkLoader.showLoadingDialog(Step5Teacher.this);

                viewModel.startLogin(  userId,   fee_schedule,   fee_amount,   total_experience,   willing_to_travel,
                        available_for_online,   help_with_homework,
                        currently_employed,   interested_association,   fee_details,   currency_id,
                        token).observe(Step5Teacher.this, new Observer<saveResponse>() {
                    @Override
                    public void onChanged(saveResponse loginResponse) {
                        if (loginResponse != null) {
                            Log.d("framg", "" + new Gson().toJson(loginResponse));

                            Toast.makeText(Step5Teacher.this, "" + loginResponse.message, Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(Step5Teacher.this,Step6Teacher.class);
                            startActivity(intent);
                        } else {
                            // Handle null response here if needed
                            Toast.makeText(Step5Teacher.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                        }

                        networkLoader.dismissLoadingDialog();

                    }
                });
                viewModel.getErrorMessage().observe(Step5Teacher.this, new Observer<ErrorData>() {
                    @Override
                    public void onChanged(ErrorData errorData) {

                        Toast.makeText(Step5Teacher.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                        Log.d("Error", errorData.getMessage());
                        networkLoader.dismissLoadingDialog();
                    }
                });
            }
        });

    }
    private void loadDummyData() {
        // Dummy degree types
        String[] degreeTypes = {"B.Tech", "M.Tech", "MBA", "B.Sc"};
        ArrayAdapter<String> degreeTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, degreeTypes);
        // degreeTypeSpinner.setAdapter(degreeTypeAdapter);

        // Dummy months
        String[] months = {"Weekly", "Monthly", "Daily", "Hourly"};
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, months);
        monthStartSpinner.setAdapter(monthAdapter);
        monthEndSpinner.setAdapter(monthAdapter);

        // Dummy years
        String[] years = {"Yes", "No"};
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);
        yearStartSpinner.setAdapter(yearAdapter);
        yearEndSpinner.setAdapter(yearAdapter);

        // Dummy associations
        String[] associations = {"Harvard", "MIT", "Stanford"};
        ArrayAdapter<String> associationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, associations);
        associationSpinner.setAdapter(associationAdapter);

        String[] timp = {"PartTime", "FullTime" };
        ArrayAdapter<String> tmpAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timp);
        partSpinner.setAdapter(tmpAdapter);

    }

}