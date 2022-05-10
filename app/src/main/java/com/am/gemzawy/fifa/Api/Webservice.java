package com.am.gemzawy.fifa.Api;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.am.gemzawy.fifa.Add_EmailID;
import com.am.gemzawy.fifa.Add_FifaID;
import com.am.gemzawy.fifa.Add_PubgID;
import com.am.gemzawy.fifa.ChangePass;
import com.am.gemzawy.fifa.CompetatorId;
import com.am.gemzawy.fifa.EditProfile;
import com.am.gemzawy.fifa.Fifa_Competitions;
import com.am.gemzawy.fifa.ForgetPassword;
import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;
import com.am.gemzawy.fifa.MainActivity;
import com.am.gemzawy.fifa.MatchScreenFinish;
import com.am.gemzawy.fifa.MyCompetitions_List;
import com.am.gemzawy.fifa.My_Competition_Details;
import com.am.gemzawy.fifa.Payment_list_status;
import com.am.gemzawy.fifa.Payments.BrainTreePayment;
import com.am.gemzawy.fifa.Payments.StripePayments;
import com.am.gemzawy.fifa.Payout;
import com.am.gemzawy.fifa.Pubg_Competitions;
import com.am.gemzawy.fifa.R;
import com.am.gemzawy.fifa.Register_Page;
import com.am.gemzawy.fifa.adapters.CompetitionRecycleAdapter;
import com.am.gemzawy.fifa.adapters.MyCompetitionAdapter;
import com.am.gemzawy.fifa.adapters.Payments_status_adapter;
import com.am.gemzawy.fifa.ui.gallery.GalleryFragment;
import com.am.gemzawy.fifa.ui.login.LoginActivity;
import com.am.gemzawy.fifa.ui.matches.MatchesFragment;
import com.am.gemzawy.fifa.ui.playstation_id.PlaystationId_Fragment;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Webservice {

    public void Register(final Context activity, final String username , final String phone , final String country , final String password , final String city , final String photo
            , final int coins , final int credit , final String playstation_id,String email ) {
        String requestUrl = activity.getString(R.string.Server_ip) + "Register_Players";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Volley Result", "" + response); //the response contains the result from the server, a json string or any other object returned by your server
                if (response != null) {
                    if (response.contains("succ")) {
                        try {
                            JSONObject jo=new JSONObject(response);
                            AttolSharedPreference attolSharedPreference = new AttolSharedPreference((Activity) activity);
                            String id = String.valueOf(jo.getInt("user_id"));
                            attolSharedPreference.setKey("id", id);
                            Toast.makeText(activity, " Thank you for registration", Toast.LENGTH_LONG).show();

                            FirebaseMessaging.getInstance ( ).subscribeToTopic (id+"_user");
                            String fifa_noti_status=attolSharedPreference.getKey("fifa_status");
                            String pubg_noti_status=attolSharedPreference.getKey("pubg_status");
                            String public_noti_status=attolSharedPreference.getKey("public_status");
                            if(public_noti_status==null)
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("public");
                                attolSharedPreference.setKey("public_status","yes");

                            }
                            else if(public_noti_status.equals("no"))
                            {

                            }
                            else if(public_noti_status.equals("yes"))
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("public");

                            }
                            else
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("public");

                            }
                            if(fifa_noti_status==null)
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("Fifa");
                                attolSharedPreference.setKey("fifa_status","yes");

                            }
                            else if(fifa_noti_status.equals("no"))
                            {

                            }
                            else if(fifa_noti_status.equals("yes"))
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("Fifa");

                            }
                            else
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("Fifa");

                            }
                            if(pubg_noti_status==null)
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("pubg");
                                attolSharedPreference.setKey("pubg_status","yes");

                            }
                            else if(pubg_noti_status.equals("no"))
                            {

                            }
                            else if(pubg_noti_status.equals("yes"))
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("pubg");

                            }
                            else
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("pubg");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Register_Page.loading.setVisibility(View.GONE);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                ((Activity)activity).finish();
                                ((Activity)activity).startActivity(new Intent(activity,MainActivity.class));
                            }
                        });



                    } else if (response.contains("Incorrect")) {
                        Toast.makeText(activity, "Data Incorrect", Toast.LENGTH_LONG).show();

                        Register_Page.loading.setVisibility(View.GONE);

                    }
                    else if (response.contains("exists")) {
                        Toast.makeText(activity, "The user is already exsist", Toast.LENGTH_LONG).show();
                        Register_Page.loading.setVisibility(View.GONE);


                    }
                }
                else {
                    Register_Page.loading.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging

                Toast.makeText(activity,"Check Internet Connection try again later please OR try to sign in",Toast.LENGTH_LONG).show();
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                Register_Page.loading.setVisibility(View.GONE);

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("phone",phone );
                params.put("country",country );
                params.put("password", password);
                params.put("city", city);
                params.put("coins", String.valueOf(coins));
                params.put("credit", String.valueOf(credit));
                params.put("playstation_id","NoIdYet");
                params.put("photo",photo );
                params.put("email",email );

                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void Login(final Context activity, final String phone , final String password) {
        String requestUrl = activity.getString(R.string.Server_ip) + "Login_Player";

       StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Volley Result", "" + response); //the response contains the result from the server, a json string or any other object returned by your server
                if (response != null) {
                    if (response.contains("succ")) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            AttolSharedPreference attolSharedPreference = new AttolSharedPreference((Activity) activity);
                            String id = String.valueOf(jo.getInt("id"));
                            attolSharedPreference.setKey("id", id);
                            String lan=attolSharedPreference.getKey("language");

                            LoginActivity.loadingProgressBar.setVisibility(View.GONE);
                            if(lan.equals("ar")) {
                                Toast.makeText(activity, "أهلا بك في تطبيق غيمزاوي", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(activity, "Welcome to Gemzawy", Toast.LENGTH_LONG).show();

                            }

                            FirebaseMessaging.getInstance ( ).subscribeToTopic (id+"_user");
                            String fifa_noti_status=attolSharedPreference.getKey("fifa_status");
                            String pubg_noti_status=attolSharedPreference.getKey("pubg_status");
                            String public_noti_status=attolSharedPreference.getKey("public_status");
                            if(public_noti_status==null)
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("public");
                                attolSharedPreference.setKey("public_status","yes");

                            }
                            else if(public_noti_status.equals("no"))
                            {

                            }
                            else if(public_noti_status.equals("yes"))
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("public");

                            }
                            else
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("public");

                            }
                            if(fifa_noti_status==null)
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("Fifa");
                                attolSharedPreference.setKey("fifa_status","yes");

                            }
                            else if(fifa_noti_status.equals("no"))
                            {

                            }
                            else if(fifa_noti_status.equals("yes"))
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("Fifa");

                            }
                            else
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("Fifa");

                            }
                            if(pubg_noti_status==null)
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("pubg");
                                attolSharedPreference.setKey("pubg_status","yes");

                            }
                            else if(pubg_noti_status.equals("no"))
                            {

                            }
                            else if(pubg_noti_status.equals("yes"))
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("pubg");

                            }
                            else
                            {
                                FirebaseMessaging.getInstance ( ).subscribeToTopic ("pubg");

                            }
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    ((Activity)activity).finish();
                                    ((Activity)activity).startActivity(new Intent(activity, MainActivity.class));
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else if (response.contains("correct")) {
                        AttolSharedPreference attolSharedPreference = new AttolSharedPreference((Activity) activity);

                        String lan=attolSharedPreference.getKey("language");

                        LoginActivity.loadingProgressBar.setVisibility(View.GONE);
                        if(lan.equals("ar")) {
                            Toast.makeText(activity, "Wrong email or password", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(activity, "Wrong Email Or Password", Toast.LENGTH_LONG).show();

                        }
                        LoginActivity.loadingProgressBar.setVisibility(View.GONE);


                    }
                    else if (response.contains("data")) {
                        Toast.makeText(activity, "Connection problem please try again later", Toast.LENGTH_LONG).show();
                        LoginActivity.loadingProgressBar.setVisibility(View.GONE);


                    }
                }
                else {
                    LoginActivity.loadingProgressBar.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                LoginActivity.loadingProgressBar.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", phone);

                params.put("password", password);

                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void getPaymentInfo(final Context activity) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "Get_Payment_info";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("payment_email")) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            String play_id = String.valueOf(jo.getString("payment_email"));
                            String phone=jo.getString("payment_phone");
                            Add_EmailID.email_payment.setText(play_id);
                            Add_EmailID.vodafon_cashEDT.setText(phone);
                            Add_EmailID.loading.setVisibility(View.GONE);
                            if(play_id.equals("") && phone.equals(""))
                            {
                                Add_EmailID.edit_email.setText(activity.getResources().getString(R.string.add_payment_metho));
                            }
                            else
                            {
                                Add_EmailID.edit_email.setText(activity.getResources().getString(R.string.edit));

                            }
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        Toast.makeText(activity, "Incorrect data ", Toast.LENGTH_LONG).show();
                        Add_EmailID.loading.setVisibility(View.GONE);


                    }
                }
                else {
                    Add_EmailID.loading.setVisibility(View.GONE);
                    Toast.makeText(activity, "Can't fetch your id please try again", Toast.LENGTH_LONG).show();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                Add_EmailID.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void getPaymentInfo_payout(final Context activity) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "Get_Payment_info";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("payment_email")) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            String play_id = String.valueOf(jo.getString("payment_email"));
                            String phone=jo.getString("payment_phone");
                            AttolSharedPreference attolSharedPreference = new AttolSharedPreference((Activity) activity);
                            String lan=attolSharedPreference.getKey("language");
                           if(play_id.equals("") && phone.equals(""))
                           {

                               if(lan.equals("ar")) {
                                   Toast.makeText(activity,"ليس لديك طريقة لاستلام الارباح من فضلك اذهاب الى الاعدادات واضاف معلومات استلام الارباح",Toast.LENGTH_LONG).show();
                               }
                               else
                               {
                                   Toast.makeText(activity,"You have to add payment information in your settings-payout page",Toast.LENGTH_LONG).show();

                               }
                           }
                           else
                           {
                               if(!play_id.equals(""))
                               {
                                   Payout.recieve_card.setVisibility(View.VISIBLE);
                                   Payout.paypal.setVisibility(View.VISIBLE);
                                   //Payout.vodafond.setVisibility(View.INVISIBLE);

                                   Payout.payment_info=play_id;
                               }
                               else
                               {
                                   Payout.paypal.setVisibility(View.INVISIBLE);

                               }
                               if(!phone.equals(""))
                               {
                                   Payout.recieve_card.setVisibility(View.VISIBLE);
                                   Payout.vodafond.setVisibility(View.VISIBLE);
                                   //Payout.paypal.setVisibility(View.INVISIBLE);
                                   Payout.payment_info=phone;


                               }
                               else
                               {
                                   Payout.vodafond.setVisibility(View.INVISIBLE);

                               }
                           }
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        Toast.makeText(activity, "Please try agin later", Toast.LENGTH_LONG).show();
                       // Add_EmailID.loading.setVisibility(View.GONE);


                    }
                }
                else {
                  //  Add_EmailID.loading.setVisibility(View.GONE);
                    Toast.makeText(activity, "can't fetch your ID please try agin later", Toast.LENGTH_LONG).show();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
               // Add_EmailID.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void ChangePass(final Context activity ,final String old,final String newp ) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "ChangePass";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        Toast.makeText(activity, "Successfully changed  ", Toast.LENGTH_LONG).show();
                        ChangePass.progressBar.setVisibility(View.GONE);
                        ((Activity)activity).finish();

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });



                    } else if (response.contains("incorrect")) {
                        Toast.makeText(activity, "Old password Incorrect", Toast.LENGTH_LONG).show();

                        ChangePass.progressBar.setVisibility(View.GONE);

                    }
                    else
                    {
                        Toast.makeText(activity, "Error try again", Toast.LENGTH_LONG).show();

                        ChangePass.progressBar.setVisibility(View.GONE);

                    }

                }
                else {
                    Toast.makeText(activity, "Try again later", Toast.LENGTH_LONG).show();

                    ChangePass.progressBar.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                ChangePass.progressBar.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("oldpass", old);
                params.put("newpass", newp);


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void Get_Playstation_Id(final Context activity) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "Get_Playstation_Id";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("id")) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            String play_id = jo.getString("playstation_id");

                                Add_FifaID.playstationET.setText(play_id);

                            Add_FifaID.loading.setVisibility(View.GONE);

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        Toast.makeText(activity, "Error Connection", Toast.LENGTH_LONG).show();
                        Add_FifaID.loading.setVisibility(View.GONE);


                    }
                }
                else {
                    Add_FifaID.loading.setVisibility(View.GONE);
                    Toast.makeText(activity, "Error Connection", Toast.LENGTH_LONG).show();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void Add_Playstation_Id(final Context activity ,final String playstation_id ) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "Add_Playstation_id";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        Toast.makeText(activity, "Added", Toast.LENGTH_LONG).show();
                        Add_FifaID.loading.setVisibility(View.GONE);
                        mySharedPreference.setKey("playstation_id",playstation_id);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });



                    } else if (response.contains("Incorrect")) {
                        Toast.makeText(activity, "Error Connection", Toast.LENGTH_LONG).show();

                        Add_FifaID.loading.setVisibility(View.GONE);

                    }
                    else
                    {
                        Add_FifaID.loading.setVisibility(View.GONE);

                    }

                }
                else {
                    Add_FifaID.loading.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                Add_FifaID.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("playstation_id", playstation_id);

                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void Add_Pubg_Id(final Context activity ,final String pubg_id,final String img ) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "Add_Pubg_id";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        Toast.makeText(activity, "Successfully added you ID  ", Toast.LENGTH_LONG).show();
                        mySharedPreference.setKey("pubg_id",pubg_id);
                        Add_PubgID.progressBar.setVisibility(View.GONE);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });



                    } else if (response.contains("IncorrectIncorrect")) {
                        Toast.makeText(activity, "Something wrong try again later", Toast.LENGTH_LONG).show();

                        Add_PubgID.progressBar.setVisibility(View.GONE);

                    }
                    else
                    {
                        Add_PubgID.progressBar.setVisibility(View.GONE);

                    }

                }
                else {
                    Add_PubgID.progressBar.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                Add_PubgID.progressBar.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("photo", img);

                params.put("pubg_id", pubg_id);

                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void Add_Payment_info(final Context activity ,final String email ,final String phone) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "Update_Payment_info";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        Toast.makeText(activity, "Successfully add the information ", Toast.LENGTH_LONG).show();
                        Add_EmailID.loading.setVisibility(View.GONE);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });



                    } else if (response.contains("Incorrect")) {
                        Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();

                        Add_EmailID.loading.setVisibility(View.GONE);

                    }

                }
                else {
                    Add_EmailID.loading.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                Add_EmailID.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("email", email);
                params.put("phone",phone);

                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void Add_profile_info(final Context activity ,final String username,final String phone , String img ,String email ) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "Update_User_Profile";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        Toast.makeText(activity, "Successfully add the information ", Toast.LENGTH_LONG).show();
                        EditProfile.progressBar.setVisibility(View.GONE);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });



                    } else if (response.contains("Incorrect")) {
                        Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();

                        EditProfile.progressBar.setVisibility(View.GONE);

                    }
                    else
                    {
                        EditProfile.progressBar.setVisibility(View.GONE);

                    }

                }
                else {
                    EditProfile.progressBar.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();
                EditProfile.progressBar.setVisibility(View.GONE);


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("username", username);
                params.put("phone", phone);
                params.put("email", email);
                params.put("img", img);



                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }

    /*public void Get_Coins(final Context activity) {
        String requestUrl = activity.getString(R.string.Server_ip) + "Get_Coins_To_Buy";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("coins_price")) {
                        try {
                            AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
                            final String id = mySharedPreference.getKey("id");
                            GalleryFragment.progressBar.setVisibility(View.GONE);
                            JSONArray JA = new JSONArray(response);
                             int [] coins_number= new int [JA.length()];
                           int []  coins_price =new int [JA.length()];
                            int []  coins_id =new int [JA.length()];

                            ArrayList<Integer> coins_numberArray = new ArrayList<Integer>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                coins_number[i] = (int ) JO.get("coins_number");
                                coins_price[i] = (int) JO.get("coins_price");
                                coins_id[i] = (int) JO.get("coins_id");


                                coins_numberArray.add(coins_number[i]);

                            }
                            GalleryFragment.coins_number=coins_number;
                            GalleryFragment.coins_price=coins_price;
                            GalleryFragment.coins_id=coins_id;

                            CoinsAdapter coinsAdapter= new CoinsAdapter(
                                    coins_numberArray,coins_price,coins_id,activity
                            );
                            getUserCoins(activity,id);
                            GalleryFragment.listView.setAdapter(coinsAdapter);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        } catch (JSONException e) {
                            GalleryFragment.progressBar.setVisibility(View.GONE);

                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        GalleryFragment.progressBar.setVisibility(View.GONE);

                        Toast.makeText(activity, "خطا في الاتصال حاول لاحقا", Toast.LENGTH_LONG).show();


                    }
                }
                else {
                    Toast.makeText(activity, "تعذر جلب ال معرف الخاص بك", Toast.LENGTH_LONG).show();
                    GalleryFragment.progressBar.setVisibility(View.GONE);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                GalleryFragment.progressBar.setVisibility(View.GONE);

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    */
    public void getUserCoins(final Context activity,String user_id) {
        String requestUrl = activity.getString(R.string.Server_ip) + "Get_Player_Coins";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("coins")) {
                        try {

                            JSONObject jo = new JSONObject(response);
                            String coins_number = String.valueOf(jo.getInt("coins"));
                               GalleryFragment.coins_nu.setText("رصيدك الحالي هو : "+coins_number);
                        } catch (JSONException e) {
                            GalleryFragment.progressBar.setVisibility(View.GONE);

                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        GalleryFragment.progressBar.setVisibility(View.GONE);

                        Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();


                    }
                }
                else {
                    Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();
                    GalleryFragment.progressBar.setVisibility(View.GONE);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                GalleryFragment.progressBar.setVisibility(View.GONE);
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
    public void getUserCoins_Payout_page(final Context activity,String user_id) {
        String requestUrl = activity.getString(R.string.Server_ip) + "Get_Player_Coins";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("coins")) {
                        try {

                            JSONObject jo = new JSONObject(response);
                            String coins_number = String.valueOf(jo.getInt("coins"));
                            AttolSharedPreference attolSharedPreference=new AttolSharedPreference((Activity) activity);
                            String lan=attolSharedPreference.getKey("language");
                            if(lan.equals("ar"))
                            {
                                Payout.coins_nu.setText("رصيد الحالي هو :  "+coins_number);

                            }
                            else
                            {
                                Payout.coins_nu.setText("Your Current balance is :  "+coins_number);

                            }
                            Payout.coins_number=Integer.parseInt(coins_number);
                        } catch (JSONException e) {
                           // GalleryFragment.progressBar.setVisibility(View.GONE);

                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                      //  GalleryFragment.progressBar.setVisibility(View.GONE);

                        Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();


                    }
                }
                else {
                    Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();
                 //   GalleryFragment.progressBar.setVisibility(View.GONE);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
               // GalleryFragment.progressBar.setVisibility(View.GONE);
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
    public void Get_Competitions(final Context activity) {
        String requestUrl = activity.getString(R.string.Server_ip) + "Get_Free_Comp";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("comp_id")) {
                        try {
                            JSONArray JA = new JSONArray(response);
                            int[] comp_id = new int[JA.length()];
                            int[] comp_price = new int[JA.length()];
                            String[] comp_name = new String[JA.length()];
                            String[] comp_date = new String[JA.length()];
                            ArrayList<Integer> comp_priceArray = new ArrayList<Integer>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                comp_id[i] = (int) JO.get("comp_id");
                                comp_price[i] = Integer.parseInt((String) JO.get("comp_price"));
                                comp_name[i] = JO.getString("comp_name");
                                comp_date[i] = JO.getString("comp_date");


                                comp_priceArray.add(comp_price[i]);

                            }
                            Fifa_Competitions.comp_id = comp_id;
                            Fifa_Competitions.comp_price = comp_price;

                            CompetitionRecycleAdapter competitionAdapter= new CompetitionRecycleAdapter(
                                    comp_priceArray,comp_name,comp_date,1,0,comp_id,activity
                            );
                           Fifa_Competitions.listView.setAdapter(competitionAdapter);
                            Fifa_Competitions.fifaBar.setVisibility(View.GONE);

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Fifa_Competitions.fifaBar.setVisibility(View.GONE);

                        }


                    }
                    else if (response.contains("data")) {
                        Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();
                        Fifa_Competitions.fifaBar.setVisibility(View.GONE);


                    }
                    else
                    {
                        Fifa_Competitions.fifaBar.setVisibility(View.GONE);

                    }
                }
                else {
                    Fifa_Competitions.fifaBar.setVisibility(View.GONE);
                    Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
               // PlaystationId_Fragment.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();
                Fifa_Competitions.fifaBar.setVisibility(View.GONE);


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void Get_Pubg_Competitions(final Context activity) {
        String requestUrl = activity.getString(R.string.Server_ip) + "Get_Pubg_Free_Competition";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("comp_id")) {
                        try {
                            JSONArray JA = new JSONArray(response);
                            int [] comp_id= new int [JA.length()];
                            int []  comp_price =new int [JA.length()];
                            String  [] comp_name = new String [JA.length()];
                            String []  comp_date =new String [JA.length()];
                            ArrayList<Integer> comp_priceArray = new ArrayList<Integer>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                comp_id[i] = (int ) JO.get("comp_id");
                                comp_price[i] = Integer.parseInt((String) JO.get("comp_price"));
                                comp_name[i]= JO.getString("comp_name");
                                comp_date[i]= JO.getString("comp_date");


                                comp_priceArray.add(comp_price[i]);

                            }
                            Pubg_Competitions.comp_id=comp_id;
                            Pubg_Competitions.comp_price=comp_price;

                            CompetitionRecycleAdapter competitionAdapter= new CompetitionRecycleAdapter(
                                    comp_priceArray,comp_name,comp_date,2,0,comp_id,activity
                            );
                            Pubg_Competitions.listView.setAdapter(competitionAdapter);
                            Pubg_Competitions.pubg_bar.setVisibility(View.GONE);

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();
                        Pubg_Competitions.pubg_bar.setVisibility(View.GONE);


                    }
                    else
                    {
                        Pubg_Competitions.pubg_bar.setVisibility(View.GONE);

                    }
                }
                else {
                    Pubg_Competitions.pubg_bar.setVisibility(View.GONE);
                    Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
//                PlaystationId_Fragment.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();

                Pubg_Competitions.pubg_bar.setVisibility(View.GONE);

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }

    public void Get_MY_Competitions(final Context activity) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "Get_My_Comp";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("comp_id")) {
                        try {
                            JSONArray JA = new JSONArray(response);
                            int[] comp_id = new int[JA.length()];
                            int[] comp_price = new int[JA.length()];
                            String[] comp_name = new String[JA.length()];
                            String[] comp_date = new String[JA.length()];
                            ArrayList<Integer> comp_priceArray = new ArrayList<Integer>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                comp_id[i] = (int) JO.get("comp_id");
                                comp_price[i] = Integer.parseInt((String) JO.get("comp_price"));
                                comp_name[i] = JO.getString("comp_name");
                                comp_date[i] = JO.getString("comp_date");


                                comp_priceArray.add(comp_price[i]);

                            }
//                            Fifa_Competitions.comp_id=comp_id;
//                            Fifa_Competitions.comp_price=comp_price;

                            CompetitionRecycleAdapter competitionAdapter = new CompetitionRecycleAdapter(
                                    comp_priceArray, comp_name, comp_date, 1, 0, comp_id, activity
                            );
                            MyCompetitions_List.progressBar.setAdapter(competitionAdapter);
                            MyCompetitions_List.my_comp_bar.setVisibility(View.GONE);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else if (response.contains("data")) {
                        Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();
                        MyCompetitions_List.my_comp_bar.setVisibility(View.GONE);


                    } else
                    {
                        MyCompetitions_List.my_comp_bar.setVisibility(View.GONE);

                    }
                }
                else {
                    //PlaystationId_Fragment.loading.setVisibility(View.GONE);
                    Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();

                    MyCompetitions_List.my_comp_bar.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
             //   PlaystationId_Fragment.loading.setVisibility(View.GONE);
                MyCompetitions_List.my_comp_bar.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                 params.put("user_id",id);

                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void Get_MY_Pubg_Competitions(final Context activity) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "Get_My_Pubg_Comp";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("comp_id")) {
                        try {
                            JSONArray JA = new JSONArray(response);
                            int [] comp_id= new int [JA.length()];
                            int []  comp_price =new int [JA.length()];
                            String  [] comp_name = new String [JA.length()];
                            String []  comp_date =new String [JA.length()];
                            ArrayList<Integer> comp_priceArray = new ArrayList<Integer>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                comp_id[i] = (int ) JO.get("comp_id");
                                comp_price[i] = Integer.parseInt((String) JO.get("comp_price"));
                                comp_name[i]= JO.getString("comp_name");
                                comp_date[i]= JO.getString("comp_date");


                                comp_priceArray.add(comp_price[i]);

                            }
                           MyCompetitions_List.pubg_comp_id=comp_id;
//                            Fifa_Competitions.comp_price=comp_price;

                            CompetitionRecycleAdapter competitionAdapter= new CompetitionRecycleAdapter(
                                    comp_priceArray,comp_name,comp_date,2,1,comp_id,activity
                            );
                            MyCompetitions_List.progressBar.setAdapter(competitionAdapter);
                            MyCompetitions_List.my_comp_bar.setVisibility(View.GONE);

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();
                        MyCompetitions_List.my_comp_bar.setVisibility(View.GONE);


                    }
                    else
                    {
                        MyCompetitions_List.my_comp_bar.setVisibility(View.GONE);

                    }
                }
                else {
                    //PlaystationId_Fragment.loading.setVisibility(View.GONE);
                    Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();

                    MyCompetitions_List.my_comp_bar.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                //   PlaystationId_Fragment.loading.setVisibility(View.GONE);
                MyCompetitions_List.my_comp_bar.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id",id);

                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }

    public void Get_MY_Competitions_details(final Context activity,int id) {
        String requestUrl = activity.getString(R.string.Server_ip) + "Get_Competition_details";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("comp_id")) {
                        try {
                            JSONArray JA = new JSONArray(response);
                            int [] comp_id= new int [JA.length()];
                            int []  comp_price =new int [JA.length()];
                            String  [] comp_name = new String [JA.length()];
                            String []  comp_date =new String [JA.length()];
                            ArrayList<Integer> comp_priceArray = new ArrayList<Integer>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                comp_id[i] = (int ) JO.get("comp_id");
                                comp_price[i] = Integer.parseInt((String) JO.get("comp_price"));
                                comp_name[i]= JO.getString("comp_name");
                                comp_date[i]= JO.getString("comp_date");


                                comp_priceArray.add( comp_price[i]);

                            }
                            Intent intent=new Intent(activity, My_Competition_Details.class);
                            intent.putExtra("comp_date",comp_date[0]);
                            intent.putExtra("comp_price",String.valueOf(comp_price[0]));
                            intent.putExtra("come_name",comp_name[0]);
                            activity.startActivity(intent);
                           // SlideshowFragment.listView.setAdapter(competitionAdapter);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();
                        //   PlaystationId_Fragment.loading.setVisibility(View.GONE);


                    }
                }
                else {
                    //PlaystationId_Fragment.loading.setVisibility(View.GONE);
                    Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                //   PlaystationId_Fragment.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));

                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }

    public void UNSubscribeToFreeComp(final Context activity ,final int compitition_id ) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");



        String requestUrl = activity.getString(R.string.Server_ip) + "UNSubscribe_To_Free_Competition";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        Toast.makeText(activity, "تم الغاء الاشتراك بالمسابقة  ", Toast.LENGTH_LONG).show();
                        FirebaseMessaging.getInstance ( ).unsubscribeFromTopic (compitition_id+"_comp");

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                                ((Activity)activity).finish();

                            }
                        });



                    }

                }
                else {
                    Toast.makeText(activity, "Error Connection", Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
              //  PlaystationId_Fragment.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("compitition_id", String.valueOf(compitition_id));



                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void UNSubscribeToPubgFreeComp(final Context activity ,final int compitition_id ) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");



        String requestUrl = activity.getString(R.string.Server_ip) + "UNSubscribe_To_Pubg_Free_Competition";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        Toast.makeText(activity, "تم الغاء الاشتراك بالمسابقة  ", Toast.LENGTH_LONG).show();
                        FirebaseMessaging.getInstance ( ).unsubscribeFromTopic (compitition_id+"_Pubg_comp");

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                                ((Activity)activity).finish();

                            }
                        });



                    } else if (response.contains("exists")) {
                        Toast.makeText(activity, "أنت مشترك بالفعل في المسابقة", Toast.LENGTH_LONG).show();


                    }

                }
                else {
                    Toast.makeText(activity, "حدث خطأ", Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                PlaystationId_Fragment.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("competition_id", String.valueOf(compitition_id));



                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }

    public void SubscribeToFreeComp(final Context activity ,final int compitition_id ) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        final String username=mySharedPreference.getKey("username");
        final String playstation_id=mySharedPreference.getKey("playstation_id");


        String requestUrl = activity.getString(R.string.Server_ip) + "Subscribe_To_Free_Competition";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        Toast.makeText(activity,"Subscribed sucessfully  ", Toast.LENGTH_LONG).show();
                        FirebaseMessaging.getInstance ( ).subscribeToTopic (compitition_id+"_Fifa_comp");

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                                ((Activity)activity).finish();

                            }
                        });



                    } else if (response.contains("exists")) {
                        Toast.makeText(activity, "You already subscribed to this competition", Toast.LENGTH_LONG).show();


                    }
                    else if (response.contains("started"))
                    {
                        Toast.makeText(activity, activity.getResources().getString(R.string.game_start), Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        Toast.makeText(activity, "Some Thing Went Wrong please try again later", Toast.LENGTH_LONG).show();

                    }

                }
                else {
                    Toast.makeText(activity, "Some thing wrong check your internet connection", Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
              //  PlaystationId_Fragment.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("compitition_id", String.valueOf(compitition_id));
                params.put("user_name",username);
                params.put("play_id",playstation_id);


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void SubscribeToPubgFreeComp(final Context activity ,final int compitition_id ) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        final String username=mySharedPreference.getKey("username");
        final String playstation_id=mySharedPreference.getKey("pubg_id");


        String requestUrl = activity.getString(R.string.Server_ip) + "Subscribe_To_Pubg_Free_Competition";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        Toast.makeText(activity,"Subscribed  ", Toast.LENGTH_LONG).show();
                        FirebaseMessaging.getInstance ( ).subscribeToTopic (compitition_id+"_Pubg_comp");

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                                ((Activity)activity).finish();

                            }
                        });



                    } else if (response.contains("exists")) {
                        Toast.makeText(activity, "Already subscribed ", Toast.LENGTH_LONG).show();


                    }
                    else if (response.contains("started"))
                    {
                        Toast.makeText(activity, activity.getResources().getString(R.string.game_start), Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        Toast.makeText(activity, "Some Thing Went Wrong please try again Later", Toast.LENGTH_LONG).show();

                    }

                }
                else {
                    Toast.makeText(activity, "try later", Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                PlaystationId_Fragment.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("competition_id", String.valueOf(compitition_id));
                params.put("user_name",username);
                params.put("pubg_id",playstation_id);


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }

    public void     Get_My_Matches(final Context activity) {
        final int id;
        AttolSharedPreference attolSharedPreference=new AttolSharedPreference((Activity) activity);
        id= Integer.parseInt(attolSharedPreference.getKey("id"));
        String requestUrl = activity.getString(R.string.Server_ip) + "Get_Next_Match";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("comp_id")) {
                        try {
                            JSONArray JA = new JSONArray(response);
                            int [] comp_id= new int [JA.length()];
                            int [] round_id= new int [JA.length()];

                            int []  copmetator_id =new int [JA.length()];
                            String  []  comp_date =new String [JA.length()];
                            String  []  competator_name =new String [JA.length()];


                            ArrayList<String > comp_dateArray = new ArrayList<String >();
                            ArrayList<Integer > competator_idArray = new ArrayList<Integer >();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);
                                comp_id[i] = (int ) JO.get("comp_id");
                                copmetator_id[i] = (int) JO.get("competator_id");
                                round_id[i] = (int) JO.get("round_id");
                                comp_date[i]= (String) JO.get("comp_date");
                                competator_name[i]= (String) JO.get("competator_name");

                                comp_dateArray.add(comp_date[i]);
                                competator_idArray.add(copmetator_id[i]);


                            }
                            MyCompetitionAdapter coinsAdapter= new MyCompetitionAdapter(
                                    comp_dateArray,comp_id,copmetator_id,round_id,competator_name,activity
                            );
                            MatchesFragment.listView.setAdapter(coinsAdapter);
                            MatchesFragment.loading.setVisibility(View.GONE);
                            MatchesFragment.comp_date=comp_date;
                            MatchesFragment.comp_id=comp_id;
                            MatchesFragment.round_id=round_id;

                            MatchesFragment.competator_id=competator_idArray.toArray();
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();
                        MatchesFragment.loading.setVisibility(View.GONE);


                    }
                }
                else {
                    MatchesFragment.loading.setVisibility(View.GONE);
                    Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                MatchesFragment.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(id));


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void Add_Complain(final Context activity ,final String username,final  String phone,final String complain, int round_id ,String img) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "User_Complain";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        Toast.makeText(activity, "Your complaints has been received we will contact you soon  ", Toast.LENGTH_LONG).show();
                    //Complain.loading.setVisibility(View.GONE);

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                ((Activity)activity).finish();

                            }
                        });



                    } else if (response.contains("Incorrect")) {
                        Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();

                        //Complain.loading.setVisibility(View.GONE);

                    }

                }
                else {
                 //   Complain.loading.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
             //   Complain.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("user_name", username);
                params.put("complain", complain);
                params.put("phone", phone);
                params.put("img",img);
                params.put("round_id", String.valueOf(round_id));


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void Add_Pubg_Complain(final Context activity ,final String username,final  String phone,final String complain, int cometiton_id , String image) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "User_Pubg_Complain";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        Toast.makeText(activity, "Your complaints has been received we will contact you soon  ", Toast.LENGTH_LONG).show();
                        //Complain.loading.setVisibility(View.GONE);

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                ((Activity)activity).finish();

                            }
                        });



                    } else if (response.contains("Incorrect")) {
                        Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();

                    //    Complain.loading.setVisibility(View.GONE);

                    }

                }
                else {
                 //   Complain.loading.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
             //   Complain.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("user_name", username);
                params.put("complain", complain);
                params.put("phone", phone);
                params.put("img",image);
                params.put("competion_id", String.valueOf(cometiton_id));


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }

    public void Win(final Context activity ,final int comp_id,final  String img,int competator_id,int round_id) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "User_Win_in_Free_Competitions";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             //   Toast.makeText(activity, response, Toast.LENGTH_LONG).show();

                if (response != null) {
                    if (response.contains("succ")) {
                        Toast.makeText(activity, "CONGRATS", Toast.LENGTH_LONG).show();
                        MatchScreenFinish.loading.setVisibility(View.GONE);
                        Animation animation;
                        animation = AnimationUtils.loadAnimation(activity, R.animator.alpha_rotate_animation);
                     //   MatchScreenFinish.MyCountDown timer = new MatchScreenFinish.MyCountDown(5000, 1000);

                        MatchScreenFinish.congrats.setAnimation(animation);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                              //  ((Activity)activity).finish();

                            }
                        });



                    } else if (response.contains("Incorrect")) {
                        Toast.makeText(activity, "some thing wrong try again later", Toast.LENGTH_LONG).show();

                        MatchScreenFinish.loading.setVisibility(View.GONE);

                    }
                    else if(response.contains("lost"))
                    {
                        AttolSharedPreference attolSharedPreference=new AttolSharedPreference((Activity) activity);
                        String lan=attolSharedPreference.getKey("language");


                        MatchScreenFinish.loading.setVisibility(View.GONE);
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(activity);
                        a_builder.setMessage(activity.getResources().getString(R.string.competator_claim))
                                .setCancelable(false)
                                .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(activity, MainActivity.class);
                                            intent.putExtra("round_id",round_id);
                                            intent.putExtra("type",1);
                                        intent.putExtra("frgToLoad", "c");
                                        intent.putExtra("from_finish", "yes");
                                            ((Activity)activity).startActivity(intent);

                                    }
                                });
                        AlertDialog alert = a_builder.create();
                            alert.setTitle("Sorry");


                        alert.show();


                    }
                    else
                    {
                        MatchScreenFinish.loading.setVisibility(View.GONE);

                    }

                }
                else {
                    MatchScreenFinish.loading.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                MatchScreenFinish.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                //params.put("round_id", String.valueOf(round_id));
                params.put("compitition_id", String.valueOf(comp_id));
                params.put("img",img);
                params.put("competator_id", String.valueOf(competator_id));


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void Pubg_Win(final Context activity ,final int comp_id,final  String img) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "User_Win_Pubg_Free_competition";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        Toast.makeText(activity, "Congratulations   ", Toast.LENGTH_LONG).show();
                        MatchScreenFinish.loading.setVisibility(View.GONE);
                    //    Animation animation;
                   //     animation = AnimationUtils.loadAnimation(activity, R.animator.alpha_rotate_animation);
                      //  MatchScreenFinish.congrats.setAnimation(animation);

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                               // ((Activity)activity).finish();

                            }
                        });



                    } else if (response.contains("Incorrect")) {
                        Toast.makeText(activity, "Connection problem", Toast.LENGTH_LONG).show();

                        MatchScreenFinish.loading.setVisibility(View.GONE);

                    }
                    else if(response.contains("lost"))
                    {
                      //  Toast.makeText(activity, "خصمك اعلن انه فاز من فضلك تواصل معنا", Toast.LENGTH_LONG).show();

                        MatchScreenFinish.loading.setVisibility(View.GONE);
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(activity);
                        a_builder.setMessage(activity.getResources().getString(R.string.pubg_claim))
                                .setCancelable(false)
                                .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(activity,MainActivity.class);
                                        intent.putExtra("comp_id",comp_id);
                                        intent.putExtra("type",2);
                                        intent.putExtra("frgToLoad", "c");
                                        intent.putExtra("from_finish", "yes");


                                        ((Activity)activity).startActivity(intent);
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("Sorry");
                        alert.show();


                    }
                    else
                    {
                        MatchScreenFinish.loading.setVisibility(View.GONE);

                    }

                }
                else {
                    MatchScreenFinish.loading.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                MatchScreenFinish.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("compitition_id", String.valueOf(comp_id));
                params.put("img", img);
               // params.put("competator_id", String.valueOf(competator_id));
                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }

    public void Add_coins(final Context activity, int number, String clientSecret, String paymeny_id, String status, double amount, String method_type) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
       // Toast.makeText(activity, "User_id is :"+id, Toast.LENGTH_LONG).show();

        String requestUrl = activity.getString(R.string.Server_ip) + "add_Coins";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        try {
                            JSONObject jo= new JSONObject(response);
                            int nowcoins=jo.getInt("new_coins");
                            Toast.makeText(activity, "Your coins has been added you have :"+nowcoins, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                       //StripePayments.payment_bar.setVisibility(View.GONE);
                        Record_payment(activity,clientSecret,paymeny_id,status,amount,method_type,"coins");
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                ((Activity)activity).finish();

                            }
                        });



                    } else if (response.contains("Incorrect")) {
                        Toast.makeText(activity,"Error ", Toast.LENGTH_LONG).show();


                        StripePayments.payment_bar.setVisibility(View.GONE);

                    }

                }
                else {
                    StripePayments.payment_bar.setVisibility(View.GONE);
                    Toast.makeText(activity, "Error ", Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                StripePayments.payment_bar.setVisibility(View.GONE);
                Toast.makeText(activity, "Error ", Toast.LENGTH_LONG).show();
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("coins", String.valueOf(number));



                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void Record_payment(final Context activity,String clientSecret, String paymeny_id, String status, double amount, String method_type,String product) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "Record_Payment";
        double price= Double.parseDouble(String.valueOf(amount));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        Toast.makeText(activity, "Recorded Thank you ", Toast.LENGTH_LONG).show();
                       StripePayments.payment_bar.setVisibility(View.GONE);
                   //     BrainTreePayment.progressBar.setVisibility(View.GONE);


                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                //((Activity)activity).finish();
                                AlertDialog.Builder a_builder = new AlertDialog.Builder(activity);
                                a_builder.setMessage("Recorded  ")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                ((Activity)activity).finish();
                                            }
                                        });
                                AlertDialog alert = a_builder.create();
                                alert.setTitle("Thank you ");
                                alert.show();
                            }
                        });




                    } else if (response.contains("Incorrect")) {
                        Toast.makeText(activity, "Something went wrong Not Recorded please contact us", Toast.LENGTH_LONG).show();


                       StripePayments.payment_bar.setVisibility(View.GONE);
                      //  BrainTreePayment.progressBar.setVisibility(View.GONE);

                    }
                    else
                    {
                        Toast.makeText(activity, "Something went wrong Not Recorded contact us", Toast.LENGTH_LONG).show();


                               StripePayments.payment_bar.setVisibility(View.GONE);
                        //BrainTreePayment.progressBar.setVisibility(View.GONE);
                    }

                }
                else {
                   // BrainTreePayment.progressBar.setVisibility(View.GONE);

                    StripePayments.payment_bar.setVisibility(View.GONE);
                    Toast.makeText(activity, "Something went wrong please contact us ", Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                StripePayments.payment_bar.setVisibility(View.GONE);
                Toast.makeText(activity, "Something went wrong Not Recorded contact us  ", Toast.LENGTH_LONG).show();
              //  BrainTreePayment.progressBar.setVisibility(View.GONE);
                //Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("client_secret", clientSecret);
                params.put("payment_id", paymeny_id);
                params.put("payment_method", method_type);
                params.put("status", status);
                params.put("payment_amount", String.valueOf(price));
                params.put("product", product);



//user_id=string&client_secret=string&payment_id=string&payment_method=string&status=string&payment_amount=string&product=string

                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }

    public void SubscribeToPaidPubgComp(final Context activity ,final int compitition_id ,String clientSecret, String paymeny_id, String status, double amount, String method_type ) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        final String username=mySharedPreference.getKey("username");
        final String playstation_id=mySharedPreference.getKey("playstation_id");
        String requestUrl = activity.getString(R.string.Server_ip) + "Subscribe_To_Pubg_Free_Competition";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        Toast.makeText(activity, "Subscribed To Pubg Comp Thank you", Toast.LENGTH_LONG).show();
                        Record_payment(activity,clientSecret,paymeny_id,status,amount,method_type,"Subscription");
                        FirebaseMessaging.getInstance ( ).subscribeToTopic (compitition_id+"_Pubg_comp");

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });



                    } else if (response.contains("Incorrect")) {
                        Toast.makeText(activity, "Error ", Toast.LENGTH_LONG).show();
                        StripePayments.payment_bar.setVisibility(View.GONE);
                        BrainTreePayment.progressBar.setVisibility(View.GONE);


                    }

                }
                else {
                    Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show();
                    StripePayments.payment_bar.setVisibility(View.GONE);
                    BrainTreePayment.progressBar.setVisibility(View.GONE);



                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                StripePayments.payment_bar.setVisibility(View.GONE);
                BrainTreePayment.progressBar.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("competition_id", String.valueOf(compitition_id));
                params.put("user_name",username);
                params.put("pubg_id",playstation_id);

                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }

    public void SubscribeToPaidComp(final Context activity ,final int compitition_id ,String clientSecret, String paymeny_id, String status, double amount, String method_type ) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        final String username=mySharedPreference.getKey("username");
        final String playstation_id=mySharedPreference.getKey("playstation_id");
        String requestUrl = activity.getString(R.string.Server_ip) + "Subscribe_To_Free_Competition";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        Toast.makeText(activity, "Subscribed to Fifa Comp Thank you", Toast.LENGTH_LONG).show();
                        Record_payment(activity,clientSecret,paymeny_id,status,amount,method_type,"Subscription");
                        FirebaseMessaging.getInstance ( ).subscribeToTopic (compitition_id+"_Fifa_comp");

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });



                    } else if (response.contains("Incorrect")) {
                        Toast.makeText(activity, "Error ", Toast.LENGTH_LONG).show();
                        StripePayments.payment_bar.setVisibility(View.GONE);
                        BrainTreePayment.progressBar.setVisibility(View.GONE);


                    }

                }
                else {
                    Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show();
                    StripePayments.payment_bar.setVisibility(View.GONE);
                    BrainTreePayment.progressBar.setVisibility(View.GONE);



                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                StripePayments.payment_bar.setVisibility(View.GONE);
                BrainTreePayment.progressBar.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("compitition_id", String.valueOf(compitition_id));
                params.put("user_name",username);
                params.put("play_id",playstation_id);

                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void Get__Competator_Playstation_Id(final Context activity,int competator_id) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "Get_competator_play_id";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("playstation_id")) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            String play_id = String.valueOf(jo.getInt("playstation_id"));
                            String phone = String.valueOf(jo.getString("user_phone"));
                            String photo = String.valueOf(jo.getString("user_photo"));

                            CompetatorId.competator_idTV.setText(play_id);
                            CompetatorId.photo=photo;
                            CompetatorId.phone=phone;

                            CompetatorId.progressBar.setVisibility(View.GONE);

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        Toast.makeText(activity, "Error Connection", Toast.LENGTH_LONG).show();
                        CompetatorId.progressBar.setVisibility(View.GONE);


                    }
                }
                else {
                    CompetatorId.progressBar.setVisibility(View.GONE);
                    Toast.makeText(activity, "Error Connection", Toast.LENGTH_LONG).show();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                CompetatorId.progressBar.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(competator_id));


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void getPlayerInfo(final Context activity) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "Get_Player_details";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("phone")) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            String username = jo.getString("username");
                            EditProfile.user_name.setText(username);
                            EditProfile.user_name.setEnabled(false);
                            String phone = String.valueOf(jo.get("phone"));
                            String email = String.valueOf(jo.get("email"));

                            EditProfile.phone.setText(phone);
                            EditProfile.phone.setEnabled(false);
                            Picasso.with(activity).load("http://95.217.146.96/users/"+username+".jpg").into(EditProfile.profile_img);

                            EditProfile.email.setText(email);
                            EditProfile.email.setEnabled(false);

                            String photo = String.valueOf(jo.getString("photo"));

                            String play_id = String.valueOf(jo.getString("playstation_id"));
                            mySharedPreference.setKey("playstation_id",play_id);

                            EditProfile.progressBar.setVisibility(View.GONE);

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        Toast.makeText(activity, "Error Connection", Toast.LENGTH_LONG).show();
                        EditProfile.progressBar.setVisibility(View.GONE);


                    }
                }
                else {
                    EditProfile.progressBar.setVisibility(View.GONE);
                    Toast.makeText(activity, "Error Connection", Toast.LENGTH_LONG).show();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                EditProfile.progressBar.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void getPlayerInfo2(final Context activity) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "Get_Player_details";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("phone")) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            String username = String.valueOf(jo.getString("username"));
                            mySharedPreference.setKey("username",username);

                            String phone = String.valueOf(jo.getString("phone"));


                            String photo = String.valueOf(jo.getString("photo"));

                            String play_id = String.valueOf(jo.getString("playstation_id"));
                            mySharedPreference.setKey("play_id",play_id);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        Toast.makeText(activity, "something wrong try again later", Toast.LENGTH_LONG).show();


                    }
                }
                else {
                    Toast.makeText(activity, "something wrong try again later", Toast.LENGTH_LONG).show();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void getPlayerInfo_main(final Context activity) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "Get_Player_details";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("phone")) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            String username = String.valueOf(jo.getString("username"));
                            if(username!=null) {
                                mySharedPreference.setKey("username", username);
                                MainActivity.username.setText(username);
                                Picasso.with(activity).load("http://95.217.146.96/users/"+username+".jpg").into(MainActivity.imageUser);
                            }
                            String phone = String.valueOf(jo.getString("phone"));
                            String photo = String.valueOf(jo.getString("photo"));
                            String pubg_id = String.valueOf(jo.getString("pubg_id"));
                            String play_id = String.valueOf(jo.getString("playstation_id"));
                            if(play_id!=null)
                            {
                                mySharedPreference.setKey("pubg_id",pubg_id);

                            }
                            if(play_id!=null)
                            {
                                mySharedPreference.setKey("playstation_id",play_id);

                            }

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        Toast.makeText(activity, "something wrong please wail ", Toast.LENGTH_LONG).show();
                        getPlayerInfo_main(activity) ;


                    }
                    else
                    {
                        getPlayerInfo_main(activity) ;
                    }
                }
                else {
                    Toast.makeText(activity, "something wrong please wail ", Toast.LENGTH_LONG).show();
                    getPlayerInfo_main(activity) ;

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
               // Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
              //  Toast.makeText(activity,"Check Internet Connection Or restart the App",Toast.LENGTH_LONG).show();
                //getPlayerInfo_main(activity) ;

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }

    public void ForgetPass(final Context activity,String email) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "ForgetPassword";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {

                        Toast.makeText(activity,"Link has been sent to your Email", Toast.LENGTH_LONG).show();
                        ForgetPassword.progressBar.setVisibility(View.GONE);


                    }
                    else if (response.contains("data")) {
                        ForgetPassword.progressBar.setVisibility(View.GONE);
                        Toast.makeText(activity,"Error try again", Toast.LENGTH_LONG).show();


                    }
                    else if (response.contains("Wrong Email"))
                    {
                        ForgetPassword.progressBar.setVisibility(View.GONE);
                        Toast.makeText(activity,"Wrong Email", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        ForgetPassword.progressBar.setVisibility(View.GONE);
                        Toast.makeText(activity,"Error try again", Toast.LENGTH_LONG).show();

                    }
                }
                else {
                    Toast.makeText(activity, "something wrong try again later", Toast.LENGTH_LONG).show();
                    ForgetPassword.progressBar.setVisibility(View.GONE);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();
                ForgetPassword.progressBar.setVisibility(View.GONE);

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }

    public void SendPaymentRequest(final Context activity ,final String type,final String payment_info , int number_of_coins ,String date) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "Payout_Request";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        Toast.makeText(activity, "Your request has been recieved  ", Toast.LENGTH_LONG).show();

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                                ((Activity)activity).finish();

                            }
                        });



                    } else if (response.contains("wrong")) {
                        Toast.makeText(activity, "You don't have enough coins ", Toast.LENGTH_LONG).show();


                    }

                }
                else {
                    Toast.makeText(activity, "something wrong try again later", Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
              //  PlaystationId_Fragment.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("coins_number", String.valueOf(number_of_coins));
                params.put("type",type);
                params.put("payment_info",payment_info);
                params.put("date",date);


                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }

    public void getPayment_status(final Context activity) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");

        String requestUrl = activity.getString(R.string.Server_ip) + "GetPaymentsRequest";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("request_id")) {
                        try {

//                            GalleryFragment.progressBar.setVisibility(View.GONE);
                            JSONArray JA = new JSONArray(response);
                            int [] request_id= new int [JA.length()];
                            int []  status =new int [JA.length()];
                            int []  coins_number =new int [JA.length()];
                            String [] date=new String[JA.length()];

                            ArrayList<Integer> coins_numberArray = new ArrayList<Integer>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                coins_number[i] = (int ) JO.get("coins_number");
                                status[i] = (int) JO.get("status");
                                request_id[i] = (int) JO.get("request_id");
                                date[i]=JO.getString("date");
                                coins_numberArray.add(coins_number[i]);

                            }
                            Payments_status_adapter payments_status_adapter = new Payments_status_adapter(
                                    coins_numberArray,status,request_id,date,activity
                            );

                            Payment_list_status.listView.setAdapter(payments_status_adapter);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        } catch (JSONException e) {
                            GalleryFragment.progressBar.setVisibility(View.GONE);

                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                       // GalleryFragment.progressBar.setVisibility(View.GONE);

                        Toast.makeText(activity, "something wrong try again later", Toast.LENGTH_LONG).show();


                    }
                }
                else {
                    Toast.makeText(activity, "something wrong try again later", Toast.LENGTH_LONG).show();
                  //  GalleryFragment.progressBar.setVisibility(View.GONE);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
            //    GalleryFragment.progressBar.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                 params.put("user_id",id);

                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }

    public void PayWithCoins(final Context activity ,final int coins_number,int comp_id ,String game) {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference((Activity) activity);
        final String id = mySharedPreference.getKey("id");
        String requestUrl = activity.getString(R.string.Server_ip) + "PayWithCoins";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        if(game.equals("fifa")) {
                            SubscribeToFreeComp(activity, comp_id);
                        }
                        else if(game.equals("pubg"))
                        {
                            SubscribeToPubgFreeComp(activity, comp_id);

                        }
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                                ((Activity)activity).finish();

                            }
                        });



                    } else if (response.contains("enough")) {
                        Toast.makeText(activity, "you don't have enough coins ", Toast.LENGTH_LONG).show();


                    }

                }
                else {
                    Toast.makeText(activity, "something wrong check the internet", Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                //  PlaystationId_Fragment.loading.setVisibility(View.GONE);
                Toast.makeText(activity,"Check Internet Connection",Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("coins_number", String.valueOf(coins_number));



                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }

}

