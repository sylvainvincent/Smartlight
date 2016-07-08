package com.esgi.teamst.smartlight.rest;

import com.esgi.teamst.smartlight.models.RecordResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sylvainvincent on 04/07/16.
 */
public interface RecordServiceInterface {

    @GET("/records")
    Call<RecordResponse> getRecords();

}
