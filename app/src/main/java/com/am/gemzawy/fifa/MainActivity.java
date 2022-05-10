package com.am.gemzawy.fifa;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.am.gemzawy.fifa.Api.Webservice;
import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;
import com.am.gemzawy.fifa.ui.complain.Complains;
import com.am.gemzawy.fifa.ui.home.HomeFragment;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
public static TextView username;
ProgressBar main;
    public static ImageView imageUser;
    int versionCode;

    int count=1;

                                        int comp_id=0;
                                        int type=0;
                                        int round_id=0;
                                        String fragment_to_load="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AttolSharedPreference attolSharedPreference = new AttolSharedPreference(MainActivity.this);
        String lan = attolSharedPreference.getKey("language");
        if (lan.equals("ar"))
        {
          /*  Resources res = MainActivity.this.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.setLocale(new Locale("ar")); // API 17+ only.
            res.updateConfiguration(conf, dm);*/
            Locale locale = new Locale("ar");
            Locale.setDefault(locale);
            // Create a new configuration object
            Configuration config = new Configuration();
            // Set the locale of the new configuration
            config.locale = locale;
            // Update the configuration of the Accplication context
            getResources().updateConfiguration(
                    config,
                    getResources().getDisplayMetrics()
            );
        }
        else if (lan.equals("en"))
        {
            /*Resources res = MainActivity.this.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.setLocale(new Locale("en")); // API 17+ only.
            res.updateConfiguration(conf, dm);*/
            Locale locale = new Locale("en");

            Locale.setDefault(locale);
            // Create a new configuration object
            Configuration config = new Configuration();
            // Set the locale of the new configuration
            config.locale = locale;
            // Update the configuration of the Accplication context
            getResources().updateConfiguration(
                    config,
                    getResources().getDisplayMetrics()
            );
        }
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        Intent intent = getIntent();
        comp_id = intent.getIntExtra("comp_id", 0);
        type = intent.getIntExtra("type", 0);
        round_id = intent.getIntExtra("round_id", 0);
        fragment_to_load = intent.getStringExtra("frgToLoad");
        main=findViewById(R.id.main_bar);
        fragment_to_load = intent.getStringExtra("frgToLoad");
        if (fragment_to_load != null) {
            if (fragment_to_load.equals("c")) {
                if (type == 2) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("comp_id", comp_id);
                    bundle.putInt("type", 2);

                    Complains fragment = new Complains();
                    fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, fragment);
                    transaction.commit();

                } else if (type == 1) {

                    Bundle bundle = new Bundle();
                    bundle.putInt("round_id", round_id);
                    bundle.putInt("type", 1);

                    Complains fragment = new Complains();
                    fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, fragment);
                    transaction.commit();

                }

            }
        }
        try {
            final PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                versionCode = (int) pInfo.getLongVersionCode(); // avoid huge version numbers and you will be ok
                //    Toast.makeText(getApplicationContext(),versionCode,Toast.LENGTH_LONG).show();
            } else {
                //noinspection deprecation
                versionCode = pInfo.versionCode;
                //  Toast.makeText(getApplicationContext(),"code is : "+versionCode,Toast.LENGTH_LONG).show();

            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //getVersion();
       // getStatus();
        getPlayerInfo_main();
        main.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#000000");
        window.setStatusBarColor(colorCodeDark);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        username = (TextView) headerView.findViewById(R.id.header_text);
        imageUser = (ImageView) headerView.findViewById(R.id.photoR);
        //username.setText("name");
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_gallery, R.id.nav_home, R.id.nav_slideshow, R.id.playstation_id, R.id.nav_matches,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send, R.id.nav_complain, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement



        return super.onOptionsItemSelected(item);

    }
    public void getVersion() {
        String requestUrl = getResources().getString(R.string.Server_ip) + "Get_Version";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("android_ver")) {
                        try {

                            JSONObject jo = new JSONObject(response);
                            int av = jo.getInt("android_ver");
                            String link=jo.getString("link");
                            AttolSharedPreference attolSharedPreference=new AttolSharedPreference(MainActivity.this);
                            attolSharedPreference.setKey("app_link",link);
                            attolSharedPreference.setKey("android_ver", String.valueOf(av));
                            if(av==1)
                            {
                                HomeFragment.fifa.setBackgroundResource(R.drawable.fifa222);
                                HomeFragment.pubg.setBackgroundResource(R.drawable.pubg222);

                            }
                            else
                            {
                                HomeFragment.fifa.setBackgroundResource(R.drawable.fifa_img_game_new);
                                HomeFragment.pubg.setBackgroundResource(R.drawable.pubg_img_game_new);

                            }

                            if (av > versionCode)
                            {

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder (MainActivity.this);
                                alertDialogBuilder.setMessage(getResources().getString(R.string.new_ver))
                                        .setCancelable (false)
                                        .setPositiveButton ("Yes",
                                                new DialogInterface.OnClickListener ( ) {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        Intent i = new Intent (Intent.ACTION_VIEW);
                                                        i.setData (Uri.parse (link));
                                                        startActivity (i);
                                                    }
                                                });
                                alertDialogBuilder.setNegativeButton ("Later",
                                        new DialogInterface.OnClickListener ( ) {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel ( );

                                            }
                                        });
                                AlertDialog alert = alertDialogBuilder.create ( );
                                alert.show ( );
                            }

                        } catch (JSONException e) {
                            // GalleryFragment.progressBar.setVisibility(View.GONE);

                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        //  GalleryFragment.progressBar.setVisibility(View.GONE);

                        Toast.makeText(MainActivity.this, "some thing wrong try again later", Toast.LENGTH_LONG).show();


                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "some thing wrong try again later", Toast.LENGTH_LONG).show();
                    //   GalleryFragment.progressBar.setVisibility(View.GONE);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                // GalleryFragment.progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,"Check Internet Connection",Toast.LENGTH_LONG).show();
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
    public void getStatus() {
        String requestUrl = getResources().getString(R.string.Server_ip) + "Get_Android_Publish_status";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (response.contains("anroid_status")) {
                        try {

                            JSONObject jo = new JSONObject(response);
                            int status = jo.getInt("anroid_status");
                            AttolSharedPreference attolSharedPreference=new AttolSharedPreference(MainActivity.this);
                            attolSharedPreference.setKey("android_status",String.valueOf(status));


                        } catch (JSONException e) {
                            // GalleryFragment.progressBar.setVisibility(View.GONE);

                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        //  GalleryFragment.progressBar.setVisibility(View.GONE);

                        Toast.makeText(MainActivity.this, "Check Internet Connection", Toast.LENGTH_LONG).show();


                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
                    //   GalleryFragment.progressBar.setVisibility(View.GONE);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                // GalleryFragment.progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,"Check Internet Connection",Toast.LENGTH_LONG).show();
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
    public void getPlayerInfo_main() {
        AttolSharedPreference mySharedPreference = new AttolSharedPreference(this
        );
        final String id = mySharedPreference.getKey("id");
        String requestUrl = getString(R.string.Server_ip) + "Get_Player_details";
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
                                Picasso.with(MainActivity.this).load("https://gemzawy.com/users/"+username+".jpg").into(MainActivity.imageUser);
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
                            getVersion();
                            main.setVisibility(View.GONE);
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
                        Toast.makeText(MainActivity.this, "Please Wait We Are Preparing your Profile", Toast.LENGTH_LONG).show();
                        getPlayerInfo_main() ;


                    }
                    else
                    {
                        getPlayerInfo_main() ;
                        Toast.makeText(MainActivity.this, "Please Wait We Are Preparing your Profile", Toast.LENGTH_LONG).show();

                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Please Wait We Are Preparing your Profile", Toast.LENGTH_LONG).show();
                    getPlayerInfo_main() ;

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                // Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                //  Toast.makeText(activity,"Check Internet Connection Or restart the App",Toast.LENGTH_LONG).show();
                //getPlayerInfo_main(activity) ;
                //getPlayerInfo_main() ;
                Toast.makeText(MainActivity.this, "Please Check Internet Connection and try again", Toast.LENGTH_LONG).show();
                finish();


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
        Volley.newRequestQueue(this).add(stringRequest);
    }




}
