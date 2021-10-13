package com.example.waterreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.MyClassView> {

    public ArrayList<CupHistory> cupHistoryArrayList = new ArrayList<>();
    Context context;
    Database_Helper database_helper;
    Personal_Data data;
    private BtnClickListener mClickListener = null;

    public RecordsAdapter(Context c, ArrayList<CupHistory> cupDetailsArrayList, BtnClickListener listener) {
        this.context = c;
        this.cupHistoryArrayList = cupDetailsArrayList;
        mClickListener = listener;
        database_helper = new Database_Helper(context);
        data = database_helper.getAllPersonalData();
    }

    @NonNull
    @Override
    public MyClassView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.records_layout, parent, false);
        return new MyClassView(view);
    }

    @Override
    public int getItemCount() {
        return cupHistoryArrayList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyClassView holder, int position) {

        holder.cup1.setImageDrawable(cupHistoryArrayList.get(position).getHistoryCup());
        holder.time1.setText(cupHistoryArrayList.get(position).getHistoryTime());

        if (data.getWeightIn().equals("Kg")) {
            holder.ml1.setText(cupHistoryArrayList.get(position).getHistoryML());
        } else {
            holder.ml1.setText(cupHistoryArrayList.get(position).getHistoryOZ());
        }

        holder.delete1.setOnClickListener(v -> {

            if (mClickListener != null)
                mClickListener.onBtnClick(position);

        });

    }

    public interface BtnClickListener {
        void onBtnClick(int position);
    }

    public class MyClassView extends RecyclerView.ViewHolder {

        ImageView cup1, delete1;
        TextView time1, ml1;
        RelativeLayout rel;

        public MyClassView(@NonNull View itemView) {
            super(itemView);
            cup1 = itemView.findViewById(R.id.cup1);
            delete1 = itemView.findViewById(R.id.delete1);
            ml1 = itemView.findViewById(R.id.ml1);
            rel = itemView.findViewById(R.id.rel);
            time1 = itemView.findViewById(R.id.time1);

        }
    }

}
