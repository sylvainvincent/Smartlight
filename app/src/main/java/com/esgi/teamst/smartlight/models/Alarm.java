package com.esgi.teamst.smartlight.models;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by sylvainvincent on 26/04/16.
 */
public class Alarm {

    private int mId;
    private boolean active;
    private Date mTime;
    private Day mDays;

    public Alarm(int mId, boolean active, Date mTime, Day mDays) {
        this.mId = mId;
        this.active = active;
        this.mTime = mTime;
        this.mDays = mDays;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getmTime() {
        return mTime;
    }

    public void setmTime(Date mTime) {
        this.mTime = mTime;
    }

    public Day getmDays() {
        return mDays;
    }

    public void setmDays(Day mDays) {
        this.mDays = mDays;
    }
}
