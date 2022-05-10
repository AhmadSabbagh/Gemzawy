package com.am.gemzawy.fifa.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.am.gemzawy.fifa.Payments.StripePayments;
import com.am.gemzawy.fifa.R;

import java.util.ArrayList;

public class CoinsAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Integer> coins_number = new ArrayList<Integer>();//offer pic
    int [] coins_price ,coins_id;
    Context context;

    public CoinsAdapter(ArrayList<Integer> coins_number, int[] coins_price,int [] coins_id, Context context) {
        this.coins_number=coins_number;
        this.coins_price=coins_price;
        this.coins_id=coins_id;
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
            view = inflater.inflate(R.layout.coins_listview, null);
        }
        TextView coins_numberTV = view.findViewById(R.id.coins_number);
        TextView coins_priceTV = view.findViewById(R.id.coins_price);
        Button buy= view.findViewById(R.id.buy_bu);
        coins_numberTV.setText(""+coins_number.get(position));
        coins_priceTV.setText(""+coins_price[position]+"$");
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, StripePayments.class);
                intent.putExtra("id",coins_id[position]);
                intent.putExtra("number",coins_number.get(position));
                intent.putExtra("type",1);
                intent.putExtra("price",coins_price[position]);


                context.startActivity(intent);
            }
        });






        return view;
    }

}
