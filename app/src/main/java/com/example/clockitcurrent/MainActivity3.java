package com.example.clockitcurrent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import classes.Navigator;
import classes.Plan;
import classes.Schedule;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {

    private Button todayButton;
    private Button yesterdayButton;
    private Button tomorrowButton;
    private String firstString;
    private final SimpleDateFormat formatting = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
    private String today;
    private String yesterday;
    private String tomorrow;
    private Button[] buttonList1;
    private TextView[] textList1;
    private View[] layoutList1;
    private View fragView;
    private Spinner schedSpinner;
    private View noSchedLayout;
    private Button currentlySelectedButton;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private SharedPreferences fullPreferences;
    private AlarmManager alarmManager;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Navigator.navigateTo((Button) this.findViewById(R.id.button9_1), this, MainActivity.class);
        Navigator.navigateTo((Button) this.findViewById(R.id.button2), this, MainActivity4.class);

        todayButton = this.findViewById(R.id.todayButton);
        yesterdayButton = this.findViewById(R.id.yesterdayButton);
        tomorrowButton = this.findViewById(R.id.tomorrowButton);
        schedSpinner = this.findViewById(R.id.scheduleListSpinner);
        noSchedLayout = this.findViewById(R.id.NoScheduleLayout);
        currentlySelectedButton = todayButton;
        preferences = this.getApplicationContext().getSharedPreferences("ScheduleDates", Context.MODE_PRIVATE);
        fullPreferences = this.getApplicationContext().getSharedPreferences("Schedules", Context.MODE_PRIVATE);

        long millis = System.currentTimeMillis();
        today = formatting.format(new Date(millis));
        int dayMillis = 86400000;
        yesterday = formatting.format(new Date(millis - dayMillis));
        tomorrow = formatting.format(new Date(millis + dayMillis));

        Map<String, String> scheduleMap = (Map<String, String>) fullPreferences.getAll();
        ArrayList<String> list = new ArrayList<String>(scheduleMap.keySet());
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        schedSpinner.setAdapter(typeAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        fullPreferences = this.getApplicationContext().getSharedPreferences("Schedules", Context.MODE_PRIVATE);
        Map<String, String> scheduleMap = (Map<String, String>) fullPreferences.getAll();
        ArrayList<String> list = new ArrayList<String>(scheduleMap.keySet());
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        schedSpinner.setAdapter(typeAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        fragView = this.findViewById(R.id.fragmentContainerView);
        buttonList1 = new Button[10];
        buttonList1[0] = (Button) fragView.findViewById(R.id.ScheduleButton1);
        buttonList1[1] = (Button) fragView.findViewById(R.id.ScheduleButton2);
        buttonList1[2] = (Button) fragView.findViewById(R.id.ScheduleButton3);
        buttonList1[3] = (Button) fragView.findViewById(R.id.ScheduleButton4);
        buttonList1[4] = (Button) fragView.findViewById(R.id.ScheduleButton5);
        buttonList1[5] = (Button) fragView.findViewById(R.id.ScheduleButton6);
        buttonList1[6] = (Button) fragView.findViewById(R.id.ScheduleButton7);
        buttonList1[7] = (Button) fragView.findViewById(R.id.ScheduleButton8);
        buttonList1[8] = (Button) fragView.findViewById(R.id.ScheduleButton9);
        buttonList1[9] = (Button) fragView.findViewById(R.id.ScheduleButton10);

        textList1 = new TextView[10];
        textList1[0] = (TextView) fragView.findViewById(R.id.ScheduleText1);
        textList1[1] = (TextView) fragView.findViewById(R.id.ScheduleText2);
        textList1[2] = (TextView) fragView.findViewById(R.id.ScheduleText3);
        textList1[3] = (TextView) fragView.findViewById(R.id.ScheduleText4);
        textList1[4] = (TextView) fragView.findViewById(R.id.ScheduleText5);
        textList1[5] = (TextView) fragView.findViewById(R.id.ScheduleText6);
        textList1[6] = (TextView) fragView.findViewById(R.id.ScheduleText7);
        textList1[7] = (TextView) fragView.findViewById(R.id.ScheduleText8);
        textList1[8] = (TextView) fragView.findViewById(R.id.ScheduleText9);
        textList1[9] = (TextView) fragView.findViewById(R.id.ScheduleText10);

        layoutList1 = new View[10];
        layoutList1[0] = (View) fragView.findViewById(R.id.ScheduleLayout1);
        layoutList1[1] = (View) fragView.findViewById(R.id.ScheduleLayout2);
        layoutList1[2] = (View) fragView.findViewById(R.id.ScheduleLayout3);
        layoutList1[3] = (View) fragView.findViewById(R.id.ScheduleLayout4);
        layoutList1[4] = (View) fragView.findViewById(R.id.ScheduleLayout5);
        layoutList1[5] = (View) fragView.findViewById(R.id.ScheduleLayout6);
        layoutList1[6] = (View) fragView.findViewById(R.id.ScheduleLayout7);
        layoutList1[7] = (View) fragView.findViewById(R.id.ScheduleLayout8);
        layoutList1[8] = (View) fragView.findViewById(R.id.ScheduleLayout9);
        layoutList1[9] = (View) fragView.findViewById(R.id.ScheduleLayout10);
/*
        Schedule schedule = new Schedule("Test", this, (Activity) this);
        for (int i = 0; i < 5; i++) {
            Plan plan = new Plan(schedule);
        }
        schedule.updateFragment(buttonList1, textList1, layoutList1);*/
        todayButton.setOnClickListener(this);
        yesterdayButton.setOnClickListener(this);
        tomorrowButton.setOnClickListener(this);

        schedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String val = "";

                if (currentlySelectedButton == todayButton)
                    val = today.toString().substring(0, 10);
                else if (currentlySelectedButton == yesterdayButton)
                    val = yesterday.toString().substring(0, 10);
                else
                    val = tomorrow.toString().substring(0, 10);
                editor = preferences.edit();
                editor.putString(val, fullPreferences.getString((String) parent.getItemAtPosition(position), null));
                editor.commit();
                System.out.println(preferences.getString(val, "N/A"));
                System.out.println(val);
                onClick(currentlySelectedButton);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    @Override
    public void onClick(View v) {
        currentlySelectedButton = (Button) v;
        Button[] dayButtonList = {yesterdayButton, todayButton, tomorrowButton};
        String[] dateList = {yesterday, today, tomorrow};
        int index = 0;
        for (int i = 0; i < dayButtonList.length; i ++) {
            if (dayButtonList[i] == v) {
                index = i;
                break;
            }
        }
        for (Button button : dayButtonList) {
            if (button == currentlySelectedButton) {
                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#32D573")));
                button.setTypeface(button.getTypeface(), Typeface.BOLD);
            } else {
                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D6D6D6")));
                button.setTypeface(button.getTypeface(), Typeface.NORMAL);
            }
        }
        String formattedDate = dateList[index].toString().substring(0, 10);
        String serializedSched = preferences.getString(formattedDate, "N/A");
        System.out.println(formattedDate);
        System.out.println(serializedSched);
        if (serializedSched.equals("N/A")) {
            fragView.setVisibility(View.GONE);
            noSchedLayout.setVisibility(View.VISIBLE);

        } else {
            noSchedLayout.setVisibility(View.GONE);
            fragView.setVisibility(View.VISIBLE);

            Schedule deserialized = new Schedule("New", this.getApplicationContext(),this);
            deserialized.deserialize(serializedSched);
            deserialized.updateFragment(buttonList1, textList1, layoutList1);
        }
    }
}