package com.am.gemzawy.fifa.Payments;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.am.gemzawy.fifa.Api.Webservice;
import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;
import com.am.gemzawy.fifa.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class StripePayments extends AppCompatActivity {
    private String paymentIntentClientSecret = "";
    //private static final String BACKEND_URL = "http://fifaapp-001-site1.ftempurl.com/webservice/api.asmx/";
    private Stripe stripe;
  Button checkout;
  int product_id=0;
  int coins_number=0;
  public String game="";
    int type_of_payment=0; // 1 for coins - 2 for competitions
   public static ProgressBar payment_bar;
    CardInputWidget cardInputWidget;
    public int current_coins=0;
    double amount=1.00;
    double amount2=1.00;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_payments);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
        checkout=findViewById(R.id.payButton);
        checkout.setEnabled(false);
         cardInputWidget = findViewById(R.id.cardInputWidget);
        // cardInputWidget.setVisibility(View.INVISIBLE);
        Intent intent1= getIntent();
        product_id=intent1.getIntExtra("id",0);
        coins_number=intent1.getIntExtra("number",0);
        type_of_payment=intent1.getIntExtra("type",0);
        game=intent1.getStringExtra("game");
        //  Toast.makeText(BrainTreePayment.this, "your game is :"+game, Toast.LENGTH_SHORT).show();
        amount=Double.parseDouble(String.valueOf(intent1.getIntExtra("price",0)));//subscribe
        amount2=intent1.getDoubleExtra("price",0); //buy coins
        payment_bar=findViewById(R.id.paymentBar);
         payment_bar.setVisibility(View.INVISIBLE);
        AttolSharedPreference attolSharedPreference=new AttolSharedPreference(StripePayments.this);
        String user_id=attolSharedPreference.getKey("id");
        getUserCoins(StripePayments.this,user_id);
        payment_bar.setVisibility(View.VISIBLE);

        PaymentConfiguration.init(
                getApplicationContext(),
                getResources().getString(R.string.p_k)
        );


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // payment_bar.setVisibility(View.VISIBLE);
                PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
                if (true) {
                    if (type_of_payment == 2) {
                        if (current_coins == 0 ) {
                            if (params != null) {
                                //cardInputWidget.setVisibility(View.INVISIBLE);
                                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                                        .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                                stripe.confirmPayment(StripePayments.this, confirmParams);
                               // payment_bar.setVisibility(View.VISIBLE);
                            }
                            // Toast.makeText(BrainTreePayment.this,"Please check your Coins or the internet and try later",Toast.LENGTH_LONG).show();
                        }
                        else if ( current_coins<amount)
                        {
                            // Toast.makeText(BrainTreePayment.this,"Not Enough Coins",Toast.LENGTH_LONG).show();
                            if (params != null) {
                                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                                        .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                                stripe.confirmPayment(StripePayments.this, confirmParams);
                                payment_bar.setVisibility(View.VISIBLE);
                            }

                        }
                        else {
                            android.app.AlertDialog.Builder a_builder = new android.app.AlertDialog.Builder(StripePayments.this);
                            a_builder.setMessage(getResources().getString(R.string.payment_method))
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //  finish();
                                            //  Toast.makeText(BrainTreePayment.this,"نتمنى رؤيتك في التطبيق مرة اخرى",Toast.LENGTH_SHORT).show();

                                            Webservice webservice = new Webservice();
                                            webservice.PayWithCoins(StripePayments.this, (int) amount, product_id,game);
                                            payment_bar.setVisibility(View.VISIBLE);
                                        }

                                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (params != null) {

                                        ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                                                .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                                        stripe.confirmPayment(StripePayments.this, confirmParams);
                                        payment_bar.setVisibility(View.VISIBLE);
                                    }

                                }
                            });

                            android.app.AlertDialog alert = a_builder.create();
                            alert.setTitle("How do you prefer to pay");
                            alert.show();
                        }
                    } else if (type_of_payment == 1){
                        if (params != null) {

                            ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                                    .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                            stripe.confirmPayment(StripePayments.this, confirmParams);
                            payment_bar.setVisibility(View.VISIBLE);
                        }
                    }

                }
            }
        });



    }



    private void displayAlert(@NonNull String title,
                              @Nullable String message,
                              boolean restartDemo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        if (restartDemo) {
            builder.setPositiveButton("Restart demo",
                    (DialogInterface dialog, int index) -> {
                        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
                        cardInputWidget.clear();
                        //startCheckout();
                    });
        } else {
            builder.setPositiveButton("Ok", null);
        }
        builder.create().show();
    }

    public void Get_Client_Secret() {
       // payment_bar.setVisibility(View.VISIBLE);


        final int id;

        String requestUrl = getString(R.string.Server_ip) + "Get_Stripe";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("CI"))
                    {
                        try {
                            Toast.makeText(StripePayments.this, "Ready", Toast.LENGTH_LONG).show();
                            checkout.setEnabled(true);
                            payment_bar.setVisibility(View.GONE);

                            JSONObject jo = new JSONObject(response);
                            String pk="";
                            paymentIntentClientSecret = jo.getString("CI");
                            pk=jo.getString("publish_key");
                            stripe = new Stripe(
                                    getApplicationContext(),
                                    Objects.requireNonNull(pk)
                            );
                        } catch (JSONException e) {
                            payment_bar.setVisibility(View.GONE);

                            e.printStackTrace();
                        }
                }

                } else {
                    Toast.makeText(StripePayments.this, "Please Wait", Toast.LENGTH_LONG).show();
                    finish();
                    payment_bar.setVisibility(View.GONE);



                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result", "Error" + error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                Toast.makeText(StripePayments.this, "Please Wait we are preparing your payment", Toast.LENGTH_SHORT).show();
               Get_Client_Secret();
                payment_bar.setVisibility(View.GONE);

               // finish();
                //startActivity(new Intent(StripePayments.this,StripePayments.class));
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                    if(type_of_payment==2) {
                        //add the user to competition
                        double newamount = amount * 0.04;
                        params.put("amount", String.valueOf(newamount));
                    }
                    else if(type_of_payment==1)
                    {
                        params.put("amount", String.valueOf(amount2));

                    }

                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(StripePayments.this).add(stringRequest);

    }
