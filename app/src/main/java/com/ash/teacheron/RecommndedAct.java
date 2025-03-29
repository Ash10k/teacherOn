package com.ash.teacheron;

import static com.ash.teacheron.constants.Contants.SERVER_ERROR;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ash.teacheron.adapter.allListAdapter;
import com.ash.teacheron.adapter.educationCerti_adapter;
import com.ash.teacheron.adapter.proExperience_adapter;
import com.ash.teacheron.adapter.recommendedTeacherListAdapter;
import com.ash.teacheron.adapter.subjectView_adapter;
import com.ash.teacheron.commonComponents.NetworkLoader;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.recommendedTeacherResponse;
import com.ash.teacheron.viewmodel.studentVM.RecommendedRequirement;
import com.ash.teacheron.viewmodel.studentVM.listRequirement;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecommndedAct extends AppCompatActivity {

    AlertDialog detailsDialog,subjectDialog,certiDialog,experDialog;
    String option = "",userId,token;
    NetworkLoader networkLoader;
    SharedPreferences sharedPreferences;
    RecyclerView beneficiary_list;
    recommendedTeacherListAdapter adapter;
    String requirement_id="0",   subject_id="0",   subject="0",   from_level_id="0",   to_level_id="0" ,location="0";

    private TextView teacherName, teacherLocation, teacherExperience, teacherFee, teacherEmail,
            teacherPhone, teacherGender, teacherRole, teacherDOB, teacherTravel, teacherAvailability;
    private EditText feeDetailsInput;
    private CircleImageView profileImage;
    private ImageView closeBtn;
    CardView openViewSub,educt,tchingpr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommnded);
        networkLoader = new NetworkLoader();
        requirement_id= String.valueOf(getIntent().getIntExtra("reqID",0));
        subject_id= String.valueOf(getIntent().getIntExtra("subjectId",0));
        // Toast.makeText(this, ""+requirement_id, Toast.LENGTH_SHORT).show();
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
                    adapter.onclickList(new recommendedTeacherListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(recommendedTeacherResponse.TutorRequest item, int instruction) {
                            if (instruction==1)
                            {
                                showDetails(item);
                            }
                            if (instruction==2)
                            {
                                Intent intent=new Intent(RecommndedAct.this,Single_chat_room.class);
                                intent.putExtra("receiver",item.id);
                                intent.putExtra("sender",Integer.parseInt(requirement_id));
                               // Toast.makeText(RecommndedAct.this, "sending sender: "+requirement_id, Toast.LENGTH_SHORT).show();
                                startActivity(intent);

                            }
                        }
                    });


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

    void showDetails(recommendedTeacherResponse.TutorRequest teacherObj)
    {
        AlertDialog.Builder mybuilder = new AlertDialog.Builder(RecommndedAct.this, R.style.mydialog);
        final LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_view_details, null);
        mybuilder.setView(view);
        detailsDialog = mybuilder.create();

        Window window = detailsDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        profileImage = view.findViewById(R.id.profile_image);
        teacherName = view.findViewById(R.id.teacher_name);
        teacherLocation = view.findViewById(R.id.teacher_location);
        teacherExperience = view.findViewById(R.id.teacher_gender);
        teacherFee = view.findViewById(R.id.fee_details);
        teacherEmail = view.findViewById(R.id.teacher_email);
        //teacherPhone = view.findViewById(R.id.teacher_phone);
        teacherGender = view.findViewById(R.id.teacher_gender);
        teacherRole = view.findViewById(R.id.teacher_role);
       // teacherDOB = view.findViewById(R.id.teacher_dob);
        teacherTravel = view.findViewById(R.id.teacher_travel);
        teacherAvailability = view.findViewById(R.id.teacher_availability);

        openViewSub = view.findViewById(R.id.openViewSub);
        educt=view.findViewById(R.id.educt);
        tchingpr=view.findViewById(R.id.tchingpr);

        closeBtn = view.findViewById(R.id.close_btn);
        teacherName.setText(getSafeString(teacherObj.name));
        teacherLocation.setText(getSafeString(teacherObj.location));
        teacherEmail.setText(getSafeString(teacherObj.email));
        teacherGender.setText(getSafeString(teacherObj.teacherMeta.gender));
        if (teacherObj.teacherDetail!=null)
        {
            teacherFee.setText(getSafeString(teacherObj.teacherDetail.feeAmount) + " / " + getSafeString(teacherObj.teacherDetail.feeSchedule));
            teacherExperience.setText("Total experience: " + getSafeString(teacherObj.teacherDetail.totalExperience) + " yr.");
            teacherTravel.setText(getSafeString(teacherObj.teacherDetail.willingToTravel));
            teacherAvailability.setText("Yes".equals(getSafeString(teacherObj.teacherDetail.availableForOnline))
                    ? "Available for online teaching"
                    : "Not Available");

        }

        if (teacherObj.profileImageUrl == null || teacherObj.profileImageUrl.isEmpty()) {
            profileImage.setImageResource(R.drawable.baseline_account_circle_24); // Default image
        } else {
            Glide.with(this).load(teacherObj.profileImageUrl).into(profileImage);
        }

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsDialog.dismiss();
            }
        });


        tchingpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCertiDialog(teacherObj.teacherCertification);
            }
        });


        educt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExperienceDialog(teacherObj.teacherExperience);
            }
        });


        openViewSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSubjectDialog(teacherObj.teacherSubject);
            }
        });


        detailsDialog.show();

    }

    void showSubjectDialog(List<recommendedTeacherResponse.TutorRequest.TeacherSubject> subjectList)
    {
        AlertDialog.Builder mybuilder = new AlertDialog.Builder(RecommndedAct.this, R.style.mydialog);
        final LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_subject_list, null);
        mybuilder.setView(view);
        subjectDialog = mybuilder.create();
        Window window = subjectDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        ListView mainList = view.findViewById(R.id.lostv);
        subjectView_adapter subadapter = new subjectView_adapter(RecommndedAct.this, subjectList);
        mainList.setAdapter(subadapter);
        subjectDialog.show();
    }


    void showCertiDialog(List<recommendedTeacherResponse.TutorRequest.TeacherCertification> subjectList)
    {
        AlertDialog.Builder mybuilder = new AlertDialog.Builder(RecommndedAct.this, R.style.mydialog);
        final LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_education_list, null);
        mybuilder.setView(view);
        certiDialog = mybuilder.create();
        Window window = certiDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        ListView mainList = view.findViewById(R.id.lostv);
        educationCerti_adapter subadapter = new educationCerti_adapter(RecommndedAct.this, subjectList);
        mainList.setAdapter(subadapter);
        certiDialog.show();
    }

    void showExperienceDialog(List<recommendedTeacherResponse.TutorRequest.TeacherExperience> subjectList)
    {
        AlertDialog.Builder mybuilder = new AlertDialog.Builder(RecommndedAct.this, R.style.mydialog);
        final LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_experience, null);
        mybuilder.setView(view);
        experDialog = mybuilder.create();
        Window window = experDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        ListView mainList = view.findViewById(R.id.lostv);
        proExperience_adapter subadapter = new proExperience_adapter(RecommndedAct.this, subjectList);
        mainList.setAdapter(subadapter);
        experDialog.show();
    }
    private String getSafeString(String value) {
        return value != null ? value : "";
    }


}