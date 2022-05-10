package com.am.gemzawy.fifa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.am.gemzawy.fifa.adapters.Settings_Adapter;

import java.util.ArrayList;

public class SettingsList extends AppCompatActivity {
ListView listView;
    String[] listItem;
    boolean[] listImages={true, false, true, false, true};
    private ArrayList<String> comp_text = new ArrayList<String>();//offer pic

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_list);
       listView=findViewById(R.id.list_setting);
       listView.setEnabled(false);
       listItem = getResources().getStringArray(R.array.staticText1Elements);
       for (int i=0;i<listItem.length;i++)
       {
           comp_text.add(listItem[i]);
       }
        listView.setEnabled(true);

        Settings_Adapter settings_adapter=new Settings_Adapter(comp_text,SettingsList.this);
       listView.setAdapter(settings_adapter);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              if(position==0)
              {
                  startActivity(new Intent(SettingsList.this,EditProfile.class));

              }
              else if(position==1)
              {
                  startActivity(new Intent(SettingsList.this,NotificationControlPage.class));

              }
              else if(position==2)
              {
                  startActivity(new Intent(SettingsList.this,Add_FifaID.class));

              }
              else if(position==3)
              {
                  startActivity(new Intent(SettingsList.this,Add_PubgID.class));

              }
              else if(position==4)
              {
                  startActivity(new Intent(SettingsList.this,Add_EmailID.class));

              }
              else if(position==5)
              {
                  startActivity(new Intent(SettingsList.this,ChangePass.class));

              }
              else if(position==6)
              {
                  startActivity(new Intent(SettingsList.this,Payout.class));

              }
              else if(position==7)
              {
                  startActivity(new Intent(SettingsList.this,Payment_list_status.class));
                  Toast.makeText(SettingsList.this,"7",Toast.LENGTH_LONG).show();
              }

           }
       });


  ;
    }
}
