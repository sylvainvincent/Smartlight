package com.esgi.teamst.smartlight.models;

/**
 * Created by sylvainvincent on 11/06/16.
 */
public class Light {

    private int mId;
    private String mName;
    private int mIntensity;
    private boolean mIsOn;
    private boolean mPresenceDetectorIsOn;

    public Light(){}

    public Light(int mId, String mName, int mIntensity, boolean mIsOn, boolean mPresenceDetectorIsOn) {
        this.mId = mId;
        this.mName = mName;
        this.mIntensity = mIntensity;
        this.mIsOn = mIsOn;
        this.mPresenceDetectorIsOn = mPresenceDetectorIsOn;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmIntensity() {
        return mIntensity;
    }

    public void setmIntensity(int mIntensity) {
        this.mIntensity = mIntensity;
    }

    public boolean ismIsOn() {
        return mIsOn;
    }

    public void setmIsOn(boolean mIsOn) {
        this.mIsOn = mIsOn;
    }

    public boolean ismPresenceDetectorIsOn() {
        return mPresenceDetectorIsOn;
    }

    public void setmPresenceDetectorIsOn(boolean mPresenceDetectorIsOn) {
        this.mPresenceDetectorIsOn = mPresenceDetectorIsOn;
    }
}
