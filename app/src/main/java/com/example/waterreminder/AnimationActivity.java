package com.example.waterreminder;

import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.waterreminder.Service.MyService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.waterreminder.GenderPage1.gender;
import static com.example.waterreminder.GenderPage1.sleepTime;
import static com.example.waterreminder.GenderPage1.unitIn;
import static com.example.waterreminder.GenderPage1.wakeUpTime;
import static com.example.waterreminder.GenderPage1.weightIn;
import static com.example.waterreminder.GenderPage1.weightKg;
import static com.example.waterreminder.GenderPage1.weightLBS;


public class AnimationActivity extends AppCompatActivity {

    public static int[] neededML = new int[101];
    public static int[] neededOZ = new int[101];
    public static int[] oneTimeNeededML = new int[101];
    public static int[] oneTimeNeededOZ = new int[101];
    public static int NeededGlassNo = 2;
    public static int TotalGlassPerDay = 2;
    private final String TAG = "AnimationActivity";
    TextView skip;
    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
    SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm aa");
    Database_Helper database;
    ViewPager mViewPager;

    // images array
    String[] images = {"file:///android_asset/one.gif", "file:///android_asset/two.gif", "file:///android_asset/three.gif", "file:///android_asset/four.gif"};

    // Creating Object of ViewPagerAdapter
    AnimTabAdapter mViewPagerAdapter;
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        this.getSupportActionBar().hide();
        skip = findViewById(R.id.skip);
        serviceIntent = new Intent(this, MyService.class);
        database = new Database_Helper(AnimationActivity.this);
        calculateML();
        Log.e("ML needed :", String.valueOf(neededML[weightKg]));
        Log.e("OZ needed :", String.valueOf(neededOZ[weightKg]));
        calculateOneTimeNeededML(neededML[weightKg]);
        Log.e("1 time ML  :", String.valueOf(oneTimeNeededML[weightKg]));
        Log.e("1 time OZ  :", String.valueOf(oneTimeNeededOZ[weightKg]));
        try {
            calculateAllTime(oneTimeNeededML[weightKg], neededML[weightKg]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        database.deleteTable3();
        database.insertPersonalData(new Personal_Data(gender, weightKg, neededML[weightKg], oneTimeNeededML[weightKg], TotalGlassPerDay, weightIn, wakeUpTime, sleepTime, "yes", unitIn, neededOZ[weightKg], oneTimeNeededOZ[weightKg], weightLBS));
        database.updateTone(RingtoneManager.getActualDefaultRingtoneUri(AnimationActivity.this, RingtoneManager.TYPE_NOTIFICATION));
        database.updateMode(1);
//        Uri uri=RingtoneManager.getActualDefaultRingtoneUri(context,RingtoneManager.TYPE_NOTIFICATION);
//        RingtoneManager.getActualDefaultRingtoneUri(mcontext,RingtoneManager.TYPE_NOTIFICATION);

        mViewPager = findViewById(R.id.view_pager);

        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new AnimTabAdapter(AnimationActivity.this, images);

        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(AnimationActivity.this, TabActivity.class);
                startActivity(in);
            }
        });
        startService();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(AnimationActivity.this, TabActivity.class);
        startActivity(in);
    }

    public void calculateML() {
        int weigh = weightKg;

        if (gender == "Male") {
            int x = 1100;
            if (weigh <= 44) {
                for (int i = 1; i <= 44; i++) {
                    neededML[i] = 1100;
                    neededOZ[i] = (int) (neededML[i] / 29.574);
                }
            } else if (weigh >= 45 && weigh <= 52) {

                for (int i = 45; i <= 52; i++) {
                    neededML[i] = x + 50;
                    neededOZ[i] = (int) (neededML[i] / 29.574);
                    x = x + 50;
                }
            } else if (weigh >= 53 && weigh <= 70) {
                x = 1500;
                for (int i = 53; i <= 70; i++) {
                    neededML[i] = x + 40;
                    neededOZ[i] = (int) (neededML[i] / 29.574);
                    x = x + 40;
                }
            } else if (weigh >= 71 && weigh <= 80) {
                x = 2240;
                for (int i = 71; i <= 80; i++) {
                    neededML[i] = x + 30;
                    neededOZ[i] = (int) (neededML[i] / 29.574);
                    x = x + 30;
                }
            } else if (weigh >= 81 && weigh <= 90) {
                x = 2510;
                for (int i = 81; i <= 90; i++) {
                    neededML[i] = x + 20;
                    neededOZ[i] = (int) (neededML[i] / 29.574);
                    x = x + 20;
                }
            } else if (weigh >= 91 && weigh <= 100) {
                x = 2730;
                for (int i = 91; i <= 100; i++) {
                    neededML[i] = x + 10;
                    neededOZ[i] = (int) (neededML[i] / 29.574);
                    x = x + 10;
                }
            }
        }
        if (gender == "Female") {
            int x = 990;
            if (weigh <= 44) {
                for (int i = 1; i <= 44; i++) {
                    neededML[i] = 990;
                    neededOZ[i] = (int) (neededML[i] / 29.574);
                }
            } else if (weigh >= 45 && weigh <= 52) {

                for (int i = 45; i <= 52; i++) {
                    neededML[i] = x + 50;
                    neededOZ[i] = (int) (neededML[i] / 29.574);
                    x = x + 50;
                }
            } else if (weigh >= 53 && weigh <= 70) {
                x = 1350;
                for (int i = 53; i <= 70; i++) {
                    neededML[i] = x + 40;
                    neededOZ[i] = (int) (neededML[i] / 29.574);
                    x = x + 40;
                }
            } else if (weigh >= 71 && weigh <= 80) {
                x = 1990;
                for (int i = 71; i <= 80; i++) {
                    neededML[i] = x + 30;
                    neededOZ[i] = (int) (neededML[i] / 29.574);
                    x = x + 30;
                }
            } else if (weigh >= 81 && weigh <= 90) {
                x = 2270;
                for (int i = 81; i <= 90; i++) {
                    neededML[i] = x + 20;
                    neededOZ[i] = (int) (neededML[i] / 29.574);
                    x = x + 20;
                }
            } else if (weigh >= 91 && weigh <= 100) {
                x = 2530;
                for (int i = 91; i <= 100; i++) {
                    neededML[i] = x + 10;
                    neededOZ[i] = (int) (neededML[i] / 29.574);
                    x = x + 10;
                }
            }
        }
    }

    public void calculateOneTimeNeededML(int totalML) {
        String wakeUpTime = GenderPage1.wakeUpTime;
        String bedTime = sleepTime;
        int totalHours = 0; // total hours = difference betwwn wakeup time & bed time
        Log.e("Wake up Time before :", wakeUpTime);
        Log.e("bed up Time before :", bedTime);

        try {

            long difference = dateFormat.parse(wakeUpTime).getTime() - dateFormat.parse(bedTime).getTime();
            if (difference < 0) {
                Date dateMax = dateFormat.parse("24:00");
                Date dateMin = dateFormat.parse("00:00");
                difference = (dateMax.getTime() - dateFormat.parse(wakeUpTime).getTime()) + (dateFormat.parse(bedTime).getTime() - dateMin.getTime());
                Log.e("log_tag", "DateMax: " + dateMax.getTime() + ", DateMin: " + dateMin.getTime() + " , Difference :" + difference);
            }
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            Log.e("log_tag", "Hours: " + hours + ", Mins: " + min);

            totalHours = hours;

            oneTimeNeededML[weightKg] = (totalML / totalHours) + (totalML / (totalHours * 2));
            oneTimeNeededOZ[weightKg] = (int) (oneTimeNeededML[weightKg] / 29.574);
            Log.e("oneTimeNeededML :", "for weight: " + weightKg + ", is: " + oneTimeNeededML[weightKg]);
            Log.e("oneTimeNeededOZ :", "for weight: " + weightLBS + ", is: " + oneTimeNeededOZ[weightKg]);

            if (oneTimeNeededML[weightKg] <= 100) NeededGlassNo = 0;
            if (oneTimeNeededML[weightKg] > 100 && oneTimeNeededML[weightKg] <= 125)
                NeededGlassNo = 1;
            if (oneTimeNeededML[weightKg] > 125 && oneTimeNeededML[weightKg] <= 150)
                NeededGlassNo = 2;
            if (oneTimeNeededML[weightKg] > 150 && oneTimeNeededML[weightKg] <= 175)
                NeededGlassNo = 3;
            if (oneTimeNeededML[weightKg] > 175 && oneTimeNeededML[weightKg] <= 200)
                NeededGlassNo = 4;
            if (oneTimeNeededML[weightKg] > 200 && oneTimeNeededML[weightKg] <= 300)
                NeededGlassNo = 5;
            if (oneTimeNeededML[weightKg] > 300) NeededGlassNo = 6;

            Log.e("NeededGlassNo :", NeededGlassNo + "");

        } catch (ParseException e) {
        }
    }

    public void calculateAllTime(int oneTimeNeededML, int totalML) throws ParseException {

        Double total = Double.parseDouble(String.valueOf(totalML));
        Double total1 = Double.parseDouble(String.valueOf(oneTimeNeededML));

        double val = (total / total1);

        TotalGlassPerDay = (int) Math.round(val);
        Log.e("TotalGlassPerDay :", String.valueOf(TotalGlassPerDay));

        Calendar cal = Calendar.getInstance();

        Date nextTime = dateFormat.parse(wakeUpTime);

        database.clearAllReminder();
        for (int i = 0; i < TotalGlassPerDay; i++) {
            cal.setTime(nextTime);
            cal.add(Calendar.HOUR, 1);
            cal.add(Calendar.MINUTE, 30);
            nextTime = cal.getTime();
            Log.e("nextTime :", "" + dateFormat1.format(nextTime));

            database.insertReminderData(new ReminderData(dateFormat1.format(nextTime), 1, "Mon,Tue,Wed,Thu,Fri,Sat,Sun"));
        }

    }

    public void startService() {
        Log.e("SettingsActivity :", "startService called");
        if (!MyService.isServiceRunning) {
            ContextCompat.startForegroundService(this, serviceIntent);
            startService(serviceIntent);
        }

    }

    public void stopService() {
        Log.e("SettingsActivity :", "stopService called");

        if (MyService.isServiceRunning) {
            stopService(serviceIntent);
        }
    }


}