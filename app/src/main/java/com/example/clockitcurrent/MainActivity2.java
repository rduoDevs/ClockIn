/*
    MainActivit2 Class
    Settings page that stores and updates settings data based on input
*/
package com.example.clockitcurrent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import classes.Navigator;

public class MainActivity2 extends AppCompatActivity {
    // Variables
    private TextView themeTextView;
    private SwitchCompat notificationSwitch;
    private SwitchCompat outsideSwitch;
    private Button lightlyButton;
    private Button darklyButton;
    private Button livelyButton;
    private Button exitButton;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init activity set-up
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ColorStateList lightVal = ColorStateList.valueOf(Color.parseColor("#C1C1C1"));
        ColorStateList darkVal = ColorStateList.valueOf(Color.parseColor("#232323"));

        // Initialize element/obj variables
        themeTextView = (TextView) this.findViewById(R.id.textView10);
        notificationSwitch = (SwitchCompat) this.findViewById(R.id.switch2);
        outsideSwitch = (SwitchCompat) this.findViewById(R.id.switch3);
        lightlyButton = (Button) this.findViewById(R.id.button);
        darklyButton = (Button) this.findViewById(R.id.button3);
        livelyButton = (Button) this.findViewById(R.id.button8);
        exitButton = (Button) this.findViewById(R.id.button9);
        Button[] themeButtonList = {lightlyButton, darklyButton, livelyButton};
        preferences = this.getApplicationContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        // Apply visual changes to UI based on current settings
        Navigator.navigateTo(exitButton, this, MainActivity.class);
        themeTextView.setText((CharSequence) preferences.getString("Theme", "Lightly"));
        notificationSwitch.setChecked(preferences.getBoolean("Notifications", true));
        outsideSwitch.setChecked(preferences.getBoolean("ShowOutsideInfo", true));
        for (Button element : themeButtonList) {
            if (element.getText().toString() == preferences.getString("Theme", "Lightly")) {
                element.setBackgroundTintList(darkVal);
                element.setTypeface(element.getTypeface(), Typeface.BOLD);
            } else {
                element.setBackgroundTintList(lightVal);
                element.setTypeface(element.getTypeface(), Typeface.NORMAL);
            }
        }

        // Set up preference changes based on input
        for (Button button : themeButtonList) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button current = (Button) v;
                    for (Button element : themeButtonList) {
                        if (element == current) {
                            element.setBackgroundTintList(darkVal);
                            element.setTypeface(element.getTypeface(), Typeface.BOLD);
                        } else {
                            element.setBackgroundTintList(lightVal);
                            element.setTypeface(element.getTypeface(), Typeface.NORMAL);
                        }
                    }
                    themeTextView.setText(current.getText());
                    editor.putString("Theme", current.getText().toString());
                    editor.commit();
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