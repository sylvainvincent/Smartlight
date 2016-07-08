package com.esgi.teamst.smartlight.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sylvainvincent on 04/07/16.
 */
public class RecordResponse {

    @SerializedName("list")
    private List<Record> records;

    public RecordResponse() {
    }

    public RecordResponse(List<Record> records) {
        this.records = records;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}
