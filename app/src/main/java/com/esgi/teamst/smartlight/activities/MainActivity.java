package com.esgi.teamst.smartlight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.esgi.teamst.smartlight.R;
import com.esgi.teamst.smartlight.models.Light;
import com.esgi.teamst.smartlight.models.LightResponse;
import com.esgi.teamst.smartlight.rest.ApiClient;
import com.esgi.teamst.smartlight.rest.LightServiceInterface;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.Date;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener, Callback<LightResponse> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private FloatingActionButton mFabLightingProgram;
    private FloatingActionButton mFabLogsList;
    private FloatingActionButton mFabSettings;
    private TextView mNameAutomaticLighting;
    private TextView mDescriptionAutomaticLighting;
    private TextView mNameContinueLighting;
    private TextView mDescriptionContinueLighting;
    private Switch mSwitchAutomaticLighting;
    private Switch mSwitchContinueLighting;
    private Switch mSwitchAutomaticIntensity;
    private SeekBar mSeekBarIntensity;

    private Light mRealmLight;
    private Realm mRealm;
    private LightServiceInterface mLightServiceInterface;
    private boolean mIgnoreChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        this.initView();
        this.initValues();
        mLightServiceInterface = ApiClient.getClient().create(LightServiceInterface.class);
        mSwitchAutomaticLighting.setOnCheckedChangeListener(this);
        mSwitchContinueLighting.setOnCheckedChangeListener(this);
        mSwitchAutomaticIntensity.setOnCheckedChangeListener(this);
        mFabLightingProgram.setOnClickListener(this);
        mFabLogsList.setOnClickListener(this);
        mFabSettings.setOnClickListener(this);
        mSeekBarIntensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i(TAG, "onProgressChanged: " + progress);
                Light light = new Light();
            //    light.setmDateSwitchedOn(new Date());
            //    light.setmAutomatic(mRealmLight.ismAutomatic());
            //    light.setmSwitchedOn(mRealmLight.ismSwitchedOn());
                light.setmBrightnessAuto(mRealmLight.ismBrightnessAuto());
                light.setmBrightnessValue(progress);
                Call<LightResponse> lightUpdate = mLightServiceInterface.updateLight(mRealmLight.getmId(), light);
                lightUpdate.enqueue(new Callback<LightResponse>() {
                    @Override
                    public void onResponse(Call<LightResponse> call, Response<LightResponse> response) {
                        Log.i(TAG, "onResponse: passage" + response.message());
                        Log.i(TAG, "onResponse: passage" + response.body().getLight().getmName());
                        if(response.code() == 200){
                            mRealm.beginTransaction();
                            mRealm.copyToRealmOrUpdate(response.body().getLight());
                            mRealm.commitTransaction();
                        }
                    }

                    @Override
                    public void onFailure(Call<LightResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @Override
    public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.switch_automatic_lighting:
                if(!mIgnoreChange){
                    Light light = new Light();
                    if(isChecked){
                        light.setmAutomatic(true);
                        light.setmSwitchedOn(false);
                    }else{
                        // Si on désactive un l'autre est déjà désactivé
                        light.setmAutomatic(false);
                        light.setmSwitchedOn(false);
                    }
                    light.setmBrightnessAuto(mRealmLight.ismBrightnessAuto());
                    Call<LightResponse> lightUpdate = mLightServiceInterface.updateLight(mRealmLight.getmId(), light);
                    lightUpdate.enqueue(this);

                }else {
                    mIgnoreChange = false; // Réinitialiser
                }

                break;
            case R.id.switch_continue_lighting:
                if(!mIgnoreChange){
                    Log.i(TAG, "onCheckedChanged: ok ok");
                    Light light = new Light();
                    light.setmDateSwitchedOn(new Date());
                    if(isChecked){
                        light.setmAutomatic(false);
                        light.setmSwitchedOn(true);
                    }else{
                        light.setmAutomatic(false);
                        light.setmSwitchedOn(false);
                    }
                    light.setmBrightnessAuto(mRealmLight.ismBrightnessAuto());
                    Call<LightResponse> lightUpdate = mLightServiceInterface.updateLight(mRealmLight.getmId(), light);
                    lightUpdate.enqueue(this);

                }else {
                    mIgnoreChange = false; // Réinitialiser
                }
                break;
            case R.id.switch_automatic_intensity:
                if(!mIgnoreChange) {
                    Light light = new Light();
                    light.setmDateSwitchedOn(new Date());
                    light.setmBrightnessAuto(isChecked);
                    light.setmAutomatic(mRealmLight.ismAutomatic());
                    light.setmSwitchedOn(mRealmLight.ismSwitchedOn());
                    Call<LightResponse> lightUpdate = mLightServiceInterface.updateLight(mRealmLight.getmId(), light);
                    lightUpdate.enqueue(this);
                }else{
                    mIgnoreChange = false; // Réinitialiser
                }
                break;

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_lighting_program:
                startActivity(new Intent(MainActivity.this, ProgrammingListActivity.class));
                break;
            case R.id.fab_logs_list:
                startActivity(new Intent(MainActivity.this, RecordListActivity.class));
                break;
            case R.id.fab_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
        }
    }

    @Override
    public void onResponse(Call<LightResponse> call, Response<LightResponse> response) {
        if(response.code() == 200){
            Log.i(TAG, "onResponse général: passage" + response.message());
            Log.i(TAG, "onResponse général: passage" + response.body().getLight().getmName());

            if(mSwitchAutomaticLighting.isChecked()){
                // Blocage du mode continue
                setContinueLightingOptionEnabled(false);
            }
            if (!mSwitchAutomaticLighting.isChecked()){
                // Déblocage du mode continue
                setContinueLightingOptionEnabled(true);
            }
            if(mSwitchContinueLighting.isChecked()){
                // Blocage du mode automatique
                setAutomaticLightingOptionEnabled(false);
            }
            if(!mSwitchContinueLighting.isChecked()){
                // Déblocage du mode continue
                setAutomaticLightingOptionEnabled(true);
            }

            if(mSwitchAutomaticIntensity.isChecked()){
                mSeekBarIntensity.setEnabled(false);
            }else{
                mSeekBarIntensity.setEnabled(true);
            }

            // Mise à jour de realm avec le nouveau éclairage
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(response.body().getLight());
            mRealm.commitTransaction();

        }else{
            Log.i(TAG, "onResponse: failed");
            // Si l'update a echoué le switch passe sur off
            if(mSwitchAutomaticLighting.isChecked()){
                mIgnoreChange = true;
                mSwitchAutomaticLighting.setChecked(false);
            }else if(mSwitchContinueLighting.isChecked()){
                mIgnoreChange = true;
                mSwitchContinueLighting.setChecked(false);
            }else{
                mIgnoreChange = true;
                mSwitchAutomaticIntensity.setChecked(false);
            }
            try {
                Log.i(TAG, "onResponse: " + response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onFailure(Call<LightResponse> call, Throwable t) {
        t.printStackTrace();
    }

    private void initView() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mFabLightingProgram = (FloatingActionButton) findViewById(R.id.fab_lighting_program);
        mFabLogsList = (FloatingActionButton) findViewById(R.id.fab_logs_list);
        mFabSettings = (FloatingActionButton) findViewById(R.id.fab_settings);
        mNameAutomaticLighting = (TextView) findViewById(R.id.text_name_automatic_lighting);
        mDescriptionAutomaticLighting = (TextView) findViewById(R.id.text_description_automatic_lighting);
        mNameContinueLighting = (TextView) findViewById(R.id.text_name_continue_lighting);
        mDescriptionContinueLighting = (TextView) findViewById(R.id.text_description_continue_lighting);
        mSwitchAutomaticLighting = (Switch) findViewById(R.id.switch_automatic_lighting);
        mSwitchContinueLighting = (Switch) findViewById(R.id.switch_continue_lighting);
        mSwitchAutomaticIntensity = (Switch) findViewById(R.id.switch_automatic_intensity);
        mSeekBarIntensity = (SeekBar) findViewById(R.id.seek_intensity);
        mSeekBarIntensity.setMax(15);
    }

    /**
     * Permet d'initialiser la vue en fonction du contenu de la base de données
     */
    private void initValues(){
        mRealm = Realm.getDefaultInstance();
        mRealmLight = mRealm.where(Light.class)
                .findFirst();
        if(mRealmLight != null){
            Log.i(TAG, "initValues: auto" + mRealmLight.ismAutomatic());
            Log.i(TAG, "initValues: on" + mRealmLight.ismSwitchedOn());
            Log.i(TAG, "initValues: photo" + mRealmLight.ismBrightnessAuto());
            mSwitchAutomaticLighting.setChecked(mRealmLight.ismAutomatic());
            mSwitchContinueLighting.setChecked(mRealmLight.ismSwitchedOn());
            mSwitchAutomaticIntensity.setChecked(mRealmLight.ismBrightnessAuto());
            // Soit éclairage auto soit continue
            if(mRealmLight.ismAutomatic()){
                setContinueLightingOptionEnabled(false);
            }else if (mRealmLight.ismSwitchedOn()){
                setAutomaticLightingOptionEnabled(false);
            }

            // Soit luminosité auto soit manuelle
            if(mRealmLight.ismBrightnessAuto()){
                mSeekBarIntensity.setEnabled(false);
            }
        }

    }

    /**
     * Permet d'activer (débloquer) ou de désactiver (bloquer) l'option de l'éclairage automatique
     * @param enabled Vrai pour activer, faux pour désactiver
     */
    private void setAutomaticLightingOptionEnabled(boolean enabled){
        mSwitchAutomaticLighting.setEnabled(enabled);
        mNameAutomaticLighting.setEnabled(enabled);
        mDescriptionAutomaticLighting.setEnabled(enabled);
    }

    /**
     * Permet d'activer (débloquer) ou de désactiver (bloquer) l'éclairage en continue
     * @param enabled Vrai pour activer, faux pour désactiver
     */
    private void setContinueLightingOptionEnabled(boolean enabled){
        mSwitchContinueLighting.setEnabled(enabled);
        mNameContinueLighting.setEnabled(enabled);
        mDescriptionContinueLighting.setEnabled(enabled);
    }

}
