/*
    Plan Class
    Components of a Schedule, that store information on events, times, event types, and more
*/
package classes;

import android.content.res.ColorStateList;

public class Plan {
    // Object Variables
    private String name;
    private String planType;
    private double startTime;
    private double endTime;
    private ColorStateList planColor;
    private Schedule parentSchedule;
    // Variable for serialization
    public static final String PLAN_PARAM_FILLER = "*_*";

    // Constructor
    public Plan(Schedule sched) {
        double[] prelimTimeInfo = sched.findEarliestTimeSlot();
        name = "Some Event";
        planType = "Other";
        parentSchedule = sched;
        startTime = prelimTimeInfo[0];
        sched.plans.add(this);
        endTime = prelimTimeInfo[1];
    }

    // Converts the time data into a timestamp that can be displayed on string
    public String convertToTimestamp() {
        String moniker1;
        String moniker2;
        String result;
        double startTimeToUse = startTime;
        double endTimeToUse = endTime;
        // Change the values depending on AM/PM to fit timestamp
        if (startTimeToUse >= 13) {
            startTimeToUse -= 12;
            moniker1 = " PM";
        } else {
            moniker1 = " AM";
        }
        if (endTimeToUse >= 13) {
            endTimeToUse -= 12;
            moniker2 = " PM";
        } else {
            moniker2 = " AM";
        }
        if (startTimeToUse < 1) {
            startTimeToUse += 12;
        }
        if (endTimeToUse < 1) {
            endTimeToUse += 12;
        }
        // Start compiling the bits together into one timestamp string
        int startInt = (int) startTimeToUse;
        int startMinutes = (int) (((startTimeToUse - startInt) * 60)+.5);
        String sMinutes = startMinutes + "";
        if (startMinutes < 10) {
            sMinutes = "0" + sMinutes;
        }
        String minutes1 = startInt + ":" + sMinutes + moniker1;
        int endInt = (int) endTimeToUse;
        int endMinutes = (int) (((endTimeToUse - endInt) * 60)+.5);
        String eMinutes = endMinutes + "";
        if (endMinutes < 10) {
            eMinutes = "0" + eMinutes;
        }
        String minutes2 = endInt + ":" + eMinutes + moniker2;
        // Return the final string
        result = minutes1 + " to " + minutes2;
        return result;
    }

    // Serializes the plan as a String that can be stored as data
    public String serialize() {
        String newString = ("Name:" + this.name + PLAN_PARAM_FILLER);
        newString += ("PlanType:" + this.planType + PLAN_PARAM_FILLER);
        newString += ("StartTime:" + startTime + PLAN_PARAM_FILLER);
        newString += ("EndTime:" + endTime + PLAN_PARAM_FILLER);
        return newString;
    }

    // Deserializes a plan in String format so it can be used
    public void deserialize(String val) {
        String[] values = val.split("\\" + PLAN_PARAM_FILLER);
        for (String string : values) {
            if (string.contains("Name:")) {
                String newString = string.replace("Name:", "");
                this.name = newString;
            } else if (string.contains("PlanType:")) {
                String newString = string.replace("PlanType:", "");
                this.planType = newString;
            } else if (string.contains("StartTime:")) {
                String newData = string.replace("StartTime:", "");
                this.startTime = Double.parseDouble(newData);
            } else if (string.contains("EndTime:")) {
                String newData = string.replace("EndTime:", "");
                this.endTime = Double.parseDouble(newData);
            }
        }
    }



    // Setter Methods
    public void setName(String name) {
        this.name = name;
    }
    public void setType(String type) {
        this.planType = type;
    }
    public void setSchedule(Schedule schedule) {
        this.parentSchedule = schedule;
    }
    public void setStartTime(double time) {
        this.startTime = time;
    }
    public void setEndTime(double time) {
        this.endTime = time;
    }
    public void setPlanColor(ColorStateList list) {
        this.planColor = list;
    }

    // Getter Methods
    public String getName() {
        return this.name;
    }
    public String getType() {
        return this.planType;
    }
    public Schedule getSchedule() {
        return this.parentSchedule;
    }
    public double getStartTime() {
        return this.startTime;
    }
    public double getEndTime() {
        return this.endTime;
    }
    public ColorStateList getPlanColor() {
        return this.planColor;
    }

}
