/*
    Globals Library
    Ryan Duong, April 2023
    Acting like a singleton for global methods & variables used across numerous activities.
 */
package classes;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Navigator {
    public Navigator() {}
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
