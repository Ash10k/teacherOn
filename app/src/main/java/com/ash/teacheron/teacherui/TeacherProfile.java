package com.ash.teacheron.teacherui;

import static com.ash.teacheron.constants.Contants.SERVER_ERROR;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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

import com.ash.teacheron.ProfileEdit;
import com.ash.teacheron.ProfileEditTeacher;
import com.ash.teacheron.R;
import com.ash.teacheron.StudentRegisterStep2;
import com.ash.teacheron.adapter.educationCerti_adapter2;
import com.ash.teacheron.adapter.proExperience_adapter2;
import com.ash.teacheron.adapter.subjectView2_adapter;
import com.ash.teacheron.commonComponents.NetworkLoader;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.recommendedProfile;
import com.ash.teacheron.retrofit.model.saveResponse;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TeacherProfile extends Fragment {

    View fragmentView;
    AlertDialog detailsDialog, subjectDialog, certiDialog, experDialog;
    SharedPrefLocal sharedPrefLocal;
    String token, userId, usertype, TAG = "TeacherProfile";

    CircleImageView profile_image;
    TextView teacher_name, teacher_location, teacher_email, teacher_phone, fee_details, experi, ptti, teacher_role, teacher_dob, feedet;
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

    
    //image
    String encodedimg1;
    private Bitmap bitmap;
    private static final int SELECT_PICTURE = 0;
    private final int REQUEST_CAMERA = 1;
    private Uri camuri;
    File imageFile;
    MultipartBody.Part imagePart;
    AuthAPI SendData;
    ImageView openEdit;
    public TeacherProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_teacher_profile, container, false);
        networkLoader = new NetworkLoader();
        sharedPrefLocal = new SharedPrefLocal(getActivity());
        userId = String.valueOf(sharedPrefLocal.getUserId());
        token = sharedPrefLocal.getSessionId();
        educt = fragmentView.findViewById(R.id.educt);
        openViewSub = fragmentView.findViewById(R.id.openViewSub);
        tchingpr = fragmentView.findViewById(R.id.tchingpr);
        feedet = fragmentView.findViewById(R.id.feedet);
        experi = fragmentView.findViewById(R.id.exp);
        ptti = fragmentView.findViewById(R.id.ptti);
        openEdit=fragmentView.findViewById(R.id.openEdit);
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


        profileImage = fragmentView.findViewById(R.id.profile_image);
        teacherName = fragmentView.findViewById(R.id.teacher_name);
        teacherLocation = fragmentView.findViewById(R.id.teacher_location);
       // teacherExperience = fragmentView.findViewById(R.id.teacher_gender);
        teacherFee = fragmentView.findViewById(R.id.fee_details);
        teacherEmail = fragmentView.findViewById(R.id.teacher_email);
        teacherPhone = fragmentView.findViewById(R.id.teacher_phone);
        teacherGender = fragmentView.findViewById(R.id.teacher_gender);
        teacherRole = fragmentView.findViewById(R.id.teacher_role);
       // teacherDOB = fragmentView.findViewById(R.id.teacher_dob);
        teacherTravel = fragmentView.findViewById(R.id.teacher_travel);
        teacherAvailability = fragmentView.findViewById(R.id.teacher_availability);
        //profileImage = fragmentView.findViewById(R.id.profile_image);

        getProfiledata();
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(21);
            }
        });
        openEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //selectImage(21);
                Intent intent=new Intent(getContext(), ProfileEditTeacher.class);
                //intent.putExtra("prot",56);
                startActivity(intent);
            }
        });

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
            Call<recommendedProfile> myCall = SendData.getProfile(token);
            myCall.enqueue(new Callback<recommendedProfile>() {
                @Override
                public void onResponse(Call<recommendedProfile> call, Response<recommendedProfile> response) {
                    if (response.isSuccessful()) {
                        if ((response.body().status).equals("success")) {
                            networkLoader.dismissLoadingDialog();
                            ExperList = response.body().data.teacherExperience;
                            otherList = response.body().data.teacherSubject;
                            certificateList = response.body().data.teacherCertification;

                            try {

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .placeholder(R.drawable.baseline_account_circle_24)
                                        .error(R.drawable.baseline_account_circle_24);

                                Glide.with(getContext())
                                        .load(response.body().data.profileImageUrl != null ? response.body().data.profileImageUrl : "")
                                        .apply(options)
                                        .into(profileImage);

                                teacherName.setText(response.body().data.name != null ? response.body().data.name : "N/A");
                                teacherLocation.setText(response.body().data.location != null ? response.body().data.location : "N/A");
                                // teacher_email.setText(response.body().data.email != null ? response.body().data.email : "N/A");
                                // teacher_phone.setText(response.body().data.phone != null ? response.body().data.phone : "N/A");

                                String email1 = (response.body().data.email != null) ? response.body().data.email : "N/A";
                                String phone1 = (response.body().data.phone != null) ? response.body().data.phone : "N/A";

                                String email =  (email1);
                                String phone =  (phone1);
                               // Toast.makeText(getContext(), ""+response.body().data.profileImageUrl, Toast.LENGTH_SHORT).show();

                                teacherEmail.setText(email);
                                teacherPhone.setText(phone);
                                sharedPrefLocal.setProfileImage(response.body().data.profileImageUrl);
                                teacherRole.setText(response.body().data.userType != null ? response.body().data.userType : "N/A");

                                teacherFee.setText(response.body().data.teacherDetail.feeAmount + "/" + response.body().data.teacherDetail.feeSchedule);
                               // teacherDOB.setText(response.body().data.teacherMeta.dob);
                                feedet.setText(response.body().data.teacherDetail.feeSchedule);
                                experi.setText("Total experience: "+response.body().data.teacherDetail.totalExperience);
                                ptti.setText(response.body().data.teacherDetail.interestedAssociation);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            // showDetails();
                        } else {
                            Toast.makeText(getContext(), "" + response.body().message, Toast.LENGTH_SHORT).show();
                            networkLoader.dismissLoadingDialog();
                        }
                    } else {
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

    private String maskEmail(String email) {
        if (email == null || !email.contains("@")) return "N/A";
        String[] parts = email.split("@");
        if (parts[0].length() < 2) return "****@" + parts[1]; // In case of short usernames
        return parts[0].substring(0, 2) + "****@" + parts[1];
    }

    private String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) return "N/A";
        return phone.substring(0, 4) + "****" + phone.substring(phone.length() - 1);
    }


    private void selectImage(int request_made_by) {
        try {

            String fileName = "new-photo-name.jpg";
            //create parameters for Intent with filename
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
            //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
            camuri = getActivity().getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            //create new Intent
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, camuri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            try {
                if (request_made_by == 21)
                    startActivityForResult(intent, REQUEST_CAMERA);


            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode ==getActivity(). RESULT_OK && requestCode == SELECT_PICTURE) {
            //  Toast.makeText(getActivity(), "called onactivity result", Toast.LENGTH_SHORT).show();
            Uri selectedImageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media
                        .getBitmap(getActivity().getContentResolver(), selectedImageUri);


                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.baseline_account_circle_24)
                        .error(R.drawable.baseline_account_circle_24);
                Glide.with(getActivity()).load(bitmap).apply(options).into(profileImage);


            } catch (Exception e) {
                e.printStackTrace();
            }
            // bitmap = BitmapFactory.decodeFile(selectedImagePath, options);
            Log.d(TAG, "onActivityResult: bitmap: " + bitmap);
            profileImage.setImageBitmap(bitmap);

            System.out.println("IMAGE_UPLOAD" + selectedImageUri);

            Log.d("Picture Path inside", "Picture pathds uri" + selectedImageUri);

            String path = getRealPathFromURI(selectedImageUri);
            Log.d("Picture pathds", "Picture pathds" + path);
            // up_img(selectedImageUri);


        } else if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {

                Uri selectedImageUri = camuri;

// Get real path from URI
                String[] projection = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.MIME_TYPE};
                CursorLoader cursorLoader = new CursorLoader(getActivity(), selectedImageUri, projection, null, null, null);
                Cursor cursor = cursorLoader.loadInBackground();

                String selectedImagePath = null;
                String mimeType = "image/jpeg"; // Default MIME type

                if (cursor != null) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    int mimeIndex = cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE);

                    cursor.moveToFirst();
                    selectedImagePath = cursor.getString(columnIndex);
                    if (mimeIndex != -1) {
                        mimeType = cursor.getString(mimeIndex); // Get actual MIME type
                    }
                    cursor.close();
                }

                byte[] byteArray = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    try {
                        byteArray = Files.readAllBytes(Paths.get(selectedImagePath));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                encodedimg1 = "data:" + mimeType + ";base64," + Base64.encodeToString(byteArray, Base64.DEFAULT);

                Log.d(TAG, "Base64 Encoded Image: " + encodedimg1.substring(0, 100) + "...");
                Glide.with(this).load(selectedImageUri).into(profileImage);
                imageFile = new File(selectedImagePath);
                saveforms();
            }


        } else {
            // selectImage();
            Toast.makeText(getActivity(), "Please click image", Toast.LENGTH_SHORT).show();
            // showInstalledAppDetails("com.a5h.hamlet");
        }
    }

    public String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null,
                null, null, null);
        if (cursor == null) {
            // path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            try {
                int idx = cursor
                        .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
            } catch (Exception e) {

                result = "";
            }
            cursor.close();
        }
        return result;
    }


    private void saveforms() {
        //Toast.makeText(this, ""+user_id, Toast.LENGTH_SHORT).show();
        networkLoader.showLoadingDialog(getActivity());
        try {
            if (imageFile != null) {
                RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
                //     imagePart = MultipartBody.Part.createFormData("profile_image", imageFile.getName(), imageRequestBody);
                imagePart = MultipartBody.Part.createFormData("profile_image", imageFile.getName(), imageRequestBody);
            }

            RequestBody user_idRequestBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));
            MultipartBody.Part uidPart = MultipartBody.Part.createFormData("user_id", null, user_idRequestBody);
            SendData = RetrofitBuilder.build().create(AuthAPI.class);
            Call<saveResponse> call = SendData.saveImageStudent(token,uidPart, imagePart);
            call.enqueue(new Callback<saveResponse>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(Call<saveResponse> call, Response<saveResponse> response) {
                    // progress.setVisibility(View.GONE);
                    if (response.code() == 201) {
                        if (response.body().status.equals("success")) {
                            Toast.makeText(getActivity(), ""+response.body().message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                            imageFile = null;
                            imagePart = null;
                        }
                        networkLoader.dismissLoadingDialog();
                    } else {
                        Toast.makeText(getActivity(), SERVER_ERROR, Toast.LENGTH_SHORT).show();
                        networkLoader.dismissLoadingDialog();
                    }
                }

                @Override
                public void onFailure(Call<saveResponse> call, Throwable t) {
                    Log.d("test", "can not connect --- " + t.getMessage());
                    networkLoader.dismissLoadingDialog();
                    Toast.makeText(getActivity(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//tnt@mail.com - 123456