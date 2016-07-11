package com.esgi.teamst.smartlight.rest;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.RealmObject;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sylvainvincent on 02/07/16.
 */
public class ApiClient {


    public static final String BASE_URL = "https://smart-light.herokuapp.com";

 //   public static final String BASE_URL = "http://10.0.2.2:8080"; // Emulateur

  //  public static final String BASE_URL = "http://192.168.1.17:8080"; // Device

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };

        Gson gson = new GsonBuilder()
                .setExclusionStrategies(exclusionStrategy)
                .create();

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
