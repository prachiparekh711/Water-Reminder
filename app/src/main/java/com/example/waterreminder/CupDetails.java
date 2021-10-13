package com.example.waterreminder;

import android.graphics.drawable.Drawable;

public class CupDetails {
    Drawable emptyCup, fullcup, blueCup;
    String cupML, cupOZ;

    public CupDetails(Drawable emptyCup, Drawable fullcup, Drawable blueCup, String cupML, String cupOZ) {
        this.emptyCup = emptyCup;
        this.fullcup = fullcup;
        this.blueCup = blueCup;
        this.cupML = cupML;
        this.cupOZ = cupOZ;
    }

    public String getCupOZ() {
        return cupOZ;
    }

    public void setCupOZ(String cupOZ) {
        this.cupOZ = cupOZ;
    }

    public Drawable getBlueCup() {
        return blueCup;
    }

    public void setBlueCup(Drawable blueCup) {
        this.blueCup = blueCup;
    }

    public Drawable getEmptyCup() {
        return emptyCup;
    }

    public void setEmptyCup(Drawable emptyCup) {
        this.emptyCup = emptyCup;
    }

    public Drawable getFullcup() {
        return fullcup;
    }

    public void setFullcup(Drawable fullcup) {
        this.fullcup = fullcup;
    }

    public String getCupML() {
        return cupML;
    }

    public void setCupML(String cupML) {
        this.cupML = cupML;
    }
}
