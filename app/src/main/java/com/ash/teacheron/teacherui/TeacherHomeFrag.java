package com.ash.teacheron.teacherui;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ash.teacheron.R;
import com.ash.teacheron.adapter.allListAdapterTeacher;
import com.ash.teacheron.adapter.allListAdapterTeacherconnected;
import com.ash.teacheron.commonComponents.NetworkLoader;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.model.requirementResponse;
import com.ash.teacheron.retrofit.model.requirementResponseconnected;
import com.ash.teacheron.viewmodel.studentVM.listRequirement;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherHomeFrag extends Fragment {

    View fragmentView;
    String option = "", userId, token;
    NetworkLoader networkLoader;
    SharedPreferences sharedPreferences;
    RecyclerView beneficiary_list;
    allListAdapterTeacher adapter;
    allListAdapterTeacherconnected adapter2;
    listRequirement viewModel;
    CardView openconnectedjb, openmatchingjb;
    TextView matchedj, connj,nodatashow;
    AlertDialog detailsDialog;

    private TextView teacherName, teacherLocation, teacherExperience, teacherFee, teacherEmail,
            teacherPhone, teacherGender, teacherRole, teacherDOB, teacherTravel, teacherAvailability;
    private EditText feeDetailsInput;
    private CircleImageView profileImage;
    private ImageView closeBtn;


    public TeacherHomeFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_teacher_home, container, false);

        networkLoader = new NetworkLoader();
        beneficiary_list = fragmentView.findViewById(R.id.beneficiary_list);
        openmatchingjb = fragmentView.findViewById(R.id.openmatchingjb);
        openconnectedjb = fragmentView.findViewById(R.id.openconnectedjb);
        matchedj = fragmentView.findViewById(R.id.matchedj);
        connj = fragmentView.findViewById(R.id.connj);
        nodatashow=fragmentView.findViewById(R.id.nodatashow);
        SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(requireContext());
        userId = String.valueOf(sharedPrefLocal.getUserId());
        token = sharedPrefLocal.getSessionId();

        viewModel = new ViewModelProvider(requireActivity()).get(listRequirement.class);

        // Initialize Adapters
        adapter = new allListAdapterTeacher(requireContext(), new ArrayList<>());
        adapter2 = new allListAdapterTeacherconnected(requireContext(), new ArrayList<>());

        beneficiary_list.setHasFixedSize(true);
        beneficiary_list.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        // Set default adapter (Matching Jobs initially)
        beneficiary_list.setAdapter(adapter);

        openmatchingjb.setOnClickListener(view -> {
            // Change button UI
            openmatchingjb.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.blue)));
            openconnectedjb.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.lightBlue)));
            matchedj.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            connj.setTextColor(ContextCompat.getColor(requireContext(), R.color.greydark));

            // Switch to Matching Jobs Adapter
            beneficiary_list.setAdapter(adapter);

            // Fetch data
            viewModel.getmjob(userId, token);
        });

        openconnectedjb.setOnClickListener(view -> {
            // Change button UI
            openconnectedjb.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.blue)));
            openmatchingjb.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.lightBlue)));
            connj.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            matchedj.setTextColor(ContextCompat.getColor(requireContext(), R.color.greydark));

            // Switch to Connected Jobs Adapter
            beneficiary_list.setAdapter(adapter2);

            // Fetch data
            viewModel.getconnectedJobs(userId, token);
        });

        // Observe LiveData
        observeJobs();

        return fragmentView;
    }

    private void observeJobs() {
        viewModel.getmjob(userId, token).observe(getViewLifecycleOwner(), loginResponse -> {
            if (loginResponse != null) {
                Log.d("Matching Jobs", new Gson().toJson(loginResponse));
                adapter.updateData(loginResponse.data.dataList);
                adapter.onclickList(new allListAdapterTeacher.OnItemClickListener() {
                    @Override
                    public void onItemClick(requirementResponse.TutorRequest item, int instruction) {
                        if (instruction == 1) {
                            showDetails(item);
                        }
                    }
                });
                try {
                    if (loginResponse.data.dataList.size()==0)
                    {
                        nodatashow.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            }
            networkLoader.dismissLoadingDialog();
        });

        viewModel.getconnectedJobs(userId, token).observe(getViewLifecycleOwner(), loginResponse -> {
            if (loginResponse != null) {
                Log.d("Connected Jobs", new Gson().toJson(loginResponse));
                adapter2.updateData(loginResponse.dataList);
                adapter2.onclickList(new allListAdapterTeacherconnected.OnItemClickListener() {
                    @Override
                    public void onItemClick(requirementResponseconnected.TutorRequest item, int instruction) {
                        if (instruction == 1) {
                            showDetails2(item);
                        }
                    }
                });

                try {
                    if (loginResponse.dataList.size()==0)
                    {
                        nodatashow.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
            networkLoader.dismissLoadingDialog();
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorData -> {
            Toast.makeText(requireContext(), "Server Error", Toast.LENGTH_SHORT).show();
            Log.d("Error", errorData.getMessage());
            networkLoader.dismissLoadingDialog();
        });
    }

    void showDetails(requirementResponse.TutorRequest teacherObj) {
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
        teacherName.setText(getSafeString(teacherObj.user.name));
        teacherLocation.setText(getSafeString(teacherObj.location));
        teacherEmail.setText(maskEmail(getSafeString(teacherObj.user.email)));
        teacherGender.setText(getSafeString(teacherObj.tutorOption));
        teacherFee.setText(getSafeString(teacherObj.budget) + " / " + getSafeString(teacherObj.budgetType));
        teacherExperience.setText("Requirement" + getSafeString(teacherObj.requirements));
        teacherTravel.setText(getSafeString(String.valueOf(teacherObj.travelLimit)));


        if (teacherObj.user.profileImageUrl == null || teacherObj.user.profileImageUrl.isEmpty()) {
            profileImage.setImageResource(R.drawable.baseline_account_circle_24); // Default image
        } else {
            Glide.with(this).load(teacherObj.user.profileImageUrl).into(profileImage);
        }

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsDialog.dismiss();
            }
        });

        detailsDialog.show();

    }

    void showDetails2(requirementResponseconnected.TutorRequest teacherObj) {
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
        teacherName.setText(getSafeString(teacherObj.user.name));
        teacherLocation.setText(getSafeString(teacherObj.location));
        teacherEmail.setText(maskEmail(getSafeString(teacherObj.user.email)));
        teacherGender.setText(getSafeString(teacherObj.tutorOption));
        teacherFee.setText(getSafeString(teacherObj.budget) + " / " + getSafeString(teacherObj.budgetType));
        teacherExperience.setText("Requirement" + getSafeString(teacherObj.requirements));
        teacherTravel.setText(getSafeString(String.valueOf(teacherObj.travelLimit)));


        if (teacherObj.user.profileImageUrl == null || teacherObj.user.profileImageUrl.isEmpty()) {
            profileImage.setImageResource(R.drawable.baseline_account_circle_24); // Default image
        } else {
            Glide.with(this).load(teacherObj.user.profileImageUrl).into(profileImage);
        }

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsDialog.dismiss();
            }
        });

        detailsDialog.show();

    }


    private String getSafeString(String value) {
        return value != null ? value : "";
    }


    private String maskEmail(String email) {
        if (email == null || !email.contains("@")) return "N/A";
        String[] parts = email.split("@");
        if (parts[0].length() < 2) return "****@" + parts[1]; // In case of short usernames
        return parts[0].substring(0, 2) + "****@" + parts[1];
    }

}