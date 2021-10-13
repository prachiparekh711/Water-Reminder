package com.example.waterreminder;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import de.nitri.slidingtoggleswitch.SlidingToggleSwitchView;
import de.nitri.slidingtoggleswitch.SlidingToggleSwitchView.OnToggleListener;

public class ReminderSound extends AppCompatActivity implements OnToggleListener {

    private final static String TAG_FRAGMENT1 = "f1";
    private final static String TAG_FRAGMENT2 = "f2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_sound);

        Fragment AppSoundFragment = new AppSoundFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, AppSoundFragment); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();

    }

    @Override
    public void onToggle(int result) {

        if (result == SlidingToggleSwitchView.LEFT_SELECTED) {
            Log.e("Selected is  ", "Left");
            Fragment AppSoundFragment = new AppSoundFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, AppSoundFragment, TAG_FRAGMENT1); // give your fragment container id in first parameter
            transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
            transaction.commit();
        } else {
            Log.e("Selected is  ", "Right");
            Fragment PhoneSoundFragment = new PhoneSoundFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, PhoneSoundFragment, TAG_FRAGMENT2); // give your fragment container id in first parameter
            transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ReminderSound.this, TabActivity.class);
        intent.putExtra("index", 2);
        startActivity(intent);

    }
}