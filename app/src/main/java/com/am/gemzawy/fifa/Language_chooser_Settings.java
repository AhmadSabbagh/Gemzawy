package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;

import java.util.Locale;

public class Language_chooser_Settings extends AppCompatActivity {
    ImageView ar,en;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_chooser__settings);
        ar=findViewById(R.id.imageButtonAR);
        en=findViewById(R.id.imageButtonEN);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
        ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Locale locale = new Locale("ar");
//                Locale.setDefault(locale);
                AttolSharedPreference attolSharedPreference=new AttolSharedPreference(Language_chooser_Settings.this);
                attolSharedPreference.setKey("language","ar");
                Resources res = Language_chooser_Settings.this.getResources();
// Change locale settings in the app.
                DisplayMetrics dm = res.getDisplayMetrics();
                android.content.res.Configuration conf = res.getConfiguration();
                conf.setLocale(new Locale("ar")); // API 17+ only.
// Use conf.locale = new Locale(...) if targeting lower versions
                res.updateConfiguration(conf, dm);
                 finish();
                Toast.makeText(Language_chooser_Settings.this,"الرجاء اعادة تشغيل التطبيق",Toast.LENGTH_LONG).show();
            }
        });
        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale locale = new Locale("en");
                AttolSharedPreference attolSharedPreference=new AttolSharedPreference(Language_chooser_Settings.this);
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
              //  startActivity(new Intent(Language_chooser_Settings.this,Privacy_Policy.class));
                    finish();
                Toast.makeText(Language_chooser_Settings.this,"Please Restart the application",Toast.LENGTH_LONG).show();

            }
        });
    }


}
