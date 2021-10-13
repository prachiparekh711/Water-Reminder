package com.example.waterreminder;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import java.util.ArrayList;
import java.util.Arrays;


public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.MyClassView> {

    public static int updatePosition = -1;
    public ArrayList<ReminderData> reminderArrayList = new ArrayList<>();
    public Database_Helper database;
    Context activity;
    String days;
    ArrayList<String> daysList = null;
    String newString = "";
    String updateString = "";
    Personal_Data data;

    public ReminderAdapter(ArrayList<ReminderData> reminderArrayList, Context act) {
        this.activity = act;
        database = new Database_Helper(act);
        this.reminderArrayList = database.getAllReminderData();
        data = database.getAllPersonalData();
    }

    @Override
    public void onBindViewHolder(@NonNull MyClassView holder, int position) {

        holder.rTime.setText(reminderArrayList.get(position).getReminderTime());
        //  reminderStatus==0 for reminder off
        //  reminderStatus==1 for reminder on
        holder.rSwitch.setChecked(reminderArrayList.get(position).getReminderStatus() != 0);

        holder.rSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.rSwitch.setChecked(isChecked);
                //  reminderStatus==0 for reminder off
                //  reminderStatus==1 for reminder on
                if (isChecked) {
                    database.updateReminderStatus(reminderArrayList.get(position).getrID(), 1);
                    reminderArrayList = database.getAllReminderData();


                } else {
                    database.updateReminderStatus(reminderArrayList.get(position).getrID(), 0);
                    reminderArrayList = database.getAllReminderData();

                }
            }
        });

        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.hiddenView1.getVisibility() == View.VISIBLE) {

                    TransitionManager.beginDelayedTransition(holder.cardView,
                            new AutoTransition());
                    holder.hiddenView1.setVisibility(View.GONE);
                    holder.delete.setVisibility(View.GONE);
                    holder.arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    holder.rel.setBackgroundColor(activity.getResources().getColor(R.color.white));
                    holder.cardView.setBackgroundColor(activity.getResources().getColor(R.color.white));

                } else {

                    TransitionManager.beginDelayedTransition(holder.cardView,
                            new AutoTransition());
                    holder.hiddenView1.setVisibility(View.VISIBLE);
                    holder.delete.setVisibility(View.VISIBLE);
                    holder.arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    holder.rel.setBackgroundColor(activity.getResources().getColor(R.color.light));
                    holder.cardView.setBackgroundColor(activity.getResources().getColor(R.color.light));
                }
            }
        });

        if (updatePosition != -1 && updatePosition == position) {
            TransitionManager.beginDelayedTransition(holder.cardView,
                    new AutoTransition());
            holder.hiddenView1.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
            holder.arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
            holder.rel.setBackgroundColor(activity.getResources().getColor(R.color.light));
            holder.cardView.setBackgroundColor(activity.getResources().getColor(R.color.light));
        } else {
            TransitionManager.beginDelayedTransition(holder.cardView,
                    new AutoTransition());
            holder.hiddenView1.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
            holder.arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
            holder.rel.setBackgroundColor(activity.getResources().getColor(R.color.white));
            holder.cardView.setBackgroundColor(activity.getResources().getColor(R.color.white));

        }


        days = "";
        days = reminderArrayList.get(position).getReminderDays();
        daysList = new ArrayList<String>(Arrays.asList(days.split(",")));
