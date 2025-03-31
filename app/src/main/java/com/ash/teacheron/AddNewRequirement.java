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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ash.teacheron.commonComponents.NetworkLoader;
import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.api.AuthAPI;
import com.ash.teacheron.retrofit.builders.RetrofitBuilder;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.appOptionsResponse;
import com.ash.teacheron.retrofit.model.languageResponse;
import com.ash.teacheron.retrofit.model.registerResponseStep2;
import com.ash.teacheron.retrofit.model.saveResponse;
import com.ash.teacheron.retrofit.model.teaacherModel.step3teacher;
import com.ash.teacheron.viewmodel.studentVM.step2VModelStudent;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

public class AddNewRequirement extends AppCompatActivity {

    ImageView selectedImg;
    private Spinner degreeTypeSpinner, monthStartSpinner, yearStartSpinner, monthEndSpinner, yearEndSpinner, associationSpinner, gndrpref, typesrates;
    private TextInputEditText degreeNameInput, budgtinp;
    private Button savestep2;
    private ChipGroup chipGroup;

    private List<step3teacher.Degree> degreeList = new ArrayList<>();
    private Set<String> addedDegreeSet = new HashSet<>(); // Prevent duplicates
    NetworkLoader networkLoader;
    String token, userId, TAG = "AddNewRequirement";
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
    String password, user_id, requirement_type, tutor_option, travel_limit, budget, budget_type, gender_preference, tutor_type, budget_currency_id, communicate_language_id, tutor_from_country_id;

