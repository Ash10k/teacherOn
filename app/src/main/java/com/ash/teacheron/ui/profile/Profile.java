package com.ash.teacheron.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ash.teacheron.R;
import com.ash.teacheron.StudentRegisterStep2;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import de.hdodenhof.circleimageview.CircleImageView;


public class Profile extends Fragment {
    SharedPrefLocal sharedPrefLocal;
    String token,userId,usertype;
    View fragmentView;
    CircleImageView profile_image;
    TextView teacher_name,teacher_location,teacher_email,teacher_phone,fee_details,exp,ptti,teacher_role,teacher_dob,feedet;
    public Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPrefLocal = new SharedPrefLocal(getActivity());
        userId= String.valueOf(sharedPrefLocal.getUserId());
        token=  sharedPrefLocal.getSessionId();
        usertype= sharedPrefLocal.getUserType();
        fragmentView=inflater.inflate(R.layout.fragment_profile, container, false);
        profile_image=fragmentView.findViewById(R.id.profile_image);
        teacher_name=fragmentView.findViewById(R.id.teacher_name);
        teacher_location=fragmentView.findViewById(R.id.teacher_location);
        teacher_email=fragmentView.findViewById(R.id.teacher_email);
        teacher_phone=fragmentView.findViewById(R.id.teacher_phone);
        fee_details=fragmentView.findViewById(R.id.fee_details);
        exp=fragmentView.findViewById(R.id.exp);
        ptti=fragmentView.findViewById(R.id.ptti);
        teacher_role=fragmentView.findViewById(R.id.teacher_role);
        teacher_dob=fragmentView.findViewById(R.id.teacher_dob);
        feedet=fragmentView.findViewById(R.id.feedet);
        try {
            if (usertype.equals("student"))
            {
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.baseline_account_circle_24)
                        .error(R.drawable.baseline_account_circle_24);
                Glide.with(getContext()).load(sharedPrefLocal.getUserProfileImage()).apply(options).into(profile_image);
                teacher_name.setText(sharedPrefLocal.getUserName());
                teacher_location.setText(sharedPrefLocal.getUserLocation());
                teacher_email.setText(sharedPrefLocal.getUserEmail());
                teacher_phone.setText(sharedPrefLocal.getUserPhone());
                fee_details.setText(sharedPrefLocal.getUserFeeAmount()+" "+sharedPrefLocal.getUserFEEDETAIL());
                teacher_role.setText(sharedPrefLocal.getUserDOB());
                teacher_dob.setText(sharedPrefLocal.getUserSchedule());
                feedet.setText(sharedPrefLocal.getUserAssocia());
            }
            else {
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.baseline_image_24)
                        .error(R.drawable.baseline_image_24);
                Glide.with(getContext()).load(sharedPrefLocal.getUserProfileImage()).apply(options).into(profile_image);
                teacher_name.setText(sharedPrefLocal.getUserName());
                teacher_location.setText(sharedPrefLocal.getUserLocation());
                teacher_email.setText(sharedPrefLocal.getUserEmail());
                teacher_phone.setText(sharedPrefLocal.getUserPhone());
                fee_details.setText(sharedPrefLocal.getUserFeeAmount()+" "+sharedPrefLocal.getUserSchedule());
                exp.setText(sharedPrefLocal.getUserExperience());
                ptti.setText(sharedPrefLocal.getUserAssocia());
                teacher_role.setText(sharedPrefLocal.getUserType());
            }



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        return fragmentView;
    }
}