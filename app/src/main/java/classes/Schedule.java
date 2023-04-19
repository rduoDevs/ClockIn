package classes;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class Schedule {
    private String name;
    private ArrayList<Plan> plans;
    private int date;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public Schedule(String name, String template, int date, Context context) {
        this.name = name;
        this.date = date;
        preferences = context.getSharedPreferences(template, Context.MODE_PRIVATE);
        editor = preferences.edit();
        // Verify it's an actual schedule template
        if (preferences.getBoolean("Valid", false)) {
            String serialized = preferences.getString("Plans", "");
            if (!serialized.equals("")) {
                try {
                    plans = (ArrayList<Plan>) ObjectSerializer.deserialize(preferences.getString("Plans", ObjectSerializer.serialize(new ArrayList<Plan>())));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public Schedule(String name) {

    }

    public void saveAsTemplate() {}

    // Returns the earliest time slot available, and the longest duration it can be before the next event
    public double[] findEarliestTimeSlot() {
        return new double[2];
    }

    public ArrayList<double[]> findOpenSpots() {
        return new ArrayList<double[]>();
    }
}
