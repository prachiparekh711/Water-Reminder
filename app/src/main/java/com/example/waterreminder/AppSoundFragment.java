package com.example.waterreminder;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppSoundFragment extends Fragment {

    public ArrayList<Song> songArrayList = new ArrayList<>();
    List<Song> audioList = new ArrayList<>();
    MediaPlayer mMediaPlayer;
    String defaultSongName;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;
    RecyclerView recuclerView;
    AppToneAdapter toneAdapter;
    String path;
    String id;
    Map<String, String> namemap = new HashMap<>();


    public AppSoundFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_sound, container, false);
        recuclerView = view.findViewById(R.id.appList);
        toneAdapter = new AppToneAdapter(getNotificationSounds(), getActivity().getBaseContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recuclerView.setLayoutManager(layoutManager);
        recuclerView.setAdapter(toneAdapter);
        return view;
    }

    public ArrayList<String> getNotificationSounds() {
//        MediaPlayer ring= MediaPlayer.create(getActivity(),R.raw.water);
//        ring.start();

        ArrayList<String> list = new ArrayList<>();

        list.add("water");
        list.add("watering");
        list.add("water_bubble");
        list.add("water_drop");
        list.add("water_droplet");
        list.add("water_drops");
        list.add("water_pouring");
        list.add("water_sms");
        list.add("water_sms_navin");

        return list;
    }


}