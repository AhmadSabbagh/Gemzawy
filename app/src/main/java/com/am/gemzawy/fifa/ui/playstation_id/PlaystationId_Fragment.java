package com.am.gemzawy.fifa.ui.playstation_id;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.am.gemzawy.fifa.Api.Webservice;
import com.am.gemzawy.fifa.R;

public class PlaystationId_Fragment extends Fragment {
   public static EditText playstationET;
    Button add_bu,editBU;
    public static ProgressBar loading;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_playastation_id, container, false);
          playstationET = root.findViewById(R.id.playstation_id);
          playstationET.setEnabled(false);
          add_bu=root.findViewById(R.id.add_id_bu);
        add_bu=root.findViewById(R.id.edit_id_bu);
        editBU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_bu.setVisibility(View.VISIBLE);
                playstationET.setEnabled(true);
            }
        });

        loading=root.findViewById(R.id.loading2);
        //  loading.setVisibility(View.INVISIBLE);
         GetPlaystationID();
     add_bu.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(playstationET.getText().toString().equals("")) {
                 Toast.makeText(getContext(),"يجب ادخال قيمة ",Toast.LENGTH_SHORT).show();
             }
             else {
                 AddPlaystationID();
             }
         }
     });

        return root;
    }

    private void GetPlaystationID() {
        Webservice webservice = new Webservice();
        webservice.Get_Playstation_Id(getContext());
        loading.setVisibility(View.VISIBLE);


    }
    private void AddPlaystationID() {

            Webservice webservice = new Webservice();
            webservice.Add_Playstation_Id(getContext(), playstationET.getText().toString());

        loading.setVisibility(View.VISIBLE);


    }

}
