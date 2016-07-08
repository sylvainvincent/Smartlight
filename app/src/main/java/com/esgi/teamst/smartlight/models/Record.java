package com.esgi.teamst.smartlight.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sylvainvincent on 02/07/16.
 */
public class Record extends RealmObject {

    @PrimaryKey
    @SerializedName("_id")
    private String mId;
    @SerializedName("presence_trigger_date")
    private Date mPresenceDate;
    @SerializedName("brightness_value")
    private int brightness;

    public Record() {
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public Date getmPresenceDate() {
        return mPresenceDate;
    }

    public void setmPresenceDate(Date mPresenceDate) {
        this.mPresenceDate = mPresenceDate;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    @Override
    public String toString() {
        return "Record{" +
                "mId='" + mId + '\'' +
                ", mPresenceDate=" + mPresenceDate +
                ", brightness=" + brightness +
                '}';
    }
}
