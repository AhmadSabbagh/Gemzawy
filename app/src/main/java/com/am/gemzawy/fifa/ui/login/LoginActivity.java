package com.am.gemzawy.fifa.ui.login;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.am.gemzawy.fifa.Api.Webservice;
import com.am.gemzawy.fifa.ForgetPassword;
import com.am.gemzawy.fifa.R;
import com.am.gemzawy.fifa.Register_Page;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
   public  static ProgressBar loadingProgressBar;
   ImageView login_header;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
        final EditText usernameEditText = findViewById(R.id.usernameR);
        final EditText passwordEditText = findViewById(R.id.passwordR);
        login_header=findViewById(R.id.login_header_id);
         Button loginButton = findViewById(R.id.login);
        loginButton.setEnabled(true);
          loadingProgressBar = findViewById(R.id.loading2);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");
        String getCurrentDateTime = sdf.format(c.getTime());
        String getMyTime="08/10/2020 09:45 PM ";
       // Log.d("getCurrentDateTime",getCurrentDateTime);
// getCurrentDateTime: 05/23/2016 18:49 PM

        if (getCurrentDateTime.compareTo(getMyTime) > 0)
        {
//@drawable/
            login_header.setBackgroundResource(R.drawable.login_header);
        }
        else
        {
          //  Log.d("Return","getMyTime older than getCurrentDateTime ");
            login_header.setBackgroundResource(R.drawable.logo_new2_amro);

        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Webservice webservice = new Webservice();
                webservice.Login(LoginActivity.this,usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                loadingProgressBar.setVisibility(View.VISIBLE);


            }
        });
    }





    public void Register(View view) {
        startActivity(new Intent(LoginActivity.this,Register_Page.class));
        //finish();
    }

    public void forget(View view) {
        startActivity(new Intent(LoginActivity.this, ForgetPassword.class));

    }
}
