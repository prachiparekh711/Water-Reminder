package com.example.waterreminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GenderPage1 extends AppCompatActivity {

    public static String gender = "Male";
    public static int weightKg = 60;
    public static int weightLBS = 132;
    public static String weightIn = "Kg";
    public static String unitIn = "ml";
    public static String wakeUpTime = "06:00";
    public static String sleepTime = "22:00";
    ImageView back1;
    ImageView male, female;
    ImageView next1;
    TextView genderText, weightText, wTime, sTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_page1);
        this.getSupportActionBar().hide();

        back1 = findViewById(R.id.back);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        genderText = findViewById(R.id.gender);
        weightText = findViewById(R.id.weight);
        wTime = findViewById(R.id.mtime);
        sTime = findViewById(R.id.ntime);
        next1 = findViewById(R.id.next1);

        genderText.setText(gender);
        if (weightIn == "Kg") {
            weightText.setText(weightKg + weightIn);
        } else {
            weightText.setText(weightLBS + weightIn);
        }
        wTime.setText(wakeUpTime);
        sTime.setText(sleepTime);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Male";
                male.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_64));
                female.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_65));
                genderText.setText("Male");
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Female";
                male.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_641));
                female.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_651));
                genderText.setText("Female");
            }
        });

        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(GenderPage1.this, WeightPage2.class);
                startActivity(in);
            }
        });

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(GenderPage1.this, MainActivity.class);
                startActivity(in);
            }
        });

    }
}