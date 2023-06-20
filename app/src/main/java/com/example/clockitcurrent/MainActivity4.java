/*
    MainActivity4 Class
    Viewing activity allowing for user to look at all their saved schedules individually
    Schedules can be prompted with additional, outside info dependent on settings
*/
package com.example.clockitcurrent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import classes.Navigator;
import classes.Plan;
import classes.Schedule;

public class MainActivity4 extends AppCompatActivity {

    // Variables
    private Spinner spinner;
    private SharedPreferences preferences;
    private Schedule currentSchedule;
    private Map<String, Schedule> scheduleMap = new HashMap<String, Schedule>();
    // Fragment element arrays
    private Button[] buttonList1 = new Button[10];
    private TextView[] textList1 = new TextView[10];
    private View[] layoutList1 = new View[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        // Set-up navigation
        Navigator.navigateTo((Button) this.findViewById(R.id.button4), this, MainActivity3.class);
        Navigator.navigateTo((Button) this.findViewById(R.id.button13), this, MainActivity5.class);

        // Initialize variables
        preferences = this.getApplicationContext().getSharedPreferences("Schedules", Context.MODE_PRIVATE);
        spinner = this.findViewById(R.id.spinner);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Initialize element arrays
        View fragView = this.findViewById(R.id.fragmentContainerView3);
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

        // Place the schedules into spinner for user to select
        Map<String, String> scheduleMap = (Map<String, String>) preferences.getAll();
        ArrayList<String> list = new ArrayList<String>(scheduleMap.keySet());
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(typeAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateScreen(parent.getItemAtPosition(position).toString(), preferences.getString(parent.getItemAtPosition(position).toString(), "N/A"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (scheduleMap.size() > 0) {
            spinner.setSelection(0);
            this.findViewById(R.id.NoScheduleText).setVisibility(View.GONE);
            fragView.setVisibility(View.VISIBLE);
            updateScreen((String) spinner.getSelectedItem(), preferences.getString((String) spinner.getSelectedItem(), "N/A"));
        } else {
            fragView.setVisibility(View.GONE);
            this.findViewById(R.id.NoScheduleText).setVisibility(View.VISIBLE);
        }

        // Set-up the click listeners for buttons in fragment container
        for (int i = 0; i < buttonList1.length; i++) {
            Button buttonUsing = buttonList1[i];
            buttonUsing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentSchedule != null) {
                        int index = -1;
                        for (int i = 0; i < buttonList1.length; i++) {
                            if ((Button) v == buttonList1[i]) {
                                index = i;
                                break;
                            }
                        }
                        Plan planUsing = currentSchedule.plans.get(index);
                        promptInfo(planUsing);
                    }
                }
            });


        }
        // Set-up exit button for the info prompt
        FragmentContainerView view = this.findViewById(R.id.infoView2);
        Button leaveFragButton = view.findViewById(R.id.LeaveFragmentButton);
        leaveFragButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation move = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.test);
                view.startAnimation(move);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(View.GONE);
                        view.clearAnimation();
                    }
                }, 500);

            }
        });

    }

    // Animation for info prompt
    public void moveFrag() {
        FragmentContainerView view = this.findViewById(R.id.infoView2);
        Animation move = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.tween);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(move);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.clearAnimation();
            }
        }, 500);


    }

    // Set-up the info prompt on demand
    public void promptInfo(Plan plan) {
        SharedPreferences settings = this.getApplicationContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        boolean valid = settings.getBoolean("ShowOutsideInfo", false);
        if (valid) {
            FragmentContainerView view = this.findViewById(R.id.infoView2);
            Button infoButton = view.findViewById(R.id.InfoButton);
            Button vidButton = view.findViewById(R.id.VideoButton);
            TextView title = view.findViewById(R.id.SelectedNameText);
            TextView timeRange = view.findViewById(R.id.SelectedTimeRange);
            TextView planText = view.findViewById(R.id.SelectedTypeText);

            CharSequence newPlanText = "(" + plan.getType() + ")";
            CharSequence newInfoText = "Find Info on: '" + plan.getName() + "' in general";
            CharSequence newVidText = "Find Videos on: '" + plan.getName() + "'";

            // Update element properties
            title.setText(plan.getName());
            timeRange.setText(plan.convertToTimestamp());
            planText.setText(newPlanText);
            infoButton.setText(newInfoText);
            vidButton.setText(newVidText);

            view.setVisibility(View.VISIBLE);
            moveFrag();

            // Allow to direct user to links 
            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToLink(plan, "https://google.com/search?q=");
                }
            });
            vidButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToLink(plan, "https://youtube.com/search?q=");
                }
            });


        }
    }

    // Send them to link with intent
    private void goToLink(Plan plan, String url) {
        try {
            String query = plan.getName();
            if (plan.getType() != "Special!" && plan.getType() != "Other") {
                query = query + " and " + plan.getType();
            }
            Uri link = Uri.parse(url + query);
            Intent infoIntent = new Intent(Intent.ACTION_VIEW, link);
            infoIntent.putExtra(SearchManager.QUERY, query);
            startActivity(infoIntent);
        } catch (ActivityNotFoundException data) {
            data.printStackTrace();
        }
    }

    // Displays the needed schedule on demand
    public void updateScreen(String data, String serializedData) {
        if (serializedData != "N/A") {
            Schedule schedule;
            if (!scheduleMap.containsKey(data)) {
                schedule = new Schedule(data, this.getApplicationContext());
                schedule.deserialize(serializedData);
                scheduleMap.put(data, schedule);
                schedule.updateFragment(buttonList1, textList1, layoutList1);
            } else {
                schedule = scheduleMap.get(data);
                currentSchedule = schedule;
                schedule.updateFragment(buttonList1, textList1, layoutList1);
            }
        }
    }
}