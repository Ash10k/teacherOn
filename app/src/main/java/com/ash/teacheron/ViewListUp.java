package com.ash.teacheron;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewListUp extends AppCompatActivity {
    Intent intent;
    CardView sendmsg,openRecom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_up);



        openRecom=findViewById(R.id.openRecom);
        sendmsg=findViewById(R.id.sendmsg);
        openRecom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int re=getIntent().getIntExtra("reqID",0);
                intent = new Intent(ViewListUp.this,  RecommndedAct.class);
                intent.putExtra("reqID",re);
                startActivity(intent);


            }
        });
    }
}