package com.am.gemzawy.fifa.HelperClass;

/**
 * Created by ahmad on 3/29/2018.
 */


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.am.gemzawy.fifa.Services.MyFirebaseMessagingService;


public class AttolSharedPreference {

    private Activity activity;
    private MyFirebaseMessagingService activity1;

    public AttolSharedPreference(Activity context) {
        this.activity = context;
    }



   public AttolSharedPreference(MyFirebaseMessagingService context) {
       this.activity1 = context;
   }

    public void setKey(String key, String value) {
        SharedPreferences sharedPref = activity.getSharedPreferences("attolPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(key, value);
        editor.commit();
    }
    public void setKey1(String key, String value) {
        SharedPreferences sharedPref = activity1.getSharedPreferences("attolPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(key, value);
        editor.commit();
    }

    public void setKey(String key, boolean value) {
        SharedPreferences sharedPref = activity.getSharedPreferences("attolPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String getKey(String key) {
        SharedPreferences sharedPref = activity.getSharedPreferences("attolPref", Context.MODE_PRIVATE);

        return sharedPref.getString(key, null);

    }
    public String getKey1(String key) {
        SharedPreferences sharedPref = activity1.getSharedPreferences("attolPref", Context.MODE_PRIVATE);

        return sharedPref.getString(key, null);

    }

    public boolean getBooleanKey(String key) {
        SharedPreferences sharedPref = activity.getSharedPreferences("attolPref", Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, true);
    }

//    public void setBudge(String key, int value) {
//        SharedPreferences sharedPref = activity1.getSharedPreferences("attolPref", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//
//        editor.putInt(key, value);
//        editor.commit();
//    }
//
//    public int getBudge(String key) {
//        SharedPreferences sharedPref = activity1.getSharedPreferences("attolPref", Context.MODE_PRIVATE);
//
//        return sharedPref.getInt(key, 0);
//
//    }

    public void setBudge1(String key, int value) {
        SharedPreferences sharedPref = activity.getSharedPreferences("attolPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(key, value);
        editor.commit();
    }


}
