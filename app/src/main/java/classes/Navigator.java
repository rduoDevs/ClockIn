/*
    Navigator Class
    Used for general navigation.
    Includes navigation into the app (notifications), in-app navigation, etc.
*/
package classes;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import com.example.clockitcurrent.R;

public class Navigator extends BroadcastReceiver {

    private String content;
    private String title;
    private int iconId;
    private int time;

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        // Verify it's within settings
        if (preferences.getBoolean("Notifications", true)) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "test_id")
                    .setSmallIcon(R.drawable.copy_of_for_glory)
                    .setContentTitle("tesssss")
                    .setContentText("Hi!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            System.out.println("YOOOOOO");
            notificationManager.notify(1, builder.build());
            System.out.println("SUCCESS");
        }
    }



    public static void navigateTo(Button button, Context context, Class toClass) {
        Intent moveIntent =  new Intent(context, toClass);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(moveIntent);
            }
        });
    }

    public static void initScheduleContainer(FragmentContainerView fragView, Button[] buttonList1, TextView[] textList1, View[] layoutList1) {
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
    }
}
