package com.example.waterreminder;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.addisonelliott.segmentedbutton.SegmentedButton;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.waterreminder.GenderPage1.weightKg;
import static com.example.waterreminder.GenderPage1.weightLBS;

public class SettingsFragment extends Fragment {

    public static boolean reset = false;
    public static int TotalGlassPerDay = 2;
    public int[] neededML = new int[101];
    public int[] neededOZ = new int[101];
    public int[] oneTimeNeededML = new int[101];
    public int[] oneTimeNeededOZ = new int[101];
    TextView rSchedule, resetData, rSound, rMode;
    Database_Helper database;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    TextView unit, goal, gender, weight, wakeup, sleep;
    LinearLayout l1, l2, l3, l4, l5;
    ImageView img1, img2, img3, img4, img5;
    int modeID = 1;
    Switch frSwitch;
    SegmentedButton kg, lbs, ml, floz;
    SegmentedButtonGroup group1, group2;
    Personal_Data personal_data;
    String wIn;
    TextView goal1, goalIn1;
    IndicatorSeekBar seekBar, seekBar1;
    ImageView resetGoal;
    int newML, newOZ, newKG, newLBS;
    WheelView1 wheelViewKg, wheelViewLbs;
    ArrayList<String> list1, list2;
    TextView wType;
    String newHour, newHour1, newMin, newMin1;
    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
    SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm aa");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        database = new Database_Helper(getActivity());

        unit = view.findViewById(R.id.unit);
        goal = view.findViewById(R.id.goal);
        gender = view.findViewById(R.id.gender);
        weight = view.findViewById(R.id.weight);
        wakeup = view.findViewById(R.id.wakeup);
        sleep = view.findViewById(R.id.sleep);
        frSwitch = view.findViewById(R.id.frSwitch);

        getKGList();
        getLBSList();

        setData();
        calculateML();

        frSwitch.setChecked(personal_data.getFurtherReminder().equals("yes"));

