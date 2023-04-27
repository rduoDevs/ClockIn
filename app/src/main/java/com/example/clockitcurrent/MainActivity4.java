package com.example.clockitcurrent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import classes.Navigator;
import classes.Schedule;

public class MainActivity4 extends AppCompatActivity {

    private Spinner spinner;
    private SharedPreferences preferences;

    private Map<String, Schedule> scheduleMap = new HashMap<String, Schedule>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Navigator.navigateTo((Button) this.findViewById(R.id.button4), this, MainActivity3.class);
        Navigator.navigateTo((Button) this.findViewById(R.id.button13), this, MainActivity5.class);

        preferences = this.getApplicationContext().getSharedPreferences("Schedules", Context.MODE_PRIVATE);
        spinner = this.findViewById(R.id.spinner);
        //formatting.format(newDate).substring(0, 10);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Map<String, String> scheduleMap = (Map<String, String>) preferences.getAll();
        ArrayList<String> list = new ArrayList<String>(scheduleMap.keySet());
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(typeAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateScreen((String) parent.getItemAtPosition(position), preferences.getString((String) spinner.getItemAtPosition(position), "N/A"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void updateScreen(String data, String serializedData) {
        if (serializedData != "N/A") {
            Schedule schedule;
            View fragView = this.findViewById(R.id.fragmentContainerView3);
            Button[] buttonList1 = new Button[10];
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

            TextView[] textList1 = new TextView[10];
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

            View[] layoutList1 = new View[10];
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

            if (!scheduleMap.containsKey(data)) {
                schedule = new Schedule(data, this.getApplicationContext(), this);
                schedule.deserialize(serializedData);
                scheduleMap.put(data, schedule);
            } else {
                schedule = scheduleMap.get(data);
            }
            System.out.println(layoutList1[0] + "");

            schedule.updateFragment(buttonList1, textList1, layoutList1);
        }
    }
}