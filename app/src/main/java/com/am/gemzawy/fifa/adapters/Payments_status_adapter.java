package com.am.gemzawy.fifa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.am.gemzawy.fifa.R;

import java.util.ArrayList;

public class Payments_status_adapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Integer> coins_number = new ArrayList<Integer>();//offer pic
    int [] status ,req_id;
    String [] date;
    Context context;

    public Payments_status_adapter(ArrayList<Integer> coins_number, int[] status,int [] req_id,String [] date, Context context) {
        this.coins_number=coins_number;
        this.status=status;
        this.req_id=req_id;
        this.date=date;
        this.context=context;

    }
    @Override
    public int getCount() {
        return coins_number.size();
    }

    @Override
    public Object getItem(int pos) {
        return coins_number.get(pos);
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
            view = inflater.inflate(R.layout.payment_list_status_view, null);
        }
        TextView coins_numberTV = view.findViewById(R.id.coins_p);
        TextView date_TV = view.findViewById(R.id.date_p);
        TextView statusTV = view.findViewById(R.id.status_p);

        coins_numberTV.setText("Amount : "+coins_number.get(position)  );
        date_TV.setText(date[position]);
        if(status[position]==0)
        {
            statusTV.setText("Pending");

        }
        else
        {
            statusTV.setText("Completed");

        }








        return view;
    }

}
