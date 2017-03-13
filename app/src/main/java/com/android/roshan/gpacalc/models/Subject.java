package com.android.roshan.gpacalc.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amilah on 13-Mar-17.
 */

public class Subject implements Parcelable{

    private int id;
    private String sub_code;
    private String sub_name;
    private float sub_credit;
    private String sub_result;
    private int sem_id;

    public Subject(){}

    protected Subject(Parcel in) {
    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    public Subject(int id, String sub_code, String sub_name,int sem_id, float sub_credit, String sub_result) {
        this.id = id;
        this.sub_code = sub_code;
        this.sub_name = sub_name;
        this.sem_id = sem_id;
        this.sub_credit = sub_credit;
        this.sub_result = sub_result;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public float getSub_credit() {
        return sub_credit;
    }

    public void setSub_credit(float sub_credit) {
        this.sub_credit = sub_credit;
    }

    public String getSub_result() {
        return sub_result;
    }

    public void setSub_result(String sub_result) {
        this.sub_result = sub_result;
    }

    public int getSem_id() {
        return sem_id;
    }

    public void setSem_id(int sem_id) {
        this.sem_id = sem_id;
    }

    public static Creator<Subject> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
