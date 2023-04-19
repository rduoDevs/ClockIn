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

    public Plan(Schedule sched) {
        double[] prelimTimeInfo = sched.findEarliestTimeSlot();
        name = "Some Event";
        planType = "Other";
        parentSchedule = sched;
        startTime = prelimTimeInfo[0];
        endTime = startTime += prelimTimeInfo[1];
    }

    // Constructs a generic Downtime plan when needed w/time params
    public Plan(Schedule sched, double start, double end) {
        name = "Downtime";
        planType = "Free-time";
        parentSchedule = sched;
        startTime = start;
        endTime = startTime += end;
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
