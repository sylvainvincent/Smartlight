package com.esgi.teamst.smartlight.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sylvainvincent on 03/07/16.
 */
public class LightResponse {

    @SerializedName("item")
    private Light light;

    @SerializedName("list")
    private List<Light> lights;

    public LightResponse() {
    }

    public LightResponse(Light light) {
        this.light = light;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public List<Light> getLights() {
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }
}
