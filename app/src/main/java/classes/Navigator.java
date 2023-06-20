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
import android.view.View;
import android.widget.Button;

import androidx.core.app.NotificationCompat;
import com.example.clockitcurrent.R;

public class Navigator extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        // Verify it's within settings
        if (preferences.getBoolean("Notifications", true)) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "test_id")
                    .setSmallIcon(intent.getIntExtra("Icon", R.drawable.logoblue))
                    .setContentTitle((CharSequence) intent.getStringExtra("Title"))
                    .setContentText((CharSequence) intent.getStringExtra("Content"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            notificationManager.notify(1, builder.build());
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
}
