package com.ash.teacheron;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ash.teacheron.commonComponents.NetworkLoader;
import com.bumptech.glide.Glide;

public class ViewListUp extends AppCompatActivity {
    Intent intent;
   // CardView sendmsg,openRecom;

    NetworkLoader networkLoader;
    TextView noo;
    CardView  onlineopen,homeopen;
    TextView  tv2,tv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_up);



        tv2= findViewById(R.id.txt2);
        tv3= findViewById(R.id.txt3);
        homeopen=findViewById(R.id.homeopen);
        onlineopen=findViewById(R.id.onlineopen);


        noo=findViewById(R.id.noo);
        networkLoader = new NetworkLoader();
        networkLoader.showLoadingDialog(ViewListUp.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                noo.setVisibility(View.VISIBLE);
                networkLoader.dismissLoadingDialog();
            }
        }, 2000);

        
        onlineopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onlineopen.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor( ViewListUp.this, R.color.blue)));
                homeopen.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor( ViewListUp.this, R.color.lightBlue)));

                tv2.setTextColor(ContextCompat.getColor( ViewListUp.this, R.color.white));
                tv3.setTextColor(ContextCompat.getColor( ViewListUp.this, R.color.greydark));
            }
        });

        homeopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                homeopen.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor( ViewListUp.this, R.color.blue)));
                onlineopen.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor( ViewListUp.this, R.color.lightBlue)));

                tv3.setTextColor(ContextCompat.getColor( ViewListUp.this, R.color.white));
                tv2.setTextColor(ContextCompat.getColor( ViewListUp.this, R.color.greydark));

                int re=getIntent().getIntExtra("reqID",0);
                int subid=getIntent().getIntExtra("subjectId",0);
                intent = new Intent(ViewListUp.this,  RecommndedAct.class);
                intent.putExtra("reqID",re);
                intent.putExtra("subjectId",subid);
                //Toast.makeText(ViewListUp.this, "putting:"+re, Toast.LENGTH_SHORT).show();
                noo.setVisibility(View.GONE);
                startActivity(intent);
            }
        });


    }
}