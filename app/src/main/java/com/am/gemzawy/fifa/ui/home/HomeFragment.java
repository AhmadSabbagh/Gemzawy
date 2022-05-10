package com.am.gemzawy.fifa.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.am.gemzawy.fifa.Api.Webservice;
import com.am.gemzawy.fifa.Fifa_Competitions;
import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;
import com.am.gemzawy.fifa.Pubg_Competitions;
import com.am.gemzawy.fifa.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public static ImageView fifa,pubg;
    Animation animation;
int av;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        fifa = root.findViewById(R.id.fifa);
        pubg = root.findViewById(R.id.pubg);
//        animation = AnimationUtils.loadAnimation(getActivity(), R.animator.alpha_rotate_animation);
//        chose.setAnimation(animation);
        //Webservice webservice=new Webservice();
        //webservice.getPlayerInfo2(getActivity());
        AttolSharedPreference attolSharedPreference = new AttolSharedPreference(getActivity());
        String av_string = attolSharedPreference.getKey("android_ver");
        if(av_string!=null) {
            int av = Integer.parseInt(av_string);
            if (av_string != null) {
                if (av == 1) {
                    HomeFragment.fifa.setBackgroundResource(R.drawable.fifa222);
                    HomeFragment.pubg.setBackgroundResource(R.drawable.pubg222);

                } else {
                    HomeFragment.fifa.setBackgroundResource(R.drawable.fifa_img_game_new);
                    HomeFragment.pubg.setBackgroundResource(R.drawable.pubg_img_game_new);

                }
            }
        }
        else
        {

        }
        fifa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), Fifa_Competitions.class));

            }
        });
        pubg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), Pubg_Competitions.class));

            }
        });

        return root;
    }
}