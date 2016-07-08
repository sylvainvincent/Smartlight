package com.esgi.teamst.smartlight.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sylvainvincent on 11/06/16.
 */
public class Light extends RealmObject{

    @PrimaryKey
    @SerializedName("_id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("switched_on")
    private boolean mSwitchedOn;
    @SerializedName("automatic")
    private boolean mAutomatic;
    @SerializedName("brightness_auto")
    private boolean mBrightnessAuto;
    @SerializedName("brightness_value")
    private int mBrightnessValue;
    @SerializedName("switched_off_auto_value")
    private int mSwitchedOffAutoValue;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("switched_on_date")
    private Date mDateSwitchedOn;

    public Light() {
    }

    public Light(String mName, boolean switchedOn, boolean automatic, boolean mBrightnessAuto, int mBrightnessValue, String mMessage, Date mDateSwitchedOn) {
        this.mName = mName;
        this.mSwitchedOn = switchedOn;
        this.mAutomatic = automatic;
        this.mBrightnessAuto = mBrightnessAuto;
        this.mBrightnessValue = mBrightnessValue;
        this.mMessage = mMessage;
        this.mDateSwitchedOn = mDateSwitchedOn;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public boolean ismSwitchedOn() {
        return mSwitchedOn;
    }

    public void setmSwitchedOn(boolean mSwitchedOn) {
        this.mSwitchedOn = mSwitchedOn;
    }

    public boolean ismAutomatic() {
        return mAutomatic;
    }

    public void setmAutomatic(boolean mAutomatic) {
        this.mAutomatic = mAutomatic;
    }

    public boolean ismBrightnessAuto() {
        return mBrightnessAuto;
    }

    public void setmBrightnessAuto(boolean mBrightnessAuto) {
        this.mBrightnessAuto = mBrightnessAuto;
    }

    public int getmBrightnessValue() {
        return mBrightnessValue;
    }

    public void setmBrightnessValue(int mBrightnessValue) {
        this.mBrightnessValue = mBrightnessValue;
    }

    public int getmSwitchedOffAutoValue() {
        return mSwitchedOffAutoValue;
    }

    public void setmSwitchedOffAutoValue(int mSwitchedOffAutoValue) {
        this.mSwitchedOffAutoValue = mSwitchedOffAutoValue;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public Date getmDateSwitchedOn() {
        return mDateSwitchedOn;
    }

    public void setmDateSwitchedOn(Date mDateSwitchedOn) {
        this.mDateSwitchedOn = mDateSwitchedOn;
    }

    @Override
    public String toString() {
        return "Light{" +
                "mId='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                ", mSwitchedOn=" + mSwitchedOn +
                ", mAutomatic=" + mAutomatic +
                ", mBrightnessAuto=" + mBrightnessAuto +
                ", mBrightnessValue=" + mBrightnessValue +
                ", mSwitchedOffAutoValue=" + mSwitchedOffAutoValue +
                ", mMessage='" + mMessage + '\'' +
                ", mDateSwitchedOn=" + mDateSwitchedOn +
                '}';
    }
}
