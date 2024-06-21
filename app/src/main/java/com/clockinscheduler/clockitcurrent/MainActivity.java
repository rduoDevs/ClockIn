/*
    MainActivity Class
    Opening intro screen
    Used to navigate to settings or rest of app
*/
package com.clockinscheduler.clockitcurrent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import com.clockinscheduler.clockitcurrent.R;

import classes.Navigator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up navigating.
        Navigator.navigateTo((Button) this.findViewById(R.id.startButton), this, MainActivity3.class);
        Navigator.navigateTo((Button) this.findViewById(R.id.settingsButton), this, MainActivity2.class);

        // Set up channel for notifications on app initialization
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("test_id", "Test Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}