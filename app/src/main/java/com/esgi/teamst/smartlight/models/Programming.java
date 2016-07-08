package com.esgi.teamst.smartlight.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sylvainvincent on 26/04/16.
 */
public class Programming extends RealmObject {

    @PrimaryKey
    @SerializedName("_id")
    private String mId;
    @SerializedName("time")
    private Date mTime;
    @SerializedName("brightness_value")
    private int mBrightnessValue;
    @SerializedName("enabled")
    private boolean mEnabled;
    @SerializedName("trigger")
    private boolean mTrigger;
    @SerializedName("gradual")
    private boolean mGradual;
    @SerializedName("days_enabled")
    private Day mDaysEnabled;

    public Programming() {
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public Date getmTime() {
        return mTime;
    }

    public void setmTime(Date mTime) {
        this.mTime = mTime;
    }

    public int getmBrightnessValue() {
        return mBrightnessValue;
    }

    public void setmBrightnessValue(int mBrightnessValue) {
        this.mBrightnessValue = mBrightnessValue;
    }

    public boolean ismEnabled() {
        return mEnabled;
    }

    public void setmEnabled(boolean mEnabled) {
        this.mEnabled = mEnabled;
    }

    public boolean ismTrigger() {
        return mTrigger;
    }

    public void setmTrigger(boolean mTrigger) {
        this.mTrigger = mTrigger;
    }

    public boolean ismGradual() {
        return mGradual;
    }

    public void setmGradual(boolean mGradual) {
        this.mGradual = mGradual;
    }

    public Day getmDaysEnabled() {
        return mDaysEnabled;
    }

    public void setmDaysEnabled(Day mDaysEnabled) {
        this.mDaysEnabled = mDaysEnabled;
    }

    @Override
    public String toString() {
        return "Programming{" +
                "mId='" + mId + '\'' +
                ", mTime=" + mTime +
                ", mBrightnessValue=" + mBrightnessValue +
                ", mEnabled=" + mEnabled +
                ", mTrigger=" + mTrigger +
                ", mGradual=" + mGradual +
                ", mDaysEnabled=" + mDaysEnabled +
                '}';
    }
}
