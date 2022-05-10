package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class Pubg_Free_Competition_Details extends AppCompatActivity {
    int comp_id;
    int comp_price;
    Button check;String pubg_id;
    int behave=0;
    TextView rules;
    String language;
    int status;
    ImageView pubg_img;
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pubg__free__competition__details);
        layout=findViewById(R.id.rl);
        //pubg_compettion_back_new
        AttolSharedPreference attolSharedPreference= new AttolSharedPreference(this);
        int av2= Integer.parseInt(attolSharedPreference.getKey("android_ver"));
        if(av2==1)
        {
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.logo) );
            } else {
                layout.setBackground(ContextCompat.getDrawable(this, R.drawable.logo));
            }

        }
        else
        {
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.pubg_compettion_back_new) );
            } else {
                layout.setBackground(ContextCompat.getDrawable(this, R.drawable.pubg_compettion_back_new));
            }


        }

        pubg_img=findViewById(R.id.pubg_id_img_details);
        int av= Integer.parseInt(attolSharedPreference.getKey("android_ver"));
        if(av==1)
        {
            pubg_img.setBackgroundResource(R.drawable.pubg222);

        }
        else
        {
            pubg_img.setBackgroundResource(R.drawable.pubg_img_game_new);

        }
        Intent intent=getIntent();
        comp_id=intent.getIntExtra("comp_id",0);
        comp_price=intent.getIntExtra("comp_price",0);
        check=findViewById(R.id.check);
        check.setEnabled(false);
        check.setText("Loading");
        pubg_id=attolSharedPreference.getKey("pubg_id");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
        getStatus();
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
            is = this.getResources().openRawResource(R.raw.pubg_rules_ararabic);
        }
        else
        {
            is = this.getResources().openRawResource(R.raw.pubg_en_rules);

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
        if (behave == 1) {
            if (comp_id != 0 && comp_price == 0) {
                if (pubg_id != null) {


                    if (pubg_id.equals("NoIdYet")) {
                        Toast.makeText(Pubg_Free_Competition_Details.this, getResources().getString(R.string.add_pubg_id_note), Toast.LENGTH_LONG).show();
                    } else {
                        Webservice webservice = new Webservice();
                        webservice.SubscribeToPubgFreeComp(Pubg_Free_Competition_Details.this, comp_id);
                    }
                } else {
                    Toast.makeText(Pubg_Free_Competition_Details.this, getResources().getString(R.string.add_pubg_id_note), Toast.LENGTH_LONG).show();

                }


            } else if ((comp_id != 0 && comp_price > 0)) {
                // HERE SHOULD BE THE PAID PART
                if (pubg_id != null) {
                    if (pubg_id.equals("NoIdYet")) {
                        Toast.makeText(Pubg_Free_Competition_Details.this, getResources().getString(R.string.add_pubg_id_note), Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(Pubg_Free_Competition_Details.this, StripePayments.class);
                        intent.putExtra("id", comp_id);
                        intent.putExtra("type", 2);
                        intent.putExtra("game","pubg");
                        intent.putExtra("price", comp_price);

                        if(status==1) {
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(this, "Thank You For Your Request", Toast.LENGTH_LONG).show();
                        }
                       // startActivity(intent);
                       // finish();
                    }
                } else {
                    Toast.makeText(Pubg_Free_Competition_Details.this, getResources().getString(R.string.add_pubg_id_note), Toast.LENGTH_LONG).show();

                }

            }
        }
        else if(behave==2)
        {
           // Webservice webservice = new Webservice();
          //  webservice.UNSubscribeToPubgFreeComp(Pubg_Free_Competition_Details.this, comp_id);
        }
    }
    public void Check() {
        final int id;
        AttolSharedPreference attolSharedPreference=new AttolSharedPreference(Pubg_Free_Competition_Details.this);
        id= Integer.parseInt(attolSharedPreference.getKey("id"));
        String requestUrl = getString(R.string.Server_ip) + "PubgSubscribtionCheck";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("succ")) {
                        check.setEnabled(true);
                        check.setText(getResources().getString(R.string.subscribe));
                           behave=1;

                    }
                    else if (response.contains("exsist")) {
                        check.setEnabled(true);
                        check.setText(getResources().getString(R.string.subscribe_check_true));
                        behave=2;

                    }
                    else if (response.contains("NoMoreSpace"))
                    {
                        //check.setEnabled(false);
                        check.setText(getResources().getString(R.string.competition_is_full));

                    }
                    else if (response.contains("started"))
                    {
                        check.setText(getResources().getString(R.string.game_start));

                    }
                }
                else {
                    MatchesFragment.loading.setVisibility(View.GONE);
                    Toast.makeText(Pubg_Free_Competition_Details.this, "Connection Error", Toast.LENGTH_LONG).show();
                    check.setEnabled(false);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
              //  MatchesFragment.loading.setVisibility(View.GONE);

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
        Volley.newRequestQueue(Pubg_Free_Competition_Details.this).add(stringRequest);
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
                            // check.setEnabled(true);
                            Check();

                        } catch (JSONException e) {
                            // GalleryFragment.progressBar.setVisibility(View.GONE);
                            Toast.makeText(Pubg_Free_Competition_Details.this, "Check Internet Connection", Toast.LENGTH_LONG).show();

                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        //  GalleryFragment.progressBar.setVisibility(View.GONE);

                        Toast.makeText(Pubg_Free_Competition_Details.this, "Check Internet Connection", Toast.LENGTH_LONG).show();


                    }
                }
                else {
                    Toast.makeText(Pubg_Free_Competition_Details.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
                    //   GalleryFragment.progressBar.setVisibility(View.GONE);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                // GalleryFragment.progressBar.setVisibility(View.GONE);
                Toast.makeText(Pubg_Free_Competition_Details.this,"Check Internet Connection",Toast.LENGTH_LONG).show();
            }
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
