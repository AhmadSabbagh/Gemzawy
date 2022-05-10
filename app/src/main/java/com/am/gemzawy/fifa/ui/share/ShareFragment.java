package com.am.gemzawy.fifa.ui.share;

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

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        AttolSharedPreference attolSharedPreference=new AttolSharedPreference(getActivity());
        String app_link=getActivity().getResources().getString(R.string.share_text)+"\n"+attolSharedPreference.getKey("app_link");
        ///////////////////////////////////////
       /* Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String Subject = "Subject";
        String Link = app_link;
        String Title = "Title";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, Subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, Link);
        startActivity(Intent.createChooser(sharingIntent, Title));*/
        ///////////////////////////////
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Here is the share content body";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, app_link);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
        return root;
    }
}