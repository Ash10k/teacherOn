package com.ash.teacheron;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ash.teacheron.commonComponents.SharedPrefLocal;

import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 2000;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        SharedPrefLocal sharedPrefLocal = new SharedPrefLocal(MainActivity.this);
        userId=sharedPrefLocal.getUserId();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (userId<=0)
                {
                    startActivity(new Intent(MainActivity.this,Login.class));
                   // startActivity(new Intent(MainActivity.this,StudentRegisterStep2.class));
                   // finish();
                }
                else {
                    if (sharedPrefLocal.getUserType().equals("teacher"))
                        startActivity(new Intent(MainActivity.this, BottomNavTeacher.class));
                    else
                        startActivity(new Intent(MainActivity.this, BothBottomAndSideNavigation.class));
                   // finish();
                }


            }
        }, SPLASH_DELAY);

        FirebaseApp.initializeApp(this);

        // Optional: Enable custom logging and crash reporting
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        FirebaseCrashlytics.getInstance().log("Crashlytics initialized");

        FirebaseCrashlytics.getInstance().log("Testing crash log");
        //FirebaseCrashlytics.getInstance().recordException(new Exception("Test non-fatal exception"));
        // throw new RuntimeException("Test Crash — triggered manually");


    }
}