package com.example.waterreminder;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppToneAdapter extends RecyclerView.Adapter<AppToneAdapter.MyClassView> {

    public static int selectedTone = -1;
    public ArrayList<String> songArrayList = new ArrayList<>();
    public ArrayList<String> songNameArrayList = new ArrayList<>();
    Context activity;
    MediaPlayer mediaPlayer;
    String songName;
    Database_Helper database_helper;
//    Personal_Data personal_data;

    public AppToneAdapter(ArrayList<String> songArrayList, Context act) {
        this.songArrayList = songArrayList;
        this.activity = act;
        database_helper = new Database_Helper(act);
        songNameArrayList.add("Water");
        songNameArrayList.add("Watering");
        songNameArrayList.add("Water Bubble");
        songNameArrayList.add("Water Drop");
        songNameArrayList.add("Water Droplet");
        songNameArrayList.add("Water Drops");
        songNameArrayList.add("Water Pouring");
        songNameArrayList.add("Water SMS");
        songNameArrayList.add("Water New");
//        personal_data=new Personal_Data();
//        personal_data.setTone(database_helper.getTone());
//        Log.e("setTone",database_helper.getTone()+ "");

    }

    @NonNull
    @Override
    public AppToneAdapter.MyClassView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apptone_layout, parent, false);
        return new AppToneAdapter.MyClassView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppToneAdapter.MyClassView holder, int position) {
//        Log.e("setTone in binder",songArrayList.get(position)+ "");
//
//        if(("android.resource://com.example.waterreminder/raw/" + songName).equals(personal_data.getTone()))
//        {
//            holder.toneSelect.setVisibility(View.VISIBLE);
//        }
        if (selectedTone == position) {
            if (holder.toneSelect.getVisibility() == View.VISIBLE) {
                holder.toneSelect.setVisibility(View.GONE);
            } else {
                holder.toneSelect.setVisibility(View.VISIBLE);
            }
        } else {
            holder.toneSelect.setVisibility(View.GONE);
        }

        holder.tone.setText(songNameArrayList.get(position));
        holder.tone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTone = position;
                songName = songArrayList.get(position);
                Log.e("Songname:", songName);
//                notifyDataSetChanged();
                playAndStopRingtone();
            }

        });

    }

    @Override
    public int getItemCount() {
//
        return songArrayList.size();
    }

    private void playAndStopRingtone() {
        try {

            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            for (String song : songArrayList) {
                if (song.equals(songName)) {
//                    Log.e("Songname:",songName);

                    int resID = activity.getResources().getIdentifier(songName, "raw", activity.getPackageName());

                    mediaPlayer = MediaPlayer.create(activity, resID);
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();

                    } else {
                        mediaPlayer.start();
                    }
                    database_helper.updateTone(Uri.parse("android.resource://com.example.waterreminder/raw/" + songName));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyClassView extends RecyclerView.ViewHolder {

        ImageView toneSelect;
        TextView tone;

        public MyClassView(@NonNull View itemView) {
            super(itemView);
            toneSelect = itemView.findViewById(R.id.toneSelect);
            tone = itemView.findViewById(R.id.toneName);

        }
    }
}