package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.am.gemzawy.fifa.Api.Webservice;
import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;

public class MyCompetitions_List extends AppCompatActivity {
public static RecyclerView progressBar;
int type =0; //1 Fifa , 2 For Pubg
    public static int  [] pubg_comp_id;
    public static ProgressBar my_comp_bar;

    ImageView my_comp_header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_competitions__list);
        progressBar=(RecyclerView) findViewById(R.id.list_r);
        my_comp_bar=findViewById(R.id.my_comp_bar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        progressBar.setLayoutManager(layoutManager);

        ActionBar actionBar=getSupportActionBar();
        my_comp_header=findViewById(R.id.my_comp_list_header);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
        Intent intent=getIntent();
        type=intent.getIntExtra("type",0);
        if(type==1)
        {
          // my_comp_header.setBackgroundResource(R.drawable.fifa_header_competion_list);
            AttolSharedPreference attolSharedPreference= new AttolSharedPreference(this);
            int av= Integer.parseInt(attolSharedPreference.getKey("android_ver"));
            if(av==1)
            {
                my_comp_header.setBackgroundResource(R.drawable.logo);

            }
            else
            {
                my_comp_header.setBackgroundResource(R.drawable.fifa_header_competion_list);

            }
            Webservice webservice = new Webservice();
        webservice.Get_MY_Competitions(MyCompetitions_List.this);
            my_comp_bar.setVisibility(View.VISIBLE);
        }
        else if (type==2)
        {
          //  my_comp_header.setBackgroundResource(R.drawable.pubg_header_competion_list);
            AttolSharedPreference attolSharedPreference= new AttolSharedPreference(this);
            int av= Integer.parseInt(attolSharedPreference.getKey("android_ver"));
            if(av==1)
            {
                my_comp_header.setBackgroundResource(R.drawable.logo);

            }
            else
            {
                my_comp_header.setBackgroundResource(R.drawable.pubg_header_competion_list);

            }
            Webservice webservice = new Webservice();
            webservice.Get_MY_Pubg_Competitions(MyCompetitions_List.this);
            my_comp_bar.setVisibility(View.VISIBLE);

        }
     /*   progressBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(type==2)
                {
                    Intent intent = new Intent (MyCompetitions_List.this, MatchScreenFinish.class);
                    intent.putExtra("comp_id",pubg_comp_id[position]);
                    intent.putExtra("type",2);
                    startActivity(intent);
                }
            }
        });*/

    }
}
