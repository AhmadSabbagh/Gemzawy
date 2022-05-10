package com.am.gemzawy.fifa.ui.matches;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.am.gemzawy.fifa.Api.Webservice;
import com.am.gemzawy.fifa.R;

public class MatchesFragment extends Fragment {
    public  static ProgressBar loading;
    public  static RecyclerView listView;
    public static Object[] competator_id;
    public static int [] comp_id;
    public static int [] round_id;
    public static String [] comp_date;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_matches, container, false);
        loading=root.findViewById(R.id.loading);
        listView=root.findViewById(R.id.list_mr);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        listView.setLayoutManager(layoutManager);
          loading.setVisibility(View.INVISIBLE);
        Webservice webservice=new Webservice();
        webservice.Get_My_Matches(getContext());
        loading.setVisibility(View.INVISIBLE);
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             Intent intent = new Intent (getContext(), MatchScreenFinish.class);
             intent.putExtra("comp_id",comp_id[position]);
                intent.putExtra("round_id",round_id[position]);
                int compet_id= (int) competator_id[position];
                intent.putExtra("competator_id",compet_id);
                startActivity(intent);
            }
        });*/
        return root;
    }
}