/*
    public void Get_Client_Secret2() {
        payment_bar.setVisibility(View.VISIBLE);


        final int id;

        String requestUrl = getString(R.string.Server_ip) + "Get_Stripe_comp";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("CI"))
                    {
                        try {
                            Toast.makeText(StripePayments.this, "التطبيق جاهز للدفع", Toast.LENGTH_LONG).show();
                            checkout.setEnabled(true);
                            payment_bar.setVisibility(View.GONE);

                            JSONObject jo = new JSONObject(response);
                            String pk="";
                            paymentIntentClientSecret = jo.getString("CI");
                            pk=jo.getString("publish_key");
                            stripe = new Stripe(
                                    getApplicationContext(),
                                    Objects.requireNonNull(pk)
                            );
                        } catch (JSONException e) {
                            payment_bar.setVisibility(View.GONE);

                            e.printStackTrace();
                        }
                    }

                } else {
                    Toast.makeText(StripePayments.this, "تعذر الدفع", Toast.LENGTH_LONG).show();
                    finish();
                    payment_bar.setVisibility(View.GONE);



                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result", "Error" + error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                Toast.makeText(StripePayments.this, "حدث خطاالرجاء الانتظار", Toast.LENGTH_LONG).show();
                // Get_Client_Secret();
                payment_bar.setVisibility(View.GONE);
                Get_Client_Secret2();
               // finish();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(product_id));

                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(StripePayments.this).add(stringRequest);

    }
*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(StripePayments.this));
    }




    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<StripePayments> activityRef;

        PaymentResultCallback(@NonNull StripePayments activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final StripePayments activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
               Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                activity.displayAlert(
//                        "Payment completed",
//                        gson.toJson(paymentIntent.getPaymentMethodTypes()),
//                        true
//                );
//user_id=string&client_secret=string&payment_id=string&payment_method=string&status=string&payment_amount=string&product=string
             /*   if(activity.type_of_payment==1) {
                    Webservice webservice = new Webservice();
                    webservice.Add_coins(activity, activity.coins_number,paymentIntent.getClientSecret(),paymentIntent.getId(),
                            String.valueOf(paymentIntent.getStatus()),paymentIntent.getAmount(),paymentIntent.getPaymentMethodTypes().get(0));
                    payment_bar.setVisibility(View.VISIBLE);
                }
                else if(activity.type_of_payment==2)
                {
                   //add the user to competition
                    Webservice webservice = new Webservice();
                    webservice.SubscribeToPaidComp(activity,activity.product_id,paymentIntent.getClientSecret(),paymentIntent.getId(),
                            String.valueOf(paymentIntent.getStatus()),paymentIntent.getAmount(),paymentIntent.getPaymentMethodTypes().get(0));
                    payment_bar.setVisibility(View.VISIBLE);
                }*/
                if(activity.type_of_payment==1) {
                    // Toast.makeText(BrainTreePayment.this, "Coins_number is :"+coins_number, Toast.LENGTH_SHORT).show();
                    Webservice webservice = new Webservice();
                    webservice.Add_coins(activity, activity.coins_number,
                            ""+new Date().toString(),paymentIntent.getId(),
                            "successfull",  activity.amount2,
                            "Stripe");
                    payment_bar.setVisibility(View.VISIBLE);
                }
                else if(activity.type_of_payment==2)
                {
                    //add the user to competition
                    double newamount=activity.amount*0.04;
                    Webservice webservice = new Webservice();
                    if(activity.game.equals("fifa")) {
                        webservice.SubscribeToPaidComp(activity, activity.product_id,
                                "" + new Date(), paymentIntent.getId(),
                                "successfull", newamount,
                                "Stripe");
                        payment_bar.setVisibility(View.VISIBLE);
                    }
                    else if (activity.game.equals("pubg"))
                    {
                        webservice.SubscribeToPaidPubgComp(activity, activity.product_id, "" + new Date(), paymentIntent.getId(),
                                "successfull", newamount,
                                "Stripe");
                        payment_bar.setVisibility(View.VISIBLE);
                    }
                }

            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "Payment failed",
                        "eror",
                        false
                );
                payment_bar.setVisibility(View.INVISIBLE);

            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final StripePayments activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString(), false);
            payment_bar.setVisibility(View.INVISIBLE);

        }

    }
    /*
    public void SendPayment() {
        payment_bar.setVisibility(View.VISIBLE);
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
                                webservice.Add_coins(StripePayments.this, coins_number,
                                        ""+new Date().toString(),id,
                                        "successfull",  amount2,
                                        "BrainTree");
                                payment_bar.setVisibility(View.VISIBLE);
                            }
                            else if(type_of_payment==2)
                            {
                                //add the user to competition
                                double newamount=amount*0.04;
                                Webservice webservice = new Webservice();
                                if(game.equals("fifa")) {
                                    webservice.SubscribeToPaidComp(StripePayments.this, product_id,
                                            "" + new Date(), id,
                                            "successfull", newamount,
                                            "BrainTree");
                                    payment_bar.setVisibility(View.VISIBLE);
                                }
                                else if (game.equals("pubg"))
                                {
                                    webservice.SubscribeToPaidPubgComp(StripePayments.this, product_id,
                                            "" + new Date(), id,
                                            "successfull", newamount,
                                            "BrainTree");
                                    payment_bar.setVisibility(View.VISIBLE);
                                }
                            }

                        } catch (JSONException e) {
                            // payment_bar.setVisibility(View.GONE);

                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Toast.makeText(StripePayments.this, response, Toast.LENGTH_LONG).show();
                        payment_bar.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(StripePayments.this, "Error Payment", Toast.LENGTH_LONG).show();
                    payment_bar.setVisibility(View.GONE);
                    finish();



                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result", "Error" + error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                Toast.makeText(StripePayments.this, "There is an Error with your payment please try again later", Toast.LENGTH_LONG).show();
                // Get_Client_Secret();
//                payment_bar.setVisibility(View.GONE);
//                Get_Client_Secret2();
                // finish();
                payment_bar.setVisibility(View.GONE);

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
*/
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
                            Get_Client_Secret();

                            // GalleryFragment.coins_nu.setText("رصيدك الحالي هو : "+coins_number);
                        } catch (JSONException e) {
                         //   GalleryFragment.progressBar.setVisibility(View.GONE);
                            payment_bar.setVisibility(View.GONE);

                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        //GalleryFragment.progressBar.setVisibility(View.GONE);

                        Toast.makeText(activity, "Please Try Again", Toast.LENGTH_LONG).show();
                        payment_bar.setVisibility(View.GONE);


                    }
                }
                else {
                    Toast.makeText(activity, "Please Try Again", Toast.LENGTH_LONG).show();
                    //  GalleryFragment.progressBar.setVisibility(View.GONE);
                    payment_bar.setVisibility(View.GONE);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                //  GalleryFragment.progressBar.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();
                payment_bar.setVisibility(View.GONE);

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id",user_id);

                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }


}
