package com.example.waterreminder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.waterreminder.AnimationActivity.NeededGlassNo;
import static com.example.waterreminder.CupAdapter.selectedPosition;

public class HomeFragment extends Fragment {


    public static ArrayList<CupDetails> cupDetailsArrayList = new ArrayList<>();
    public static ArrayList<CupHistory> cupHistoryArrayList = new ArrayList<>();

    public static int click_count = 0, customize_count = 0;
    public static int needML, drunkML;
    public static int needOZ, drunkOZ;
    Database_Helper database;
    CupAdapter cupAdapter;
    RecyclerView cupRecycler;
    ImageView save, targetCup, cupSelect;
    RelativeLayout customize, cupSelector;
    EditText addML;
    TextView addType;
    TextView targetText;
    boolean b;
    TextView nextTime, nextML, targetMLText, drunkMLText, logo_text;
    RecyclerView recyclerView;
    RecordsAdapter recordsAdapter;
    int v1 = 0, x = 0;
    RelativeLayout cupClick;
    ProgressBar progressBar;
    SharedPreferences sp, sp1;
    SharedPreferences.Editor editor;
    String nTime = null;
    Personal_Data data;
    ScrollView sv;
    Date c;
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    String formattedDate1;
    private BottomSheetBehavior mBottomSheetBehavior1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        sv = v.findViewById(R.id.scrollView);

        cupSelector = v.findViewById(R.id.cupSelector);
        cupSelect = v.findViewById(R.id.cupSelect);
        save = v.findViewById(R.id.save);
        targetCup = v.findViewById(R.id.targetImg);
        targetText = v.findViewById(R.id.targetText);
        View bottomSheet1 = v.findViewById(R.id.bottom_sheet1);
        mBottomSheetBehavior1 = BottomSheetBehavior.from(bottomSheet1);
        cupRecycler = v.findViewById(R.id.cupRecycler);
        customize = v.findViewById(R.id.cr1);
        nextTime = v.findViewById(R.id.nextTime);
        nextML = v.findViewById(R.id.nextML);
        recyclerView = v.findViewById(R.id.recyclerView);
        cupClick = v.findViewById(R.id.targetCop);

        targetMLText = v.findViewById(R.id.targetML);
        drunkMLText = v.findViewById(R.id.drunkML);
        progressBar = v.findViewById(R.id.progressBar);
        logo_text = v.findViewById(R.id.logo_text);

