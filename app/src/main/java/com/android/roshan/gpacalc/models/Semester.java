package com.android.roshan.gpacalc.models;

/**
 * Created by amilah on 09-Mar-17.
 */

public class Semester {
    private int id;
    private float sgpa;
    private float scredit;

    public Semester(int id, float sgpa, float aFloat) {
        this.id = id;
        this.sgpa = sgpa;
        this.scredit = aFloat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getSgpa() {
        return sgpa;
    }

    public void setSgpa(float sgpa) {
        this.sgpa = sgpa;
    }

    public float getScredit() {
        return scredit;
    }

    public void setScredit(float scredit) {
        this.scredit = scredit;
    }
}
