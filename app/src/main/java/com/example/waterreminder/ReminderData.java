package com.example.waterreminder;

public class ReminderData {
    String reminderTime, reminderDays;
    int reminderStatus, rID;

    //  reminderStatus==0 for reminder off
    //  reminderStatus==1 for reminder on

    public ReminderData(int rID, String reminderTime, int reminderStatus, String reminderDays) {
        this.reminderTime = reminderTime;
        this.reminderDays = reminderDays;
        this.reminderStatus = reminderStatus;
        this.rID = rID;
    }

    public ReminderData(String reminderTime, int reminderStatus, String reminderDays) {
        this.reminderTime = reminderTime;
        this.reminderDays = reminderDays;
        this.reminderStatus = reminderStatus;
    }

    public int getrID() {
        return rID;
    }

    public void setrID(int rID) {
        this.rID = rID;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getReminderDays() {
        return reminderDays;
    }

    public void setReminderDays(String reminderDays) {
        this.reminderDays = reminderDays;
    }

    public int getReminderStatus() {
        return reminderStatus;
    }

    public void setReminderStatus(int reminderStatus) {
        this.reminderStatus = reminderStatus;
    }
}
