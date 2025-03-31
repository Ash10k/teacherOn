package com.ash.teacheron.teacherui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.ash.teacheron.R;
import com.ash.teacheron.adapter.educationCerti_adapter;
import com.ash.teacheron.adapter.educationCerti_adapter2;
import com.ash.teacheron.adapter.proExperience_adapter;
import com.ash.teacheron.adapter.proExperience_adapter2;
import com.ash.teacheron.adapter.subjectView2_adapter;
import com.ash.teacheron.adapter.subjectView_adapter;
import com.ash.teacheron.commonComponents.NetworkLoader;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.appOptionsResponse;
import com.ash.teacheron.retrofit.model.recommendedProfile;
import com.ash.teacheron.retrofit.model.recommendedTeacherResponse;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TeacherProfile extends Fragment {

    View fragmentView;
    AlertDialog detailsDialog, subjectDialog, certiDialog, experDialog;
    SharedPrefLocal sharedPrefLocal;
    String token, userId, usertype, TAG = "TeacherProfile";

    CircleImageView profile_image;
    TextView teacher_name, teacher_location, teacher_email, teacher_phone, fee_details, exp, ptti, teacher_role, teacher_dob, feedet;
    private TextView teacherName, teacherLocation, teacherExperience, teacherFee, teacherEmail,
            teacherPhone, teacherGender, teacherRole, teacherDOB, teacherTravel, teacherAvailability;
    private EditText feeDetailsInput;
    private CircleImageView profileImage;
    private ImageView closeBtn;
    CardView openViewSub, educt, tchingpr;
    NetworkLoader networkLoader;

    List<recommendedProfile.TutorRequest.TeacherCertification> certificateList;
    List<recommendedProfile.TutorRequest.TeacherExperience> ExperList;
    List<recommendedProfile.TutorRequest.TeacherSubject> otherList;


    public TeacherProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_teacher_profile, container, false);
        networkLoader = new NetworkLoader();
        SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(getActivity());
        userId= String.valueOf(sharedPrefLocal.getUserId());
        token=  sharedPrefLocal.getSessionId();
        educt=fragmentView.findViewById(R.id.educt);
        openViewSub=fragmentView.findViewById(R.id.openViewSub);
        tchingpr=fragmentView.findViewById(R.id.tchingpr);

        educt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCertiDialog();
            }
        });

        openViewSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSubjectDialog();
            }
        });


        tchingpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExperienceDialog();
            }
        });

        getProfiledata();
        return fragmentView;
    }

   /* void showDetails(recommendedTeacherResponse.TutorRequest teacherObj) {
        AlertDialog.Builder mybuilder = new AlertDialog.Builder(getContext(), R.style.mydialog);
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
        educt = view.findViewById(R.id.educt);
        tchingpr = view.findViewById(R.id.tchingpr);
        closeBtn = view.findViewById(R.id.close_btn);
        teacherName.setText(getSafeString(teacherObj.name));
        teacherLocation.setText(getSafeString(teacherObj.location));
        teacherEmail.setText(getSafeString(teacherObj.email));
        teacherGender.setText(getSafeString(teacherObj.teacherMeta.gender));
        if (teacherObj.teacherDetail != null) {
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
                showCertiDialog();
            }
        });


        educt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExperienceDialog( );
            }
        });


        openViewSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSubjectDialog( );
            }
        });


        detailsDialog.show();

    }
*/

    void showSubjectDialog() {
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
        subjectView2_adapter subadapter = new subjectView2_adapter(getContext(), otherList);
        mainList.setAdapter(subadapter);
        subjectDialog.show();
    }


    void showCertiDialog() {
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
        educationCerti_adapter2 subadapter = new educationCerti_adapter2(getContext(), certificateList);
        mainList.setAdapter(subadapter);
        certiDialog.show();
    }

    void showExperienceDialog() {
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
        proExperience_adapter2 subadapter = new proExperience_adapter2(getContext(), ExperList);
        mainList.setAdapter(subadapter);
        experDialog.show();
    }

    private String getSafeString(String value) {
        return value != null ? value : "";
    }


    private boolean getProfiledata() {
        // tried_username_pass(user, pass);
        try {
            networkLoader.showLoadingDialog(getContext());
            AuthAPI SendData = RetrofitBuilder.build().create(AuthAPI.class);
            // String first_name, String last_name, String email, String password_confirmation, String password
            Call<recommendedProfile> myCall = SendData.getProfile(  token);
            myCall.enqueue(new Callback<recommendedProfile>() {
                @Override
                public void onResponse(Call<recommendedProfile> call, Response<recommendedProfile> response)
                {
                    if (response.isSuccessful())
                    {
                        if ((response.body().status).equals("success")) {
                            networkLoader.dismissLoadingDialog();
                            ExperList=response.body().data.teacherExperience;
                            otherList=response.body().data.teacherSubject;
                            certificateList=response.body().data.teacherCertification;
                            // showDetails();
                        } else {
                            Toast.makeText(getContext(), "" + response.body().message, Toast.LENGTH_SHORT).show();
                            networkLoader.dismissLoadingDialog();
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Server Error" + response.raw(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onResponse: error body is here response is not successful " + response.raw());
                        networkLoader.dismissLoadingDialog();
                    }
                }

                @Override
                public void onFailure(Call<recommendedProfile> call, Throwable t) {
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


}