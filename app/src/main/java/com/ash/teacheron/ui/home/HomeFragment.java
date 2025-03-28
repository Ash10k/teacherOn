package com.ash.teacheron.ui.home;

import static android.content.Context.MODE_PRIVATE;
import static com.ash.teacheron.constants.Contants.Login_credentials;
import static com.ash.teacheron.constants.Contants.SERVER_ERROR;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ash.teacheron.R;
import com.ash.teacheron.RegisterTeachStep2;
import com.ash.teacheron.Step3Teacher;
import com.ash.teacheron.adapter.allListAdapter;
import com.ash.teacheron.commonComponents.NetworkLoader;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.databinding.FragmentHomeBinding;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.requirementResponse;
import com.ash.teacheron.retrofit.model.saveResponse;
import com.ash.teacheron.viewmodel.studentVM.listRequirement;
import com.ash.teacheron.viewmodel.teacherVM.step2VModel;
import com.google.gson.Gson;

import java.util.zip.Inflater;

public class HomeFragment extends Fragment {

    View fragmentView;
    String option = "",userId,token;
    NetworkLoader networkLoader;
    SharedPreferences sharedPreferences;
    RecyclerView beneficiary_list;
    allListAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        networkLoader = new NetworkLoader();
        beneficiary_list=fragmentView.findViewById(R.id.beneficiary_list);
        SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(getActivity());
        userId= String.valueOf(sharedPrefLocal.getUserId());
        token=  sharedPrefLocal.getSessionId();
        listRequirement viewModel = new ViewModelProvider(getActivity()).get(listRequirement.class);
        networkLoader.showLoadingDialog(getActivity());
        viewModel.startLogin( userId, token).observe(getActivity(), new Observer<requirementResponse>() {
            @Override
            public void onChanged(requirementResponse loginResponse) {
                if (loginResponse != null) {
                    Log.d("framg", "" + new Gson().toJson(loginResponse));
                    adapter = new allListAdapter(getActivity(),loginResponse.data.dataList);
                    beneficiary_list.setHasFixedSize(true);
                    beneficiary_list.setAdapter(adapter);
                    beneficiary_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    adapter.notifyDataSetChanged();


                } else {
                    // Handle null response here if needed
                    //Toast.makeText(getActivity(), SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }

                networkLoader.dismissLoadingDialog();

            }
        });
        viewModel.getErrorMessage().observe(getActivity(), new Observer<ErrorData>() {
            @Override
            public void onChanged(ErrorData errorData) {
                // Display error message
                try{
                    Toast.makeText(requireContext().getApplicationContext(), SERVER_ERROR, Toast.LENGTH_SHORT).show();

                }
                catch (Exception e)
                {
                    e.printStackTrace();

                }
                Log.d("Error", errorData.getMessage());
                networkLoader.dismissLoadingDialog();
            }
        });

        return fragmentView;
    }



}