package com.example.waterreminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.waterreminder.GenderPage1.gender;
import static com.example.waterreminder.GenderPage1.sleepTime;
import static com.example.waterreminder.GenderPage1.wakeUpTime;
import static com.example.waterreminder.GenderPage1.weightIn;
import static com.example.waterreminder.GenderPage1.weightKg;
import static com.example.waterreminder.GenderPage1.weightLBS;

public class WakeUpPage3 extends AppCompatActivity {

    ImageView back1, skip;
    ImageView next2;
    ImageView figure;
    TextView genderText, weightText, wTime, sTime;
    RelativeLayout r1, r2;
    String x = "06";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up_page3);
        this.getSupportActionBar().hide();

        back1 = findViewById(R.id.back);
        skip = findViewById(R.id.skip);
        genderText = findViewById(R.id.gender);
        weightText = findViewById(R.id.weight);
        wTime = findViewById(R.id.mtime);
        sTime = findViewById(R.id.ntime);
        next2 = findViewById(R.id.next2);
        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);
        figure = findViewById(R.id.figure);

        genderText.setText(gender);
        if (weightIn == "Kg") {
            weightText.setText(weightKg + weightIn);
        } else {
            weightText.setText(weightLBS + weightIn);
        }
        wTime.setText(wakeUpTime);
        sTime.setText(sleepTime);

        if (gender.equals("Male")) {
            figure.setImageDrawable(getResources().getDrawable(R.drawable.ic_wake_up_vector));
        } else {
            figure.setImageDrawable(getResources().getDrawable(R.drawable.ic_girl_wak_up));
        }

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(WakeUpPage3.this, GenderPage1.class);
                startActivity(in);
            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(WakeUpPage3.this, WeightPage2.class);
                startActivity(in);
            }
        });

        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(WakeUpPage3.this, SleepPage4.class);
                startActivity(in);
            }
        });

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(WakeUpPage3.this, WeightPage2.class);
                startActivity(in);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(WakeUpPage3.this, AnimationActivity.class);
                startActivity(in);
            }
        });


        final WheelView wheelView = (WheelView) findViewById(R.id.loop_view1);
        wheelView.setInitialPosition(6);
        wheelView.setIsLoopEnabled(false);
        wheelView.addOnLoopScrollListener(new WheelView.OnLoopScrollListener() {
            @Override
            public void onLoopScrollFinish(@NonNull Object item, int position) {
//                Log.e("position : ",position+"");
//                Log.e("item : ",item.toString());
                x = item.toString();
                wakeUpTime = item.toString() + ":" + "00";
                wTime.setText(wakeUpTime);
            }
        });
        wheelView.setTextSize(25);
        wheelView.setItems(getList());

        final WheelView wheelView1 = (WheelView) findViewById(R.id.loop_view2);

        wheelView1.setInitialPosition(0);
        wheelView1.setIsLoopEnabled(false);
        wheelView1.addOnLoopScrollListener(new WheelView.OnLoopScrollListener() {
            @Override
            public void onLoopScrollFinish(@NonNull Object item, int position) {
                wakeUpTime = x + ":" + item.toString();
                wTime.setText(wakeUpTime);
            }
        });
        wheelView1.setTextSize(25);
        wheelView1.setItems(getList1());

    }

    public List getList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("00");
        list.add("01");
        list.add("02");
        list.add("03");
        list.add("04");
        list.add("05");
        list.add("06");
        list.add("07");
        list.add("08");
        list.add("09");
        for (int i = 10; i < 24; i++) {
            list.add("" + i);
        }

        return list;
    }

    public List getList1() {
        ArrayList<String> list = new ArrayList<>();
        list.add("00");
        list.add("01");
        list.add("02");
        list.add("03");
        list.add("04");
        list.add("05");
        list.add("06");
        list.add("07");
        list.add("08");
        list.add("09");
        for (int i = 10; i < 60; i++) {
            list.add("" + i);
        }

        return list;
    }
}