package com.android.roshan.gpacalc.models;

/**
 * Created by amilah on 20-Dec-16.
 */

public class User {

    private int uid ;
    private String index;
    private String name;
    private String pword ;

    public User(){}

    public User(int uid, String index, String name, String pword) {
        this.uid = uid;
        this.index = index;
        this.name = name;
        this.pword = pword;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPword() {
        return pword;
    }

    public void setPword(String pword) {
        this.pword = pword;
    }
}
