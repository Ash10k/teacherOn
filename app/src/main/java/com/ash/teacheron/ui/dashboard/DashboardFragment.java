package com.ash.teacheron.ui.dashboard;

import static com.ash.teacheron.constants.Contants.SERVER_ERROR;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ash.teacheron.R;
import com.ash.teacheron.RecommndedAct;
import com.ash.teacheron.Single_chat_room;
import com.ash.teacheron.adapter.educationCerti_adapter;
import com.ash.teacheron.adapter.proExperience_adapter;
import com.ash.teacheron.adapter.recommendedTeacherListAdapter;
import com.ash.teacheron.adapter.subjectView_adapter;
import com.ash.teacheron.commonComponents.NetworkLoader;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.databinding.FragmentDashboardBinding;
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

public class DashboardFragment extends Fragment {

    View fragmentView;
    AlertDialog detailsDialog,subjectDialog,certiDialog,experDialog,levelDialog;
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

    RecommendedRequirement viewModel;

    CardView openall,onlineopen,homeopen,searchv;
    ImageView im1,im2,im3;
    TextView tv1,tv2,tv3;
    private List<appOptionsResponse.Subject> subjectsList = new ArrayList<>();
    private List<appOptionsResponse.Level> levelsList = new ArrayList<>();
    private Spinner subjectSpinner, fromLevelSpinner,toLevelSpinner;

    ImageView tvopenLevel;
    CardView resetclose,applylevelfilter;
    TextView lvlname;


    int currentPage = 1;
    int lastPage = 1;
    boolean isLoading = false;
    List<recommendedTeacherResponse.TutorRequest> allData = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        networkLoader = new NetworkLoader();
       // requirement_id= String.valueOf(getIntent().getIntExtra("reqID",0));
       // subject_id= String.valueOf(getIntent().getIntExtra("subjectId",0));
        beneficiary_list= fragmentView.findViewById(R.id.beneficiary_list);
        SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(getContext());
        userId= String.valueOf(sharedPrefLocal.getUserId());
        token=  sharedPrefLocal.getSessionId();
        viewModel = new ViewModelProvider(getActivity()).get(RecommendedRequirement.class);


        fromLevelSpinner = fragmentView.findViewById(R.id.fromLevelSpinner);
        toLevelSpinner = fragmentView.findViewById(R.id.toLevelSpinner);
        openall=fragmentView.findViewById(R.id.openall);
        onlineopen=fragmentView.findViewById(R.id.onlineopen);
        homeopen=fragmentView.findViewById(R.id.homeopen);

        tvopenLevel=fragmentView.findViewById(R.id.tvopenLevel);
        lvlname=fragmentView.findViewById(R.id.lvlname);

        im1=fragmentView.findViewById(R.id.im1);
        im2=fragmentView.findViewById(R.id.im2);
        im3=fragmentView.findViewById(R.id.im3);

        tv1=fragmentView.findViewById(R.id.tv1);
        tv2=fragmentView.findViewById(R.id.txt2);
        tv3=fragmentView.findViewById(R.id.txt3);

        //searchv=fragmentView.findViewById(R.id.searchv);

