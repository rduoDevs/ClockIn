package com.example.clockitcurrent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import classes.Navigator;

public class MainActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Navigator.navigateTo((Button) this.findViewById(R.id.button14), this, MainActivity4.class);
    }
}