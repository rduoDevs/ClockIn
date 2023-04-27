/*
    Navigator Class
    Ryan Duong, April 2023
    Used for general navigation.
    Includes navigation into the app (notifications), in-app navigation, etc.
 */
package classes;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Navigator extends BroadcastReceiver {

    private String content;
    private int time;

    public Navigator(String content, int time) {
        this.content = content;
        this.time = time;
    }

    private void newChannel() {
                // Create the NotificationChannel, but only on API 26+ because
                // the NotificationChannel class is new and not in the support library
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    CharSequence name = getString(R.string.channel_name);
                    String description = getString(R.string.channel_description);
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                    channel.setDescription(description);
                    // Register the channel with the system; you can't change the importance
                    // or other notification behaviors after this
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                }

    private void setUpInteraction(){}



    public static void navigateTo(Button button, Context context, Class toClass) {
        Intent moveIntent =  new Intent(context, toClass);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(moveIntent);
            }
        });
    }
}