//        Log.e("Days :",days);
        if (days.contains("Mon") && days.contains("Tue") && days.contains("Wed") && days.contains("Thu") && days.contains("Fri") && days.contains("Sat") && days.contains("Sun")) {
            holder.heading.setText("Everyday");

        } else {
            newString = "";
            for (int i = 0; i < daysList.size(); i++) {
                newString = newString + daysList.get(i) + " ";
            }
            holder.heading.setText(newString);
        }

        if (days.contains("Mon")) {
            holder.mon.setBackground(activity.getResources().getDrawable(R.drawable.ic_g36));
            holder.mon.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            holder.mon.setBackground(activity.getResources().getDrawable(R.drawable.ic_group_366w));
            holder.mon.setTextColor(Color.parseColor("#FF000000"));
        }

        if (days.contains("Tue")) {
            holder.tue.setBackground(activity.getResources().getDrawable(R.drawable.ic_g36));
            holder.tue.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            holder.tue.setBackground(activity.getResources().getDrawable(R.drawable.ic_group_366w));
            holder.tue.setTextColor(Color.parseColor("#FF000000"));
        }

        if (days.contains("Wed")) {
            holder.wed.setBackground(activity.getResources().getDrawable(R.drawable.ic_g36));
            holder.wed.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            holder.wed.setBackground(activity.getResources().getDrawable(R.drawable.ic_group_366w));
            holder.wed.setTextColor(Color.parseColor("#FF000000"));
        }

        if (days.contains("Thu")) {
            holder.thu.setBackground(activity.getResources().getDrawable(R.drawable.ic_g36));
            holder.thu.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            holder.thu.setBackground(activity.getResources().getDrawable(R.drawable.ic_group_366w));
            holder.thu.setTextColor(Color.parseColor("#FF000000"));
        }

        if (days.contains("Fri")) {
            holder.fri.setBackground(activity.getResources().getDrawable(R.drawable.ic_g36));
            holder.fri.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            holder.fri.setBackground(activity.getResources().getDrawable(R.drawable.ic_group_366w));
            holder.fri.setTextColor(Color.parseColor("#FF000000"));
        }

        if (days.contains("Sat")) {
            holder.sat.setBackground(activity.getResources().getDrawable(R.drawable.ic_g36));
            holder.sat.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            holder.sat.setBackground(activity.getResources().getDrawable(R.drawable.ic_group_366w));
            holder.sat.setTextColor(Color.parseColor("#FF000000"));
        }

        if (days.contains("Sun")) {
            holder.sun.setBackground(activity.getResources().getDrawable(R.drawable.ic_g36));
            holder.sun.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            holder.sun.setBackground(activity.getResources().getDrawable(R.drawable.ic_group_366w));
            holder.sun.setTextColor(Color.parseColor("#FF000000"));
        }


        holder.mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = reminderArrayList.get(position).getReminderDays();
                daysList = new ArrayList<String>(Arrays.asList(days.split(",")));
                Log.e("Days :", days);
                updateString = "";
                if (days.contains("Mon")) {
                    daysList.remove("Mon");
                } else {
                    daysList.add("Mon");
                }
                for (int i = 0; i < daysList.size(); i++) {
                    if (i == daysList.size() - 1)
                        updateString = updateString + daysList.get(i);
                    else
                        updateString = updateString + daysList.get(i) + ",";
                }
                days = "";
                updatePosition = position;
                Log.e("Updated :" + updateString, "at position :" + position);

                database.updateReminderDays(reminderArrayList.get(position).getrID(), updateString);
                reminderArrayList.clear();
                database = new Database_Helper(activity);
                reminderArrayList.addAll(database.getAllReminderData());

                ((AllSchedules) activity).runOnUiThread(() -> notifyDataSetChanged());
            }
        });

        holder.tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = reminderArrayList.get(position).getReminderDays();
                daysList = new ArrayList<String>(Arrays.asList(days.split(",")));
                Log.e("Days :", days);
                updateString = "";

                if (days.contains("Tue")) {
                    daysList.remove("Tue");
                } else {
                    daysList.add("Tue");
                }
                for (int i = 0; i < daysList.size(); i++) {
                    if (i == daysList.size() - 1)
                        updateString = updateString + daysList.get(i);
                    else
                        updateString = updateString + daysList.get(i) + ",";
                }
                days = "";
                updatePosition = position;
                Log.e("Updated :" + updateString, "at position :" + position);

                database.updateReminderDays(reminderArrayList.get(position).getrID(), updateString);
                reminderArrayList.clear();
                database = new Database_Helper(activity);
                reminderArrayList.addAll(database.getAllReminderData());

                ((AllSchedules) activity).runOnUiThread(() -> notifyDataSetChanged());

            }
        });

        holder.wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = reminderArrayList.get(position).getReminderDays();
                daysList = new ArrayList<String>(Arrays.asList(days.split(",")));
                Log.e("Days :", days);
                updateString = "";

                if (days.contains("Wed")) {
                    daysList.remove("Wed");
                } else {
                    daysList.add("Wed");
                }
                for (int i = 0; i < daysList.size(); i++) {
                    if (i == daysList.size() - 1)
                        updateString = updateString + daysList.get(i);
                    else
                        updateString = updateString + daysList.get(i) + ",";
                }
                days = "";
                Log.e("Updated :" + updateString, "at position :" + position);
                updatePosition = position;

                database.updateReminderDays(reminderArrayList.get(position).getrID(), updateString);
                reminderArrayList.clear();
                database = new Database_Helper(activity);

                reminderArrayList.addAll(database.getAllReminderData());
                ((AllSchedules) activity).runOnUiThread(() -> notifyDataSetChanged());


            }
        });

        holder.thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = reminderArrayList.get(position).getReminderDays();
                daysList = new ArrayList<String>(Arrays.asList(days.split(",")));
                Log.e("Days :", days);
                updateString = "";

                if (days.contains("Thu")) {
                    daysList.remove("Thu");
                } else {
                    daysList.add("Thu");
                }
                for (int i = 0; i < daysList.size(); i++) {
                    if (i == daysList.size() - 1)
                        updateString = updateString + daysList.get(i);
                    else
                        updateString = updateString + daysList.get(i) + ",";
                }
                days = "";
                updatePosition = position;
                Log.e("Updated :" + updateString, "at position :" + position);

                database.updateReminderDays(reminderArrayList.get(position).getrID(), updateString);
                reminderArrayList.clear();
                database = new Database_Helper(activity);

                reminderArrayList.addAll(database.getAllReminderData());

                ((AllSchedules) activity).runOnUiThread(() -> notifyDataSetChanged());

            }
        });

        holder.fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = reminderArrayList.get(position).getReminderDays();
                daysList = new ArrayList<String>(Arrays.asList(days.split(",")));
                Log.e("Days :", days);
                updateString = "";

                if (days.contains("Fri")) {
                    daysList.remove("Fri");
                } else {
                    daysList.add("Fri");
                }
                for (int i = 0; i < daysList.size(); i++) {
                    if (i == daysList.size() - 1)
                        updateString = updateString + daysList.get(i);
                    else
                        updateString = updateString + daysList.get(i) + ",";
                }
                days = "";
                updatePosition = position;
                Log.e("Updated :" + updateString, "at position :" + position);

                database.updateReminderDays(reminderArrayList.get(position).getrID(), updateString);
                reminderArrayList.clear();
                database = new Database_Helper(activity);

                reminderArrayList.addAll(database.getAllReminderData());

                ((AllSchedules) activity).runOnUiThread(() -> notifyDataSetChanged());

            }
        });

        holder.sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = reminderArrayList.get(position).getReminderDays();
                daysList = new ArrayList<String>(Arrays.asList(days.split(",")));
                Log.e("Days :", days);
                updateString = "";

                if (days.contains("Sat")) {
                    daysList.remove("Sat");
                } else {
                    daysList.add("Sat");
                }
                for (int i = 0; i < daysList.size(); i++) {
                    if (i == daysList.size() - 1)
                        updateString = updateString + daysList.get(i);
                    else
                        updateString = updateString + daysList.get(i) + ",";
                }
                days = "";
                updatePosition = position;
                Log.e("Updated :" + updateString, "at position :" + position);

                database.updateReminderDays(reminderArrayList.get(position).getrID(), updateString);
                reminderArrayList.clear();
                database = new Database_Helper(activity);

                reminderArrayList.addAll(database.getAllReminderData());

                ((AllSchedules) activity).runOnUiThread(() -> notifyDataSetChanged());

            }
        });

        holder.sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = reminderArrayList.get(position).getReminderDays();
                daysList = new ArrayList<String>(Arrays.asList(days.split(",")));
                Log.e("Days :", days);
                updateString = "";

                if (days.contains("Sun")) {
                    daysList.remove("Sun");
                } else {
                    daysList.add("Sun");
                }
                for (int i = 0; i < daysList.size(); i++) {
                    if (i == daysList.size() - 1)
                        updateString = updateString + daysList.get(i);
                    else
                        updateString = updateString + daysList.get(i) + ",";
                }
                days = "";
                updatePosition = position;
                Log.e("Updated :" + updateString, "at position :" + position);

                database.updateReminderDays(reminderArrayList.get(position).getrID(), updateString);
                reminderArrayList.clear();
                database = new Database_Helper(activity);

                reminderArrayList.addAll(database.getAllReminderData());
                ((AllSchedules) activity).runOnUiThread(() -> notifyDataSetChanged());

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                database.deleteReminder(reminderArrayList.get(position).getrID());
                reminderArrayList.remove(holder.getAdapterPosition());
                ((AllSchedules) activity).runOnUiThread(() -> notifyDataSetChanged());
            }
        });

    }

    @NonNull
    @Override
    public MyClassView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_layout, parent, false);
        return new MyClassView(view);
    }


    @Override
    public int getItemCount() {
        return reminderArrayList.size();
    }

    public class MyClassView extends RecyclerView.ViewHolder {


        TextView rTime;
        Switch rSwitch;
        TextView mon, tue, wed, thu, fri, sat, sun, delete, heading;
        ImageView arrow;
        LinearLayout hiddenView1;
        CardView cardView;
        RelativeLayout rel;

        public MyClassView(@NonNull View itemView) {
            super(itemView);

            rTime = itemView.findViewById(R.id.rTime);
            rSwitch = itemView.findViewById(R.id.rSwitch);

            cardView = itemView.findViewById(R.id.base_cardview);
            arrow = itemView.findViewById(R.id.arrow_button);
            hiddenView1 = itemView.findViewById(R.id.my_view);

            heading = itemView.findViewById(R.id.heading);

            mon = itemView.findViewById(R.id.mon);
            tue = itemView.findViewById(R.id.tue);
            wed = itemView.findViewById(R.id.wed);
            thu = itemView.findViewById(R.id.thu);
            fri = itemView.findViewById(R.id.fri);
            sat = itemView.findViewById(R.id.sat);
            sun = itemView.findViewById(R.id.sun);

            delete = itemView.findViewById(R.id.delete);
            rel = itemView.findViewById(R.id.rel);

        }
    }
}
