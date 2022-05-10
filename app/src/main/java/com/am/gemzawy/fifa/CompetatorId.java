package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.am.gemzawy.fifa.Api.Webservice;

public class CompetatorId extends AppCompatActivity {
int competator_id;
    public static TextView competator_idTV;
public static ProgressBar progressBar;
public static String phone,photo;
ImageView whats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competator_id);
        competator_idTV=findViewById(R.id.play_id);
        progressBar=findViewById(R.id.progressBar2);
        Intent intent=getIntent();
        competator_id=intent.getIntExtra("id",0);
        whats=findViewById(R.id.whatsAppId);

        Webservice webservice = new Webservice();
        webservice.Get__Competator_Playstation_Id(CompetatorId.this,competator_id);
        progressBar.setVisibility(View.VISIBLE);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));

        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = "https://api.whatsapp.com/send?phone="+phone;
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
                Toast.makeText(CompetatorId.this,"Soon",Toast.LENGTH_LONG).show();
            }
        });
    }



}
