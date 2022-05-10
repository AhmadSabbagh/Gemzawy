package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.am.gemzawy.fifa.Api.Webservice;

public class Add_EmailID extends AppCompatActivity {
public static EditText email_payment,vodafon_cashEDT;
public static Button edit_email,confirm_email;
public static ProgressBar loading ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__email_id);
        email_payment=findViewById(R.id.email_payment);
        email_payment.setEnabled(false);
        loading=findViewById(R.id.email_bar);
        vodafon_cashEDT=findViewById(R.id.vodafone_cashET);
        vodafon_cashEDT.setEnabled(false);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
        confirm_email=findViewById(R.id.confirm_bu_email_id);
        confirm_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update info
                if(email_payment.getText().toString().equals("") && vodafon_cashEDT.getText().toString().equals(""))
                {
                    Toast.makeText(Add_EmailID.this,"Please Enter your payment Info ",Toast.LENGTH_LONG).show();
                }
                else {
                    Webservice webservice1 = new Webservice();
                    webservice1.Add_Payment_info(Add_EmailID.this, email_payment.getText().toString(),vodafon_cashEDT.getText().toString());
                    loading.setVisibility(View.VISIBLE);
                }
            }
        });

        edit_email=findViewById(R.id.edit_email_bu);
        edit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_email.setVisibility(View.VISIBLE);
                email_payment.setEnabled(true);
                vodafon_cashEDT.setEnabled(true);
            }
        });
        Webservice webservice1 = new Webservice();
        webservice1.getPaymentInfo(Add_EmailID.this);

    }
}
