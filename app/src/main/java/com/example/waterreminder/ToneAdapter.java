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


public class ToneAdapter extends RecyclerView.Adapter<ToneAdapter.MyClassView> {

    public static int selectedTone = -1;
    public ArrayList<Song> songArrayList = new ArrayList<>();
    Context activity;
    MediaPlayer mMediaPlayer;
    String songName;
    Database_Helper database_helper;
//    Personal_Data personal_data;

    public ToneAdapter(ArrayList<Song> songArrayList, Context act) {
        this.songArrayList = songArrayList;
        this.activity = act;
        database_helper = new Database_Helper(act);
//        personal_data=new Personal_Data();
//        personal_data.setTone(database_helper.getTone());
//        Log.e("setTone",database_helper.getTone()+ "");
    }

    @NonNull
    @Override
    public ToneAdapter.MyClassView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tone_layout, parent, false);
        return new ToneAdapter.MyClassView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToneAdapter.MyClassView holder, int position) {
//        Log.e("setTone in binder",(songArrayList.get(position).getUri() + "/" + songArrayList.get(position).getId()));
//
//        if((songArrayList.get(position).getUri() + "/" + songArrayList.get(position).getId()).equals(personal_data.getTone()))
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
        holder.tone.setText(songArrayList.get(position).getSongName());
        holder.tone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTone = position;
                songName = songArrayList.get(position).getUri() + "/" + songArrayList.get(position).getId();
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

            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
            }

            String path;
            for (Song song : songArrayList) {
                if ((song.getUri() + "/" + song.getId()).equals(songName)) {
                    Log.e("Songname:", songName);
                    Log.e("Songuri:", song.getSongName());

                    path = song.getUri() + "/" + song.getId();
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setDataSource(path);
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                    database_helper.updateTone(Uri.parse(path));
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

