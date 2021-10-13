package com.example.waterreminder;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.waterreminder.ReminderAdapter.updatePosition;

public class AllSchedules extends AppCompatActivity {

    public static ArrayList<ReminderData> reminderDataArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    Database_Helper database;
    ReminderAdapter reminderAdapter;
    RelativeLayout newReminder;
    SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm aa");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_schedules);

        database = new Database_Helper(AllSchedules.this);
        recyclerView = findViewById(R.id.scheduleRecycler);

        reminderDataArrayList = database.getAllReminderData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(AllSchedules.this);
        recyclerView.setLayoutManager(layoutManager);
        reminderAdapter = new ReminderAdapter(reminderDataArrayList, AllSchedules.this);
        recyclerView.setAdapter(reminderAdapter);

        newReminder = findViewById(R.id.newReminder);
        newReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker = new TimePickerDialog(AllSchedules.this, R.style.CustomPickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        Calendar datetime = Calendar.getInstance();
                        datetime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        datetime.set(Calendar.MINUTE, selectedMinute);
                        Log.e("Now :", dateFormat1.format(datetime.getTime()));
                        database.insertReminderData(new ReminderData(dateFormat1.format(datetime.getTime()), 1, "Mon,Tue,Wed,Thu,Fri,Sat,Sun"));
                        database = new Database_Helper(AllSchedules.this);

                        reminderAdapter = new ReminderAdapter(reminderDataArrayList, AllSchedules.this);
                        recyclerView.setAdapter(reminderAdapter);

                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updatePosition = -1;
    }
}