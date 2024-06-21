/*
    MainActivity3 Class
    Handles the process of assigning schedules on different dates
    Schedules can be prompted with additional, outside info dependent on settings
*/

package com.clockinscheduler.clockitcurrent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.clockinscheduler.clockitcurrent.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import classes.Navigator;
import classes.Plan;
import classes.Schedule;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {
    // Constant for date formatting
    private final SimpleDateFormat formatting = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
    // Button references
    private Button todayButton;
    private Button yesterdayButton;
    private Button tomorrowButton;
    private String firstString;
    // String date-timestamps
    private String today;
    private String yesterday;
    private String tomorrow;
    // Arrays for elements in schedule fragment container
    private Button[] buttonList1;
    private TextView[] textList1;
    private View[] layoutList1;
    // Elements that interact with container
    private View fragView;
    private Spinner schedSpinner;
    private View noSchedLayout;
    private Button currentlySelectedButton;
    // Data variables
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private SharedPreferences fullPreferences;
    // Notifications & Corresponding Elements
    private Boolean[] notifsSet = {false, false, false};
    private AlarmManager alarmManager;
    private Map<String, Integer> typeToIcon = new HashMap<String, Integer>();
    private Map<Button, Plan> buttonToPlan = new HashMap<Button, Plan>();
    private Schedule currentSchedule;
    private boolean fragInUse = false;
    public NotificationChannel channel;



    // Send them to link with intent
    private void goToLink(Plan plan, String url) {
        try {
            String query = plan.getName();
            if (plan.getType() != "Special!" && plan.getType() != "Other") {
                query = query + " and " + plan.getType();
            }
            Uri link = Uri.parse(url+ query);
            Intent infoIntent = new Intent(Intent.ACTION_VIEW, link);
            infoIntent.putExtra(SearchManager.QUERY, query);
            startActivity(infoIntent);
        } catch (ActivityNotFoundException data) {
            data.printStackTrace();
        }
    }
    public void setNotification(Plan plan, int index) {
        // Set up the time for notification
        Calendar calendar = Calendar.getInstance();
        double diff = plan.getStartTime() - ((int) plan.getStartTime());
        int minutes = (int) ((diff * 60) + 0.5);
        int currentDay = Calendar.DAY_OF_MONTH;
        if (index == 0) {
            currentDay--;
        } else if (index == 2) {
            currentDay++;
        }
        calendar.set(Calendar.DAY_OF_MONTH, currentDay);
        calendar.set(Calendar.HOUR_OF_DAY, (int) (plan.getStartTime()));
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Only deploy notification if it's in future, not past
        if (calendar.getTimeInMillis() > System.currentTimeMillis()) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            String[] encouragements = {"Keep up the good work!", "Your next activity on the schedule's starting now!", "One more plan to do!"};
            Intent alarmIntent = new Intent(getApplicationContext(), Navigator.class);
            alarmIntent.putExtra("Title", plan.getName());
            alarmIntent.putExtra("Content", encouragements[(int) (Math.random() * 3)]);
            alarmIntent.putExtra("Icon", typeToIcon.get(plan.getType()));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, alarmIntent, PendingIntent.FLAG_IMMUTABLE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    // Animation to move the fragment within view
    private void moveFrag() {
        FragmentContainerView view = this.findViewById(R.id.infoView);
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

    public void promptInfo(Plan plan) {
        SharedPreferences settings = this.getApplicationContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        boolean valid = settings.getBoolean("ShowOutsideInfo", false);
        if (valid) {
            // Define elements
            FragmentContainerView view = this.findViewById(R.id.infoView);
            Button infoButton = view.findViewById(R.id.InfoButton);
            Button vidButton = view.findViewById(R.id.VideoButton);
            TextView title = view.findViewById(R.id.SelectedNameText);
            TextView timeRange = view.findViewById(R.id.SelectedTimeRange);
            TextView planText = view.findViewById(R.id.SelectedTypeText);

            // Change properties of elements to reflect current plan being viewed
            CharSequence newPlanText = "(" + plan.getType() + ")";
            CharSequence newInfoText = "Find Info on: '" + plan.getName() + "' in general";
            CharSequence newVidText = "Find Videos on: '" + plan.getName() + "'";

            title.setText(plan.getName());
            timeRange.setText(plan.convertToTimestamp());
            planText.setText(newPlanText);
            infoButton.setText(newInfoText);
            vidButton.setText(newVidText);

            view.setVisibility(View.VISIBLE);
            moveFrag();
            // Send user to specific Google query when prompted
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        // Set-up navigation
        Navigator.navigateTo((Button) this.findViewById(R.id.button9_1), this, MainActivity.class);
        Navigator.navigateTo((Button) this.findViewById(R.id.button2), this, MainActivity4.class);
        // Initialize variables
        todayButton = this.findViewById(R.id.todayButton);
        yesterdayButton = this.findViewById(R.id.yesterdayButton);
        tomorrowButton = this.findViewById(R.id.tomorrowButton);
        schedSpinner = this.findViewById(R.id.scheduleListSpinner);
        noSchedLayout = this.findViewById(R.id.NoScheduleLayout);
        currentlySelectedButton = todayButton;
        preferences = this.getApplicationContext().getSharedPreferences("ScheduleDates", Context.MODE_PRIVATE);
        fullPreferences = this.getApplicationContext().getSharedPreferences("Schedules", Context.MODE_PRIVATE);

        typeToIcon.put("School", R.drawable.school);
        typeToIcon.put("Work", R.drawable.work);
        typeToIcon.put("Wellbeing", R.drawable.wellbeing);
        typeToIcon.put("Other", R.drawable.empty);
        typeToIcon.put("Special!", R.drawable.star);
        typeToIcon.put("Downtime", R.drawable.downtime);

        // Set-up variables related to current time
        long millis = System.currentTimeMillis();
        today = formatting.format(new Date(millis));
        int dayMillis = 86400000;
        yesterday = formatting.format(new Date(millis - dayMillis));
        tomorrow = formatting.format(new Date(millis + dayMillis));

    }

    /*@Override
    /*protected void onResume() {
        super.onResume();
        fullPreferences = this.getApplicationContext().getSharedPreferences("Schedules", Context.MODE_PRIVATE);
        Map<String, String> scheduleMap = (Map<String, String>) fullPreferences.getAll();
        ArrayList<String> list = new ArrayList<String>(scheduleMap.keySet());
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        schedSpinner.setAdapter(typeAdapter);
    }*/


    @Override
    protected void onStart() {
        super.onStart();
        // Initialize the fragment element arrays
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

        // Set-up click-input listeners for the buttons
        todayButton.setOnClickListener(this);
        yesterdayButton.setOnClickListener(this);
        tomorrowButton.setOnClickListener(this);
        onClick(todayButton);

        // If an element within fragment is clicked, prompt outside info if settings allow
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

        // Set up click-input that closes the outside info tab on exit button
        FragmentContainerView view = this.findViewById(R.id.infoView);
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
        // Change visuals to reflect change in selected date
        for (Button button : dayButtonList) {
            if (button == currentlySelectedButton) {
                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#32D573")));
                button.setTypeface(button.getTypeface(), Typeface.BOLD);
            } else {
                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D6D6D6")));
                button.setTypeface(button.getTypeface(), Typeface.NORMAL);
            }
        }

        // Grab data for schedule on that date
        String formattedDate = dateList[index].toString().substring(0, 10);
        String serializedSched = preferences.getString(formattedDate, "N/A");

        if (serializedSched.equals("N/A")) { // Display no schedule found elements, allow for user to pick
            fragView.setVisibility(View.GONE);
            noSchedLayout.setVisibility(View.VISIBLE);
            if (schedSpinner.getSelectedItem() == null) {
                Map<String, String> scheduleMap = (Map<String, String>) fullPreferences.getAll();
                ArrayList<String> list = new ArrayList<String>(scheduleMap.keySet());
                ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
                schedSpinner.setAdapter(typeAdapter);

                // Update data for schedule on said data with input
                Button confirmButton = this.findViewById(R.id.confirmButton);
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (schedSpinner.getSelectedItem() != null) {
                            String val = "";

                            if (currentlySelectedButton == todayButton)
                                val = today.toString().substring(0, 10);
                            else if (currentlySelectedButton == yesterdayButton)
                                val = yesterday.toString().substring(0, 10);
                            else
                                val = tomorrow.toString().substring(0, 10);
                            editor = preferences.edit();
                            editor.putString(val, fullPreferences.getString((String) schedSpinner.getSelectedItem(), null));
                            editor.commit();
                            toClick(currentlySelectedButton);
                        }
                    }
                });

            }
        } else { // If there's data, display it
            noSchedLayout.setVisibility(View.GONE);
            fragView.setVisibility(View.VISIBLE);

            Schedule deserialized = new Schedule("New", this.getApplicationContext());
            currentSchedule = deserialized;
            deserialized.deserialize(serializedSched);
            deserialized.updateFragment(buttonList1, textList1, layoutList1);

            if (notifsSet[index] != true && index > 0) {
                notifsSet[index] = true;
                for (Plan plan : deserialized.plans) {
                    setNotification(plan, index);
                }
            }
        }
    }

    private void toClick(Button button) {
        onClick(button);
    }
}