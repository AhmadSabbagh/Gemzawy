package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.am.gemzawy.fifa.Api.Webservice;
import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;

public class Fifa_Competitions extends AppCompatActivity {
public static RecyclerView listView;
public static int [] comp_id;
public static int [] comp_price;
    public static ProgressBar fifaBar;
    ImageView fifa_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifa__competitions);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
        listView = (RecyclerView) findViewById(R.id.free_comp_list);
        fifaBar=findViewById(R.id.fifaProgressBar);
        fifa_header=findViewById(R.id.fifa_header_id);
        AttolSharedPreference attolSharedPreference= new AttolSharedPreference(this);
       int av= Integer.parseInt(attolSharedPreference.getKey("android_ver"));
        if(av==1)
        {
            fifa_header.setBackgroundResource(R.drawable.logo);

        }
        else
        {
            fifa_header.setBackgroundResource(R.drawable.fifa_header_competion_list);

        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        listView.setLayoutManager(layoutManager);


        final Webservice webservice = new Webservice();
        webservice.Get_Competitions(Fifa_Competitions.this);
        fifaBar.setVisibility(View.VISIBLE);
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Fifa_Competitions.this,Free_Competition_Details.class);
                intent.putExtra("comp_id",comp_id[position]);
                intent.putExtra("comp_price",comp_price[position]);

                startActivity(intent);
            }
        });

         */

    }
}
