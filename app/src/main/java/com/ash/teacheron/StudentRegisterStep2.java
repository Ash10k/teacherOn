package com.ash.teacheron;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.ash.teacheron.constants.Contants.SERVER_ERROR;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ash.teacheron.commonComponents.NetworkLoader;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.languageResponse;
import com.ash.teacheron.retrofit.model.registerResponseStep2;
import com.ash.teacheron.retrofit.model.registerResponseStud;
import com.ash.teacheron.retrofit.model.saveResponse;
import com.ash.teacheron.retrofit.model.teaacherModel.step3teacher;
import com.ash.teacheron.viewmodel.studentVM.step1VModelStudent;
import com.ash.teacheron.viewmodel.studentVM.step2VModelStudent;
import com.ash.teacheron.viewmodel.teacherVM.step3VModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRegisterStep2 extends AppCompatActivity {

    ImageView selectedImg;
    private Spinner degreeTypeSpinner, monthStartSpinner, yearStartSpinner, monthEndSpinner, yearEndSpinner, associationSpinner, gndrpref,typesrates;
    private TextInputEditText degreeNameInput, budgtinp,detailsrequi,loctiio;
    private Button savestep2;
    private ChipGroup chipGroup;

    private List<step3teacher.Degree> degreeList = new ArrayList<>();
    private Set<String> addedDegreeSet = new HashSet<>(); // Prevent duplicates
    NetworkLoader networkLoader;
    String token, TAG = "StudentRegisterStep2";
    AuthAPI SendData;
    RelativeLayout educationlist;
    private List<languageResponse.Lang> subjectsList = new ArrayList<>();
    private List<languageResponse.Lang> currencyList = new ArrayList<>();
    private List<languageResponse.Lang> countryList = new ArrayList<>();
    String encodedimg1;
    private Bitmap bitmap;
    private static final int SELECT_PICTURE = 0;
    private final int REQUEST_CAMERA = 1;
    private Uri camuri;
    File imageFile;
    MultipartBody.Part imagePart;
    String details,lo,password,  user_id,   requirement_type,   tutor_option,   travel_limit,   budget,   budget_type,   gender_preference,   tutor_type,   budget_currency_id,   communicate_language_id,   tutor_from_country_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register_step2);
        selectedImg = findViewById(R.id.selectedImg);
        SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(StudentRegisterStep2.this);
        user_id= String.valueOf(sharedPrefLocal.getUserId());
        password=sharedPrefLocal.getUserPassword();
        typesrates=findViewById(R.id.typesrates);
        degreeTypeSpinner = findViewById(R.id.degreeTYPESpinner);
        degreeNameInput = findViewById(R.id.degreeName);
        budgtinp=findViewById(R.id.budget);
        monthStartSpinner = findViewById(R.id.monthstart);
        yearStartSpinner = findViewById(R.id.yearstart);
        monthEndSpinner = findViewById(R.id.monthend);
        yearEndSpinner = findViewById(R.id.yearend);
        associationSpinner = findViewById(R.id.association);
        // addButton = findViewById(R.id.addButton);
        savestep2 = findViewById(R.id.savestep2);
        chipGroup = findViewById(R.id.chipGroup);
        gndrpref = findViewById(R.id.gndrpref);

        detailsrequi=findViewById(R.id.detailsrequi);
        loctiio=findViewById(R.id.loctiio);

        loadDummyData();
        networkLoader = new NetworkLoader();
        getLanguage();

        token = sharedPrefLocal.getSessionId();


        // addButton.setOnClickListener(view -> addDegree());
        savestep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                details=detailsrequi.getText().toString().trim();
                lo=loctiio.getText().toString().trim();

                budget = budgtinp.getText().toString().trim();
                travel_limit = degreeNameInput.getText().toString().trim();

                requirement_type  = String.valueOf((degreeTypeSpinner.getSelectedItem()));
                tutor_option  = String.valueOf((monthStartSpinner.getSelectedItem()));
                int subjectIndex = yearStartSpinner.getSelectedItemPosition();
                communicate_language_id= String.valueOf(subjectsList.get(subjectIndex).id);

                int lm = monthEndSpinner.getSelectedItemPosition();
                budget_currency_id= String.valueOf(currencyList.get(lm).id);

                int mk = yearEndSpinner.getSelectedItemPosition();
                tutor_from_country_id= String.valueOf(countryList.get(mk).id);

                gender_preference=String.valueOf((gndrpref.getSelectedItem()));
                        budget_type   = String.valueOf((typesrates.getSelectedItem()));
                tutor_type=String.valueOf((associationSpinner.getSelectedItem()));

                if (budget.isEmpty()) {
                    Toast.makeText(StudentRegisterStep2.this, "Enter Amount", Toast.LENGTH_SHORT).show();
                } else if (travel_limit.isEmpty()) {
                    Toast.makeText(StudentRegisterStep2.this, "Enter Travel KM", Toast.LENGTH_SHORT).show();
                }  else {
                    step2VModelStudent viewModel = new ViewModelProvider(StudentRegisterStep2.this).get(step2VModelStudent.class);
                    networkLoader.showLoadingDialog(StudentRegisterStep2.this);
                    viewModel.startLogin(user_id,   requirement_type,   tutor_option,   travel_limit,   budget,   budget_type,   gender_preference,   tutor_type,   budget_currency_id,   communicate_language_id,   tutor_from_country_id,password,details,lo).observe(StudentRegisterStep2.this, new Observer<registerResponseStep2>() {
                        @Override
                        public void onChanged(registerResponseStep2 loginResponse) {
                            if (loginResponse != null) {
                                Log.d("framg", "" + new Gson().toJson(loginResponse));
                                SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(StudentRegisterStep2.this);
                                sharedPrefLocal.setSessionId( (loginResponse.data.tokenData.accessToken));
                                 sharedPrefLocal.setUserName(loginResponse.data.name);
                                sharedPrefLocal.setProfileImage(loginResponse.data.profileImage);

                                Toast.makeText(StudentRegisterStep2.this, "" + loginResponse.message, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(StudentRegisterStep2.this,BothBottomAndSideNavigation.class);
                                startActivity(intent);
                            } else {
                                // Handle null response here if needed
                                Toast.makeText(StudentRegisterStep2.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                            }

                            networkLoader.dismissLoadingDialog();

                        }
                    });
                    viewModel.getErrorMessage().observe(StudentRegisterStep2.this, new Observer<ErrorData>() {
                        @Override
                        public void onChanged(ErrorData errorData) {
                            // Display error message
                            Toast.makeText(StudentRegisterStep2.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                            Log.d("Error", errorData.getMessage());
                            networkLoader.dismissLoadingDialog();
                        }
                    });

                }
            }
        });

        selectedImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(21);
            }
        });
        if (ContextCompat.checkSelfPermission(StudentRegisterStep2.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(StudentRegisterStep2.this, "Please give permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(StudentRegisterStep2.this, new String[]{Manifest.permission.CAMERA, MANAGE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, 101);

        }
    }

    private void loadDummyData() {
        // Dummy degree types
        String[] degreeTypes = {"Tutoring", "Assignment Help"};
        ArrayAdapter<String> degreeTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, degreeTypes);
        degreeTypeSpinner.setAdapter(degreeTypeAdapter);

        // Dummy months
        String[] months = {"Online", "At my place", "Travel to tutor"};
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, months);
        monthStartSpinner.setAdapter(monthAdapter);



        // Dummy associations
        String[] associations = {"Part Time", "Full Time"};
        ArrayAdapter<String> associationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, associations);
        associationSpinner.setAdapter(associationAdapter);

        String[] genderPref = {"None", "Preferably Male", "Preferably Female" };
        ArrayAdapter<String> genderPrefAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderPref);
        gndrpref.setAdapter(genderPrefAdapter);

        String[] rtPref = {"Per Hour", "Fixed/Flat", "Per Day", "Per Week"};
        ArrayAdapter<String> rfAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, rtPref);
        typesrates.setAdapter(rfAdapter);


    }

    private boolean getLanguage() {
        // tried_username_pass(user, pass);
        try {
            networkLoader.showLoadingDialog(StudentRegisterStep2.this);
            SendData = RetrofitBuilder.build().create(AuthAPI.class);
            // String first_name, String last_name, String email, String password_confirmation, String password
            Call<languageResponse> myCall = SendData.getLanguage("Bearer " + token);
            myCall.enqueue(new Callback<languageResponse>() {
                @Override
                public void onResponse(Call<languageResponse> call, Response<languageResponse> response) {

                    if (response.isSuccessful()) {

                        if ((response.body().status).equals("success")) {
                            networkLoader.dismissLoadingDialog();
                            subjectsList = response.body().language;
                            getCurrency();
                            //setupSpinners();

                        } else {

                            Toast.makeText(StudentRegisterStep2.this, "" + response.body().message, Toast.LENGTH_SHORT).show();
                            networkLoader.dismissLoadingDialog();

                        }
                    } else {
                        Toast.makeText(StudentRegisterStep2.this, "Server Error" + response.raw(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onResponse: error body is here response is not successful " + response.raw());
                        networkLoader.dismissLoadingDialog();
                        educationlist.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<languageResponse> call, Throwable t) {
                    Toast.makeText(StudentRegisterStep2.this, "Server Error", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    networkLoader.dismissLoadingDialog();
                }
            });
        } catch (Exception exception) {
            Toast.makeText(this, "Some thing wrong", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private boolean getCurrency() {
        // tried_username_pass(user, pass);
        try {
            networkLoader.showLoadingDialog(StudentRegisterStep2.this);
            SendData = RetrofitBuilder.build().create(AuthAPI.class);
            // String first_name, String last_name, String email, String password_confirmation, String password
            Call<languageResponse> myCall = SendData.getCurrency("Bearer " + token);
            myCall.enqueue(new Callback<languageResponse>() {
                @Override
                public void onResponse(Call<languageResponse> call, Response<languageResponse> response) {

                    if (response.isSuccessful()) {

                        if ((response.body().status).equals("success")) {
                            networkLoader.dismissLoadingDialog();
                            currencyList = response.body().language;
                            getCountry();
                            //setupSpinners();

                        } else {

                            Toast.makeText(StudentRegisterStep2.this, "" + response.body().message, Toast.LENGTH_SHORT).show();
                            networkLoader.dismissLoadingDialog();

                        }
                    } else {
                        Toast.makeText(StudentRegisterStep2.this, "Server Error" + response.raw(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onResponse: error body is here response is not successful " + response.raw());
                        networkLoader.dismissLoadingDialog();
                        educationlist.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<languageResponse> call, Throwable t) {
                    Toast.makeText(StudentRegisterStep2.this, "Server Error", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    networkLoader.dismissLoadingDialog();
                }
            });
        } catch (Exception exception) {
            Toast.makeText(this, "Some thing wrong", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private boolean getCountry() {
        // tried_username_pass(user, pass);
        try {
            networkLoader.showLoadingDialog(StudentRegisterStep2.this);
            SendData = RetrofitBuilder.build().create(AuthAPI.class);
            // String first_name, String last_name, String email, String password_confirmation, String password
            Call<languageResponse> myCall = SendData.getCountry("Bearer " + token);
            myCall.enqueue(new Callback<languageResponse>() {
                @Override
                public void onResponse(Call<languageResponse> call, Response<languageResponse> response) {

                    if (response.isSuccessful()) {

                        if ((response.body().status).equals("success")) {
                            networkLoader.dismissLoadingDialog();
                            countryList = response.body().language;
                            setupSpinners();

                        } else {

                            Toast.makeText(StudentRegisterStep2.this, "" + response.body().message, Toast.LENGTH_SHORT).show();
                            networkLoader.dismissLoadingDialog();

                        }
                    } else {
                        Toast.makeText(StudentRegisterStep2.this, "Server Error" + response.raw(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onResponse: error body is here response is not successful " + response.raw());
                        networkLoader.dismissLoadingDialog();
                        educationlist.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<languageResponse> call, Throwable t) {
                    Toast.makeText(StudentRegisterStep2.this, "Server Error", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    networkLoader.dismissLoadingDialog();
                }
            });
        } catch (Exception exception) {
            Toast.makeText(this, "Some thing wrong", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    private void setupSpinners() {
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getSubjectNames());
        yearStartSpinner.setAdapter(subjectAdapter);

        ArrayAdapter<String> currAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getCurrencyNames());
        monthEndSpinner.setAdapter(currAdapter);

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getCountryNames());
        yearEndSpinner.setAdapter(countryAdapter);

        List<String> countryNames = getCountryNames();
        int defaultPosition = countryNames.indexOf("India"); // Find index of "India"
        if (defaultPosition != -1) {
            yearEndSpinner.setSelection(defaultPosition); // Set selection if found
        }


        List<String> currencyNames = getCurrencyNames();
        int defaultPositionCurr = currencyNames.indexOf("Indian Rupee"); // Find index of "Indian Rupee"
        if (defaultPositionCurr != -1) {
            monthEndSpinner.setSelection(defaultPositionCurr); // Set selection if found
        }


    }

    private List<String> getSubjectNames() {
        List<String> names = new ArrayList<>();
        for (languageResponse.Lang subject : subjectsList) {
            names.add(subject.name);
        }
        return names;
    }

    private List<String> getCurrencyNames() {
        List<String> names = new ArrayList<>();
        for (languageResponse.Lang subject : currencyList) {
            names.add(subject.name);
        }
        return names;
    }

    private List<String> getCountryNames() {
        List<String> names = new ArrayList<>();
        for (languageResponse.Lang subject : countryList) {
            names.add(subject.name);
        }
        return names;
    }

    private void selectImage(int request_made_by) {
        try {

            String fileName = "new-photo-name.jpg";
            //create parameters for Intent with filename
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
            //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
            camuri = StudentRegisterStep2.this.getContentResolver()
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

        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            //  Toast.makeText(StudentRegisterStep2.this, "called onactivity result", Toast.LENGTH_SHORT).show();
            Uri selectedImageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media
                        .getBitmap(StudentRegisterStep2.this.getContentResolver(), selectedImageUri);


                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.baseline_account_circle_24)
                        .error(R.drawable.baseline_account_circle_24);
                Glide.with(StudentRegisterStep2.this).load(bitmap).apply(options).into(selectedImg);


            } catch (Exception e) {
                e.printStackTrace();
            }
            // bitmap = BitmapFactory.decodeFile(selectedImagePath, options);
            Log.d(TAG, "onActivityResult: bitmap: " + bitmap);
            selectedImg.setImageBitmap(bitmap);

            System.out.println("IMAGE_UPLOAD" + selectedImageUri);

            Log.d("Picture Path inside", "Picture pathds uri" + selectedImageUri);

            String path = getRealPathFromURI(selectedImageUri);
            Log.d("Picture pathds", "Picture pathds" + path);
            // up_img(selectedImageUri);


        } else if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {


                Uri selectedImageUri = camuri;

// Get real path from URI
                String[] projection = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.MIME_TYPE};
                CursorLoader cursorLoader = new CursorLoader(StudentRegisterStep2.this, selectedImageUri, projection, null, null, null);
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
                Glide.with(this).load(selectedImageUri).into(selectedImg);
                imageFile = new File(selectedImagePath);
                saveforms();
            }


        } else {
            // selectImage();
            Toast.makeText(StudentRegisterStep2.this, "Please click image", Toast.LENGTH_SHORT).show();
            // showInstalledAppDetails("com.a5h.hamlet");
        }
    }

    public String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = StudentRegisterStep2.this.getContentResolver().query(contentURI, null,
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
        networkLoader.showLoadingDialog(StudentRegisterStep2.this);
        try {
            if (imageFile != null) {
                RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
           //     imagePart = MultipartBody.Part.createFormData("profile_image", imageFile.getName(), imageRequestBody);
                imagePart = MultipartBody.Part.createFormData("profile_image", imageFile.getName(), imageRequestBody);
            }

            RequestBody user_idRequestBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(user_id));
            MultipartBody.Part uidPart = MultipartBody.Part.createFormData("user_id", null, user_idRequestBody);
            SendData = RetrofitBuilder.build().create(AuthAPI.class);
            Call<saveResponse> call = SendData.saveImageStudent("",uidPart, imagePart);
            call.enqueue(new Callback<saveResponse>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(Call<saveResponse> call, Response<saveResponse> response) {
                    // progress.setVisibility(View.GONE);
                    if (response.code() == 201) {
                        if (response.body().status.equals("success")) {
                            Toast.makeText(StudentRegisterStep2.this, ""+response.body().message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(StudentRegisterStep2.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                            imageFile = null;
                            imagePart = null;
                        }
                        networkLoader.dismissLoadingDialog();
                    } else {
                        Toast.makeText(StudentRegisterStep2.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                        networkLoader.dismissLoadingDialog();
                    }
                }

                @Override
                public void onFailure(Call<saveResponse> call, Throwable t) {
                    Log.d("test", "can not connect --- " + t.getMessage());
                    networkLoader.dismissLoadingDialog();
                    Toast.makeText(StudentRegisterStep2.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}