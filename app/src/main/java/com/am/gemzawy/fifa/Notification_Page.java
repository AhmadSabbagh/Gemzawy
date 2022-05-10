package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Notification_Page extends AppCompatActivity {
TextView msg;
ImageView share_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification__page);
        msg = findViewById(R.id.message_id);
        share_img = findViewById(R.id.share_img_id);

        Intent intent = getIntent();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
        String message = intent.getStringExtra("msg");
        if (message != null){
            if (message.contains("sorry")) {

            }
        msg.setText(message);
    }
        share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String Subject = message;
                String Link = message;
                String Title = "Gamzawi";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, Subject);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, Link);
                startActivity(Intent.createChooser(sharingIntent, Title));
            }
        });
    }


}
