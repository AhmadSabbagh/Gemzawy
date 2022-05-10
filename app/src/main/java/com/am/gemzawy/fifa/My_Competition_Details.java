package com.am.gemzawy.fifa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class My_Competition_Details extends AppCompatActivity {
    TextView comp_nameTV,comp_dateTV,comp_priceTV;
    String comp_name;
    String comp_date;
    String comp_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__competition__details);
        Intent intent=getIntent();
        comp_date=intent.getStringExtra("comp_date");
        comp_price=intent.getStringExtra("comp_price");
        comp_name=intent.getStringExtra("comp_name");

        comp_nameTV=findViewById(R.id.comp_name_id);
        comp_nameTV.setText(comp_name);

        comp_dateTV=findViewById(R.id.comp_date_id);
        comp_dateTV.setText(comp_date);

        comp_priceTV=findViewById(R.id.comp_price_id);
        comp_priceTV.setText(comp_price);

    }
}
