package com.esgi.teamst.smartlight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.Toast;

import com.esgi.teamst.smartlight.R;
import com.esgi.teamst.smartlight.models.Day;
import com.esgi.teamst.smartlight.models.Light;
import com.esgi.teamst.smartlight.models.LightResponse;
import com.esgi.teamst.smartlight.models.Record;
import com.esgi.teamst.smartlight.models.RecordResponse;
import com.esgi.teamst.smartlight.models.Programming;
import com.esgi.teamst.smartlight.models.ProgrammingResponse;
import com.esgi.teamst.smartlight.rest.ApiClient;
import com.esgi.teamst.smartlight.rest.LightServiceInterface;
import com.esgi.teamst.smartlight.rest.RecordServiceInterface;
import com.esgi.teamst.smartlight.rest.ProgrammingServiceInterface;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by sylvainvincent on 03/07/16.
 */
public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    private ProgressBar mProgressBar;
    private Realm mRealm;
    private Light mLight;
    private Programming mProgramming;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initViews();
        initRealm();
        mProgressBar.setProgress(0);

        final LightServiceInterface lightServiceInterface = ApiClient.getClient().create(LightServiceInterface.class);
        final ProgrammingServiceInterface programmingServiceInterface = ApiClient.getClient().create(ProgrammingServiceInterface.class);
        final RecordServiceInterface recordServiceInterface = ApiClient.getClient().create(RecordServiceInterface.class);

        Call<LightResponse> lightsListCall = lightServiceInterface.getLights();
        lightsListCall.enqueue(new Callback<LightResponse>() {
            @Override
            public void onResponse(Call<LightResponse> call, Response<LightResponse> response) {
                if(response.code() == 200){
                    if(response.body().getLights().size() > 0 ){
                        // Récupération du 1er éclairage et ajout/modification dans Realm
                        mLight = response.body().getLights().get(0);
                        Log.i(TAG, "onResponse: RECUPERATION ECLAIRAGE" + mLight.toString());
                        mRealm.beginTransaction();
                        mRealm.clear(Light.class);
                        mRealm.copyToRealm(mLight);
                        mRealm.commitTransaction();

                    }else{
                        // Création d'une nouvelle éclairage et ajout/modification dans Realm
                        mLight = createDefaultLight();
                        Call<Light> lightPostCall = lightServiceInterface.postLight(mLight);
                        lightPostCall.enqueue(new Callback<Light>() {
                            @Override
                            public void onResponse(Call<Light> call, Response<Light> postResponse) {
                                if(postResponse.code() == 201){
                                    mLight.setmId(postResponse.body().getmId());
                                    Realm realm = Realm.getDefaultInstance();
                                    realm.beginTransaction();
                                    realm.copyToRealm(mLight);
                                    realm.commitTransaction();

                                }else{
                                    try {
                                        Log.i(TAG, "onResponse post : " + postResponse.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Light> call, Throwable t) {
                                if(t instanceof IOException){
                                    Log.i(TAG, "onResponse: IOException  ");
                                    // FIXME: 08/07/16 AJOUTER Un alerte dialog
                                }else{
                                    Log.i(TAG, "onResponse: autre probleme ");
                                }
                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call<LightResponse> call, Throwable t) {
                if(t instanceof IOException){
                    Log.i(TAG, "onResponse: IOException  ");
                    // FIXME: 08/07/16 AJOUTER Un alerte dialog
                }else{
                    Log.i(TAG, "onResponse: autre probleme ");
                }
            }
        });

        Call<ProgrammingResponse> programmingListCall = programmingServiceInterface.getProgrammings();
        programmingListCall.enqueue(new Callback<ProgrammingResponse>() {
            @Override
            public void onResponse(Call<ProgrammingResponse> call, Response<ProgrammingResponse> response) {
                if(response.code() == 200) {
                    // Récupération d'une programmation d'éclairage
                    if (response.body().getProgrammings().size() > 0) {
                        mProgramming = response.body().getProgrammings().get(0);
                        Log.i(TAG, "onResponse: RECUPERATION PROGRAMMATION" + mProgramming.toString());
                        try{
                            mRealm.beginTransaction();
                            mRealm.clear(Programming.class);
                            mRealm.copyToRealm(mProgramming);
                            mRealm.commitTransaction();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }else{
                        // Création d'une programmation d'éclairage et ajout/modification dans Realm
                        mProgramming = createDefaultProgramming();
                        Call<Programming> programmingPostCall = programmingServiceInterface.postProgramming(mProgramming);
                        programmingPostCall.enqueue(new Callback<Programming>() {
                            @Override
                            public void onResponse(Call<Programming> call, Response<Programming> postResponse) {
                                if(postResponse.code() == 201){
                                    mProgramming.setmId(postResponse.body().getmId());
                                    Log.i(TAG, "onResponse: Création Programming" + mProgramming.toString());
                                    Realm realm = Realm.getDefaultInstance();
                                    realm.beginTransaction();
                                    realm.copyToRealmOrUpdate(mProgramming);
                                    realm.commitTransaction();
                                }else{
                                    try {

                                        Log.i(TAG, "onResponse post : " + postResponse.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Programming> call, Throwable t) {
                                if(t instanceof IOException){
                                    Log.i(TAG, "onResponse: IOException  ");
                                    // FIXME: 08/07/16 AJOUTER Un alerte dialog
                                }else{
                                    Log.i(TAG, "onResponse: autre probleme ");
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ProgrammingResponse> call, Throwable t) {
                if(t instanceof IOException){
                    Log.i(TAG, "onResponse: IOException  ");
                    // FIXME: 08/07/16 AJOUTER Un alerte dialog
                }else{
                    Log.i(TAG, "onResponse: autre probleme ");
                }
            }
        });

        Call<RecordResponse> recordResponseCall = recordServiceInterface.getRecords();
        recordResponseCall.enqueue(new Callback<RecordResponse>() {
            @Override
            public void onResponse(Call<RecordResponse> call, Response<RecordResponse> response) {
                if(response.code() == 200){
                    List<Record> recordList = response.body().getRecords();
                    for (Record record: recordList) {
                        Log.i(TAG, "onResponse: get record " + record.toString());
                        mRealm.beginTransaction();
                        mRealm.clear(Record.class);
                        mRealm.copyToRealm(record);
                        mRealm.commitTransaction();
                    }
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<RecordResponse> call, Throwable t) {
                if(t instanceof IOException){
                    Log.i(TAG, "onResponse: IOException  ");
                    // FIXME: 08/07/16 AJOUTER Un alerte dialog
                }else{
                    Log.i(TAG, "onResponse: autre probleme ");
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    private void initViews(){
        mProgressBar = (ProgressBar) findViewById(R.id.progress_splash_screen);
    }

    private void initRealm(){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        mRealm = Realm.getDefaultInstance();
    }

    /**
     * Permet de créer une éclairage par défaut s'il n'en existe pas
     * dans la base de données du serveur
     * @return une éclairage
     */
    private Light createDefaultLight(){

        Light light = new Light();
        light.setmName("light");
        light.setmBrightnessValue(100);
        light.setmAutomatic(true);
        light.setmSwitchedOn(false);
        light.setmBrightnessAuto(true);
        light.setmSwitchedOffAutoValue(5);
       // light.setmDateSwitchedOn(new Date());

        return light;
    }

    /**
     * Permet de créer une programmation d'éclairage par défaut s'il n'en existe pas
     * dans la base de données du serveur
     * @return une programmation d'éclairage
     */
    private Programming createDefaultProgramming(){

        Day day = new Day();
        day.setMonday(true);
        day.setTuesday(true);
        day.setWednesday(true);
        day.setThursday(true);
        day.setFriday(true);
        day.setSaturday(false);
        day.setSunday(false);

        Programming programming = new Programming();
        programming.setmEnabled(false);
        programming.setmTrigger(false);
        programming.setmBrightnessValue(2);
        programming.setmGradual(false);
        programming.setmTime(new Date());
        programming.setmDaysEnabled(day);

        return programming;
    }
}
