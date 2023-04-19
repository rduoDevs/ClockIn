package com.example.clockitcurrent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import classes.Navigator;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Navigator.navigateTo((Button) this.findViewById(R.id.button4), this, MainActivity3.class);
        Navigator.navigateTo((Button) this.findViewById(R.id.button13), this, MainActivity5.class);
    }
}