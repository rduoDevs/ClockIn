package com.example.clockitcurrent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import classes.Navigator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up navigating.
        Navigator.navigateTo((Button) this.findViewById(R.id.startButton), this, MainActivity3.class);
        Navigator.navigateTo((Button) this.findViewById(R.id.settingsButton), this, MainActivity2.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("test_id", "Test Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Navigation to settings or rest of app
    /* public void onClick(View button) {
        if (button.getId() == R.id.startButton) {
            Intent firstMoveToIntent = new Intent(this, MainActivity2.class);
            startActivity(firstMoveToIntent);
        } else if (button.getId() == R.id.settingsButton) {
            Intent firstMoveToIntent = new Intent(this, MainActivity3.class);
            startActivity(firstMoveToIntent);
        }
    }*/

}