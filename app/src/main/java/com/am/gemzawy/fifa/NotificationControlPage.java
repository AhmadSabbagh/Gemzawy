package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;
import com.google.firebase.messaging.FirebaseMessaging;

public class NotificationControlPage extends AppCompatActivity {
    Switch fifa_noti_activate,pubg_noti_activate,public_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_control_page);
        fifa_noti_activate = (Switch) findViewById(R.id.active_Fifa_notification);
        pubg_noti_activate = (Switch) findViewById(R.id.active_Pubg_notification2);
        public_notification = (Switch) findViewById(R.id.active_Public_noti);
        AttolSharedPreference attolSharedPreference = new AttolSharedPreference(NotificationControlPage.this);
        String fifa_noti_status=attolSharedPreference.getKey("fifa_status");
        String pubg_noti_status=attolSharedPreference.getKey("pubg_status");
        String public_noti_status=attolSharedPreference.getKey("public_status");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
        if (public_noti_status != null) {
            if (public_noti_status.equals("yes"))//the alarmBroadCastReciever is ON
            {
                public_notification.setChecked(true);
            } else if (public_noti_status.equals("no"))//the alarmBroadCastReciever is ON
            {
                public_notification.setChecked(false);
            }
        }
        else {
            public_notification.setChecked(true);
        }
        if (pubg_noti_status != null) {
            if (pubg_noti_status.equals("yes"))//the alarmBroadCastReciever is ON
            {
                pubg_noti_activate.setChecked(true);
            } else if (pubg_noti_status.equals("no"))//the alarmBroadCastReciever is ON
            {
                pubg_noti_activate.setChecked(false);
            }
        }
        else {
            pubg_noti_activate.setChecked(true);
        }

        if (fifa_noti_status != null) {
            if (fifa_noti_status.equals("yes"))//the alarmBroadCastReciever is ON
            {
                fifa_noti_activate.setChecked(true);
            } else if (fifa_noti_status.equals("no"))//the alarmBroadCastReciever is ON
            {
                fifa_noti_activate.setChecked(false);
            }
        }
        else {
            fifa_noti_activate.setChecked(true);
        }
        fifa_noti_activate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    attolSharedPreference.setKey("fifa_status", "yes");
                    //MyAnimationImage.startAnimation(animation);
                    FirebaseMessaging.getInstance ( ).subscribeToTopic ("Fifa");



                } else {

                    attolSharedPreference.setKey("fifa_status", "no");
                    //   MyAnimationImage.startAnimation(animation);
                    FirebaseMessaging.getInstance ( ).unsubscribeFromTopic ("Fifa");



                }
            }
        });
        pubg_noti_activate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    attolSharedPreference.setKey("pubg_status", "yes");
                    //MyAnimationImage.startAnimation(animation);
                    FirebaseMessaging.getInstance ( ).subscribeToTopic ("pubg");



                } else {

                    attolSharedPreference.setKey("pubg_status", "no");
                    //   MyAnimationImage.startAnimation(animation);
                    FirebaseMessaging.getInstance ( ).unsubscribeFromTopic ("pubg");



                }
            }
        });
        public_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    attolSharedPreference.setKey("public_status", "yes");
                    //MyAnimationImage.startAnimation(animation);
                    FirebaseMessaging.getInstance ( ).subscribeToTopic ("public");



                } else {

                    attolSharedPreference.setKey("public_status", "no");
                    //   MyAnimationImage.startAnimation(animation);
                    FirebaseMessaging.getInstance ( ).unsubscribeFromTopic ("public");



                }
            }
        });
    }
}
