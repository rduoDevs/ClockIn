package classes;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.clockitcurrent.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Schedule {
    private String name;
    private Context context;
    private Activity viewToUse;
    public ArrayList<Plan> plans = new ArrayList<Plan>();
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Map<String, ColorStateList> colorMap = getColors();
    
   /* public static ArrayList<Button> buttonList;
    public static ArrayList<View> layoutList;
    public static ArrayList<TextView> textList;
    */
    
    public static final String SCHEDULE_FILLER = "'-]!!!";
    public static final String PLAN_FILLER = "!!=_=!!";

    private static Map<String, ColorStateList> getColors() {
        Map<String, ColorStateList> colorDict = new HashMap<String, ColorStateList>();
        colorDict.put("Wellbeing", ColorStateList.valueOf(Color.parseColor("#FBE242")));
        colorDict.put("School", ColorStateList.valueOf(Color.parseColor("#25AF4C")));
        colorDict.put("Work", ColorStateList.valueOf(Color.parseColor("#2A55AA")));
        colorDict.put("Downtime", ColorStateList.valueOf(Color.parseColor("#232323")));
        colorDict.put("Special!", ColorStateList.valueOf(Color.parseColor("#FF4C4C")));
        colorDict.put("Other", ColorStateList.valueOf(Color.parseColor("#919191")));
        return colorDict;
    }

    /*// Creating a new schedule based off template.
    public Schedule(String name, String template, int date, Context context, Activity view) {
        this.name = name;
        this.date = date;
        this.viewToUse = view;
        this.context = context;
        preferences = context.getSharedPreferences(template, Context.MODE_PRIVATE);
        editor = preferences.edit();
        // Verify it's an actual schedule template + deserialize for use
        if (preferences.getBoolean("Valid", false)) {
            String serialized = preferences.getString("Data", "");
            deserialize(serialized);
        }
    }*/
    public static Map<String, String> getSchedules(Context context) {
        SharedPreferences preference = context.getSharedPreferences("Schedules", Context.MODE_PRIVATE);
        return (Map<String, String>) preference.getAll();
    }
    // Default, new blank schedule w/no template
    public Schedule(String name, Context context, Activity view) {
        this.name = name;
        this.context = context;
        this.viewToUse = view;
    }

    public void save() {
        preferences = this.context.getSharedPreferences("Schedules", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(this.name, serialize());
        editor.commit();
    }

    public String serialize() {
        String serializedString = "";
        serializedString = ("Name:" + this.name + SCHEDULE_FILLER);
        serializedString = serializedString + ("Plans:");
        for (Plan plan: this.plans) {
            serializedString += (PLAN_FILLER + plan.serialize());
        }
        return serializedString;
    }

    public void deserialize(String serializedString) {
        String[] product = serializedString.split(SCHEDULE_FILLER);
        System.out.println(product.length);
        int index = 0;
        for (String string : product) {
            index++;
            System.out.println(string);
            if (string.contains("Name:") && index == 1) {
                String newString = string.replace("Name:", "");
                this.name = newString;
            } else if (string.contains("Plans:")) {
               // System.out.println("HERE!!");
                String newString = string.replace("Plans:", "");
                String[] planStrings = newString.split(PLAN_FILLER);
                System.out.println(newString);
                System.out.println(planStrings.length);
                System.out.println(this.plans.size() + "PLANS");
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

    public void addPlan(Plan plan) {
        this.plans.add(plan);
    }
    public Map<Button, Plan> updateFragment(Button[] buttonList, TextView[] textList, View[] layoutList) {
        reorganizePlans();
        Map<Button, Plan> result = new HashMap<Button, Plan>();
        for (View layout : layoutList) {
            //System.out.println(layout);
           layout.setVisibility(View.GONE);
        }

        for (int i = 0; i < this.plans.size(); i++) {
            Plan plan = this.plans.get(i);
            Button buttonToEdit = (Button) buttonList[i];
            result.put(buttonToEdit, plan);
            TextView textToEdit = (TextView) textList[i];
            buttonToEdit.setText((CharSequence) plan.getName());
            buttonToEdit.setBackgroundTintList(colorMap.get(plan.getType()));
            textToEdit.setText(plan.convertToTimestamp());
            layoutList[i].setVisibility(View.VISIBLE);
        }
        return result;
    }

    public void reorganizePlans() {
        double timeToBeat = 24;
        ArrayList<Plan> planArray = new ArrayList<Plan>();
        for (int i = 0; i < planArray.size(); i++) {
            for (Plan planVal : this.plans) {
                if (planVal.startTime < timeToBeat) {
                    timeToBeat = planVal.startTime;

                }
            }
        }
    }

    // Returns the earliest time slot available, and the longest duration it can be before the next event
    private double  starter = 0;
    public double[] findEarliestTimeSlot() {
        if (this.plans.size() == 0) {
            double[] result = {0, 1};
            return result;
        } else if (this.plans[0].getStartTime > 0) {
            double[] result = {0, this.plans.get(i).getStartTime()};
            return result;
        } else {
            double duration = 0;
            double newTime = 0;
            for (int i = 0; i < this.plans.size() - 1; i++) {
                if (this.plans.get(i).getEndTime() < this.plans.get(i + 1).getStartTime()) {
                    double[] result = {this.plans.get(i).getEndTime(), this.plans.get(i).getStartTime() - (this.plans.get(i).getEndTime()};
                    return result;
                }
            }
            double duration = 1.0;
            if (24 - this.plans.get(this.plans.size() - 1).getEndTime() < 1) {
                duration = 24 - this.plans.get(this.plans.size() - 1).getEndTime();
            }
            return {this.plans.get(this.plans.size() - 1).getEndTime(), duration};
        }
        starter ++;
        ender ++;
        double[] result = {starter, ender};
        return result;
    }

    public void setName(String name) {
        this.name = name;
    }

}
