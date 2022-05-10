package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.am.gemzawy.fifa.Api.Webservice;

public class ChangePass extends AppCompatActivity {
EditText oldp,newp,newp2;
public static ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        oldp=findViewById(R.id.oldpassID);
        newp=findViewById(R.id.newPassID);
        newp2=findViewById(R.id.newPassID2);
        progressBar=findViewById(R.id.changeBar);
        progressBar.setVisibility(View.INVISIBLE);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
    }

    public void confirm(View view) {
        if(oldp.getText().toString().equals("")||
                newp.getText().toString().equals("")||
                newp2.getText().toString().equals(""))
        {
            Toast.makeText(ChangePass.this,"Please Enter all data",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(newp.getText().toString().equals(newp2.getText().toString())) {
                Webservice webservice = new Webservice();
                webservice.ChangePass(ChangePass.this,oldp.getText().toString(), newp.getText().toString());
                progressBar.setVisibility(View.VISIBLE);
            }
            else
            {
                Toast.makeText(ChangePass.this,"Passwords don't match",Toast.LENGTH_LONG).show();

            }
        }
    }
}
