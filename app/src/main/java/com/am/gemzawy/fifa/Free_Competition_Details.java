package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.am.gemzawy.fifa.Api.Webservice;
import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;
import com.am.gemzawy.fifa.Payments.StripePayments;
import com.am.gemzawy.fifa.ui.matches.MatchesFragment;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Free_Competition_Details extends AppCompatActivity {
int comp_id;
int comp_price;
Button check;String playstation_id;
int behave=0;
TextView rules;
String language;
int status;
ProgressBar fifa_bar;
ImageView fifa_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free__competition__details);
        fifa_img=findViewById(R.id.fifa_img_id_details);
        AttolSharedPreference attolSharedPreference= new AttolSharedPreference(this);
        int av= Integer.parseInt(attolSharedPreference.getKey("android_ver"));
        if(av==1)
        {
            fifa_img.setBackgroundResource(R.drawable.fifa222);

        }
        else
        {
            fifa_img.setBackgroundResource(R.drawable.fifa_img_game_new);

        }
        Intent intent=getIntent();
        comp_id=intent.getIntExtra("comp_id",0);
        comp_price=intent.getIntExtra("comp_price",0);
        check=findViewById(R.id.check);
        check.setEnabled(false);
        check.setText("Loading");
        fifa_bar=findViewById(R.id.Fifa_details_bar);
          playstation_id=attolSharedPreference.getKey("playstation_id");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
        Check();
        fifa_bar.setVisibility(View.VISIBLE);
        rules=findViewById(R.id.text_id);
        language=attolSharedPreference.getKey("language");
        try {
            ReadTextFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void ReadTextFile() throws IOException {
        String string = "";
        StringBuilder stringBuilder = new StringBuilder();
        InputStream is;
        if(language.equals("ar")) {
            is = this.getResources().openRawResource(R.raw.fifa_arabic_rules);
        }
        else
        {
            is = this.getResources().openRawResource(R.raw.fifa_en_rules);

        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                if ((string = reader.readLine()) == null) break;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            stringBuilder.append(string).append("\n");
            rules.setText(stringBuilder);
        }
        is.close();
//        Toast.makeText(getBaseContext(), stringBuilder.toString(),
//                Toast.LENGTH_LONG).show();
    }

    public void subscribe(View view) {
        if(behave==1) {
            if (comp_id != 0 && comp_price == 0) {
                if (playstation_id != null) {
                    if (playstation_id.equals("NoIdYet")) {
                        Toast.makeText(Free_Competition_Details.this, getResources().getString(R.string.add_play_id_note), Toast.LENGTH_LONG).show();
                    } else {
                        Webservice webservice = new Webservice();
                        webservice.SubscribeToFreeComp(Free_Competition_Details.this, comp_id);
                    }
                } else {
                    Toast.makeText(Free_Competition_Details.this, getResources().getString(R.string.add_play_id_note), Toast.LENGTH_LONG).show();

                }


            } else if ((comp_id != 0 && comp_price > 0)) {
                // HERE SHOULD BE THE PAID PART
                if (playstation_id != null) {
                    if (playstation_id.equals("NoIdYet")) {
                        Toast.makeText(Free_Competition_Details.this, getResources().getString(R.string.add_play_id_note), Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(Free_Competition_Details.this, StripePayments.class);
                        intent.putExtra("id", comp_id);
                        intent.putExtra("type", 2);
                        intent.putExtra("price", comp_price);
                        intent.putExtra("game","fifa");

                        if(status==1) {
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(this, "Thank You For Your Request", Toast.LENGTH_LONG).show();
                        }
                       // startActivity(intent);
                    }
                } else {
                    Toast.makeText(Free_Competition_Details.this, getResources().getString(R.string.add_play_id_note), Toast.LENGTH_LONG).show();

                }


            }
        }
        else if(behave==2)
        {
//            Webservice webservice = new Webservice();
//            webservice.UNSubscribeToFreeComp(Free_Competition_Details.this, comp_id);
        }
    }
    public void Check() {
        final int id;
        AttolSharedPreference attolSharedPreference=new AttolSharedPreference(Free_Competition_Details.this);
        id= Integer.parseInt(attolSharedPreference.getKey("id"));
        String requestUrl = getString(R.string.Server_ip) + "SubscribtionCheck";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                      check.setEnabled(true);
                      check.setText(getResources().getString(R.string.subscribe));
                      behave=1;
                        getStatus();

                    }
                    else if (response.contains("exsist")) {
                        //check.setEnabled(true);
                        check.setText(getResources().getString(R.string.subscribe_check_true));
                       behave=2;
                       fifa_bar.setVisibility(View.GONE);

                    }
                    else if (response.contains("NoMoreSpace"))
                    {
                        //check.setEnabled(false);
                        check.setText(getResources().getString(R.string.competition_is_full));
                        fifa_bar.setVisibility(View.GONE);

                    }
                    else if (response.contains("started"))
                    {
                        check.setText(getResources().getString(R.string.game_start));
                        fifa_bar.setVisibility(View.GONE);


                    }
                    else {
                        MatchesFragment.loading.setVisibility(View.GONE);
                        Toast.makeText(Free_Competition_Details.this, "Unexpected Error Try again later", Toast.LENGTH_LONG).show();
                        check.setEnabled(false);
                        fifa_bar.setVisibility(View.GONE);

                    }
                }
                else {
                    MatchesFragment.loading.setVisibility(View.GONE);
                    Toast.makeText(Free_Competition_Details.this, "Connection Erorr", Toast.LENGTH_LONG).show();
                    check.setEnabled(false);
                    fifa_bar.setVisibility(View.GONE);



                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
              //  MatchesFragment.loading.setVisibility(View.GONE);
                Toast.makeText(Free_Competition_Details.this, "Connection Erorr", Toast.LENGTH_LONG).show();
                fifa_bar.setVisibility(View.GONE);


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(id));
                params.put("comp_id", String.valueOf(comp_id));



                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(Free_Competition_Details.this).add(stringRequest);
    }

    public void click(View view) {
    }
    public void getStatus() {
        String requestUrl = getResources().getString(R.string.Server_ip) + "Get_Android_Publish_status";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("anroid_status")) {
                        try {

                            JSONObject jo = new JSONObject(response);
                             status = jo.getInt("anroid_status");
                             fifa_bar.setVisibility(View.GONE);
                            // check.setEnabled(true);

                        } catch (JSONException e) {
                            // GalleryFragment.progressBar.setVisibility(View.GONE);
                            Toast.makeText(Free_Competition_Details.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
                            fifa_bar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        //  GalleryFragment.progressBar.setVisibility(View.GONE);
                        fifa_bar.setVisibility(View.GONE);
                        Toast.makeText(Free_Competition_Details.this, "Can not Fetch Payment's Syatus", Toast.LENGTH_LONG).show();


                    }
                }
                else {
                    fifa_bar.setVisibility(View.GONE);
                    Toast.makeText(Free_Competition_Details.this, "Can not Fetch Payment's Syatus", Toast.LENGTH_LONG).show();                    //   GalleryFragment.progressBar.setVisibility(View.GONE);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                // GalleryFragment.progressBar.setVisibility(View.GONE);
                fifa_bar.setVisibility(View.GONE);
                Toast.makeText(Free_Competition_Details.this, "Can not Fetch Payment's Syatus", Toast.LENGTH_LONG).show();            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // params.put("user_Id",user_id);

                //..... Add as many key value pairs in the map as necessary for your request
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

}