    private List<appOptionsResponse.Subject> subjectsListActual = new ArrayList<>();
    private List<appOptionsResponse.Level> levelsList = new ArrayList<>();
    private Spinner subjectSpinner, fromLevelSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_requirement);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        subjectSpinner = findViewById(R.id.subjectSpinner);
        fromLevelSpinner = findViewById(R.id.fromLevelSpinner);

        selectedImg = findViewById(R.id.selectedImg);
        SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(AddNewRequirement.this);
        user_id = String.valueOf(sharedPrefLocal.getUserId());
        //password=sharedPrefLocal.getSessionId();
        typesrates = findViewById(R.id.typesrates);
        degreeTypeSpinner = findViewById(R.id.degreeTYPESpinner);
        degreeNameInput = findViewById(R.id.degreeName);
        budgtinp = findViewById(R.id.budget);
        monthStartSpinner = findViewById(R.id.monthstart);
        yearStartSpinner = findViewById(R.id.yearstart);
        monthEndSpinner = findViewById(R.id.monthend);
        yearEndSpinner = findViewById(R.id.yearend);
        associationSpinner = findViewById(R.id.association);
        // addButton = findViewById(R.id.addButton);
        savestep2 = findViewById(R.id.savestep2);
        chipGroup = findViewById(R.id.chipGroup);
        gndrpref = findViewById(R.id.gndrpref);

        loadDummyData();
        networkLoader = new NetworkLoader();
        getLanguage();

        token = sharedPrefLocal.getSessionId();

        get_edu_journey();
        // addButton.setOnClickListener(view -> addDegree());
        savestep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                budget = budgtinp.getText().toString().trim();
                travel_limit = degreeNameInput.getText().toString().trim();

                requirement_type = String.valueOf((degreeTypeSpinner.getSelectedItem()));
                tutor_option = String.valueOf((monthStartSpinner.getSelectedItem()));
                int subjectIndex = yearStartSpinner.getSelectedItemPosition();
                communicate_language_id = String.valueOf(subjectsList.get(subjectIndex).id);

                int lm = monthEndSpinner.getSelectedItemPosition();
                budget_currency_id = String.valueOf(currencyList.get(lm).id);

                int mk = yearEndSpinner.getSelectedItemPosition();
                tutor_from_country_id = String.valueOf(countryList.get(mk).id);

                gender_preference = String.valueOf((gndrpref.getSelectedItem()));
                budget_type = String.valueOf((typesrates.getSelectedItem()));
                tutor_type = String.valueOf((associationSpinner.getSelectedItem()));
                if (budget.isEmpty()) {
                    Toast.makeText(AddNewRequirement.this, "Enter Amount", Toast.LENGTH_SHORT).show();
                } else if (travel_limit.isEmpty()) {
                    Toast.makeText(AddNewRequirement.this, "Enter Travel KM", Toast.LENGTH_SHORT).show();
                } else {
                    step2VModelStudent viewModel = new ViewModelProvider(AddNewRequirement.this).get(step2VModelStudent.class);
                    networkLoader.showLoadingDialog(AddNewRequirement.this);

                    int subjectIndex2 = subjectSpinner.getSelectedItemPosition();
                    int fromLevelIndex = fromLevelSpinner.getSelectedItemPosition();
                    String subject_id = String.valueOf(subjectsList.get(subjectIndex2).id);
                    String from_level_id = String.valueOf(levelsList.get(fromLevelIndex).id);

                    viewModel.createPost(subject_id, from_level_id, user_id, requirement_type, tutor_option, travel_limit, budget, budget_type, gender_preference, tutor_type, budget_currency_id, communicate_language_id, tutor_from_country_id, password, token).observe(AddNewRequirement.this, new Observer<registerResponseStep2>() {
                        @Override
                        public void onChanged(registerResponseStep2 loginResponse) {
                            if (loginResponse != null) {
                                Log.d("framg", "" + new Gson().toJson(loginResponse));
                               /* SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(AddNewRequirement.this);
                                sharedPrefLocal.setSessionId( (loginResponse.data.tokenData.accessToken));
                                sharedPrefLocal.setUserName(loginResponse.data.name);
                                sharedPrefLocal.setProfileImage(loginResponse.data.profileImage);
                                Intent intent=new Intent(AddNewRequirement.this,BottomNav.class);
                                startActivity(intent);
                                */
                                Toast.makeText(AddNewRequirement.this, "" + loginResponse.message, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                // Handle null response here if needed
                                Toast.makeText(AddNewRequirement.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                            }

                            networkLoader.dismissLoadingDialog();

                        }
                    });
                    viewModel.getErrorMessage().observe(AddNewRequirement.this, new Observer<ErrorData>() {
                        @Override
                        public void onChanged(ErrorData errorData) {
                            // Display error message
                            Toast.makeText(AddNewRequirement.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();
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
        if (ContextCompat.checkSelfPermission(AddNewRequirement.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(AddNewRequirement.this, "Please give permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(AddNewRequirement.this, new String[]{Manifest.permission.CAMERA, MANAGE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, 101);
            // Log.d(TAG, "onCreate: request code" + requestCode);
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

        String[] genderPref = {"None", "Preferably Male", "Preferably Female"};
        ArrayAdapter<String> genderPrefAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderPref);
        gndrpref.setAdapter(genderPrefAdapter);

        String[] rtPref = {"Per Hour", "Fixed/Flat", "Per Day", "Per Week"};
        ArrayAdapter<String> rfAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, rtPref);
        typesrates.setAdapter(rfAdapter);


    }

    private boolean getLanguage() {
        // tried_username_pass(user, pass);
        try {
            networkLoader.showLoadingDialog(AddNewRequirement.this);
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

                            Toast.makeText(AddNewRequirement.this, "" + response.body().message, Toast.LENGTH_SHORT).show();
                            networkLoader.dismissLoadingDialog();

                        }
                    } else {
                        Toast.makeText(AddNewRequirement.this, "Server Error" + response.raw(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onResponse: error body is here response is not successful " + response.raw());
                        networkLoader.dismissLoadingDialog();
                        educationlist.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<languageResponse> call, Throwable t) {
                    Toast.makeText(AddNewRequirement.this, "Server Error", Toast.LENGTH_SHORT).show();
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
            networkLoader.showLoadingDialog(AddNewRequirement.this);
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

                            Toast.makeText(AddNewRequirement.this, "" + response.body().message, Toast.LENGTH_SHORT).show();
                            networkLoader.dismissLoadingDialog();

                        }
                    } else {
                        Toast.makeText(AddNewRequirement.this, "Server Error" + response.raw(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onResponse: error body is here response is not successful " + response.raw());
                        networkLoader.dismissLoadingDialog();
                        educationlist.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<languageResponse> call, Throwable t) {
                    Toast.makeText(AddNewRequirement.this, "Server Error", Toast.LENGTH_SHORT).show();
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
            networkLoader.showLoadingDialog(AddNewRequirement.this);
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

                            Toast.makeText(AddNewRequirement.this, "" + response.body().message, Toast.LENGTH_SHORT).show();
                            networkLoader.dismissLoadingDialog();

                        }
                    } else {
                        Toast.makeText(AddNewRequirement.this, "Server Error" + response.raw(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onResponse: error body is here response is not successful " + response.raw());
                        networkLoader.dismissLoadingDialog();
                        educationlist.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<languageResponse> call, Throwable t) {
                    Toast.makeText(AddNewRequirement.this, "Server Error", Toast.LENGTH_SHORT).show();
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
            camuri = AddNewRequirement.this.getContentResolver()
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
            //  Toast.makeText(AddNewRequirement.this, "called onactivity result", Toast.LENGTH_SHORT).show();
            Uri selectedImageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media
                        .getBitmap(AddNewRequirement.this.getContentResolver(), selectedImageUri);


                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.baseline_image_24)
                        .error(R.drawable.baseline_image_24);
                Glide.with(AddNewRequirement.this).load(bitmap).apply(options).into(selectedImg);


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
                CursorLoader cursorLoader = new CursorLoader(AddNewRequirement.this, selectedImageUri, projection, null, null, null);
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
            Toast.makeText(AddNewRequirement.this, "Please click image", Toast.LENGTH_SHORT).show();
            // showInstalledAppDetails("com.a5h.hamlet");
        }
    }

    public String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = AddNewRequirement.this.getContentResolver().query(contentURI, null,
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
        networkLoader.showLoadingDialog(AddNewRequirement.this);
        try {
            if (imageFile != null) {
                RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
                //     imagePart = MultipartBody.Part.createFormData("profile_image", imageFile.getName(), imageRequestBody);
                imagePart = MultipartBody.Part.createFormData("1st_img", imageFile.getName(), imageRequestBody);
            }

            RequestBody user_idRequestBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));
            MultipartBody.Part uidPart = MultipartBody.Part.createFormData("user_id", null, user_idRequestBody);
            SendData = RetrofitBuilder.build().create(AuthAPI.class);
            Call<saveResponse> call = SendData.saveImageStudent(uidPart, imagePart);
            call.enqueue(new Callback<saveResponse>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(Call<saveResponse> call, Response<saveResponse> response) {
                    // progress.setVisibility(View.GONE);
                    if (response.code() == 201) {
                        if (response.body().status.equals("success")) {
                            Toast.makeText(AddNewRequirement.this, "" + response.body().message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddNewRequirement.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                            imageFile = null;
                            imagePart = null;
                        }
                        networkLoader.dismissLoadingDialog();
                    } else {
                        Toast.makeText(AddNewRequirement.this, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                        networkLoader.dismissLoadingDialog();
                    }
                }

                @Override
                public void onFailure(Call<saveResponse> call, Throwable t) {
                    Log.d("test", "can not connect --- " + t.getMessage());
                    networkLoader.dismissLoadingDialog();
                    Toast.makeText(AddNewRequirement.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean get_edu_journey() {
        // tried_username_pass(user, pass);
        try {


            networkLoader.showLoadingDialog(AddNewRequirement.this);
            AuthAPI SendData = RetrofitBuilder.build().create(AuthAPI.class);
            // String first_name, String last_name, String email, String password_confirmation, String password
            Call<appOptionsResponse> myCall = SendData.getAppOptions("Bearer " + token);
            myCall.enqueue(new Callback<appOptionsResponse>() {
                @Override
                public void onResponse(Call<appOptionsResponse> call, Response<appOptionsResponse> response) {

                    if (response.isSuccessful()) {

                        if ((response.body().status).equals("success")) {
                            networkLoader.dismissLoadingDialog();
                            subjectsListActual = response.body().data.subjects;
                            levelsList = response.body().data.levels;
                            setupSpinners2();

                        } else {

                            Toast.makeText(AddNewRequirement.this, "" + response.body().message, Toast.LENGTH_SHORT).show();
                            networkLoader.dismissLoadingDialog();

                        }
                    } else {
                        Toast.makeText(AddNewRequirement.this, "Server Error" + response.raw(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onResponse: error body is here response is not successful " + response.raw());
                        networkLoader.dismissLoadingDialog();
                        // educationlist.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<appOptionsResponse> call, Throwable t) {
                    Toast.makeText(AddNewRequirement.this, "Server Error", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    networkLoader.dismissLoadingDialog();
                }
            });
        } catch (Exception exception) {
            Toast.makeText(AddNewRequirement.this, "Some thing wrong", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private List<String> getSubjectNames2() {
        List<String> names = new ArrayList<>();
        for (appOptionsResponse.Subject subject : subjectsListActual) {
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

    private void setupSpinners2() {
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(AddNewRequirement.this, android.R.layout.simple_spinner_dropdown_item, getSubjectNames2());
        subjectSpinner.setAdapter(subjectAdapter);

        ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(AddNewRequirement.this, android.R.layout.simple_spinner_dropdown_item, getLevelNames());
        fromLevelSpinner.setAdapter(levelAdapter);

    }

}