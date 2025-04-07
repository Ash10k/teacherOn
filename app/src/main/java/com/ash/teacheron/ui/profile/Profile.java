package com.ash.teacheron.ui.profile;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ash.teacheron.ProfileEdit;
import com.ash.teacheron.R;
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

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile extends Fragment {
    SharedPrefLocal sharedPrefLocal;
    String token, userId, usertype,TAG="Profile";
    View fragmentView;
    CircleImageView profile_image;
    NetworkLoader networkLoader;
    TextView teacher_name, teacher_location, teacher_email, teacher_phone, fee_details, exp, ptti, teacher_role, teacher_dob, feedet;

    String encodedimg1;
    private Bitmap bitmap;
    private static final int SELECT_PICTURE = 0;
    private final int REQUEST_CAMERA = 1;
    private Uri camuri;
    File imageFile;
    MultipartBody.Part imagePart;
    AuthAPI SendData;
    ImageView openEdit;
    public Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPrefLocal = new SharedPrefLocal(getActivity());
        userId = String.valueOf(sharedPrefLocal.getUserId());
        token = sharedPrefLocal.getSessionId();
        usertype = sharedPrefLocal.getUserType();

        networkLoader = new NetworkLoader();


        fragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
        profile_image = fragmentView.findViewById(R.id.profile_image);
        teacher_name = fragmentView.findViewById(R.id.teacher_name);
        teacher_location = fragmentView.findViewById(R.id.teacher_location);
        teacher_email = fragmentView.findViewById(R.id.teacher_email);
        teacher_phone = fragmentView.findViewById(R.id.teacher_phone);
       // fee_details = fragmentView.findViewById(R.id.fee_details);
        exp = fragmentView.findViewById(R.id.exp);
        ptti = fragmentView.findViewById(R.id.ptti);
      teacher_role = fragmentView.findViewById(R.id.teacher_role);
      //  teacher_dob = fragmentView.findViewById(R.id.teacher_dob);
      //  feedet = fragmentView.findViewById(R.id.feedet);
        openEdit=fragmentView.findViewById(R.id.openEdit);
        /*try {
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
                        .placeholder(R.drawable.baseline_account_circle_24)
                        .error(R.drawable.baseline_account_circle_24);
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



        }*/


        getProfiledata();
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(21);
            }
        });

        openEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //selectImage(21);
                startActivity(new Intent(getContext(), ProfileEdit.class));
            }
        });
        return fragmentView;
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
                            //ExperList=response.body().data.teacherExperience;
                            //otherList=response.body().data.teacherSubject;
                            //certificateList=response.body().data.teacherCertification;
                            // showDetails();
                            try {

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .placeholder(R.drawable.baseline_account_circle_24)
                                        .error(R.drawable.baseline_account_circle_24);

                                Glide.with(getContext())
                                        .load(response.body().data.profileImageUrl != null ? response.body().data.profileImageUrl : "")
                                        .apply(options)
                                        .into(profile_image);
                                sharedPrefLocal.setProfileImage(response.body().data.profileImageUrl);

                                teacher_name.setText(response.body().data.name != null ? response.body().data.name : "N/A");
                                teacher_location.setText(response.body().data.location != null ? response.body().data.location : "N/A");
                               // teacher_email.setText(response.body().data.email != null ? response.body().data.email : "N/A");
                               // teacher_phone.setText(response.body().data.phone != null ? response.body().data.phone : "N/A");

                                String email1 = (response.body().data.email != null) ? response.body().data.email : "N/A";
                                String phone1 = (response.body().data.phone != null) ? response.body().data.phone : "N/A";

                                String email =  (email1);
                                String phone =  (phone1);

                                teacher_email.setText(email);
                                teacher_phone.setText(phone);


                                teacher_role.setText(response.body().data.userType != null ? response.body().data.userType : "N/A");

                               // fee_details.setText("");
                                //teacher_dob.setText("");
                                //feedet.setText("");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(getContext(), "" + response.body().message, Toast.LENGTH_SHORT).show();
                            networkLoader.dismissLoadingDialog();
                        }
                    } else {
                        Toast.makeText(getContext(), "Server Error" + response.raw(), Toast.LENGTH_LONG).show();
                        //Log.d(TAG, "onResponse: error body is here response is not successful " + response.raw());
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
                Glide.with(getActivity()).load(bitmap).apply(options).into(profile_image);


            } catch (Exception e) {
                e.printStackTrace();
            }
            // bitmap = BitmapFactory.decodeFile(selectedImagePath, options);
            Log.d(TAG, "onActivityResult: bitmap: " + bitmap);
            profile_image.setImageBitmap(bitmap);

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
                Glide.with(this).load(selectedImageUri).into(profile_image);
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