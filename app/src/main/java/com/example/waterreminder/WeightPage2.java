package com.example.waterreminder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.waterreminder.GenderPage1.gender;
import static com.example.waterreminder.GenderPage1.sleepTime;
import static com.example.waterreminder.GenderPage1.unitIn;
import static com.example.waterreminder.GenderPage1.wakeUpTime;
import static com.example.waterreminder.GenderPage1.weightIn;
import static com.example.waterreminder.GenderPage1.weightKg;
import static com.example.waterreminder.GenderPage1.weightLBS;

public class WeightPage2 extends AppCompatActivity {

    ImageView back1;
    ImageView next2;
    ImageView figure;
    TextView genderText, weightText, wTime, sTime;
    RelativeLayout r1;
    WheelView1 wheelViewKg, wheelView1, wheelViewLbs;
    ArrayList<String> mainList;
    int x = 0;
    ArrayList<String> list1, list2;
    Database_Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_page2);
        this.getSupportActionBar().hide();

        helper = new Database_Helper(WeightPage2.this);
        back1 = findViewById(R.id.back);
        genderText = findViewById(R.id.gender);
        weightText = findViewById(R.id.weight);
        wTime = findViewById(R.id.mtime);
        sTime = findViewById(R.id.ntime);
        next2 = findViewById(R.id.next2);
        r1 = findViewById(R.id.r1);
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
            figure.setImageDrawable(getResources().getDrawable(R.drawable.ic_boy_weigh));
        } else {
            figure.setImageDrawable(getResources().getDrawable(R.drawable.ic_girl_weigh));
        }

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(WeightPage2.this, GenderPage1.class);
                startActivity(in);
            }
        });

        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(WeightPage2.this, WakeUpPage3.class);
                startActivity(in);
            }
        });

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(WeightPage2.this, GenderPage1.class);
                startActivity(in);
            }
        });

        getKGList();
        getLBSList();
        mainList = new ArrayList<>();
        wheelViewKg = findViewById(R.id.loop_view1);
        wheelView1 = findViewById(R.id.loop_view2);
        wheelViewLbs = findViewById(R.id.loop_view0);
        wheelViewKg.setSeletion(weightKg);
        wheelViewLbs.setSeletion(weightLBS);
        wheelView1.setSeletion(0);
        wheelViewKg.setItems(list1);
        wheelViewLbs.setItems(list2);
        if (weightIn.equals("Kg")) {
            wheelViewKg.setVisibility(View.VISIBLE);
            wheelViewLbs.setVisibility(View.GONE);
        } else {
            wheelViewKg.setVisibility(View.GONE);
            wheelViewLbs.setVisibility(View.VISIBLE);
        }
        setUnitWheelOnChange();
        setWeightInWheel();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (weightIn.equals("Kg")) {
            wheelView1.setSeletion(0);
            wheelViewKg.setVisibility(View.VISIBLE);
            wheelViewLbs.setVisibility(View.GONE);
        } else {
            wheelView1.setSeletion(1);
            wheelViewKg.setVisibility(View.GONE);
            wheelViewLbs.setVisibility(View.VISIBLE);
        }
    }

    public void setUnitWheelOnChange() {
        mainList.clear();

        if (weightIn == "Kg") {
            weightKg = (int) Math.round(weightLBS / 2.205);
            Log.e("LLL weightKg :", String.valueOf(weightKg));
            Log.e("LLL index :", String.valueOf(wheelViewKg.selectedIndex));
            wheelViewKg.setSeletion(weightKg - 1);
            weightText.setText(weightKg + weightIn);

        } else {
            weightLBS = (int) Math.round(weightKg * 2.205);
            Log.e("LLL weightLBS :", String.valueOf(weightLBS));
            Log.e("LLL index contains :", String.valueOf(list2.contains(String.valueOf(weightLBS))));
            Log.e("LLL index :", String.valueOf(list2.indexOf(String.valueOf(weightLBS))));
//            wheelView.setSeletion(mainList.indexOf(String.valueOf(weightLBS)));
            int lbs = list2.indexOf(String.valueOf(weightLBS));
            wheelViewLbs.setSeletion(lbs);

            weightText.setText(weightLBS + weightIn);
        }
        wheelViewKg.setOnWheelViewListener(new WheelView1.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.e("position : ", selectedIndex + "");
                Log.e("item : ", item);

                weightKg = Integer.parseInt(item);
                weightLBS = (int) Math.round(weightKg * 2.205);
                weightText.setText(weightKg + weightIn);

            }
        });

        wheelViewLbs.setOnWheelViewListener(new WheelView1.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.e("position : ", selectedIndex + "");
                Log.e("item : ", item);

                weightLBS = Integer.parseInt(item);
                weightKg = (int) Math.round(weightLBS / 2.205);
                weightText.setText(weightLBS + weightIn);

            }
        });


//        wheelView.setTextSize(25);
    }

    public void setWeightInWheel() {

        wheelView1.setOnWheelViewListener(new WheelView1.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.e("position of type : ", selectedIndex + "");
                if (selectedIndex == 1) {
                    weightIn = "Kg";
                    unitIn = "ml";
                    wheelViewKg.setVisibility(View.VISIBLE);
                    wheelViewLbs.setVisibility(View.GONE);
                } else {
                    weightIn = "Lbs";
                    unitIn = "fl oz";
                    wheelViewLbs.setVisibility(View.VISIBLE);
                    wheelViewKg.setVisibility(View.GONE);
                }
                helper.updateWeightIn(weightIn);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setUnitWheelOnChange();
                    }
                });

            }
        });


//        wheelView1.setTextSize(20);
        wheelView1.setItems(getList1());
    }

    public void getKGList() {
        list1 = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            list1.add("" + i);
        }

    }

    public void getLBSList() {
        list2 = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
//            Log.e("getLBSList :" , "" + (int)Math.round(i * 2.205));
            list2.add("" + Integer.parseInt(String.valueOf(Math.round(i * 2.205))));
//            list.add("" + i);
        }

    }

    public List getList1() {
        ArrayList<String> list = new ArrayList<>();

        list.add("Kg");
        list.add("Lbs");

        return list;
    }

}