package com.ash.teacheron;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ash.teacheron.commonComponents.NetworkLoader;

public class Notifications extends AppCompatActivity {

    NetworkLoader networkLoader;
    TextView noo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notifications);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        noo=findViewById(R.id.noo);
        networkLoader = new NetworkLoader();
        networkLoader.showLoadingDialog(Notifications.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                noo.setVisibility(View.VISIBLE);
               networkLoader.dismissLoadingDialog();
            }
        }, 2000);


    }
}