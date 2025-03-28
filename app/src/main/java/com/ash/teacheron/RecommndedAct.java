package com.ash.teacheron;

import static com.ash.teacheron.constants.Contants.SERVER_ERROR;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ash.teacheron.adapter.allListAdapter;
import com.ash.teacheron.adapter.recommendedTeacherListAdapter;
import com.ash.teacheron.commonComponents.NetworkLoader;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.recommendedTeacherResponse;
import com.ash.teacheron.viewmodel.studentVM.RecommendedRequirement;
import com.ash.teacheron.viewmodel.studentVM.listRequirement;
import com.google.gson.Gson;

public class RecommndedAct extends AppCompatActivity {

   
    String option = "",userId,token;
    NetworkLoader networkLoader;
    SharedPreferences sharedPreferences;
    RecyclerView beneficiary_list;
    recommendedTeacherListAdapter adapter;
    String requirement_id="0",   subject_id="0",   subject="0",   from_level_id="0",   to_level_id="0" ,location="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommnded);
        networkLoader = new NetworkLoader();
        requirement_id=getIntent().getStringExtra("reqID");
        beneficiary_list= findViewById(R.id.beneficiary_list);
        SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(RecommndedAct.this);
        userId= String.valueOf(sharedPrefLocal.getUserId());
        token=  sharedPrefLocal.getSessionId();
        RecommendedRequirement viewModel = new ViewModelProvider(RecommndedAct.this).get(RecommendedRequirement.class);
        networkLoader.showLoadingDialog(RecommndedAct.this);
        viewModel.startLogin( token,  requirement_id,   subject_id,   subject,   from_level_id,   to_level_id,   location).observe(RecommndedAct.this, new Observer<recommendedTeacherResponse>() {
            @Override
            public void onChanged(recommendedTeacherResponse loginResponse) {
                if (loginResponse != null) {
                    Log.d("framg", "" + new Gson().toJson(loginResponse));
                    adapter = new recommendedTeacherListAdapter(RecommndedAct.this,loginResponse.data.dataList);
                    beneficiary_list.setHasFixedSize(true);
                    beneficiary_list.setAdapter(adapter);
                    beneficiary_list.setLayoutManager(new LinearLayoutManager(RecommndedAct.this, LinearLayoutManager.VERTICAL, false));
                    adapter.notifyDataSetChanged();


                } else {
                    // Handle null response here if needed
                    //Toast.makeText(RecommndedAct.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }

                networkLoader.dismissLoadingDialog();

            }
        });
        viewModel.getErrorMessage().observe(RecommndedAct.this, new Observer<ErrorData>() {
            @Override
            public void onChanged(ErrorData errorData) {
                // Display error message
                try{
                    Toast.makeText(RecommndedAct.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();

                }
                catch (Exception e)
                {
                    e.printStackTrace();

                }
                Log.d("Error", errorData.getMessage());
                networkLoader.dismissLoadingDialog();
            }
        });

    }
}