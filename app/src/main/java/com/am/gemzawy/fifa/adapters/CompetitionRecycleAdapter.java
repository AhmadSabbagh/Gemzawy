package com.am.gemzawy.fifa.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.am.gemzawy.fifa.Free_Competition_Details;
import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;
import com.am.gemzawy.fifa.MatchScreenFinish;
import com.am.gemzawy.fifa.Pubg_Free_Competition_Details;
import com.am.gemzawy.fifa.R;

import java.util.ArrayList;

public class CompetitionRecycleAdapter extends RecyclerView.Adapter<CompetitionRecycleAdapter.ViewHolder> {

    private ArrayList<Integer> comp_priceArray = new ArrayList<Integer>();//offer pic
    Context context;
    String [] date,name;
    int type;
    int []comp_id;
    private LayoutInflater mInflater;
    int should_finish=0;

    // data is passed into the constructor
    public CompetitionRecycleAdapter(ArrayList<Integer> comp_priceArray, String[] name, String[] date, int type,int finish,int [] comp_id, Context context) {
        this.comp_priceArray=comp_priceArray;
        this.name=name;
        this.date=date;
        this.context=context;
        this.type=type;
        this.mInflater = LayoutInflater.from(context);
        this.comp_id=comp_id;
        this.should_finish=finish;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.competions_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.comp_nameTV.setText(name[position]);
        holder.comp_dateTV.setText(date[position]+": Cairo");
        if (comp_priceArray.get(position).equals(0)) {
            holder.comp_priceTV.setText("Free");
        } else {
            holder.comp_priceTV.setText(String.valueOf(comp_priceArray.get(position)+" Coins"));
        }
        if (type == 1) {
           // holder.slide_img.setBackgroundResource(R.drawable.fifa_comp_adapter_list);

            AttolSharedPreference attolSharedPreference= new AttolSharedPreference((Activity) context);
            int av= Integer.parseInt(attolSharedPreference.getKey("android_ver"));
            if(av==1)
            {
                holder.slide_img.setBackgroundResource(R.drawable.fifa222);


            }
            else
            {
                holder.slide_img.setBackgroundResource(R.drawable.fifa_comp_adapter_list);


            }
            holder.slide_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Free_Competition_Details.class);
                    intent.putExtra("comp_id",comp_id[position]);
                    intent.putExtra("comp_price",comp_priceArray.get(position));

                    context.startActivity(intent);
                }
            });


        } else if (type == 2)
        {
           // holder.slide_img.setBackgroundResource(R.drawable.pubg_comp_adapter_list);

            AttolSharedPreference attolSharedPreference= new AttolSharedPreference((Activity) context);
            int av= Integer.parseInt(attolSharedPreference.getKey("android_ver"));
            if(av==1)
            {
                holder.slide_img.setBackgroundResource(R.drawable.pubg222);


            }
            else
            {
                holder.slide_img.setBackgroundResource(R.drawable.pubg_comp_adapter_list);


            }
            holder.slide_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Pubg_Free_Competition_Details.class);
                    intent.putExtra("comp_id",comp_id[position]);
                    intent.putExtra("comp_price",comp_priceArray.get(position));

                    context.startActivity(intent);
                }
            });
            if(should_finish==1) {
                holder.finsh_pubg_bu.setVisibility(View.VISIBLE);
            }
            holder.finsh_pubg_bu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent (context, MatchScreenFinish.class);
                    intent.putExtra("comp_id",comp_id[position]);
                    intent.putExtra("type",2);

                    context.startActivity(intent);
                }
            });
        }
        else
        {

        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return comp_priceArray.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView comp_nameTV, comp_dateTV, comp_priceTV;
        ImageView slide_img;
        Button finsh_pubg_bu;

        public ViewHolder(View itemView) {
            super(itemView);
            comp_nameTV = itemView.findViewById(R.id.comp_name_id);
            comp_dateTV = itemView.findViewById(R.id.comp_date_id);
            comp_priceTV = itemView.findViewById(R.id.comp_price_id);
             slide_img = itemView.findViewById(R.id.agentImage);
            finsh_pubg_bu = itemView.findViewById(R.id.finish_bu_pubg_comp);



        }


        @Override
        public void onClick(View v) {

        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return String.valueOf(comp_priceArray.get(id));
    }

    // allows clicks events to be caught


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
