package classes;

import android.content.res.ColorStateList;

public class Plan {
    class tags {}
    // Constants
    public static final String[] PLAN_TYPES = {
            "Wellbeing",
            "Work-Related",
            "School-Related",
            "Free-time",
            "Other"
    };

    // Object Variables
    private String name;
    private String planType;
    private double startTime;
    private double endTime;
    private ColorStateList planColor;
    private Schedule parentSchedule;
    public static final String PLAN_PARAM_FILLER = "*_*";

    public Plan(Schedule sched) {
        double[] prelimTimeInfo = sched.findEarliestTimeSlot();
        name = "Some Event";
        planType = "Other";
        parentSchedule = sched;
        startTime = prelimTimeInfo[0];
        sched.addPlan(this);
        endTime = prelimTimeInfo[1];
    }

    // Constructs a generic Downtime plan when needed w/time params
    public Plan(Schedule sched, double start, double end) {
        name = "Downtime";
        planType = "Free-time";
        parentSchedule = sched;
        startTime = start;
        endTime = startTime += end;
    }

    public String convertToTimestamp() {
        String moniker1;
        String moniker2;
        String result;
        double startTimeToUse = startTime;
        double endTimeToUse = endTime;
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

        result = minutes1 + " to " + minutes2;
        return result;
    }

    public String serialize() {
        String newString = ("Name:" + this.name + PLAN_PARAM_FILLER);
        newString += ("PlanType:" + this.planType + PLAN_PARAM_FILLER);
        newString += ("StartTime:" + startTime + PLAN_PARAM_FILLER);
        newString += ("EndTime:" + endTime + PLAN_PARAM_FILLER);
        return newString;
    }

    public void deserialize(String val) {
        String[] values = val.split(PLAN_PARAM_FILLER);
        for (String string : values) {
            if (string.contains("Name:")) {
                String newString = string.replace("Name:", "");
                this.name = newString;
            } else if (string.contains("PlanType:")) {
                String newString = string.replace("PlanType:", "");
                this.planType = newString;
            } else if (string.contains("StartTime:")) {
                String newData = string.replace("StartTime:", "");
                this.startTime = Integer.parseInt(newData);
            } else if (string.contains("EndTime:")) {
                String newData = string.replace("EndTime:", "");
                this.startTime = Integer.parseInt(newData);
            }
        }
    }

    public String stringifyDate() {
        // TODO: Finalize how date will be done.
        return "";
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
    public double getStartTime(double time) {
        return this.startTime;
    }
    public double getEndTime(double time) {
        return this.endTime;
    }
    public ColorStateList getPlanColor() {
        return this.planColor;
    }

}
