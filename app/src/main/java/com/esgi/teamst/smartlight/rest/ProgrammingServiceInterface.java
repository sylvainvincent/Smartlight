package com.esgi.teamst.smartlight.rest;

import com.esgi.teamst.smartlight.models.Light;
import com.esgi.teamst.smartlight.models.Programming;
import com.esgi.teamst.smartlight.models.ProgrammingResponse;

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
public interface ProgrammingServiceInterface {

    @Headers("Content-Type: application/json")
    @POST("/programmings")
    Call<Programming> postProgramming(@Body Programming programming);

    @GET("/programmings")
    Call<ProgrammingResponse> getProgrammings();

    @Headers("Content-Type: application/json")
    @PUT("/programmings/{id}")
    Call<ProgrammingResponse> updateProgramming(@Path("id") String id, @Body Programming programming);

}
