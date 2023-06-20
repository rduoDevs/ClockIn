/*
    MainActivit2 Class
    Settings page that stores and updates settings data based on input
*/
package com.example.clockitcurrent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import classes.Navigator;

public class MainActivity2 extends AppCompatActivity {
    // Variables
    private TextView themeTextView;
    private SwitchCompat notificationSwitch;
    private SwitchCompat outsideSwitch;
    private Spinner spinner;
    private String currentPlanType;
    private Button exitButton;
    private Button[] colorButtonList;
    private Map<Button, String> buttonToColor = new HashMap<Button, String>();
    private Map<String, String> planTypeDefaults = new HashMap<String, String>();
    private SharedPreferences preferences;



    private void updateColorSelection(Button[] buttonList, String color) {
        for (Button button : buttonList) {
            if (buttonToColor.get(button).equals(color)) {
                button.setText("CHOSEN");
            } else {
                button.setText(" ");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init activity set-up
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        buttonToColor.put(this.findViewById(R.id.RedButton), "#FF2727");
        buttonToColor.put(this.findViewById(R.id.OrangeButton), "#FF7E1A");
        buttonToColor.put(this.findViewById(R.id.YellowButton), "#FFCE3B");
        buttonToColor.put(this.findViewById(R.id.GreenButton), "#5BE17D");
        buttonToColor.put(this.findViewById(R.id.BlueButton), "#47AFFF");
        buttonToColor.put(this.findViewById(R.id.IndigoButton), "#7621FF");
        buttonToColor.put(this.findViewById(R.id.VioletButton), "#A217DD");
        buttonToColor.put(this.findViewById(R.id.PinkButton), "#FF3CF5");
        buttonToColor.put(this.findViewById(R.id.BlackButton), "#000000");
        buttonToColor.put(this.findViewById(R.id.GrayButton), "#737373");

        planTypeDefaults.put("PlanTypeColor:Wellbeing", "#5BE17D");
        planTypeDefaults.put("PlanTypeColor:Other", "#737373");
        planTypeDefaults.put("PlanTypeColor:Downtime", "#000000");
        planTypeDefaults.put("PlanTypeColor:Work", "#47AFFF");
        planTypeDefaults.put("PlanTypeColor:School", "#FF2727");
        planTypeDefaults.put("PlanTypeColor:Special!", "#FF7E1A");


        // Initialize element/obj variables
        notificationSwitch = (SwitchCompat) this.findViewById(R.id.switch2);
        outsideSwitch = (SwitchCompat) this.findViewById(R.id.switch3);
        exitButton = (Button) this.findViewById(R.id.button9);
        Button[] list = {
                this.findViewById(R.id.RedButton),
                this.findViewById(R.id.OrangeButton),
                this.findViewById(R.id.YellowButton),
                this.findViewById(R.id.GreenButton),
                this.findViewById(R.id.BlueButton),
                this.findViewById(R.id.IndigoButton),
                this.findViewById(R.id.VioletButton),
                this.findViewById(R.id.PinkButton),
                this.findViewById(R.id.BlackButton),
                this.findViewById(R.id.GrayButton)

        };
        colorButtonList = list;
        preferences = this.getApplicationContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        // Apply visual changes to UI based on current settings
        Navigator.navigateTo(exitButton, this, MainActivity.class);
        notificationSwitch.setChecked(preferences.getBoolean("Notifications", false));
        outsideSwitch.setChecked(preferences.getBoolean("ShowOutsideInfo", false));

        ArrayList<String> planTypeList = new ArrayList<String>();
        planTypeList.add("Wellbeing");
        planTypeList.add("School");
        planTypeList.add("Work");
        planTypeList.add("Downtime");
        planTypeList.add("Special!");
        planTypeList.add("Other");

        spinner = this.findViewById(R.id.planColorSpinner);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, planTypeList);
        spinner.setAdapter(typeAdapter);

        String currentColorString = preferences.getString("PlanTypeColor:" + spinner.getSelectedItem(), planTypeDefaults.get("PlanTypeColor:" + spinner.getSelectedItem()));
        updateColorSelection(colorButtonList, currentColorString);

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences.Editor editor = preferences.edit();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currentColorString = preferences.getString("PlanTypeColor:" + parent.getSelectedItem(), planTypeDefaults.get("PlanTypeColor:" + parent.getSelectedItem()));
                updateColorSelection(colorButtonList, currentColorString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Set up preference changes based on input
        for (Button button : colorButtonList) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String colorString = buttonToColor.get(v);
                    editor.putString("PlanTypeColor:" + spinner.getSelectedItem(), colorString);
                    editor.commit();
                    updateColorSelection(colorButtonList, colorString);
                }
            });
        }
        notificationSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        editor.putBoolean("Notifications", isChecked);
                        editor.commit();
                    }
                }
        );
        outsideSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        editor.putBoolean("ShowOutsideInfo", isChecked);
                        editor.commit();
                    }
                }
        );
    }
}