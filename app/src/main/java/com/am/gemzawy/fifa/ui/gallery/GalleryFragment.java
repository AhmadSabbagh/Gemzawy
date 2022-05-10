package com.am.gemzawy.fifa.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;
import com.am.gemzawy.fifa.MainActivity;
import com.am.gemzawy.fifa.Payments.StripePayments;
import com.am.gemzawy.fifa.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class
GalleryFragment extends Fragment {
public static int [] coins_number;
    public static int [] coins_id;
    public static int [] coins_price;
    public static ProgressBar progressBar;
    private GalleryViewModel galleryViewModel;
   // public static ListView listView;
    public static TextView coins_nu;
    Button buy;
    EditText coins_et;
    int status;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        //listView=root.findViewById(R.id.list);
        progressBar=root.findViewById(R.id.progressbar);
        coins_nu=root.findViewById(R.id.coins_number_id);
        coins_et=root.findViewById(R.id.coins_numberE);
        progressBar.setVisibility(View.INVISIBLE);
        buy=root.findViewById(R.id.buy_coins_bu);
        buy.setEnabled(false);
        getStatus();
        buy.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (coins_et.getText().toString().equals(""))
                 {
                     Toast.makeText(getActivity(),"Enter coins number please ",Toast.LENGTH_LONG).show();
                 }
                 else
                 {
                     double coins_number=Double.parseDouble(coins_et.getText().toString());
                     int coi_number=Integer.parseInt(coins_et.getText().toString());
                     if(coi_number<50)
                     {
                         Toast.makeText(getActivity(),"50 coins at least ",Toast.LENGTH_LONG).show();

                     }
                     else
                     {
                         coins_et.getText().clear();
                         double price = coins_number * 0.04  ;
                         Intent intent= new Intent(getContext(), StripePayments.class);
                         intent.putExtra("id","1");
                         intent.putExtra("number",coi_number);
                         intent.putExtra("type",1);
                         intent.putExtra("price",price);

                         if(status==1) {
                             startActivity(intent);
                         }
                         else {
                             Toast.makeText(getActivity(), "Thank You For Your Request", Toast.LENGTH_LONG).show();
                         }


                     }
                 }
             }
         });
        Intent intent= getActivity().getIntent();
         int x= intent.getIntExtra("mode",0);
//        Webservice webservice = new Webservice();
//        webservice.Get_Coins(getContext());
       // webservice.getUserCoins(getActivity());
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(getContext());
                a_builder.setMessage("أنت على وشك شراء coins"  )
                        .setCancelable(false)
                        .setNegativeButton("رجوع", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // PURCHASE PART
                                Intent intent= new Intent(getContext(), Stripe.class);
                                intent.putExtra("id",coins_id[position]);
                                intent.putExtra("number",coins_number[position]);
                                intent.putExtra("type",1);
                                intent.putExtra("price",coins_price[position]);


                                startActivity(intent);

                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("شكرا ");
                alert.show();
            }
        });
        */
        return root;
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
                            buy.setEnabled(true);
                        } catch (JSONException e) {
                            // GalleryFragment.progressBar.setVisibility(View.GONE);

                            e.printStackTrace();
                        }


                    }
                    else if (response.contains("data")) {
                        //  GalleryFragment.progressBar.setVisibility(View.GONE);

                        Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_LONG).show();


                    }
                }
                else {
                    Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_LONG).show();
                    //   GalleryFragment.progressBar.setVisibility(View.GONE);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.e("Volley Result",  "Error"+error.getLocalizedMessage()); //the response contains the result from the server, a json string or any other object returned by your server
                // GalleryFragment.progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

}