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

public class ForgetPassword extends AppCompatActivity {
public static ProgressBar progressBar;
EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        progressBar=findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.INVISIBLE);
        email=findViewById(R.id.email_id);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
    }

    public void send(View view) {
        if(email.getText().toString().equals(""))
        {
            Toast.makeText(ForgetPassword.this,"Wrong Email ",Toast.LENGTH_LONG).show();
        }
        else
        {
            Webservice webservice = new Webservice() ;
            webservice.ForgetPass(ForgetPassword.this,email.getText().toString());
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
