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
import com.ash.teacheron.viewmodel.teacherVM.step6VModel;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

public class Step6Teacher extends AppCompatActivity {

    private Spinner degreeTypeSpinner, monthStartSpinner;
    private TextInputEditText proInput,pdesc,feeamt;
    private Button savestep2;
    private ChipGroup chipGroup;
    NetworkLoader networkLoader;
    String token,userId;

    String profile_intro,  profile_description,  privacy_accepted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_step6_teacher);
        feeamt = findViewById(R.id.jobdes);
        proInput = findViewById(R.id.degreeName);
        pdesc=findViewById(R.id.institute);
        monthStartSpinner = findViewById(R.id.monthstart);

         ;
        savestep2=findViewById(R.id.savestep2);
        chipGroup = findViewById(R.id.chipGroup);
        loadDummyData();
        networkLoader = new NetworkLoader();

        SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(Step6Teacher.this);
        userId= String.valueOf(sharedPrefLocal.getUserId());
        token= sharedPrefLocal.getSessionId();


         
        savestep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                profile_intro=proInput.getText().toString();
                profile_description=pdesc.getText().toString();
                privacy_accepted=monthStartSpinner.getSelectedItem().toString();

                step6VModel viewModel = new ViewModelProvider(Step6Teacher.this).get(step6VModel.class);
                networkLoader.showLoadingDialog(Step6Teacher.this);
                viewModel.startLogin(userId,profile_intro,profile_description,privacy_accepted, token).observe(Step6Teacher.this, new Observer<saveResponse>() {
                    @Override
                    public void onChanged(saveResponse loginResponse) {
                        if (loginResponse != null) {
                            Log.d("framg", "" + new Gson().toJson(loginResponse));

                            Toast.makeText(Step6Teacher.this, "" + loginResponse.message, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Step6Teacher.this,BottomNav.class);
                            startActivity(intent);
                        } else {
                            // Handle null response here if needed
                            Toast.makeText(Step6Teacher.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                        }

                        networkLoader.dismissLoadingDialog();

                    }
                });
                viewModel.getErrorMessage().observe(Step6Teacher.this, new Observer<ErrorData>() {
                    @Override
                    public void onChanged(ErrorData errorData) {

                        Toast.makeText(Step6Teacher.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                        Log.d("Error", errorData.getMessage());
                        networkLoader.dismissLoadingDialog();
                    }
                });
            }
        });

    }
    private void loadDummyData() {

        // Dummy months
        String[] months = {"Yes", "No"};
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, months);
        monthStartSpinner.setAdapter(monthAdapter);

    }


}