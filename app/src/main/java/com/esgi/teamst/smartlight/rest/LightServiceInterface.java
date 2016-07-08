package com.esgi.teamst.smartlight.rest;

import com.esgi.teamst.smartlight.models.Light;
import com.esgi.teamst.smartlight.models.LightResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


/**
 * Created by sylvainvincent on 02/07/16.
 */
public interface LightServiceInterface {

    @Headers("Content-Type: application/json")
    @POST("/lights")
    Call<Light> postLight(@Body Light light);

    @GET("/lights/{id}")
    Call<LightResponse> getLight(@Path("id") String id);

    @GET("/lights")
    Call<LightResponse> getLights();

    @Headers("Content-Type: application/json")
    @PUT("/lights/{id}")
    Call<LightResponse> updateLight(@Path("id") String id, @Body Light light);
}