        get_edu_journey();
        openall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openall.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.blue)));
                onlineopen.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
                homeopen.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));

                tv1.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                tv2.setTextColor(ContextCompat.getColor(requireContext(), R.color.greydark));
                tv3.setTextColor(ContextCompat.getColor(requireContext(), R.color.greydark));

                Glide.with(requireContext()).load(R.drawable.baseline_content_paste_search_24_white).into(im1);
                Glide.with(requireContext()).load(R.drawable.baseline_computer_24_blue).into(im2);
                Glide.with(requireContext()).load(R.drawable.baseline_home_24_blue).into(im3);

                searchthisData(1);
            }
        });

        onlineopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onlineopen.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.blue)));
                openall.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
                homeopen.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));

                tv2.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                tv3.setTextColor(ContextCompat.getColor(requireContext(), R.color.greydark));
                tv1.setTextColor(ContextCompat.getColor(requireContext(), R.color.greydark));


                Glide.with(requireContext()).load(R.drawable.baseline_content_paste_search_24).into(im1);
                Glide.with(requireContext()).load(R.drawable.baseline_computer_24_white).into(im2);
                Glide.with(requireContext()).load(R.drawable.baseline_home_24_blue).into(im3);

                searchthisData(1);
            }
        });

        homeopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                homeopen.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.blue)));
                openall.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
                onlineopen.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));

                tv3.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                tv2.setTextColor(ContextCompat.getColor(requireContext(), R.color.greydark));
                tv1.setTextColor(ContextCompat.getColor(requireContext(), R.color.greydark));

                Glide.with(requireContext()).load(R.drawable.baseline_content_paste_search_24).into(im1);
                Glide.with(requireContext()).load(R.drawable.baseline_computer_24_blue).into(im2);
                Glide.with(requireContext()).load(R.drawable.baseline_home_24_white).into(im3);

                searchthisData(1);
            }
        });

        /*searchv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (toLevelSpinner!=null && fromLevelSpinner!=null)
                {
                    int fromLevelIndex = fromLevelSpinner.getSelectedItemPosition();
                    from_level_id= String.valueOf(levelsList.get(fromLevelIndex).id);
                    int toLevelIndex = toLevelSpinner.getSelectedItemPosition();
                    to_level_id=String.valueOf(levelsList.get(toLevelIndex).id);
                }
                int subjectIndex = subjectSpinner.getSelectedItemPosition();
                subject_id= String.valueOf(subjectsList.get(subjectIndex).id);



                searchthisData(1);
            }
        });*/

        tvopenLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openLevelDialog();
            }
        });

        searchthisData(1);


        beneficiary_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { // only when scrolling up
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && currentPage < lastPage) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0) {
                            searchthisData(currentPage + 1);
                        }
                    }
                }
            }
        });

        return fragmentView;
    }

    void showDetails(recommendedTeacherResponse.TutorRequest teacherObj)
    {
        AlertDialog.Builder mybuilder = new AlertDialog.Builder(getContext(), R.style.mydialog);
        final LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_view_details_new, null);
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
        teacherEmail.setText(maskEmail(getSafeString(teacherObj.email)));
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

   /* void searchthisData()
    {
        networkLoader.showLoadingDialog(getContext());
        viewModel.searchTeacher( token,  requirement_id,   subject_id,   subject,   from_level_id,   to_level_id,   location).observe(getActivity(), new Observer<recommendedTeacherResponse>() {
            @Override
            public void onChanged(recommendedTeacherResponse loginResponse) {
                if (loginResponse != null) {
                    Log.d("framg", "" + new Gson().toJson(loginResponse));
                    adapter = new recommendedTeacherListAdapter(getContext(),loginResponse.data.dataList);
                    beneficiary_list.setHasFixedSize(true);
                    beneficiary_list.setAdapter(adapter);
                    beneficiary_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
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
                                Intent intent=new Intent(getContext(), Single_chat_room.class);
                                intent.putExtra("receiver",item.id);
                                intent.putExtra("sender",Integer.parseInt(requirement_id));
                                // Toast.makeText(getContext(), "sending sender: "+requirement_id, Toast.LENGTH_SHORT).show();
                                startActivity(intent);

                            }
                        }
                    });


                } else {
                    // Handle null response here if needed
                    //Toast.makeText(getContext(), SERVER_ERROR, Toast.LENGTH_SHORT).show();
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

    }*/

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
                          //  setupSpinners();

                        } else {

                            Toast.makeText(getContext(), "" + response.body().message, Toast.LENGTH_SHORT).show();
                            networkLoader.dismissLoadingDialog();

                        }
                    } else {
                        Toast.makeText(getContext(), "Server Error" + response.raw(), Toast.LENGTH_LONG).show();
                        Log.d("TAG", "onResponse: error body is here response is not successful " + response.raw());
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

    private void setupSpinners() {
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getSubjectNames());
        subjectSpinner.setAdapter(subjectAdapter);

       /* ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getLevelNames());
        fromLevelSpinner.setAdapter(levelAdapter);
        toLevelSpinner.setAdapter(levelAdapter);*/
    }



    void openLevelDialog()
    {
        AlertDialog.Builder mybuilder = new AlertDialog.Builder(getContext(), R.style.mydialog);
        final LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_level, null);
        mybuilder.setView(view);
        levelDialog = mybuilder.create();

        Window window = levelDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);


        applylevelfilter = view.findViewById(R.id.applylevelfilter);
        resetclose=view.findViewById(R.id.resetclose);
        fromLevelSpinner = view. findViewById(R.id.fromLevelSpinner);
        toLevelSpinner = view.findViewById(R.id.toLevelSpinner);
        subjectSpinner = view.findViewById(R.id.subjectSpinner);
        setupSpinners();

        resetclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                levelDialog.dismiss();
            }
        });

        applylevelfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fromLevelIndex = fromLevelSpinner.getSelectedItemPosition();
                int toLevelIndex = toLevelSpinner.getSelectedItemPosition();
                from_level_id= String.valueOf(levelsList.get(fromLevelIndex).id);
                to_level_id=String.valueOf(levelsList.get(toLevelIndex).id);
                lvlname.setText("Level\n"+levelsList.get(fromLevelIndex).title+ levelsList.get(toLevelIndex).title);
                levelDialog.dismiss();
                //searchthisData(1);


                /*if (toLevelSpinner!=null && fromLevelSpinner!=null)
                {
                    int fromLevelIndex = fromLevelSpinner.getSelectedItemPosition();
                    int toLevelIndex = toLevelSpinner.getSelectedItemPosition();
                    from_level_id= String.valueOf(levelsList.get(fromLevelIndex).id);
                    to_level_id=String.valueOf(levelsList.get(toLevelIndex).id);
                }*/
                int subjectIndex = subjectSpinner.getSelectedItemPosition();
                subject_id= String.valueOf(subjectsList.get(subjectIndex).id);



                searchthisData(1);

            }
        });


        setupLevelDialogSpinner();

        levelDialog.show();


    }

    void setupLevelDialogSpinner()
    {
        ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getLevelNames());
        fromLevelSpinner.setAdapter(levelAdapter);
        toLevelSpinner.setAdapter(levelAdapter);
    }



    void searchthisData(int page) {
        if (isLoading) return; // prevent multiple triggers
        isLoading = true;
        networkLoader.showLoadingDialog(getContext());

        viewModel.searchTeacher(token, requirement_id, subject_id, subject, from_level_id, to_level_id, location, page)
                .observe(getActivity(), new Observer<recommendedTeacherResponse>() {
                    @Override
                    public void onChanged(recommendedTeacherResponse response) {
                        if (response != null) {
                            lastPage = response.data.last_page;
                            currentPage = response.data.current_page;

                            if (page == 1) {
                                allData.clear();
                            }

                            allData.addAll(response.data.dataList);

                            if (adapter == null) {
                                adapter = new recommendedTeacherListAdapter(getContext(), allData);
                                beneficiary_list.setHasFixedSize(true);
                                beneficiary_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                                beneficiary_list.setAdapter(adapter);
                                adapter.onclickList(new recommendedTeacherListAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(recommendedTeacherResponse.TutorRequest item, int instruction) {
                                        if (instruction == 1) {
                                            showDetails(item);
                                        } else if (instruction == 2) {
                                            Intent intent = new Intent(getContext(), Single_chat_room.class);
                                            intent.putExtra("receiver", item.id);
                                            intent.putExtra("sender", Integer.parseInt(requirement_id));
                                            startActivity(intent);
                                        }
                                    }
                                });
                            } else {
                                adapter.notifyDataSetChanged();
                            }

                        }
                        isLoading = false;
                        networkLoader.dismissLoadingDialog();
                    }
                });
    }

    private String maskEmail(String email) {
        if (email == null || !email.contains("@")) return "N/A";
        String[] parts = email.split("@");
        if (parts[0].length() < 2) return "****@" + parts[1]; // In case of short usernames
        return parts[0].substring(0, 2) + "****@" + parts[1];
    }

}