        rSchedule = view.findViewById(R.id.rSchedule);
        rSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), AllSchedules.class);
                startActivity(in);
            }
        });

        frSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    database.updateFurtherReminderStatus("yes");
                else
                    database.updateFurtherReminderStatus("no");
            }
        });

        resetData = view.findViewById(R.id.resetData);
        resetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();
            }
        });

        rSound = view.findViewById(R.id.rSound);
        rSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), ReminderSound.class);
                startActivity(in);
            }
        });

        rMode = view.findViewById(R.id.rMode);
        rMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReminderModeDialog();
            }
        });

        unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnitDialog();
            }
        });

        goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalDialog();
            }
        });

        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightDialog();
            }
        });

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        wakeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog mTimePicker = new TimePickerDialog(getActivity(), R.style.CustomPickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (String.valueOf(selectedHour).length() == 1)
                            newHour = "0" + selectedHour;
                        else
                            newHour = String.valueOf(selectedHour);

                        if (String.valueOf(selectedMinute).length() == 1)
                            newMin = "0" + selectedMinute;
                        else
                            newMin = String.valueOf(selectedMinute);

                        Log.e("new wake time : ", newHour + ":" + newMin);
                        database.updateWakeUpTime(newHour + ":" + newMin);
                        setData();
                        resetSchedule();
                    }
                }, hour, minute, true);//Yes 24 hour time

                mTimePicker.show();
            }
        });

        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker = new TimePickerDialog(getActivity(), R.style.CustomPickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (String.valueOf(selectedHour).length() == 1)
                            newHour1 = "0" + selectedHour;
                        else
                            newHour1 = String.valueOf(selectedHour);

                        if (String.valueOf(selectedMinute).length() == 1)
                            newMin1 = "0" + selectedMinute;
                        else
                            newMin1 = String.valueOf(selectedMinute);

                        Log.e("new sleep time : ", newHour1 + ":" + newMin1);
                        database.updateSleepTime(newHour1 + ":" + newMin1);
                        setData();
                        resetSchedule();
                    }
                }, hour, minute, true);//Yes 24 hour time

                mTimePicker.show();
            }
        });

        return view;
    }

    public void resetSchedule() {
        personal_data = database.getAllPersonalData();
        String wakeUpTime = personal_data.getWakeUpTime();
        String bedTime = personal_data.getSleepTime();
        int totalML = personal_data.getNeededML();
        int totalHours = 0; // total hours = difference betwwn wakeup time & bed time
        Log.e("Wake up Time before :", wakeUpTime);
        Log.e("bed up Time before :", bedTime);

        try {

            long difference = dateFormat.parse(wakeUpTime).getTime() - dateFormat.parse(bedTime).getTime();
            if (difference < 0) {
                Date dateMax = dateFormat.parse("24:00");
                Date dateMin = dateFormat.parse("00:00");
                difference = (dateMax.getTime() - dateFormat.parse(wakeUpTime).getTime()) + (dateFormat.parse(bedTime).getTime() - dateMin.getTime());
            }
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            Log.e("log_tag", "Hours: " + hours + ", Mins: " + min);

            totalHours = hours;
            if (hours > 0) {
                oneTimeNeededML[personal_data.getWeightKg()] = (totalML / totalHours) + (totalML / (totalHours * 2));
                oneTimeNeededOZ[personal_data.getWeightKg()] = (int) (oneTimeNeededML[personal_data.getWeightKg()] / 29.574);
                Log.e("oneTimeNeededML :", "for weight: " + weightKg + ", is: " + oneTimeNeededML[personal_data.getWeightKg()]);
                Log.e("oneTimeNeededOZ :", "for weight: " + weightLBS + ", is: " + oneTimeNeededOZ[personal_data.getWeightKg()]);
            } else {
                Toast.makeText(getActivity(), "May Be BedTime need to reset", Toast.LENGTH_SHORT).show();
            }

            Double total = Double.parseDouble(String.valueOf(personal_data.getNeededML()));
            Double total1 = Double.parseDouble(String.valueOf(oneTimeNeededML[personal_data.getWeightKg()]));

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

        } catch (ParseException e) {
        }

    }

    public void resetData() {
        AlertDialog.Builder alertReset = new AlertDialog.Builder(getActivity());
        LayoutInflater factory = LayoutInflater.from(getActivity());
        alertReset.setTitle("Reset Data ?");
        alertReset.setMessage("Reset plans and delete all existing data.");
        alertReset.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                sp = getActivity().getSharedPreferences("pref", getActivity().MODE_PRIVATE);
                editor = sp.edit();
                editor.putBoolean("first", true);
                editor.putInt("drunkML", 0);
                editor.putInt("drunkOZ", 0);
                editor.apply();
                database.deleteTable();
                database.deleteTable1();
                database.deleteTable2();
                database.deleteTable3();
                Intent in = new Intent(getActivity(), GenderPage1.class);
                startActivity(in);
            }
        });
        alertReset.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        alertReset.show();
    }

    public void setData() {
        personal_data = database.getAllPersonalData();
        if (personal_data.getWeightIn().equals("Kg")) {
            unit.setText(personal_data.getWeightIn() + " , " + "ml");
            goal.setText(personal_data.getNeededML() + " ml");
            weight.setText(personal_data.getWeightKg() + " kg");
        } else {
            unit.setText(personal_data.getWeightIn() + " , " + "fl oz");
            goal.setText(personal_data.getNeededOZ() + " fl oz");
            weight.setText(personal_data.getWeightLBS() + " lbs");
        }
        gender.setText(personal_data.getGender());

        wakeup.setText(personal_data.getWakeUpTime());
        sleep.setText(personal_data.getSleepTime());

    }

    @Override
    public void onResume() {
        super.onResume();
        if (reset) {
            setData();
        }
    }

    public void weightDialog() {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(getActivity());
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View view = factory.inflate(R.layout.weight_dialog, null);
        alertadd.setView(view);
        wType = view.findViewById(R.id.wType);
        wheelViewKg = view.findViewById(R.id.loop_viewKg);
        wheelViewLbs = view.findViewById(R.id.loop_viewLbs);
        wheelViewKg.setItems(list1);
        wheelViewLbs.setItems(list2);
        wheelViewKg.setSeletion(personal_data.getWeightKg() - 1);
        int lbs = list2.indexOf(String.valueOf(personal_data.getWeightLBS()));
        wheelViewLbs.setSeletion(lbs);
        if (personal_data.getWeightIn().equals("Kg")) {
            wheelViewKg.setVisibility(View.VISIBLE);
            wheelViewLbs.setVisibility(View.GONE);
            wType.setText("kg");
        } else {
            wheelViewKg.setVisibility(View.GONE);
            wheelViewLbs.setVisibility(View.VISIBLE);
            wType.setText("lbs");
        }
        wheelViewKg.setOnWheelViewListener(new WheelView1.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.e("position kg : ", selectedIndex + "");
                Log.e("item : ", item);
                newKG = selectedIndex;
                newLBS = (int) Math.round(newKG * 2.205);
//
            }
        });
        wheelViewLbs.setOnWheelViewListener(new WheelView1.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.e("position lbs: ", selectedIndex + "");
                Log.e("item : ", item);
                newLBS = Integer.parseInt(item);
                newKG = (int) Math.round(newLBS / 2.205);

            }
        });

        alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                calculateML();
                Log.e("newKg : " + newKG, "newLbs : " + newLBS);
                Log.e("ML needed :", String.valueOf(neededML[newKG]));
                Log.e("OZ needed :", String.valueOf(neededOZ[newKG]));
                database.updateWeight(newKG, newLBS, neededML[newKG], neededOZ[newKG]);
                setData();
                dialog.dismiss();
            }
        });
        alertadd.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertadd.show();
    }

    public void goalDialog() {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(getActivity());
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View view = factory.inflate(R.layout.goal_dialog, null);
        alertadd.setView(view);
        goal1 = view.findViewById(R.id.goal1);
        goalIn1 = view.findViewById(R.id.goalIn1);
        seekBar = view.findViewById(R.id.seekBar);
        seekBar1 = view.findViewById(R.id.seekBar1);
        resetGoal = view.findViewById(R.id.resetGoal);

        seekBar.setProgress(personal_data.getNeededML());
        seekBar1.setProgress(personal_data.getNeededOZ());
        resetGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personal_data.getWeightIn().equals("Kg")) {
                    seekBar.setProgress(personal_data.getNeededML());
                    goal1.setText(personal_data.getNeededML() + "");
                } else {
                    seekBar1.setProgress(personal_data.getNeededOZ());
                    goal1.setText(personal_data.getNeededOZ() + "");
                }
            }
        });
        if (personal_data.getWeightIn().equals("Kg")) {
            seekBar.setVisibility(View.VISIBLE);
            seekBar1.setVisibility(View.GONE);
            goal1.setText(personal_data.getNeededML() + "");
            goalIn1.setText("ml");
        } else {
            seekBar1.setVisibility(View.VISIBLE);
            seekBar.setVisibility(View.GONE);
            goal1.setText(personal_data.getNeededOZ() + "");
            goalIn1.setText("fl oz");
        }

        seekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                if (personal_data.getWeightIn().equals("Kg")) {
                    goal1.setText(seekParams.progress + "");
                } else {
                    goal1.setText(seekParams.progress + "");
                }
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });

        seekBar1.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                if (personal_data.getWeightIn().equals("Kg")) {
                    goal1.setText(seekParams.progress + "");
                } else {
                    goal1.setText(seekParams.progress + "");
                }
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });

        alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                reset = true;
                if (seekBar.getVisibility() == View.VISIBLE) {
                    newML = seekBar.getProgress();
                    newOZ = (int) Math.round(newML / 29.574);
                } else {
                    newOZ = seekBar1.getProgress();
                    newML = (int) Math.round(newOZ * 29.574);
                }
                database.updateNeededMlOz(newML, newOZ);
                setData();
                dialog.dismiss();
            }
        });
        alertadd.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertadd.show();
    }

    public void UnitDialog() {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(getActivity());
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View view = factory.inflate(R.layout.unit_dialog, null);
        alertadd.setView(view);
        kg = view.findViewById(R.id.kg);
        ml = view.findViewById(R.id.ml);
        lbs = view.findViewById(R.id.lbs);
        floz = view.findViewById(R.id.floz);
        group1 = view.findViewById(R.id.segmented1);
        group2 = view.findViewById(R.id.segmented2);

        wIn = personal_data.getWeightIn();
        if (wIn.equals("Kg")) {
            group1.setPosition(0, true);
            group2.setPosition(0, true);
        } else {
            group1.setPosition(1, true);
            group2.setPosition(1, true);
        }
        kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group2.setPosition(0, true);
                database.updateWeightIn("Kg");
            }
        });
        lbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group2.setPosition(1, true);
                database.updateWeightIn("Lbs");
            }
        });
        ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group1.setPosition(0, true);
                database.updateWeightIn("Kg");
            }
        });
        floz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group1.setPosition(1, true);
                database.updateWeightIn("Lbs");
            }
        });
        alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                reset = true;
                setData();
                dialog.dismiss();
            }
        });
        alertadd.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertadd.show();
    }


    public void ReminderModeDialog() {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(getActivity());
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View view = factory.inflate(R.layout.reminder_mode, null);
        alertadd.setView(view);


        l1 = view.findViewById(R.id.l1);
        l2 = view.findViewById(R.id.l2);
        l3 = view.findViewById(R.id.l3);
        l4 = view.findViewById(R.id.l4);
        l5 = view.findViewById(R.id.l5);

        img1 = view.findViewById(R.id.img1);
        img2 = view.findViewById(R.id.img2);
        img3 = view.findViewById(R.id.img3);
        img4 = view.findViewById(R.id.img4);
        img5 = view.findViewById(R.id.img5);

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeID = 1;
                img1.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_mobile_friendly_241));
                img2.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_notifications_active_24));
                img3.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_vibration_24));
                img4.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_message_24));
                img5.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_notifications_off_24));
            }
        });

        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeID = 2;
                img1.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_mobile_friendly_24));
                img2.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_notifications_active_241));
                img3.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_vibration_24));
                img4.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_message_24));
                img5.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_notifications_off_24));
            }
        });

        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeID = 3;
                img1.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_mobile_friendly_24));
                img2.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_notifications_active_24));
                img3.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_vibration_241));
                img4.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_message_24));
                img5.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_notifications_off_24));
            }
        });

        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeID = 4;
                img1.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_mobile_friendly_24));
                img2.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_notifications_active_24));
                img3.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_vibration_24));
                img4.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_message_241));
                img5.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_notifications_off_24));
            }
        });

        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeID = 5;
                img1.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_mobile_friendly_24));
                img2.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_notifications_active_24));
                img3.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_vibration_24));
                img4.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_message_24));
                img5.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_notifications_off_241));
            }
        });
        alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                database.updateMode(modeID);
                dialog.dismiss();
            }
        });
        alertadd.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        alertadd.show();
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

    public void calculateML() {
        int weigh = newKG;

        if (personal_data.getGender().equals("Male")) {
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
        if (personal_data.getGender().equals("Female")) {
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
}