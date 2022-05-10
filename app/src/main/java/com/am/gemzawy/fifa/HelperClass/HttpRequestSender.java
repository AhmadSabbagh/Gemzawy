package com.am.gemzawy.fifa.HelperClass;

/**
 * Created by ahmad on 3/29/2018.
 */


import android.app.Activity;
import android.content.Context;
import android.util.Log;


import com.am.gemzawy.fifa.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestSender {
    private Context context;
    private String id;
    public String getID() {
        return this.id;
    }
    public void setID(String id) {
        this.id = id;
    }
    public HttpRequestSender(Activity context) {
        this.context = context;
        AttolSharedPreference mySharedPreference = new AttolSharedPreference(context);
        this.id = mySharedPreference.getKey("id");
    }
    /*
    public String SEND_REG(String url, String name, String password, String phone, String parent_phone, int location, int nationality) throws Exception {

//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("POST");
//      //  Date cDate = new Date();
//       // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
//        JSONObject jo = new JSONObject();
//        String name1= name.replace(" ", "");
//        String location1=location.replace(" ", "");

        InputStream is;

        ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();

        nameValuePairs1.add(new BasicNameValuePair("name", name));
        nameValuePairs1.add(new BasicNameValuePair("password", password));
        nameValuePairs1.add(new BasicNameValuePair("phone",phone));
        nameValuePairs1.add(new BasicNameValuePair("nationality", ""+1));
        nameValuePairs1.add(new BasicNameValuePair("address",""+ location));
        nameValuePairs1.add(new BasicNameValuePair("phone_father", parent_phone));


        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(url);

        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1,"UTF-8"));

        HttpResponse responce = httpclient.execute(httppost);

        HttpEntity entity = responce.getEntity();

        is = entity.getContent();

        BufferedReader bufr = new BufferedReader(new InputStreamReader(is,"iso-8859-1"), 8);

        StringBuilder sb = new StringBuilder();

        sb.append(bufr.readLine() + "\n");

        String line = "0";

        while ((line = bufr.readLine()) != null)

        {

            sb.append(line + "\n");

        }

        is.close();

        return  sb.toString();
    }

*/



    public String Reg(  String username ,  String phone ,  String country ,  String password , String city ,  String photo
            ,  int coins ,  int credit ,  int playstation_id)throws Exception {
         String url = context.getString(R.string.Server_ip) + "Register_Players?";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();


        jo.put("username", "nan");
        jo.put("phone","0987665431" );
        jo.put("city","man" );
        jo.put("country","uni" );
        jo.put("password", "1234");
        jo.put("coins", 0);
        jo.put("credit",0);
        jo.put("playstation_id", 0);
        jo.put("photo","aaa" );
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String RegisterGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");


        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //   con.setDoOutput(true); //remove it if we want to get
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }


}