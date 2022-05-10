package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;
import com.am.gemzawy.fifa.ui.login.LoginActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Privacy_Policy extends AppCompatActivity {
CheckBox agree_cb;
    String language;
TextView pri_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy__policy);
        agree_cb=findViewById(R.id.checkbox_id);
        pri_text=findViewById(R.id.privacy_text);
        AttolSharedPreference attolSharedPreference=new AttolSharedPreference(Privacy_Policy.this);
         language=attolSharedPreference.getKey("language");
        try {
            ReadTextFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
    }
    public void ReadTextFile() throws IOException {
        String string = "";
        StringBuilder stringBuilder = new StringBuilder();
        InputStream is;
        if(language.equals("ar")) {
             is = this.getResources().openRawResource(R.raw.privacy);
        }
        else
        {
             is = this.getResources().openRawResource(R.raw.privacy2);

        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                if ((string = reader.readLine()) == null) break;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            stringBuilder.append(string).append("\n");
            pri_text.setText(stringBuilder);
        }
        is.close();
//        Toast.makeText(getBaseContext(), stringBuilder.toString(),
//                Toast.LENGTH_LONG).show();
    }
    public void continue_click(View view) {
        if(agree_cb.isChecked())
        {
               startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        else
        {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setMessage("Please accept the privacy policy or press i don't accept")
                    .setCancelable(false)
                    .setPositiveButton("I don't accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            Toast.makeText(Privacy_Policy.this,"Sorry, we hope see you again soon",Toast.LENGTH_SHORT).show();
                        }

                    }).setNegativeButton("Go back to privacy policy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alert = a_builder.create();
            alert.setTitle("Error :( ");
            alert.show();
        }
    }
}
