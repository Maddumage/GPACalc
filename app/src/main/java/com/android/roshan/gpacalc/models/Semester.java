package com.android.roshan.gpacalc.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amilah on 09-Mar-17.
 */

public class Semester implements Parcelable {
    private int id;
    private float sgpa;
    private float scredit;

    public Semester(){}

    public Semester(int id, float sgpa, float aFloat) {
        this.id = id;
        this.sgpa = sgpa;
        this.scredit = aFloat;
    }

    protected Semester(Parcel in) {
        id = in.readInt();
        sgpa = in.readFloat();
        scredit = in.readFloat();
    }

    public static final Creator<Semester> CREATOR = new Creator<Semester>() {
        @Override
        public Semester createFromParcel(Parcel in) {
            return new Semester(in);
        }

        @Override
        public Semester[] newArray(int size) {
            return new Semester[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeFloat(sgpa);
        dest.writeFloat(scredit);
    }
}
