package com.am.gemzawy.fifa.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;
import com.am.gemzawy.fifa.R;

import java.util.ArrayList;

public class CompetitionAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Integer> comp_priceArray = new ArrayList<Integer>();//offer pic
    Context context;
    String [] date,name;
    int type;
    public CompetitionAdapter(ArrayList<Integer> comp_priceArray, String[] name, String[] date,int type,  Context context) {
        this.comp_priceArray=comp_priceArray;
        this.name=name;
        this.date=date;
        this.context=context;
       this.type=type;
    }
    @Override
    public int getCount() {
        return comp_priceArray.size();
    }

    @Override
    public Object getItem(int pos) {
        return comp_priceArray.get(pos);
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
            view = inflater.inflate(R.layout.competions_list, null);
        }
        TextView comp_nameTV, comp_dateTV, comp_priceTV;
        comp_nameTV = view.findViewById(R.id.comp_name_id);
        comp_dateTV = view.findViewById(R.id.comp_date_id);
        comp_priceTV = view.findViewById(R.id.comp_price_id);
        ImageView slide_img = view.findViewById(R.id.agentImage);
        comp_nameTV.setText(name[position]);
        comp_dateTV.setText(date[position]);
        if (comp_priceArray.get(position).equals(0)) {
            comp_priceTV.setText("لا توجد رسوم ");
        } else {
            comp_priceTV.setText("" + comp_priceArray.get(position));
        }
        if (type == 1) {
          //slide_img.setBackgroundResource(R.drawable.fifa_comp_adapter_list);
            AttolSharedPreference attolSharedPreference= new AttolSharedPreference((Activity) context);
            int av= Integer.parseInt(attolSharedPreference.getKey("android_ver"));
            if(av==1)
            {
                slide_img.setBackgroundResource(R.drawable.fifa222);


            }
            else
            {
                slide_img.setBackgroundResource(R.drawable.fifa_comp_adapter_list);
            }

        } else if (type == 2)
        {
           // slide_img.setBackgroundResource(R.drawable.pubg_comp_adapter_list);
            AttolSharedPreference attolSharedPreference= new AttolSharedPreference((Activity) context);
            int av= Integer.parseInt(attolSharedPreference.getKey("android_ver"));
            if(av==1)
            {
                slide_img.setBackgroundResource(R.drawable.pubg222);


            }
            else
            {
                slide_img.setBackgroundResource(R.drawable.pubg_comp_adapter_list);


            }
        }
        else
        {

        }







        return view;
    }

}
