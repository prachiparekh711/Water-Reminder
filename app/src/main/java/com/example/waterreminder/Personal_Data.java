package com.example.waterreminder;

import android.net.Uri;

public class Personal_Data {
    String gender;
    int weightKg, weightLBS, neededML, neededOZ, oneTimeNeededML, oneTimeNeededOZ, TotalGlassPerDay;
    String weightIn, unitIn, wakeUpTime, sleepTime;
    Uri tone;
    String furtherReminder;

    public Personal_Data(String gender, int weightKg, int neededML, int oneTimeNeededML, int totalGlassPerDay, String weightIn, String wakeUpTime, String sleepTime, String furtherReminder, String unitIn, int neededOZ, int oneTimeNeededOZ, int weightLBS) {
        this.gender = gender;
        this.weightKg = weightKg;
        this.neededML = neededML;
        this.oneTimeNeededML = oneTimeNeededML;
        TotalGlassPerDay = totalGlassPerDay;
        this.weightIn = weightIn;
        this.wakeUpTime = wakeUpTime;
        this.sleepTime = sleepTime;
        this.furtherReminder = furtherReminder;
        this.unitIn = unitIn;
        this.neededOZ = neededOZ;
        this.oneTimeNeededOZ = oneTimeNeededOZ;
        this.weightLBS = weightLBS;
    }

    public Personal_Data(Uri tone) {
        this.tone = tone;
    }

    public Personal_Data() {

    }

    public int getNeededOZ() {
        return neededOZ;
    }

    public void setNeededOZ(int neededOZ) {
        this.neededOZ = neededOZ;
    }

    public int getOneTimeNeededOZ() {
        return oneTimeNeededOZ;
    }

    public void setOneTimeNeededOZ(int oneTimeNeededOZ) {
        this.oneTimeNeededOZ = oneTimeNeededOZ;
    }

    public String getFurtherReminder() {
        return furtherReminder;
    }

    public void setFurtherReminder(String furtherReminder) {
        this.furtherReminder = furtherReminder;
    }

    public String getUnitIn() {
        return unitIn;
    }

    public void setUnitIn(String unitIn) {
        this.unitIn = unitIn;
    }

    public Uri getTone() {
        return tone;
    }

    public void setTone(Uri tone) {
        this.tone = tone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(int weightKg) {
        this.weightKg = weightKg;
    }

    public int getWeightLBS() {
        return weightLBS;
    }

    public void setWeightLBS(int weightLBS) {
        this.weightLBS = weightLBS;
    }

    public int getNeededML() {
        return neededML;
    }

    public void setNeededML(int neededML) {
        this.neededML = neededML;
    }

    public int getOneTimeNeededML() {
        return oneTimeNeededML;
    }

    public void setOneTimeNeededML(int oneTimeNeededML) {
        this.oneTimeNeededML = oneTimeNeededML;
    }

    public int getTotalGlassPerDay() {
        return TotalGlassPerDay;
    }

    public void setTotalGlassPerDay(int totalGlassPerDay) {
        TotalGlassPerDay = totalGlassPerDay;
    }

    public String getWeightIn() {
        return weightIn;
    }

    public void setWeightIn(String weightIn) {
        this.weightIn = weightIn;
    }

    public String getWakeUpTime() {
        return wakeUpTime;
    }

    public void setWakeUpTime(String wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }
}
