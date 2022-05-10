package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;
import com.google.firebase.messaging.FirebaseMessaging;

public class Welcome extends AppCompatActivity {
int count=0;
private Handler handler = new Handler();
    Animation animation;
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            /*for (String key : getIntent().getExtras().keySet()) {
                if(key.equals("comp_id"))
                {
                    Object comp_id=getIntent().getExtras().get(key);
                }
                Object value = getIntent().getExtras().get(key);
                Log.d("MainActivityTestPayload", "Key: " + key + " Value: " + value);
            }*/
            String title = getIntent().getExtras().getString("title");
            String body = getIntent().getExtras().getString("body");
            String comp_id = getIntent().getExtras().getString("comp_id");
            Log.d("MainActivityTestPayload", "Key: " + title);
            Log.d("MainActivityTestPayload", "Key: " + body);
            Log.d("MainActivityTestPayload", "Key: " + comp_id);
            if (comp_id != null) {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(comp_id + "_Fifa_comp");

            }
            if (title != null)
            {
                Intent intent = new Intent(getApplicationContext(), Notification_Page.class);
            intent.putExtra("msg", body);
            startActivity(intent);
            finish();
           }
            else
            {
                setContentView(R.layout.activity_welcome);
                final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar3);
                imageView = findViewById(R.id.imageView2);
                animation = AnimationUtils.loadAnimation(Welcome.this, R.animator.alpha_rotate_animation);
                AttolSharedPreference attolSharedPreference = new AttolSharedPreference(this);
                String id = attolSharedPreference.getKey("id");
                if (id == null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (count < 4) {
                                count++;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setProgress(count);
                                    }
                                });
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException i) {

                                }
                                if (count >= 2) {
                                    startActivity(new Intent(getApplicationContext(), Language_Chooser.class));
                                    finish();
                                }
                            }
                        }
                    }).start();
                } else if (id.equals("0")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (count < 5) {
                                count++;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setProgress(count);
                                    }
                                });
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException i) {

                                }
                                if (count >= 5) {
                                    startActivity(new Intent(getApplicationContext(), Language_Chooser.class));
                                    finish();
                                }
                            }
                        }
                    }).start();
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }

        } else {
            setContentView(R.layout.activity_welcome);
            final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar3);
            imageView = findViewById(R.id.imageView2);
            animation = AnimationUtils.loadAnimation(Welcome.this, R.animator.alpha_rotate_animation);
            AttolSharedPreference attolSharedPreference = new AttolSharedPreference(this);
            //imageView.startAnimation(animation);
            // attolSharedPreference.setKey("me","teacher");
            //  attolSharedPreference.setKey("id","0");
            ActionBar actionBar = getSupportActionBar();
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
            String id = attolSharedPreference.getKey("id");


            if (id == null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (count < 4) {
                            count++;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(count);
                                }
                            });
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException i) {

                            }
                            if (count >= 4) {
                                startActivity(new Intent(getApplicationContext(), Language_Chooser.class));
                                finish();
                            }
                        }
                    }
                }).start();
            } else if (id.equals("0")) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (count < 5) {
                            count++;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(count);
                                }
                            });
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException i) {

                            }
                            if (count >= 5) {
                                startActivity(new Intent(getApplicationContext(), Language_Chooser.class));
                                finish();
                            }
                        }
                    }
                }).start();
            } else {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

        }
    }
}
