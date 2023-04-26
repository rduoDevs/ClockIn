package com.example.clockitcurrent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import classes.Navigator;

public class MainActivity4 extends AppCompatActivity {

    private Button todayButton;
    private Button yesterdayButton;
    private Button tomorrowButton;
    private String firstString;
    private long dayMillis = 8.64e+7;
    private SimpleDateFormat formatting = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");  
    private Date today;
    private Date yesterday;
    private Date tomorrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Navigator.navigateTo((Button) this.findViewById(R.id.button4), this, MainActivity3.class);
        Navigator.navigateTo((Button) this.findViewById(R.id.button13), this, MainActivity5.class);

        todayButton = this.findViewById(R.id.todayButton);
        yesterdayButton = this.findViewById(R.id.yesterdayButton);
        tomorrowButton = this.findViewById(R.id.tomorrowButton);

        long millis = System.currentTimeMillis();
        today = new Date(millis);
        yesterday = new Date(millis - dayMillis);
        tomorrow = new Date(millis + dayMillis);
        //formatting.format(newDate).substring(0, 10);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button[] dayButtonList = {yesterdayButton, todayButton, tomorrowButton}; 
        Date[] dateList = {yesterday, today, tomorrow};
        Date[] 
        today.setOnClickListener(new View.onClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}