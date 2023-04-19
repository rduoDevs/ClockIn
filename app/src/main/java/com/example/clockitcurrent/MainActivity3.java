package com.example.clockitcurrent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;


import classes.Navigator;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Navigator.navigateTo((Button) this.findViewById(R.id.button9_1), this, MainActivity.class);
        Navigator.navigateTo((Button) this.findViewById(R.id.button2), this, MainActivity4.class);
    }
}