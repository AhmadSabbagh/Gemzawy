package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;

import com.am.gemzawy.fifa.Api.Webservice;

public class Payment_list_status extends AppCompatActivity {
public static ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list_status);
        listView=findViewById(R.id.list_payment);
        Webservice webservice= new Webservice();
        webservice.getPayment_status(Payment_list_status.this);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
    }
}
