package com.am.gemzawy.fifa.ui.logout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;
import com.am.gemzawy.fifa.R;
import com.am.gemzawy.fifa.ui.home.HomeViewModel;
import com.am.gemzawy.fifa.ui.login.LoginActivity;
import com.google.firebase.messaging.FirebaseMessaging;

public class Log_Out extends Fragment {

    private HomeViewModel homeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =

            ViewModelProviders.of(this).get(HomeViewModel.class);
            final View root = inflater.inflate(R.layout.logout, container, false);
        AlertDialog.Builder a_builder = new AlertDialog.Builder(getActivity());
        a_builder.setMessage(getResources().getString(R.string.sure))
                .setCancelable(true)
                .setPositiveButton("LogOut", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AttolSharedPreference attolSharedPreference=new AttolSharedPreference(getActivity());
                        String user_id =attolSharedPreference.getKey("id");
                        String fifa_noti_status=attolSharedPreference.getKey("fifa_status");
                        attolSharedPreference.setKey("id","0");
                        attolSharedPreference.setKey("username","0");
                        attolSharedPreference.setKey("playstation_id","NoIdYet");
                        attolSharedPreference.setKey("pubg_id","NoIdYet");
                        FirebaseMessaging.getInstance ( ).unsubscribeFromTopic ("public");
                        FirebaseMessaging.getInstance ( ).unsubscribeFromTopic (user_id+"_user");
                        FirebaseMessaging.getInstance ( ).unsubscribeFromTopic ("Fifa");
                        FirebaseMessaging.getInstance ( ).unsubscribeFromTopic ("pubg");
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.setTitle("");


        alert.show();



        return root;
    }
}