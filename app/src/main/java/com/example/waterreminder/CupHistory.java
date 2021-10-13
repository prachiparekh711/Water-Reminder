package com.example.waterreminder;

import android.graphics.drawable.Drawable;

public class CupHistory {
    Drawable historyCup;
    String historyML, historyOZ, historyTime, historyDate;
    int hID;


    public CupHistory(Drawable historyCup, String historyML, String historyTime, String historyDate, String historyOZ) {
        this.historyCup = historyCup;
        this.historyML = historyML;
        this.historyTime = historyTime;
        this.historyDate = historyDate;
        this.historyOZ = historyOZ;
    }

    public CupHistory(int hID, Drawable historyCup, String historyML, String historyTime, String historyDate, String historyOZ) {
        this.historyCup = historyCup;
        this.historyML = historyML;
        this.historyTime = historyTime;
        this.historyDate = historyDate;
        this.hID = hID;
        this.historyOZ = historyOZ;
    }

    public String getHistoryOZ() {
        return historyOZ;
    }

    public void setHistoryOZ(String historyOZ) {
        this.historyOZ = historyOZ;
    }

    public String getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(String historyDate) {
        this.historyDate = historyDate;
    }

    public Drawable getHistoryCup() {
        return historyCup;
    }

    public void setHistoryCup(Drawable historyCup) {
        this.historyCup = historyCup;
    }

    public String getHistoryML() {
        return historyML;
    }

    public void setHistoryML(String historyML) {
        this.historyML = historyML;
    }

    public String getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(String historyTime) {
        this.historyTime = historyTime;
    }

    public int gethID() {
        return hID;
    }

    public void sethID(int hID) {
        this.hID = hID;
    }
}
