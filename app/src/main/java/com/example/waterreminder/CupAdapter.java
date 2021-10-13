package com.example.waterreminder;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class CupAdapter extends RecyclerView.Adapter<CupAdapter.MyClassView> {

    public static int selectedPosition = -1;

    public ArrayList<CupDetails> cupDetailsArrayList = new ArrayList<>();
    Context activity;
    Database_Helper database_helper;
    Personal_Data data;

    public CupAdapter(ArrayList<CupDetails> cupDetailsArrayList, Context act) {
        this.cupDetailsArrayList = cupDetailsArrayList;
        this.activity = act;
        database_helper = new Database_Helper(activity);
        data = database_helper.getAllPersonalData();
    }

    @NonNull
    @Override
    public MyClassView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cup_layout, parent, false);
        return new MyClassView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyClassView holder, int position) {

        if (selectedPosition == position) {
            holder.img_cup.setImageDrawable(cupDetailsArrayList.get(position).getFullcup());
            SharedPreferences sp1 = activity.getSharedPreferences("pref1", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp1.edit();
            editor.putInt("value", selectedPosition);
            editor.apply();
        } else {
            holder.img_cup.setImageDrawable(cupDetailsArrayList.get(position).getEmptyCup());
        }
        if (data.getWeightIn().equals("Kg")) {
            holder.cup_text.setText(cupDetailsArrayList.get(position).getCupML());
        } else {
            holder.cup_text.setText(cupDetailsArrayList.get(position).getCupOZ());
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
//        Log.e ( "size of list:" , String.valueOf ( cupMLList.size ( ) ) );
        return cupDetailsArrayList.size();
    }

    public class MyClassView extends RecyclerView.ViewHolder {

        ImageView img_cup;
        TextView cup_text;
        RelativeLayout relativeLayout;

        public MyClassView(@NonNull View itemView) {
            super(itemView);
            img_cup = itemView.findViewById(R.id.c1);

            relativeLayout = itemView.findViewById(R.id.cr1);
            cup_text = itemView.findViewById(R.id.ct1);

        }
    }
}
