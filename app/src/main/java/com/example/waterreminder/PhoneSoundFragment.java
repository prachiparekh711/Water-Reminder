package com.example.waterreminder;

import android.app.Fragment;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PhoneSoundFragment extends Fragment {


    RecyclerView recuclerView;
    ToneAdapter toneAdapter;


    public PhoneSoundFragment() {
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
        View view = inflater.inflate(R.layout.fragment_phone_sound, container, false);


        recuclerView = view.findViewById(R.id.phoneList);
        toneAdapter = new ToneAdapter(getNotificationSounds(), getActivity().getBaseContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recuclerView.setLayoutManager(layoutManager);
        recuclerView.setAdapter(toneAdapter);
        return view;
    }

    public ArrayList<Song> getNotificationSounds() {
        RingtoneManager manager = new RingtoneManager(getActivity());
        manager.setType(RingtoneManager.TYPE_NOTIFICATION);
        Cursor cursor = manager.getCursor();

        ArrayList<Song> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String id = cursor.getString(RingtoneManager.ID_COLUMN_INDEX);
            String uri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);

            list.add(new Song(name, id, uri));

        }

        return list;
    }


}