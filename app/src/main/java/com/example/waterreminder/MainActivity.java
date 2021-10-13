package com.example.waterreminder;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;
    public static ArrayList<ReminderData> reminderDataArrayList = new ArrayList<>();
    ImageView letsgo;
    SharedPreferences.Editor editor;
    Boolean bool;
    SharedPreferences sp;
    Database_Helper database_helper;
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().hide();

        database_helper = new Database_Helper(MainActivity.this);
        reminderDataArrayList = database_helper.getAllReminderData();


        sp = getSharedPreferences("pref", MODE_PRIVATE);
        editor = sp.edit();
        editor.putBoolean("first", true);

        bool = sp.getBoolean("first", true);
        if (bool) {
            setContentView(R.layout.activity_main);
            letsgo = findViewById(R.id.letsGo);
            letsgo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(MainActivity.this, GenderPage1.class);
                    startActivity(in);
                }
            });
        } else {
            Intent in = new Intent(MainActivity.this, TabActivity.class);
            startActivity(in);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}