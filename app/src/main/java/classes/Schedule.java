package classes;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class Schedule {
    private String name;
    private Context context;
    private ArrayList<Plan> plans;
    private int date;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public static final String SCHEDULE_FILLER = "$#@";
    public static final String PLAN_FILLER = "!!=_=!!";

    // Creating a new schedule based off template.
    public Schedule(String name, String template, int date, Context context) {
        this.name = name;
        this.date = date;
        this.context = context;
        preferences = context.getSharedPreferences(template, Context.MODE_PRIVATE);
        editor = preferences.edit();
        // Verify it's an actual schedule template + deserialize for use
        if (preferences.getBoolean("Valid", false)) {
            String serialized = preferences.getString("Data", "");
            deserialize(serialized);
        }
    }

    // Default, new blank schedule w/no template
    public Schedule(String name, Context context) {
        this.name = name;
        this.context = context;
        this.date = 0;
    }

    public void saveAsTemplate() {
        preferences = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putBoolean("Valid", true);
        editor.putString("Data", serialize());
    }

    public String serialize() {
        String serializedString = "";
        serializedString += ("Name:" + this.name);
        serializedString += (SCHEDULE_FILLER + "Date:" + this.date + SCHEDULE_FILLER);
        serializedString += ("Plans:");
        for (Plan plan: this.plans) {
            serializedString += (PLAN_FILLER + plan.serialize());
        }
        return serializedString;
    }

    public void deserialize(String serializedString) {
        String[] product = serializedString.split(SCHEDULE_FILLER);
        for (String string : product) {
            /* if (string.contains("Name:")) {
                String newString = string.replace("Name:", "");
                this.name = newString;
            } else */
            if (string.contains("Plans:")) {
                String newString = string.replace("Plans:", "");
                String[] planStrings = newString.split(PLAN_FILLER);
                for (String val : planStrings) {
                    Plan newPlan = new Plan(this);
                    newPlan.deserialize(val);
                    this.plans.add(newPlan);
                }
            } else if (string.contains("Date:")) {
                String newData = string.replace("Date:", "");
                this.date = Integer.parseInt(newData);
            }
        }
    }

    public void updateFragment() {}

    // Returns the earliest time slot available, and the longest duration it can be before the next event
    public double[] findEarliestTimeSlot() {
        return new double[2];
    }

    public ArrayList<double[]> findOpenSpots() {
        return new ArrayList<double[]>();
    }
}
