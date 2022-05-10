package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.am.gemzawy.fifa.Api.Webservice;
import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;

public class Add_FifaID extends AppCompatActivity {
    public static EditText playstationET;
    Button add_bu,editBU;
    public static ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__fifa_id);
        playstationET = findViewById(R.id.playstation_id);
        playstationET.setEnabled(false);
        add_bu=findViewById(R.id.add_id_bu);
        editBU=findViewById(R.id.edit_id_bu);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
        AttolSharedPreference attolSharedPreference=new AttolSharedPreference(this);
        String play_id=attolSharedPreference.getKey("playstation_id");
        if(play_id!=null)
        {
            playstationET.setText(play_id);
        }
        else
        {

        }
        editBU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_bu.setVisibility(View.VISIBLE);
                playstationET.setEnabled(true);
            }
        });

        loading=findViewById(R.id.loading2);
        loading.setVisibility(View.INVISIBLE);
        //  loading.setVisibility(View.INVISIBLE);
        GetPlaystationID();
        add_bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playstationET.getText().toString().equals("")) {
                    Toast.makeText(Add_FifaID.this,"You can't leave the ID empty ",Toast.LENGTH_SHORT).show();
                }
                else {
                    AddPlaystationID();
                }
            }
        });
    }
    private void GetPlaystationID() {
//        Webservice webservice = new Webservice();
//        webservice.Get_Playstation_Id(Add_FifaID.this);
//        loading.setVisibility(View.VISIBLE);


    }
    private void AddPlaystationID() {

        Webservice webservice = new Webservice();
        webservice.Add_Playstation_Id(Add_FifaID.this, playstationET.getText().toString());

        loading.setVisibility(View.VISIBLE);


    }
}
