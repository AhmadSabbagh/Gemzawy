package com.am.gemzawy.fifa.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.am.gemzawy.fifa.CompetatorId;
import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;
import com.am.gemzawy.fifa.MatchScreenFinish;
import com.am.gemzawy.fifa.R;

import java.util.ArrayList;

public class MyCompetitionAdapter extends RecyclerView.Adapter<MyCompetitionAdapter.ViewHolder> {

    private ArrayList<String> comp_dateArray = new ArrayList<String>();//offer pic
    Context context;
    String [] competator_name;
    int type;
    int []comp_id,copmetator_id,round_id;
    private LayoutInflater mInflater;

    // data is passed into the constructor


    public MyCompetitionAdapter(ArrayList<String> comp_dateArray, int[] comp_id,
                                int[] copmetator_id, int[] round_id,String [] competator_name
            ,Context activity) {
        this.context=activity;
        this.comp_dateArray=comp_dateArray;
        this.comp_id=comp_id;
        this.competator_name=competator_name;
        this.mInflater = LayoutInflater.from(context);
        this.copmetator_id=copmetator_id;
        this.round_id=round_id;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_comp_recycle_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyCompetitionAdapter.ViewHolder holder, int position) {
        holder.comp_nameTV.setText(competator_name[position]);
        holder.comp_dateTV.setText(comp_dateArray.get(position));

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
        String lan=attolSharedPreference.getKey("language");
        if(lan.equals("ar"))
        {
            holder.finish.setBackgroundResource(R.drawable.finish_game_bu);
            holder.get_bu.setBackgroundResource(R.drawable.get_competator_info_bu);


        }
        else
        {
            holder.finish.setBackgroundResource(R.drawable.finish_game_en);
            holder.get_bu.setBackgroundResource(R.drawable.get_competator_info);


        }
            holder.finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent (context, MatchScreenFinish.class);
                    intent.putExtra("comp_id",comp_id[position]);
                    intent.putExtra("round_id",round_id[position]);
                    intent.putExtra("competator_id",copmetator_id[position]);
                    context.startActivity(intent);
                }
            });
        holder.get_bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, CompetatorId.class);
                intent.putExtra("id",copmetator_id[position]);
                context.startActivity(intent);

            }
        });



    }

    // total number of rows
    @Override
    public int getItemCount() {
        return comp_dateArray.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView comp_nameTV, comp_dateTV;
        ImageView slide_img;
        ImageView get_bu,finish;

        public ViewHolder(View itemView) {
            super(itemView);
            comp_nameTV = itemView.findViewById(R.id.comp_name_id);
            comp_dateTV = itemView.findViewById(R.id.comp_date_id);
            slide_img = itemView.findViewById(R.id.agentImage);
            get_bu = itemView.findViewById(R.id.get_competator_bu);
            finish = itemView.findViewById(R.id.finish_bu_id);


        }


        @Override
        public void onClick(View v) {

        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return String.valueOf(comp_dateArray.get(id));
    }

    // allows clicks events to be caught


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