        return v;
    }

    public void fillmap() {

        database.insertData(new CupDetails(getResources().getDrawable(R.drawable.a2), getResources().getDrawable(R.drawable.a22), getResources().getDrawable(R.drawable.bluecup2), "100 ml", "3 fl oz"));
        database.insertData(new CupDetails(getResources().getDrawable(R.drawable.a3), getResources().getDrawable(R.drawable.a33), getResources().getDrawable(R.drawable.bluecup3), "125 ml", "4 fl oz"));
        database.insertData(new CupDetails(getResources().getDrawable(R.drawable.a4), getResources().getDrawable(R.drawable.a44), getResources().getDrawable(R.drawable.bluecup4), "150 ml", "5 fl oz"));
        database.insertData(new CupDetails(getResources().getDrawable(R.drawable.a5), getResources().getDrawable(R.drawable.a55), getResources().getDrawable(R.drawable.bluecup5), "175 ml", "6 fl oz"));
        database.insertData(new CupDetails(getResources().getDrawable(R.drawable.a6), getResources().getDrawable(R.drawable.a66), getResources().getDrawable(R.drawable.bluecup6), "200 ml", "7 fl oz"));
        database.insertData(new CupDetails(getResources().getDrawable(R.drawable.a7), getResources().getDrawable(R.drawable.a77), getResources().getDrawable(R.drawable.bluecup7), "300 ml", "10 fl oz"));
        database.insertData(new CupDetails(getResources().getDrawable(R.drawable.a8), getResources().getDrawable(R.drawable.a88), getResources().getDrawable(R.drawable.bluecup8), "400 ml", "14 fl oz"));

        cupDetailsArrayList.clear();
        cupDetailsArrayList = database.getAllData();
        cupAdapter = new CupAdapter(cupDetailsArrayList, getActivity());
        cupRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        cupRecycler.setAdapter(cupAdapter);

    }

    public void CustomizeDialog() {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(getActivity());
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View view = factory.inflate(R.layout.customize, null);
        addType = view.findViewById(R.id.addType);
        if (data.getWeightIn().equals("Kg")) {
            addType.setText("ml");
        } else {
            addType.setText("fl oz");
        }
        alertadd.setView(view);
        alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                addML = view.findViewById(R.id.addML);
                if (data.getWeightIn().equals("Kg")) {
                    int oz = (int) (Integer.parseInt(String.valueOf(addML.getText())) / 29.574);
                    database.insertData(new CupDetails
                            (getResources().getDrawable(R.drawable.b2), getResources().getDrawable(R.drawable.a11), getResources().getDrawable(R.drawable.bluecup1),
                                    addML.getText().toString() + " ml", oz + " fl oz"));
                } else {
                    int ml = (int) (Integer.parseInt(String.valueOf(addML.getText())) * 29.574);
                    database.insertData(new CupDetails
                            (getResources().getDrawable(R.drawable.b2), getResources().getDrawable(R.drawable.a11), getResources().getDrawable(R.drawable.bluecup1),
                                    ml + " ml", addML.getText().toString() + " fl oz"));
                }
                cupDetailsArrayList.clear();
                cupDetailsArrayList = database.getAllData();
                cupAdapter = new CupAdapter(cupDetailsArrayList, getActivity());
                cupRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                cupRecycler.setAdapter(cupAdapter);
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

    public void historyRefresh() {
        cupHistoryArrayList.clear();
        cupHistoryArrayList.addAll(database.getAllHistoryData());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        RecordsAdapter.BtnClickListener btnClickListener = new RecordsAdapter.BtnClickListener() {
            @Override
            public void onBtnClick(int position) {
                if (data.getWeightIn().equals("Kg")) {
                    String ml = cupHistoryArrayList.get(position).getHistoryML();
                    ml = ml.replace(" ml", "");
                    ml = ml.trim();
                    drunkML = drunkML - Integer.parseInt(ml);
                    progressBar.setProgress(drunkML);
                    drunkMLText.setText(drunkML + "");
                    editor.putInt("drunkML", drunkML);
                } else {
                    String oz = cupHistoryArrayList.get(position).getHistoryOZ();
                    oz = oz.replace(" fl oz", "");
                    oz = oz.trim();
                    drunkOZ = drunkOZ - Integer.parseInt(oz);
                    progressBar.setProgress(drunkOZ);
                    drunkMLText.setText(drunkOZ + "");
                    editor.putInt("drunkOZ", drunkOZ);
                }
                editor.apply();
                database.deleteEntry(cupHistoryArrayList.get(position).gethID());
                cupHistoryArrayList.remove(position);

                getActivity().runOnUiThread(() -> recordsAdapter.notifyDataSetChanged());

                if (progressBar.getProgress() == progressBar.getMax()) {
                    animateParallel();
                } else {
                    logo_text.setVisibility(View.GONE);
                }
            }
        };

        recordsAdapter = new RecordsAdapter(getActivity(), cupHistoryArrayList, btnClickListener);
        recyclerView.setAdapter(recordsAdapter);


    }

    protected void animateParallel() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            logo_text.setVisibility(View.VISIBLE);
            ViewAnimator.animate(logo_text)
                    .translationY(-1000, 0)
                    .alpha(0, 1)
                    .andAnimate(logo_text)
                    .dp().translationX(-20, 0)
                    .decelerate()
                    .duration(1000)
                    .thenAnimate(logo_text)
                    .scale(1f, 0.5f, 1f)
                    .accelerate()
                    .duration(1000).start();
        }, 2000);


    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("On resume called :", "true");
        database = new Database_Helper(getActivity());
        data = database.getAllPersonalData();

        sp = getActivity().getSharedPreferences("pref", getActivity().MODE_PRIVATE);
        sp1 = getActivity().getSharedPreferences("pref1", getActivity().MODE_PRIVATE);
        b = sp.getBoolean("first", true);
        editor = sp.edit();
        editor.putBoolean("first", false);
        editor.putInt("neededML", data.getNeededML());
        editor.putInt("neededOZ", data.getNeededOZ());
        editor.apply();
        historyRefresh();

        needML = sp.getInt("neededML", 1100);
        needOZ = sp.getInt("neededOZ", (int) (1100 / 29.574));
        drunkML = database.getDrunkML();
        drunkOZ = database.getDrunkOZ();
