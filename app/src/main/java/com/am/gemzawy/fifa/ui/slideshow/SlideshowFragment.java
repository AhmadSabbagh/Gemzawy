package com.am.gemzawy.fifa.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;
import com.am.gemzawy.fifa.MyCompetitions_List;
import com.am.gemzawy.fifa.R;

public class SlideshowFragment extends Fragment {
//public static ListView listView ;
//public static int []  comp_id;
//public static ProgressBar progressBar;
    private SlideshowViewModel slideshowViewModel;
    ImageView fifa,pubg;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        //listView=root.findViewById(R.id.list);
        //progressBar=root.findViewById(R.id.progressBarD);
//        final Webservice webservice = new Webservice();
//        webservice.Get_MY_Competitions(getActivity());
       // progressBar.setVisibility(View.VISIBLE);
        fifa=root.findViewById(R.id.fifa);
        pubg=root.findViewById(R.id.pubg);
        AttolSharedPreference attolSharedPreference= new AttolSharedPreference(getActivity());
        int av= Integer.parseInt(attolSharedPreference.getKey("android_ver"));
        if(av==1)
        {
            fifa.setBackgroundResource(R.drawable.fifa222);
            pubg.setBackgroundResource(R.drawable.pubg222);

        }
        else
        {
            fifa.setBackgroundResource(R.drawable.fifa_img_game_new);
            pubg.setBackgroundResource(R.drawable.pubg_img_game_new);

        }
        fifa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent intent= new Intent(getActivity(), MyCompetitions_List.class);
intent.putExtra("type",1);
getActivity().startActivity(intent);
            }
        });
        pubg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), MyCompetitions_List.class);
                intent.putExtra("type",2);
                getActivity().startActivity(intent);
            }
        });

        return root;
    }
}