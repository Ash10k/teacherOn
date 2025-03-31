package com.ash.teacheron;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ash.teacheron.commonComponents.SharedPrefLocal;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.teaacherModel.registerResponse;
import com.ash.teacheron.viewmodel.registerVM.RegisterVModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

import com.ash.teacheron.commonComponents.NetworkLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class Register extends AppCompatActivity {

    Button login;
    TextInputEditText new_Email, new_Password,phone,fname,speciality,confm_Password,postalcode;
    String pass, mail, selectedGender ,dname,fnm,lnme,cnf,path,TAG="Register",pstcode;

    NetworkLoader networkLoader;
    CircleImageView profilePhoto;
    Uri fileUri;
    RequestBody requestBody = null;
    TextView date_choosen;
    AlertDialog mydialog;
    DatePickerDialog datePickerDialog;
    RelativeLayout requesttutor;
    LinearLayout alrdylogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
         ;
        login =  findViewById(R.id.registerbtn);
        new_Email =  findViewById(R.id.new_Email);
        new_Password =  findViewById(R.id.new_Password);
        phone= findViewById(R.id.phone);
        fname= findViewById(R.id.fname);
        date_choosen= findViewById(R.id.date_choosen);
        speciality= findViewById(R.id.speciality);
        postalcode= findViewById(R.id.postalcode);
        Spinner genderSpinner = findViewById(R.id.choosegender);
        requesttutor=findViewById(R.id.requesttutor);
        alrdylogin=findViewById(R.id.alrdylogin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.gender_options, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        genderSpinner.setAdapter(adapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle case where nothing is selected
            }
        });

        networkLoader = new NetworkLoader();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pass = new_Password.getText().toString();
                mail = new_Email.getText().toString();
                pstcode = postalcode.getText().toString();
                fnm = fname.getText().toString();
                lnme = date_choosen.getText().toString();


                if (pass != null && !pass.isEmpty() && mail != null && !mail.isEmpty() &&
                        fnm != null && !fnm.isEmpty() &&
                        lnme != null && !lnme.isEmpty()
                )
                {
                    RegisterVModel viewModel = new ViewModelProvider( Register.this).get(RegisterVModel.class);
                    networkLoader.showLoadingDialog(Register.this);
                    viewModel.startRegisterStep1( mail,   pass,   lnme,  fnm,  phone.getText().toString(), "India", "0",   "0",  "teacher",  pstcode,speciality.getText().toString(),   selectedGender).observe( Register.this, new Observer<registerResponse>()
                    {
                        @Override
                        public void onChanged(registerResponse reResponse) {
                            if (reResponse != null) {
                                Log.d("framg", "" + new Gson().toJson(reResponse));
                                SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(Register.this);
                                sharedPrefLocal.setUserId(reResponse.data.id);
                                //sharedPrefLocal.setSessionId("Bearer "+reResponse.session_token);


                                Intent intent=new Intent(Register.this,RegisterTeachStep2.class);
                                startActivity(intent);
                                Toast.makeText(Register.this, reResponse.message , Toast.LENGTH_SHORT).show();

                            } else {
                                // Handle null response here if needed
                                Toast.makeText(Register.this, "Failed to send OTP", Toast.LENGTH_SHORT).show();
                            }

                            networkLoader.dismissLoadingDialog();

                        }
                    });
                    viewModel.getErrorMessage().observe( Register.this, new Observer<ErrorData>() {
                        @Override
                        public void onChanged(ErrorData errorData) {
                            // Display error message
                            Toast.makeText(Register.this, "Failed to send OTP", Toast.LENGTH_SHORT).show();
                            Log.d("Error", errorData.getMessage());
                            networkLoader.dismissLoadingDialog();
                        }
                    });

                }
                else
                    Toast.makeText(Register.this, "Choose all the fields", Toast.LENGTH_SHORT).show();


            }
        });


        date_choosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDatePicker();
            }
        });

        requesttutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(Register.this, StudentRegisterStep1.class));
            }
        });

        alrdylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(Register.this,Login.class));
            }
        });
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri!=null)
        {
            if (uri.getScheme().equals("content")) {
                Cursor cursor = Register.this.getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            }
            if (result == null) {
                result = uri.getPath();
                int cut = result.lastIndexOf('/');
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
        }

        return result;
    }

    void createImagePart()
    {

        try {
            final InputStream inputStream = Register.this.getContentResolver().openInputStream(fileUri);
            final byte[] inputData = toByteArray(inputStream);
            if (inputStream != null) {
                requestBody = new RequestBody() {
                    @Override
                    public void writeTo(@NonNull BufferedSink bufferedSink) throws IOException {
                        bufferedSink.write(inputData);
                    }

                    @Override
                    public MediaType contentType() {
                        return MediaType.parse("image/*");
                    }

                    @Override
                    public long contentLength() {
                        return inputData.length;
                    }
                };
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("BdyIm", "in error");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode ==161 && resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            fileUri = uri;
            Log.d(TAG, "getImageUri: cop: " + uri);
            String filePath = getPathFromUri(Register.this, uri);
            if (filePath != null) {
                Toast.makeText(Register.this, "File Path: " + filePath, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Register.this, "Unable to get file path", Toast.LENGTH_SHORT).show();
            }
            path = filePath;

            Bitmap bitmap= BitmapFactory.decodeFile(path);
            profilePhoto.setImageBitmap(bitmap);
            createImagePart();

        }
    }
    private byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        return byteArrayOutputStream.toByteArray();
    }
    private String getPathFromUri(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(Register.this, uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            String[] split = documentId.split(":");
            String type = split[0];

            if ("primary".equalsIgnoreCase(type)) {
                filePath = Environment.getExternalStorageDirectory() + "/" + split[1];
            } else {
                // Handle non-primary volumes (e.g., SD cards)
                File[] externalStorageVolumes = ContextCompat.getExternalFilesDirs(Register.this, null);
                for (File file : externalStorageVolumes) {
                    if (file.getAbsolutePath().contains(type)) {
                        filePath = file.getAbsolutePath() + "/" + split[1];
                        break;
                    }
                }
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = Register.this.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        return filePath;
    }

    private void checkPermissionsAndOpenGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Scoped storage is enforced, no need for WRITE_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(Register.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            } else {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 16);
            }
        } else {
            // Before Android 10, we need both READ and WRITE permissions
            if (ContextCompat.checkSelfPermission(Register.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(Register.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            } else {
                requestPermissions( new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 16);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Toast.makeText(Register.this, "here ", Toast.LENGTH_SHORT).show();
    }


    private  void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;
                String date=i+"-"+i1+"-"+i2;
                date_choosen.setText(date);
            }
        };

        Calendar cal =Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);


        datePickerDialog=new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener,year,month,day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();

    }

}