package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.am.gemzawy.fifa.Api.Webservice;
import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Payout extends AppCompatActivity {
    public static TextView coins_nu;
    public static int coins_number;
    EditText wanted_et;
    RadioGroup radioGroup;
    public static RadioButton paypal,vodafond;
    public static CardView recieve_card;
    String user_id;
    String type ="";
   public static String payment_info="";
    int number_of_wanted_coins;
    public static ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payout);
        coins_nu=findViewById(R.id.coins_number_id);
        wanted_et=findViewById(R.id.wanted_coins_number_id);
        radioGroup=findViewById(R.id.radio_groub_id);
        paypal=findViewById(R.id.radioButton_paypal);
        vodafond=findViewById(R.id.radioButton_voda);
        loading=findViewById(R.id.paypiy_progress);
        recieve_card=findViewById(R.id.cart_recieve_id);
        recieve_card.setVisibility(View.INVISIBLE);
        AttolSharedPreference attolSharedPreference=new AttolSharedPreference(Payout.this);
         user_id=attolSharedPreference.getKey("id");
        Webservice webservice= new Webservice();
        webservice.getUserCoins_Payout_page(Payout.this,user_id);
        addListenerOnButton();
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
    }

    public void check(View view) {
        if(!wanted_et.getText().toString().equals(""))
        {
            number_of_wanted_coins= Integer.parseInt(wanted_et.getText().toString());
            if(number_of_wanted_coins>coins_number)
            {
                Toast.makeText(Payout.this,"You don't have enough Coins",Toast.LENGTH_LONG).show();
            }
            else
            {
                if(number_of_wanted_coins <50)
                {
                    Toast.makeText(Payout.this,"50 coins at least",Toast.LENGTH_LONG).show();

                }
                else
                {
                    Webservice webservice= new Webservice();
                    webservice.getPaymentInfo_payout(Payout.this);
                }
            }
        }
        else
        {
            Toast.makeText(Payout.this,"Please Enter Coins Number",Toast.LENGTH_LONG).show();

        }

    }

    public void send(View view) {
        if(type.equals("")) {
            Toast.makeText(Payout.this,"Choose way to pay",Toast.LENGTH_LONG).show();
        }
        else
        {

            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

            Webservice webservice = new Webservice();
            webservice.SendPaymentRequest(Payout.this, type, payment_info, number_of_wanted_coins, currentDate);
        }
    }
    public void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.radio_groub_id);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButton_paypal:
                        // do operations specific to this selection
                        type="Paypal";
                        break;
                    case R.id.radioButton_voda:
                        // do operations specific to this selection
                        type="VodafondCash";
                        break;

                }
            }
        });

    }

    public void get_status(View view) {
        startActivity(new Intent(Payout.this, Payment_list_status.class));

    }
}
