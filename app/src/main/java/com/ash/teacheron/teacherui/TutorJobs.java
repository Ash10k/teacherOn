package com.ash.teacheron.teacherui;

import static com.ash.teacheron.constants.Contants.SERVER_ERROR;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ash.teacheron.R;
import com.ash.teacheron.RecommndedAct;
import com.ash.teacheron.RegisterTeachStep2;
import com.ash.teacheron.Single_chat_room;
import com.ash.teacheron.adapter.educationCerti_adapter;
import com.ash.teacheron.adapter.proExperience_adapter;
import com.ash.teacheron.adapter.recommendedTeacherListAdapter;
import com.ash.teacheron.adapter.subjectView_adapter;
import com.ash.teacheron.adapter.teacherrecommendedTeacherListAdapter;
import com.ash.teacheron.commonComponents.NetworkLoader;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.appOptionsResponse;
import com.ash.teacheron.retrofit.model.recommendedTeacherResponse;
import com.ash.teacheron.viewmodel.studentVM.RecommendedRequirement;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TutorJobs extends Fragment {


    View fragmentView;
    AlertDialog detailsDialog,subjectDialog,certiDialog,experDialog;
    String option = "",userId,token,TAG="TutorJobs.java";
    NetworkLoader networkLoader;
    SharedPreferences sharedPreferences;
    RecyclerView beneficiary_list;
    teacherrecommendedTeacherListAdapter adapter;
    String requirement_id="",   subject_id="",   subject="",   from_level_id="",   to_level_id="" ,location="";
    private Spinner subjectSpinner, fromLevelSpinner;
    private TextView teacherName, teacherLocation, teacherExperience, teacherFee, teacherEmail,
            teacherPhone, teacherGender, teacherRole, teacherDOB, teacherTravel, teacherAvailability;
    private EditText feeDetailsInput;
    private CircleImageView profileImage;
    private ImageView closeBtn;
    CardView openViewSub,educt,tchingpr;
    private List<appOptionsResponse.Subject> subjectsList = new ArrayList<>();
    private List<appOptionsResponse.Level> levelsList = new ArrayList<>();
    CardView openall,onlineopen,homeopen,searchv;
    ImageView im1,im2,im3;
    TextView tv1,tv2,tv3;
    public TutorJobs() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView=inflater.inflate(R.layout.fragment_tutor_jobs, container, false);
        networkLoader = new NetworkLoader();
      //  requirement_id= String.valueOf(getActivity().getIntent().getIntExtra("reqID",0));
      //  subject_id= String.valueOf(getActivity().getIntent().getIntExtra("subjectId",0));


        subjectSpinner = fragmentView.findViewById(R.id.subjectSpinner);
        fromLevelSpinner = fragmentView.findViewById(R.id.fromLevelSpinner);
        // Toast.makeText(this, ""+requirement_id, Toast.LENGTH_SHORT).show();
        get_edu_journey();
        beneficiary_list=fragmentView. findViewById(R.id.beneficiary_list);
        SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(getActivity());
        userId= String.valueOf(sharedPrefLocal.getUserId());
        token=  sharedPrefLocal.getSessionId();

        searchthisData();

        openall=fragmentView.findViewById(R.id.openall);
        onlineopen=fragmentView.findViewById(R.id.onlineopen);
        homeopen=fragmentView.findViewById(R.id.homeopen);

        im1=fragmentView.findViewById(R.id.im1);
        im2=fragmentView.findViewById(R.id.im2);
        im3=fragmentView.findViewById(R.id.im3);

        tv1=fragmentView.findViewById(R.id.tv1);
        tv2=fragmentView.findViewById(R.id.txt2);
        tv3=fragmentView.findViewById(R.id.txt3);

        searchv=fragmentView.findViewById(R.id.searchv);


        openall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openall.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.blue)));
                onlineopen.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.lightBlue)));
                homeopen.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.lightBlue)));

                tv1.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                tv2.setTextColor(ContextCompat.getColor(requireContext(), R.color.greydark));
                tv3.setTextColor(ContextCompat.getColor(requireContext(), R.color.greydark));

                Glide.with(requireContext()).load(R.drawable.baseline_content_paste_search_24_white).into(im1);
                Glide.with(requireContext()).load(R.drawable.baseline_computer_24_blue).into(im2);
                Glide.with(requireContext()).load(R.drawable.baseline_home_24_blue).into(im3);


            }
        });

        onlineopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onlineopen.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.blue)));
                openall.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.lightBlue)));
                homeopen.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.lightBlue)));

                tv2.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                tv3.setTextColor(ContextCompat.getColor(requireContext(), R.color.greydark));
                tv1.setTextColor(ContextCompat.getColor(requireContext(), R.color.greydark));


                Glide.with(requireContext()).load(R.drawable.baseline_content_paste_search_24).into(im1);
                Glide.with(requireContext()).load(R.drawable.baseline_computer_24_white).into(im2);
                Glide.with(requireContext()).load(R.drawable.baseline_home_24_blue).into(im3);


            }
        });

        homeopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                homeopen.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.blue)));
                openall.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.lightBlue)));
                onlineopen.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.lightBlue)));

                tv3.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                tv2.setTextColor(ContextCompat.getColor(requireContext(), R.color.greydark));
                tv1.setTextColor(ContextCompat.getColor(requireContext(), R.color.greydark));

                Glide.with(requireContext()).load(R.drawable.baseline_content_paste_search_24).into(im1);
                Glide.with(requireContext()).load(R.drawable.baseline_computer_24_blue).into(im2);
                Glide.with(requireContext()).load(R.drawable.baseline_home_24_white).into(im3);



            }
        });

        searchv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int subjectIndex = subjectSpinner.getSelectedItemPosition();
                int fromLevelIndex = fromLevelSpinner.getSelectedItemPosition();
                subject_id= String.valueOf(subjectsList.get(subjectIndex).id);
                from_level_id= String.valueOf(levelsList.get(fromLevelIndex).id);
                searchthisData();
            }
        });
        return fragmentView;
    }

    void showDetails(recommendedTeacherResponse.TutorRequest teacherObj)
    {
        AlertDialog.Builder mybuilder = new AlertDialog.Builder(getContext(), R.style.mydialog);
        final LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_view_details_student, null);
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


       // Toast.makeText(getContext(), ""+teacherObj.userinobj.name, Toast.LENGTH_SHORT).show();
        closeBtn = view.findViewById(R.id.close_btn);
        teacherName.setText(getSafeString(teacherObj.userinobj.name));
        teacherLocation.setText(getSafeString(teacherObj.location));
        teacherEmail.setText(getSafeString(teacherObj.userinobj.email));
        teacherGender.setText(getSafeString(teacherObj.tutor_option));
        teacherFee.setText(getSafeString(teacherObj.budget) + " / " + getSafeString(teacherObj.budget_type));
        teacherExperience.setText("Requirement" + getSafeString(teacherObj.requirements)  );
        teacherTravel.setText(getSafeString(teacherObj.travel_limit));


        if (teacherObj.userinobj.profile_image_url == null || teacherObj.userinobj.profile_image_url.isEmpty()) {
            profileImage.setImageResource(R.drawable.baseline_account_circle_24); // Default image
        } else {
            Glide.with(this).load(teacherObj.userinobj.profile_image_url).into(profileImage);
        }

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsDialog.dismiss();
            }
        });

        detailsDialog.show();

    }

    void showSubjectDialog(List<recommendedTeacherResponse.TutorRequest.TeacherSubject> subjectList)
    {
        AlertDialog.Builder mybuilder = new AlertDialog.Builder(getContext(), R.style.mydialog);
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
        subjectView_adapter subadapter = new subjectView_adapter(getContext(), subjectList);
        mainList.setAdapter(subadapter);
        subjectDialog.show();
    }


    void showCertiDialog(List<recommendedTeacherResponse.TutorRequest.TeacherCertification> subjectList)
    {
        AlertDialog.Builder mybuilder = new AlertDialog.Builder(getContext(), R.style.mydialog);
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
        educationCerti_adapter subadapter = new educationCerti_adapter(getContext(), subjectList);
        mainList.setAdapter(subadapter);
        certiDialog.show();
    }

    void showExperienceDialog(List<recommendedTeacherResponse.TutorRequest.TeacherExperience> subjectList)
    {
        AlertDialog.Builder mybuilder = new AlertDialog.Builder(getContext(), R.style.mydialog);
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
        proExperience_adapter subadapter = new proExperience_adapter(getContext(), subjectList);
        mainList.setAdapter(subadapter);
        experDialog.show();
    }
    private String getSafeString(String value) {
        return value != null ? value : "";
    }


    private void setupSpinners() {
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getSubjectNames());
        subjectSpinner.setAdapter(subjectAdapter);

        ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getLevelNames());
        fromLevelSpinner.setAdapter(levelAdapter);
        
    }

    private boolean get_edu_journey() {
        // tried_username_pass(user, pass);
        try {


            networkLoader.showLoadingDialog(getContext());
            AuthAPI SendData = RetrofitBuilder.build().create(AuthAPI.class);
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

                        } else {

                            Toast.makeText(getContext(), "" + response.body().message, Toast.LENGTH_SHORT).show();
                            networkLoader.dismissLoadingDialog();

                        }
                    } else {
                        Toast.makeText(getContext(), "Server Error" + response.raw(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onResponse: error body is here response is not successful " + response.raw());
                        networkLoader.dismissLoadingDialog();
                       // educationlist.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<appOptionsResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    networkLoader.dismissLoadingDialog();
                }
            });
        } catch (Exception exception) {
            Toast.makeText(getContext(), "Some thing wrong", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
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

    void searchthisData()
    {
        RecommendedRequirement viewModel = new ViewModelProvider(getActivity()).get(RecommendedRequirement.class);
        networkLoader.showLoadingDialog(getActivity());
        viewModel.searchReq( token,  requirement_id,   subject_id,   subject,   from_level_id,   to_level_id,   location).observe(getActivity(), new Observer<recommendedTeacherResponse>() {
            @Override
            public void onChanged(recommendedTeacherResponse loginResponse) {
                if (loginResponse != null) {
                    Log.d("framg", "" + new Gson().toJson(loginResponse));
                    adapter = new teacherrecommendedTeacherListAdapter(getContext(),loginResponse.data.dataList);
                    beneficiary_list.setHasFixedSize(true);
                    beneficiary_list.setAdapter(adapter);
                    beneficiary_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    adapter.notifyDataSetChanged();
                    adapter.onclickList(new teacherrecommendedTeacherListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(recommendedTeacherResponse.TutorRequest item, int instruction) {
                            if (instruction==1)
                            {
                                showDetails(item);


                            }
                            if (instruction==2)
                            {
                                Intent intent=new Intent(getActivity(), Single_chat_room.class);
                                intent.putExtra("receiver",item.userinobj.id);
                                intent.putExtra("sender",Integer.parseInt(userId));
                                // Toast.makeText(getContext, "sending sender: "+requirement_id, Toast.LENGTH_SHORT).show();
                                startActivity(intent);

                            }
                        }
                    });


                } else {
                    // Handle null response here if needed
                    //Toast.makeText(getContext, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }

                networkLoader.dismissLoadingDialog();

            }
        });
        viewModel.getErrorMessage().observe(getActivity(), new Observer<ErrorData>() {
            @Override
            public void onChanged(ErrorData errorData) {
                // Display error message
                try{
                    Toast.makeText(getContext(), SERVER_ERROR, Toast.LENGTH_SHORT).show();

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