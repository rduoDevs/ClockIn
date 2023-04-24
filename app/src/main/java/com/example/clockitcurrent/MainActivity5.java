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
    private Plan currentPlan;
    private Map<Button, Plan> buttonToPlan = new Map<Button, Plan>();



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

        ArrayList<String> planTypeList = new ArrayList<String>();
        planTypeList.add("Wellbeing");
        planTypeList.add("School");
        planTypeList.add("Work");
        planTypeList.add("Downtime");
        planTypeList.add("Special!");
        planTypeList.add("Other");

        ArrayList<String> hourList = new ArrayList<String>();
        hourList.add("12");
        for (int i = 1; i < 12; i++) {
            hourList.add(i+"");
        }


        ArrayList<String> minuteList = new ArrayList<String>();
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
        planToButton = schedule.updateFragment(buttonList1, textList1, layoutList1);

        Button newPlanButton = this.findViewById(R.id.newPlanButton);
        newPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.plans.size() < 10) {
                    Plan plan = new Plan(schedule);
                    currentPlan = plan;
                    planToButton = schedule.updateFragment(buttonList1, textList1, layoutList1);
                }
            }
        });

        planNamer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                currentPlan.setName(planNamer.getText().toString());
                currentPlan.getSchedule().updateFragment(buttonList1, textList1, layoutList1);
                return false;
            }
        });

        public void setSpinnerSetsToPlan() {
            endAMScroll.setSelection(arr)
        }

        // Sync up the buttons to be editable when clicked
        for (Button currentButton : buttonList1) {
            currentButton.setOnClickListener( new View.OnClickListener() {
                if (planToButton.get(currentButton) != null) {
                    currentPlan = plan.planToButton.get(currentButton);
                    setSpinnerSetsToPlan();
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