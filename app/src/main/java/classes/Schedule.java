/*
    Schedule Class
    Custom class that simulates schedules for the app
    Helps handle with logic implemeneting it onto UIs
*/

package classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Schedule {

    // Class Variables
    private String name;
    private Context context;
    public ArrayList<Plan> plans = new ArrayList<Plan>();
    private SharedPreferences preferences;
    private SharedPreferences colorPrefs;
    private SharedPreferences.Editor editor;
    
    // Variables for serialization + deserialization
    public static final String SCHEDULE_FILLER = "'-]!!!";
    public static final String PLAN_FILLER = "!!=_=!!";

    // Grab the values of corresponding plan types + colors
    private static Map<String, ColorStateList> getColors(SharedPreferences pref) {
        Map<String, ColorStateList> colorDict = new HashMap<String, ColorStateList>();
        colorDict.put("Wellbeing", ColorStateList.valueOf(Color.parseColor(pref.getString("PlanTypeColor:Wellbeing", "#5BE17D"))));
        colorDict.put("School", ColorStateList.valueOf(Color.parseColor(pref.getString("PlanTypeColor:School", "#FF2727"))));
        colorDict.put("Work", ColorStateList.valueOf(Color.parseColor(pref.getString("PlanTypeColor:Work", "#47AFFF"))));
        colorDict.put("Downtime", ColorStateList.valueOf(Color.parseColor(pref.getString("PlanTypeColor:Downtime", "#000000"))));
        colorDict.put("Special!", ColorStateList.valueOf(Color.parseColor(pref.getString("PlanTypeColor:Special!", "#FF7E1A"))));
        colorDict.put("Other", ColorStateList.valueOf(Color.parseColor(pref.getString("PlanTypeColor:Other", "#737373"))));
        return colorDict;
    }


    // Sends out a HashMap of the app data for schedules
    public static Map<String, String> getSchedules(Context context) {
        SharedPreferences preference = context.getSharedPreferences("Schedules", Context.MODE_PRIVATE);
        return (Map<String, String>) preference.getAll();
    }

    // Default, new blank schedule ready for editing
    public Schedule(String name, Context context) {
        this.name = name;
        this.context = context;
        this.colorPrefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
    }

    // Saves the schedule in app data with serialization
    public void save() {
        preferences = this.context.getSharedPreferences("Schedules", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(this.name, serialize());
        editor.commit();
    }

    // Transform schedule and its plans into a single, serialized string
    // This string can be saved as data under SharedPrefs
    public String serialize() {
        String serializedString = "";
        serializedString = ("Name:" + this.name + SCHEDULE_FILLER);
        serializedString = serializedString + ("Plans:");
        for (Plan plan: this.plans) {
            serializedString += (PLAN_FILLER + plan.serialize());
        }
        return serializedString;
    }

    // Deserialize stored schedule data from SharedPref
    // Converts to actual, usable Schedule + Plan objects
    public void deserialize(String serializedString) {
        String[] product = serializedString.split(SCHEDULE_FILLER);
        System.out.println(product.length);
        int index = 0;
        for (String string : product) { // Separates the string into different parts
            index++;
            System.out.println(string);
            if (string.contains("Name:") && index == 1) {
                String newString = string.replace("Name:", "");
                this.name = newString;
            } else if (string.contains("Plans:")) {
                String newString = string.replace("Plans:", "");
                String[] planStrings = newString.split(PLAN_FILLER);
                for (String val : planStrings) {
                    if (val.contains("Name:")) {
                        System.out.println(val);
                        Plan newPlan = new Plan(this);
                        newPlan.deserialize(val);
                    }
                }
                System.out.println(this.plans.size() + "PLANS");
            }
        }
    }

    // Updates the given elements in a FragmentContainer to display schedule
    public Map<Button, Plan> updateFragment(Button[] buttonList, TextView[] textList, View[] layoutList) {
        reorganizePlans();
        Map<Button, Plan> result = new HashMap<Button, Plan>();
        // Reset the container
        for (View layout : layoutList) {
           layout.setVisibility(View.GONE);
        }
        // Add the plans + details back in
        for (int i = 0; i < this.plans.size(); i++) {
            Plan plan = this.plans.get(i);
            Button buttonToEdit = (Button) buttonList[i];
            result.put(buttonToEdit, plan);
            TextView textToEdit = (TextView) textList[i];
            buttonToEdit.setText((CharSequence) plan.getName());
            buttonToEdit.setBackgroundTintList(getColors(colorPrefs).get(plan.getType()));
            textToEdit.setText(plan.convertToTimestamp());
            layoutList[i].setVisibility(View.VISIBLE);
        }
        // Return HashMap of corresponding buttons + plans in case activity class needs it
        return result;
    }

    // Reorganizes the plans in chronological order w/selection sort
    // Called after plan edits or new plan additions
    public void reorganizePlans() {
        double timeToBeat = 24;
        Plan planToBeat = null;
        ArrayList<Plan> planArray = new ArrayList<Plan>();
        int length = this.plans.size();
        for (int i = 0; i < length; i++) {
            timeToBeat = 24;
            for (Plan planVal : this.plans) {
                if (planVal.getStartTime() < timeToBeat) {
                    timeToBeat = planVal.getStartTime();
                    planToBeat = planVal;
                }
            }
            planArray.add(planToBeat);
            this.plans.remove(this.plans.indexOf(planArray.get(i)));
        }
        this.plans = planArray;
    }

    // Returns the earliest time slot available
    // and the longest duration it can be before the next event
    public double[] findEarliestTimeSlot() {
        if (this.plans.size() == 0) { // Return default 12AM to 1AM if no plans yet
            double[] result = {0, 1};
            return result;
        } else if (this.plans.get(0).getStartTime() > 0) { // Return 12AM to first plan start time if possible
            double[] result = {0, this.plans.get(0).getStartTime()};
            return result;
        } else { // Return the earliest time and available space if otherwise
            double duration = 0;
            double newTime = 0;
            for (int i = 0; i < this.plans.size() - 1; i++) {
                if (this.plans.get(i).getEndTime() < this.plans.get(i + 1).getStartTime()) {
                    double[] result = {this.plans.get(i).getEndTime(), this.plans.get(i+1).getStartTime() - (this.plans.get(i).getEndTime())};
                    return result;
                }
            }
            duration = 1.0;
            if (24 - this.plans.get(this.plans.size() - 1).getEndTime() < 1) {
                duration = 24 - this.plans.get(this.plans.size() - 1).getEndTime();
            }
            double[] result = {this.plans.get(this.plans.size() - 1).getEndTime(), duration};
            return result;
        }
    }


    // Setter method for name
    public void setName(String name) {
        this.name = name;
    }

}
