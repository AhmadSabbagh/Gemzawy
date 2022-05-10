package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;

import java.util.Locale;

public class Language_Chooser extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton arabic;
    private RadioButton english;
    private Button Go;
    String language;
    ImageView ar,en;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language__chooser);
    //    addListenerOnButton();
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
     //   Go=findViewById(R.id.Go_Bu);
        ar=findViewById(R.id.imageButtonAR);
        en=findViewById(R.id.imageButtonEN);
        ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale locale = new Locale("ar");
                Locale.setDefault(locale);
                AttolSharedPreference attolSharedPreference=new AttolSharedPreference(Language_Chooser.this);
                attolSharedPreference.setKey("language","ar");
                // Create a new configuration object
                Configuration config = new Configuration();
                // Set the locale of the new configuration
                config.locale = locale;
                // Update the configuration of the Accplication context
                getResources().updateConfiguration(
                        config,
                        getResources().getDisplayMetrics()
                );
                startActivity(new Intent(Language_Chooser.this,Privacy_Policy.class));
            }
        });
        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale locale = new Locale("en");
                AttolSharedPreference attolSharedPreference=new AttolSharedPreference(Language_Chooser.this);
                attolSharedPreference.setKey("language","en");
                Locale.setDefault(locale);
                // Create a new configuration object
                Configuration config = new Configuration();
                // Set the locale of the new configuration
                config.locale = locale;
                // Update the configuration of the Accplication context
                getResources().updateConfiguration(
                        config,
                        getResources().getDisplayMetrics()
                );
                startActivity(new Intent(Language_Chooser.this,Privacy_Policy.class));

            }
        });




    }
//    public void addListenerOnButton() {
//
//        radioGroup = (RadioGroup) findViewById(R.id.radio_choser);
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//        {
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch(checkedId){
//                    case R.id.arabic:
//                        // do operations specific to this selection
//                        language="A";
//                        break;
//                    case R.id.english:
//                        // do operations specific to this selection
//                        language="E";
//                        break;
//
//                }
//            }
//        });
//
//    }
}
