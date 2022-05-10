package com.am.gemzawy.fifa.Payments;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.am.gemzawy.fifa.Api.Webservice;
import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;
import com.am.gemzawy.fifa.R;
import com.am.gemzawy.fifa.ui.gallery.GalleryFragment;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PaymentMethodNonce;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BrainTreePayment extends AppCompatActivity {
String client_token;
double amount=1.00;
    double amount2=1.00;
    String strnonce="";
    int product_id=0;
    int coins_number=0;
    int type_of_payment=0; // 1 for coins - 2 for competitions
public static ProgressBar progressBar;
public int current_coins=0;
public String game="";
Button pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brain_tree_payment);
        Intent intent1= getIntent();
        product_id=intent1.getIntExtra("id",0);
        coins_number=intent1.getIntExtra("number",0);
        game=intent1.getStringExtra("game");
     //  Toast.makeText(BrainTreePayment.this, "your game is :"+game, Toast.LENGTH_SHORT).show();

        type_of_payment=intent1.getIntExtra("type",0);
        progressBar=findViewById(R.id.payemntbar);
        pay=findViewById(R.id.but);
        pay.setEnabled(false);
        amount=Double.parseDouble(String.valueOf(intent1.getIntExtra("price",0)));//subscribe
        amount2=intent1.getDoubleExtra("price",0); //buy coins

        GetToken();
        AttolSharedPreference attolSharedPreference=new AttolSharedPreference(BrainTreePayment.this);
        String user_id=attolSharedPreference.getKey("id");
        getUserCoins(BrainTreePayment.this,user_id);
        progressBar.setVisibility(View.VISIBLE);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
    }
    public void onBraintreeSubmit(View v) {
        if (type_of_payment == 2) {
            if (current_coins == 0 ) {
                DropInRequest dropInRequest = new DropInRequest()
                        .clientToken(client_token);
                startActivityForResult(dropInRequest.getIntent(BrainTreePayment.this), 1);
              // Toast.makeText(BrainTreePayment.this,"Please check your Coins or the internet and try later",Toast.LENGTH_LONG).show();
            }
            else if ( current_coins<amount)
            {
               // Toast.makeText(BrainTreePayment.this,"Not Enough Coins",Toast.LENGTH_LONG).show();

                DropInRequest dropInRequest = new DropInRequest()
                        .clientToken(client_token);
                startActivityForResult(dropInRequest.getIntent(BrainTreePayment.this), 1);

            }
            else {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
                a_builder.setMessage(getResources().getString(R.string.payment_method))
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //  finish();
                                //  Toast.makeText(BrainTreePayment.this,"نتمنى رؤيتك في التطبيق مرة اخرى",Toast.LENGTH_SHORT).show();

                                Webservice webservice = new Webservice();
                              //  webservice.PayWithCoins(BrainTreePayment.this, (int) amount, product_id);

                            }

                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DropInRequest dropInRequest = new DropInRequest()
                                .clientToken(client_token);
                        startActivityForResult(dropInRequest.getIntent(BrainTreePayment.this), 1);


                    }
                });

                AlertDialog alert = a_builder.create();
                alert.setTitle("How do you prefer to pay");
                alert.show();
            }
        } else if (type_of_payment == 1){
            DropInRequest dropInRequest = new DropInRequest()
                    .clientToken(client_token);
        startActivityForResult(dropInRequest.getIntent(this), 1);
    }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
               // Toast.makeText(BrainTreePayment.this,"I am in activity result now ",Toast.LENGTH_LONG).show();
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                 strnonce=nonce.getNonce();
             //   Toast.makeText(BrainTreePayment.this,"NONCE IS :  "+strnonce,Toast.LENGTH_SHORT).show();
                Log.d("nonce iss :",strnonce);

                SendPayment();
                // use the result to update your UI and send the payment method nonce to your server
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(BrainTreePayment.this,"Canceled",Toast.LENGTH_LONG).show();

                // the user canceled
            } else {
                // handle errors here, an exception may be available in
                Toast.makeText(BrainTreePayment.this,"ERRRROR",Toast.LENGTH_LONG).show();

                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
    }
    public void GetToken() {


        final int id;

        String requestUrl = getString(R.string.Server_ip) + "Get_Token";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("CI"))
                    {
                        try {
                            Toast.makeText(BrainTreePayment.this, "Ready", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            pay.setEnabled(true);

                            JSONObject jo = new JSONObject(response);
                            String pk="";
                            client_token = jo.getString("CI");
                            Log.d("Token_client_today",client_token);

                            pk="12";
                            pk="12";

                        } catch (JSONException e) {
                           // payment_bar.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);

                            e.printStackTrace();
                        }
                    }

                } else {
                    Toast.makeText(BrainTreePayment.this, "Try again Later or restart the App", Toast.LENGTH_LONG).show();
                    finish();

                    progressBar.setVisibility(View.GONE);


                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result", "Error" + error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                Toast.makeText(BrainTreePayment.this, "Please Wait we are preparing your payment", Toast.LENGTH_LONG).show();
                GetToken();
//                Get_Client_Secret2();
                // finish();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(BrainTreePayment.this).add(stringRequest);

    }
    public void SendPayment() {
        progressBar.setVisibility(View.VISIBLE);
        Log.d("checkout","i am here");

        final int id;

        String requestUrl = getString(R.string.Server_ip) + "Checkout";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ"))
                    {
                        try {

                            JSONObject jo = new JSONObject(response);
                            String id="";
                            id = jo.getString("tr_id");
                           // Toast.makeText(BrainTreePayment.this, "tans_id : "+id, Toast.LENGTH_SHORT).show();
                            if(type_of_payment==1) {
                               // Toast.makeText(BrainTreePayment.this, "Coins_number is :"+coins_number, Toast.LENGTH_SHORT).show();
                                Webservice webservice = new Webservice();
                                webservice.Add_coins(BrainTreePayment.this, coins_number,
                                        ""+new Date().toString(),id,
                                        "successfull",  amount2,
                                        "BrainTree");
                                progressBar.setVisibility(View.VISIBLE);
                            }
                            else if(type_of_payment==2)
                            {
                                //add the user to competition
                                double newamount=amount*0.04;
                                Webservice webservice = new Webservice();
                                if(game.equals("fifa")) {
                                    webservice.SubscribeToPaidComp(BrainTreePayment.this, product_id,
                                            "" + new Date(), id,
                                            "successfull", newamount,
                                            "BrainTree");
                                    progressBar.setVisibility(View.VISIBLE);
                                }
                                else if (game.equals("pubg"))
                                {
                                    webservice.SubscribeToPaidPubgComp(BrainTreePayment.this, product_id,
                                            "" + new Date(), id,
                                            "successfull", newamount,
                                            "BrainTree");
                                    progressBar.setVisibility(View.VISIBLE);
                                }
                            }

                        } catch (JSONException e) {
                            // payment_bar.setVisibility(View.GONE);

                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Toast.makeText(BrainTreePayment.this, response, Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(BrainTreePayment.this, "Error Payment", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    finish();



                }
                }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result", "Error" + error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                Toast.makeText(BrainTreePayment.this, "There is an Error with your payment please try again later", Toast.LENGTH_LONG).show();
                // Get_Client_Secret();
//                payment_bar.setVisibility(View.GONE);
//                Get_Client_Secret2();
                // finish();
                progressBar.setVisibility(View.GONE);

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.d("check_out_map","i am here");
                Map<String, String> params = new HashMap<>();
                 params.put("nonceFromTheClient",strnonce);
                if(type_of_payment==1) {
                    params.put("amount", String.valueOf(amount2));
                }
                else if (type_of_payment==2)
                {
                    double newamount=amount*0.04;
                    params.put("amount", String.valueOf(newamount));

                }
                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(BrainTreePayment.this).add(stringRequest);

    }
    public void getUserCoins(final Context activity, String user_id) {
        String requestUrl = activity.getString(R.string.Server_ip) + "Get_Player_Coins";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("coins")) {
                        try {

                            JSONObject jo = new JSONObject(response);
                            current_coins = jo.getInt("coins");
                           // GalleryFragment.coins_nu.setText("رصيدك الحالي هو : "+coins_number);
                        } catch (JSONException e) {
                            GalleryFragment.progressBar.setVisibility(View.GONE);

                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        //GalleryFragment.progressBar.setVisibility(View.GONE);

                        Toast.makeText(activity, "Please Try Again", Toast.LENGTH_LONG).show();


                    }
                }
                else {
                    Toast.makeText(activity, "Please Try Again", Toast.LENGTH_LONG).show();
                  //  GalleryFragment.progressBar.setVisibility(View.GONE);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
              //  GalleryFragment.progressBar.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_Id",user_id);

                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }



}
