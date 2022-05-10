package com.am.gemzawy.fifa.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;

import com.am.gemzawy.fifa.Add_EmailID;
import com.am.gemzawy.fifa.Add_FifaID;
import com.am.gemzawy.fifa.Add_PubgID;
import com.am.gemzawy.fifa.ChangePass;
import com.am.gemzawy.fifa.EditProfile;
import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;
import com.am.gemzawy.fifa.Language_chooser_Settings;
import com.am.gemzawy.fifa.NotificationControlPage;
import com.am.gemzawy.fifa.Payout;
import com.am.gemzawy.fifa.R;

import java.util.ArrayList;

public class Settings_Adapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> comp_text = new ArrayList<String>();//offer pic
  int av;
    Context context;

    public Settings_Adapter(ArrayList<String > comp_text , Context context) {
        this.comp_text=comp_text;

        this.context=context;
        AttolSharedPreference attolSharedPreference= new AttolSharedPreference((Activity) context);

        av= Integer.parseInt(attolSharedPreference.getKey("android_ver"));

    }
    @Override
    public int getCount() {
        return comp_text.size();
    }

    @Override
    public Object getItem(int pos) {
        return comp_text.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row, null);
        }
        Button comp_IdTV;
        comp_IdTV=view.findViewById(R.id.text_setting);
        comp_IdTV.setText(comp_text.get(position));
        comp_IdTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        context.startActivity(new Intent(context, EditProfile.class));
                        break;
                    case 1:
                        context.startActivity(new Intent(context, NotificationControlPage.class));
                        break;
                    case 2:
                        context.startActivity(new Intent(context, Add_FifaID.class));
                        break;
                    case 3:
                        context.startActivity(new Intent(context, Add_PubgID.class));
                        break;
                    case 4:
                        if(av!=1) {
                            context.startActivity(new Intent(context, Add_EmailID.class));
                        }
                        break;
                    case 5:
                        context.startActivity(new Intent(context, ChangePass.class));
                        break;
                    case 6:
                        context.startActivity(new Intent(context, Payout.class));
                        break;
                    case 7:
                        context.startActivity(new Intent(context, Language_chooser_Settings.class));
                        break;


                }
            }
        });










        return view;
    }

}