//        drunkML = sp.getInt("drunkML",0);
//        drunkOZ = sp.getInt("drunkOZ",0);

        if (data.getWeightIn().equals("Kg")) {
            targetMLText.setText(data.getNeededML() + "ml");
            drunkMLText.setText(drunkML + "");
            progressBar.setMax(data.getNeededML());
            progressBar.setProgress(drunkML);
        } else {
            targetMLText.setText(data.getNeededOZ() + "fl oz");
            drunkMLText.setText(drunkOZ + "");
            progressBar.setMax(data.getNeededOZ());
            progressBar.setProgress(drunkOZ);
        }

        nTime = database.getNextReminderTime();
        nextTime.setText(nTime);
        if (b) {
            fillmap();
            cupDetailsArrayList.clear();
            cupDetailsArrayList = database.getAllData();
            cupAdapter = new CupAdapter(cupDetailsArrayList, getActivity());
            cupRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            cupRecycler.setAdapter(cupAdapter);
            targetCup.setImageDrawable(cupDetailsArrayList.get(NeededGlassNo).getFullcup());
            cupSelect.setImageDrawable(cupDetailsArrayList.get(NeededGlassNo).getFullcup());
            if (data.getWeightIn().equals("Kg")) {
                targetText.setText(cupDetailsArrayList.get(NeededGlassNo).getCupML());
                nextML.setText(cupDetailsArrayList.get(NeededGlassNo).getCupML());
            } else {
                targetText.setText(cupDetailsArrayList.get(NeededGlassNo).getCupOZ());
                nextML.setText(cupDetailsArrayList.get(NeededGlassNo).getCupOZ());
            }
        } else {
            cupDetailsArrayList.clear();
            cupDetailsArrayList = database.getAllData();
            cupAdapter = new CupAdapter(cupDetailsArrayList, getActivity());
            cupRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            cupRecycler.setAdapter(cupAdapter);

            v1 = sp1.getInt("value", 0);
            targetCup.setImageDrawable(cupDetailsArrayList.get(v1).getFullcup());
            cupSelect.setImageDrawable(cupDetailsArrayList.get(v1).getFullcup());
            if (data.getWeightIn().equals("Kg")) {
                targetText.setText(cupDetailsArrayList.get(v1).getCupML());
                nextML.setText(cupDetailsArrayList.get(v1).getCupML());
            } else {
                targetText.setText(cupDetailsArrayList.get(v1).getCupOZ());
                nextML.setText(cupDetailsArrayList.get(v1).getCupOZ());
            }
            historyRefresh();
        }
        cupSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });

        customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomizeDialog();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition != -1) {
                    customize_count++;
                    targetCup.setImageDrawable(cupDetailsArrayList.get(selectedPosition).getFullcup());
                    cupSelect.setImageDrawable(cupDetailsArrayList.get(selectedPosition).getFullcup());
                    if (data.getWeightIn().equals("Kg")) {
                        targetText.setText(cupDetailsArrayList.get(selectedPosition).getCupML());
                        nextML.setText(cupDetailsArrayList.get(selectedPosition).getCupML());
                    } else {
                        targetText.setText(cupDetailsArrayList.get(selectedPosition).getCupOZ());
                        nextML.setText(cupDetailsArrayList.get(selectedPosition).getCupOZ());
                    }
                } else {
                    Toast.makeText(getActivity(), "No Cup Selected...", Toast.LENGTH_SHORT).show();
                }
                mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        cupClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                sv.setBottom(100);
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedDate1 = df.format(c);

                x = sp1.getInt("value", 0);

                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
                String formattedDate = dateFormat.format(new Date());

                if (b == true && customize_count == 0) {
                    database.insertHistoryData(new CupHistory(cupDetailsArrayList.get(NeededGlassNo).getBlueCup(), cupDetailsArrayList.get(NeededGlassNo).getCupML(), formattedDate, formattedDate1, cupDetailsArrayList.get(NeededGlassNo).getCupOZ()));
                    if (data.getWeightIn().equals("Kg")) {
                        String ml = cupDetailsArrayList.get(NeededGlassNo).getCupML();
                        ml = ml.replace(" ml", "");
                        ml = ml.trim();
                        drunkML = drunkML + Integer.parseInt(ml);
                        drunkOZ = (int) Math.round(drunkML / 29.574);
                        progressBar.setProgress(drunkML);
                        drunkMLText.setText(drunkML + "");
                    } else {
                        String oz = cupDetailsArrayList.get(NeededGlassNo).getCupOZ();
                        oz = oz.replace(" fl oz", "");
                        oz = oz.trim();
                        drunkOZ = drunkOZ + Integer.parseInt(oz);
                        drunkML = (int) Math.round(drunkOZ * 29.574);
                        progressBar.setProgress(drunkOZ);
                        drunkMLText.setText(drunkOZ + "");
                    }
                    if (progressBar.getProgress() == progressBar.getMax()) {
                        animateParallel();
                    } else {
                        logo_text.setVisibility(View.GONE);
                    }

                } else {
                    database.insertHistoryData(new CupHistory(cupDetailsArrayList.get(x).getBlueCup(), cupDetailsArrayList.get(x).getCupML(), formattedDate, formattedDate1, cupDetailsArrayList.get(x).getCupOZ()));
                    if (data.getWeightIn().equals("Kg")) {
                        String ml = cupDetailsArrayList.get(x).getCupML();
                        ml = ml.replace(" ml", "");
                        ml = ml.trim();
                        drunkML = drunkML + Integer.parseInt(ml);
                        drunkOZ = (int) Math.round(drunkML / 29.574);
                        Log.e("Substring ml:", ml);
                        progressBar.setProgress(drunkML);

                        drunkMLText.setText(drunkML + "");
                    } else {
                        String oz = cupDetailsArrayList.get(x).getCupOZ();
                        oz = oz.replace(" fl oz", "");
                        oz = oz.trim();
                        drunkOZ = drunkOZ + Integer.parseInt(oz);
                        drunkML = (int) Math.round(drunkOZ * 29.574);
                        Log.e("Substring oz:", oz);
                        progressBar.setProgress(drunkOZ);

                        drunkMLText.setText(drunkOZ + "");
                    }
                    if (progressBar.getProgress() == progressBar.getMax()) {
                        animateParallel();
                    } else {
                        logo_text.setVisibility(View.GONE);
                    }

                }


                editor.putInt("drunkML", drunkML);
                editor.putInt("drunkOZ", drunkOZ);
                editor.apply();
                click_count++;
                historyRefresh();

            }
        });

    }
}