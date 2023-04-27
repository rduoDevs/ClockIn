package com.example.clockitcurrent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import classes.Navigator;
import classes.Plan;
import classes.Schedule;

public class MainActivity5 extends AppCompatActivity {

    private EditText scheduleNamer;
    private EditText planNamer;
    private Spinner typeScroll;
    private Spinner startHourScroll;
    private Spinner startMinuteScroll;
    private Spinner startAMScroll;
    private Spinner endAMScroll;
    private Spinner endHourScroll;
    private Spinner endMinuteScroll;
    private Button removePlanButton;
    private Button saveScheduleButton;
    private Plan currentPlan;
    private Button[] buttonList1;
    private TextView[] textList1;
    private View[] layoutList1;
    private Map<Button, Plan> planToButton = new HashMap<Button, Plan>();

    private ArrayList<String> planTypeList = new ArrayList<String>();
    private ArrayList<String> minuteList = new ArrayList<String>();
    private ArrayList<String> hourList = new ArrayList<String>();


    private void setSpinnerSetsToPlan() {
        int hour;
        int minutes;
        int endHour;
        int endMinutes;
        if (currentPlan != null) {
            removePlanButton.setVisibility(View.VISIBLE);
            planNamer.setText((CharSequence) currentPlan.getName());
            hour = (int) currentPlan.getStartTime();
            minutes = (int) ((currentPlan.getStartTime() - hour) * 60 + 0.5);
            endHour = (int) currentPlan.getEndTime();
            endMinutes = (int) ((currentPlan.getEndTime() - endHour) * 60 + 0.5);

            // Set up AM/PM
            if (currentPlan.getStartTime() >= 12) {
                startAMScroll.setSelection(1);
            } else {
                startAMScroll.setSelection(0);
            }
            if (currentPlan.getEndTime() >= 12) {
                endAMScroll.setSelection(1);
            } else {
                endAMScroll.setSelection(0);
            }

            // Start Set-up
            startHourScroll.setSelection(hourList.indexOf(String.valueOf(hour)));
            String minuteString = String.valueOf(minutes);
            if (minutes < 10) {
                minuteString = "0" + minuteString;
            }
            startMinuteScroll.setSelection(minuteList.indexOf(minuteString));

            // End Set-Up
            endHourScroll.setSelection(hourList.indexOf(String.valueOf(endHour)));
            String endMinString = String.valueOf(endMinutes);
            if (endMinutes < 10) {
                endMinString = "0" + endMinString;
            }
            endMinuteScroll.setSelection(minuteList.indexOf(endMinString));
            typeScroll.setSelection(planTypeList.indexOf(currentPlan.getType()));
        } else {
            removePlanButton.setVisibility(View.GONE);
            startAMScroll.setSelection(0);
            startMinuteScroll.setSelection(0);
            startHourScroll.setSelection(0);
            endAMScroll.setSelection(0);
            endHourScroll.setSelection(0);
            typeScroll.setSelection(0);
            endMinuteScroll.setSelection(0);
            planNamer.setText((CharSequence) "No Plan Selected");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Navigator.navigateTo((Button) this.findViewById(R.id.button14), this, MainActivity4.class);
        typeScroll = this.findViewById(R.id.planTypeSpinner);
        planNamer = this.findViewById(R.id.planNamer);
        scheduleNamer = this.findViewById(R.id.scheduleNamer);
        startHourScroll = this.findViewById(R.id.startHourSpinner);
        startMinuteScroll = this.findViewById(R.id.startMinuteSpinner);
        startAMScroll = this.findViewById(R.id.startAMSpinner);
        endAMScroll = this.findViewById(R.id.endAMSpinner);
        endHourScroll = this.findViewById(R.id.endHourSpinner);
        endMinuteScroll = this.findViewById(R.id.endMinuteSpinner);
        planNamer = this.findViewById(R.id.planNamer);
        scheduleNamer = this.findViewById(R.id.scheduleNamer);
        removePlanButton = this.findViewById(R.id.removePlanButton);
        saveScheduleButton = this.findViewById(R.id.saveScheduleButton);


        planTypeList.add("Wellbeing");
        planTypeList.add("School");
        planTypeList.add("Work");
        planTypeList.add("Downtime");
        planTypeList.add("Special!");
        planTypeList.add("Other");


        hourList.add("12");
        for (int i = 1; i < 12; i++) {
            hourList.add(i+"");
        }



        for (int i = 0; i <= 59; i++) {
            if (i >= 10) {
                minuteList.add(i + "");
            } else {
                minuteList.add("0"+i);
            }
        }

        ArrayList<String> amList = new ArrayList<String>();
        amList.add("AM");
        amList.add("PM");

        addToSpinner(planTypeList, typeScroll);
        addToSpinner(hourList, startHourScroll);
        addToSpinner(hourList, endHourScroll);
        addToSpinner(minuteList, startMinuteScroll);
        addToSpinner(minuteList, endMinuteScroll);
        addToSpinner(amList, startAMScroll);
        addToSpinner(amList, endAMScroll);
    }
    @Override
    protected void onStart() {
        super.onStart();
        View fragView = this.findViewById(R.id.newScheduleContainer);
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


        Schedule schedule = new Schedule("Test", this.getApplicationContext(), (Activity) this);
        planToButton = schedule.updateFragment(buttonList1, textList1, layoutList1);
        Button newPlanButton = this.findViewById(R.id.newPlanButton);

        removePlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPlan != null) {
                    int index = schedule.plans.indexOf(currentPlan);
                    schedule.plans.remove(index);
                    currentPlan = null;
                    setSpinnerSetsToPlan();
                }
            }
        });


        saveScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schedule.save();
            }
        });

        newPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.plans.size() < 10) {
                    Plan plan = new Plan(schedule);
                    currentPlan = plan;
                    setSpinnerSetsToPlan();
                    planToButton = schedule.updateFragment(buttonList1, textList1, layoutList1);
                }
            }
        });

        planNamer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (currentPlan != null) {
                    currentPlan.setName(v.getText().toString());
                    currentPlan.getSchedule().updateFragment(buttonList1, textList1, layoutList1);
                }
                return false;
            }
        });

        scheduleNamer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                schedule.setName(v.getText().toString());
                return false;
            }
        });



        // Sync up the buttons to be editable when clicked
        for (Button currentButton : buttonList1) {
            currentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (planToButton.get(currentButton) != null) {
                        currentPlan = planToButton.get(currentButton);
                        setSpinnerSetsToPlan();
                    }
                }
            });
        }

        // For Type
        typeScroll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentPlan != null) {
                    String data = (String) parent.getItemAtPosition(position);
                    currentPlan.setType(data);
                    currentPlan.getSchedule().updateFragment(buttonList1, textList1, layoutList1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // For Times
        startHourScroll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentPlan != null) {
                    int data = Integer.parseInt((String) parent.getItemAtPosition(position));
                    if (data == 12) {
                        data = 0;
                    }
                    if ((String) startAMScroll.getSelectedItem() == "PM") {
                        data += 12;
                    }
                    currentPlan.setStartTime(data + ((double) (Integer.parseInt((String) startMinuteScroll.getSelectedItem())) /60));
                    currentPlan.getSchedule().updateFragment(buttonList1, textList1, layoutList1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        startAMScroll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentPlan != null) {
                    int data = Integer.parseInt((String) startHourScroll.getSelectedItem());
                    if (data == 12) {
                        data = 0;
                    }
                    if (parent.getItemAtPosition(position) == "PM") {
                        data += 12;
                    }
                    currentPlan.setStartTime(data + ((double) (Integer.parseInt((String) startMinuteScroll.getSelectedItem())) /60));
                    currentPlan.getSchedule().updateFragment(buttonList1, textList1, layoutList1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        startMinuteScroll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentPlan != null) {
                    int data = Integer.parseInt((String) startHourScroll.getSelectedItem());
                    if (data == 12) {
                        data = 0;
                    }
                    if (startAMScroll.getSelectedItem() == "PM") {
                        data += 12;
                    }
                    System.out.println(startMinuteScroll.getSelectedItem());
                    System.out.println(((double) (Integer.parseInt((String) startMinuteScroll.getSelectedItem())) / 60));
                    currentPlan.setStartTime(data + ((double) (Integer.parseInt((String) startMinuteScroll.getSelectedItem())) /60));
                    currentPlan.getSchedule().updateFragment(buttonList1, textList1, layoutList1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });





        endHourScroll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentPlan != null) {
                    int data = Integer.parseInt((String) parent.getItemAtPosition(position));
                    if (data == 12) {
                        data = 0;
                    }
                    if ((String) endAMScroll.getSelectedItem() == "PM") {
                        data += 12;
                    }
                    currentPlan.setEndTime(data + ((double) (Integer.parseInt((String) endMinuteScroll.getSelectedItem())) /60));
                    currentPlan.getSchedule().updateFragment(buttonList1, textList1, layoutList1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        endAMScroll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentPlan != null) {
                    int data = Integer.parseInt((String) endHourScroll.getSelectedItem());
                    if (data == 12) {
                        data = 0;
                    }
                    if (parent.getItemAtPosition(position) == "PM") {
                        data += 12;
                    }
                    currentPlan.setEndTime(data + ((double) (Integer.parseInt((String) endMinuteScroll.getSelectedItem())) /60));
                    currentPlan.getSchedule().updateFragment(buttonList1, textList1, layoutList1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        endMinuteScroll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentPlan != null) {
                    int data = Integer.parseInt((String) endHourScroll.getSelectedItem());
                    if (data == 12) {
                        data = 0;
                    }
                    if (endAMScroll.getSelectedItem() == "PM") {
                        data += 12;
                    }
                    System.out.println(endMinuteScroll.getSelectedItem());
                    System.out.println(((double) (Integer.parseInt((String) endMinuteScroll.getSelectedItem())) / 60));
                    currentPlan.setEndTime(data + ((double) (Integer.parseInt((String) endMinuteScroll.getSelectedItem())) /60));
                    currentPlan.getSchedule().updateFragment(buttonList1, textList1, layoutList1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }


    public void addToSpinner(ArrayList list, Spinner spinner) {
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(typeAdapter);
    }
}