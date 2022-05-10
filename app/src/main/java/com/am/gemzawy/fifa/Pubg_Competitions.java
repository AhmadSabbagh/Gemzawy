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

public class Pubg_Competitions extends AppCompatActivity {
    public static RecyclerView listView;
    public static int [] comp_id;
    public static int [] comp_price;
    public static ProgressBar pubg_bar;
    ImageView pubg_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pubg__competitions);
        listView=findViewById(R.id.free_comp_list);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
        pubg_header=findViewById(R.id.pubg_header_id);
        AttolSharedPreference attolSharedPreference= new AttolSharedPreference(this);
       int av= Integer.parseInt(attolSharedPreference.getKey("android_ver"));
        if(av==1)
        {
            pubg_header.setBackgroundResource(R.drawable.logo);

        }
        else
        {
            pubg_header.setBackgroundResource(R.drawable.pubg_header_competion_list);

        }
       pubg_bar=findViewById(R.id.pubgPrgoressBar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.free_comp_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        final Webservice webservice = new Webservice();
        webservice.Get_Pubg_Competitions(Pubg_Competitions.this);
        pubg_bar.setVisibility(View.VISIBLE);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(Pubg_Competitions.this,Pubg_Free_Competition_Details.class);
//                intent.putExtra("comp_id",comp_id[position]);
//                intent.putExtra("comp_price",comp_price[position]);
//
//                startActivity(intent);
//            }
//        });
    }
}
