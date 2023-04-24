package com.example.clockitcurrent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import classes.Navigator;
import classes.Plan;
import classes.Schedule;

public class MainActivity3 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Navigator.navigateTo((Button) this.findViewById(R.id.button9_1), this, MainActivity.class);
        Navigator.navigateTo((Button) this.findViewById(R.id.button2), this, MainActivity4.class);


    }

    @Override
    protected void onStart() {
        super.onStart();
        View fragView = this.findViewById(R.id.fragmentContainerView);
        Button[] buttonList1 = {
                (Button) fragView.findViewById(R.id.ScheduleButton1),
                (Button) fragView.findViewById(R.id.ScheduleButton2),
                (Button) fragView.findViewById(R.id.ScheduleButton3),
                (Button) fragView.findViewById(R.id.ScheduleButton4),
                (Button) fragView.findViewById(R.id.ScheduleButton5),
                (Button) fragView.findViewById(R.id.ScheduleButton6),
                (Button) fragView.findViewById(R.id.ScheduleButton7),
                (Button) fragView.findViewById(R.id.ScheduleButton8),
                (Button) fragView.findViewById(R.id.ScheduleButton9),
                (Button) fragView.findViewById(R.id.ScheduleButton10),
        };
        TextView[] textList1 = {
                (TextView) fragView.findViewById(R.id.ScheduleText1),
                (TextView) fragView.findViewById(R.id.ScheduleText2),
                (TextView) fragView.findViewById(R.id.ScheduleText3),
                (TextView) fragView.findViewById(R.id.ScheduleText4),
                (TextView) fragView.findViewById(R.id.ScheduleText5),
                (TextView) fragView.findViewById(R.id.ScheduleText6),
                (TextView) fragView.findViewById(R.id.ScheduleText7),
                (TextView) fragView.findViewById(R.id.ScheduleText8),
                (TextView) fragView.findViewById(R.id.ScheduleText9),
                (TextView) fragView.findViewById(R.id.ScheduleText10),
        };
        View[] layoutList1 = {
                (View) fragView.findViewById(R.id.ScheduleLayout1),
                (View) fragView.findViewById(R.id.ScheduleLayout2),
                (View) fragView.findViewById(R.id.ScheduleLayout3),
                (View) fragView.findViewById(R.id.ScheduleLayout4),
                (View) fragView.findViewById(R.id.ScheduleLayout5),
                (View) fragView.findViewById(R.id.ScheduleLayout6),
                (View) fragView.findViewById(R.id.ScheduleLayout7),
                (View) fragView.findViewById(R.id.ScheduleLayout8),
                (View) fragView.findViewById(R.id.ScheduleLayout9),
                (View) fragView.findViewById(R.id.ScheduleLayout10),
        };

        Schedule schedule = new Schedule("Test", this, (Activity) this);
        for (int i = 0; i < 5; i++) {
            Plan plan = new Plan(schedule);
        }
        schedule.updateFragment(buttonList1, textList1, layoutList1);
    }
}