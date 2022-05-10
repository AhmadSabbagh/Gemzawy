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

import com.am.gemzawy.fifa.CompetatorId;
import com.am.gemzawy.fifa.MatchScreenFinish;
import com.am.gemzawy.fifa.R;

import java.util.ArrayList;

public class MatchesAdapter  extends BaseAdapter implements ListAdapter {
    private ArrayList<String> comp_date = new ArrayList<String>();//offer pic
    private ArrayList<Integer> competator_id = new ArrayList<Integer>();//offer pic

    Context context;
    int [] comp_id,round_id;
    public MatchesAdapter(ArrayList<String > comp_date, int [] comp_id, ArrayList<Integer > competator_id,int [] round_id , Context context) {
        this.comp_date=comp_date;
        this.comp_id=comp_id;
        this.competator_id=competator_id;
        this.round_id=round_id;
        this.context=context;

    }
    @Override
    public int getCount() {
        return comp_date.size();
    }

    @Override
    public Object getItem(int pos) {
        return comp_date.get(pos);
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
            view = inflater.inflate(R.layout.matches_list_view, null);
        }
        TextView comp_IdTV,comp_DateTV,competatorIdTV;
        comp_IdTV=view.findViewById(R.id.comp_id);
        comp_DateTV=view.findViewById(R.id.comp_date_id);
        //competatorIdTV=view.findViewById(R.id.competator_id);
        comp_IdTV.setText(""+comp_id[position]);
        //competatorIdTV.setText(""+competator_id.get(position));
        comp_DateTV.setText("" +comp_date.get(position));
        Button get_competator_id,Finish_BU;
        get_competator_id=view.findViewById(R.id.get_id_bu);
        Finish_BU=view.findViewById(R.id.finishBU);
        get_competator_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get id
                Intent intent= new Intent(context, CompetatorId.class);
                intent.putExtra("id",competator_id.get(position));
                context.startActivity(intent);
            }
        });
        Finish_BU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, MatchScreenFinish.class);
                intent.putExtra("comp_id",comp_id[position]);
                intent.putExtra("round_id",round_id[position]);
                int compet_id= (int) competator_id.get(position);
                intent.putExtra("competator_id",competator_id.get(position));
                context.startActivity(intent);
            }
        });









        return view;
    }

}

