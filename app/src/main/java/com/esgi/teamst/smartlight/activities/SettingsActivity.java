package com.esgi.teamst.smartlight.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.esgi.teamst.smartlight.R;
import com.esgi.teamst.smartlight.models.Light;
import com.esgi.teamst.smartlight.models.LightResponse;
import com.esgi.teamst.smartlight.models.Programming;
import com.esgi.teamst.smartlight.rest.ApiClient;
import com.esgi.teamst.smartlight.rest.LightServiceInterface;
import com.esgi.teamst.smartlight.rest.ProgrammingServiceInterface;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sylvainvincent on 02/07/16.
 */
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SettingsActivity.class.getSimpleName();
    private EditText mEditAutomaticSwitchedOff;
    private FloatingActionButton mFabSaveSettings;
    private CoordinatorLayout mCoordinatorSettings;
    private ProgressDialog progress;

    private Realm mRealm;
    private Light mRealmLight;
    private LightServiceInterface mLightServiceInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.initViews();
        this.initValues();
        mFabSaveSettings.setOnClickListener(this);
        mLightServiceInterface = ApiClient.getClient().create(LightServiceInterface.class);
    }

    private void initViews(){
        mEditAutomaticSwitchedOff = (EditText) findViewById(R.id.edit_automatic_swiched_off);
        mFabSaveSettings = (FloatingActionButton) findViewById(R.id.fab_save_settings);
        mCoordinatorSettings = (CoordinatorLayout) findViewById(R.id.coordinator_settings);
    }

    private void initValues(){
        mRealm = Realm.getDefaultInstance();
        mRealmLight = mRealm.where(Light.class)
                .findFirst();
        mRealm.close();
        if(mRealmLight != null){
            mEditAutomaticSwitchedOff.setText(String.valueOf(mRealmLight.getmSwitchedOffAutoValue()));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_save_settings:
                if(!mEditAutomaticSwitchedOff.getText().toString().equals("")){
                    if(!mEditAutomaticSwitchedOff.getText().toString().equals(String.valueOf(mRealmLight.getmSwitchedOffAutoValue()))){


                        final int automaticSwitchedOff = Integer.parseInt(mEditAutomaticSwitchedOff.getText().toString());
                        Light light = new Light();
                        light.setmSwitchedOffAutoValue(automaticSwitchedOff);
                        light.setmBrightnessValue(mRealmLight.getmBrightnessValue());
                        light.setmAutomatic(mRealmLight.ismAutomatic());
                        light.setmBrightnessAuto(mRealmLight.ismBrightnessAuto());
                        light.setmSwitchedOn(mRealmLight.ismSwitchedOn());
                        final Call<LightResponse> lightCall = mLightServiceInterface.updateLight(mRealmLight.getmId(),light);
                        lightCall.enqueue(new Callback<LightResponse>() {
                            @Override
                            public void onResponse(Call<LightResponse> call, Response<LightResponse> response) {
                                if(response.code() == 200){
                                    mRealm.beginTransaction();
                                    mRealm.copyToRealmOrUpdate(response.body().getLight());
                                    mRealm.commitTransaction();
                                    progress.dismiss();
                                    Snackbar.make(mCoordinatorSettings,"Sauvegarde effectuée",Snackbar.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<LightResponse> call, Throwable t) {
                                if(t instanceof IOException){
                                    Log.i(TAG, "onResponse: IOException  ");
                                    call.cancel();
                                    call.clone().enqueue(this);
                                }else{
                                    Log.i(TAG, "onResponse: autre probleme ");
                                }
                            }
                        });
                        progress = ProgressDialog.show(this, null, "Chargement", true, true, new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                lightCall.cancel();
                            }
                        });
                    }else{
                        Snackbar.make(mCoordinatorSettings,"Il n'y a pas de changement",Snackbar.LENGTH_SHORT).show();
                    }

                }else{
                    Snackbar.make(mCoordinatorSettings,"Le champ est vide",Snackbar.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
