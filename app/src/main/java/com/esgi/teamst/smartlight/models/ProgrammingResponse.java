package com.esgi.teamst.smartlight.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sylvainvincent on 04/07/16.
 */
public class ProgrammingResponse {

    @SerializedName("list")
    private List<Programming> programmings;

    @SerializedName("item")
    private Programming programming;

    public ProgrammingResponse() {
    }

    public ProgrammingResponse(List<Programming> programmings) {
        this.programmings = programmings;
    }

    public List<Programming> getProgrammings() {
        return programmings;
    }

    public void setProgrammings(List<Programming> programmings) {
        this.programmings = programmings;
    }

    public Programming getProgramming() {
        return programming;
    }

    public void setProgramming(Programming programming) {
        this.programming = programming;
    }
}
