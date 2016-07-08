package com.esgi.teamst.smartlight.activities;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.esgi.teamst.smartlight.R;
import com.esgi.teamst.smartlight.listeners.ScrollViewListener;
import com.esgi.teamst.smartlight.models.Day;
import com.esgi.teamst.smartlight.models.Programming;
import com.esgi.teamst.smartlight.models.ProgrammingResponse;
import com.esgi.teamst.smartlight.rest.ApiClient;
import com.esgi.teamst.smartlight.rest.ProgrammingServiceInterface;
import com.esgi.teamst.smartlight.utility.DateUtil;
import com.esgi.teamst.smartlight.utility.Util;
import com.esgi.teamst.smartlight.views.CustomScrollView;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sylvainvincent on 11/06/16.
 */
public class ProgrammingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,
        View.OnClickListener, ScrollViewListener,
        Callback<ProgrammingResponse> {

    private static final String TAG = ProgrammingActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private CustomScrollView mScrollProgramming;
    private TimePicker mTimeProgramming;
    private TextView mTextAlarmMonday;
    private TextView mTextAlarmTuesday;
    private TextView mTextAlarmWednesday;
    private TextView mTextAlarmThursday;
    private TextView mTextAlarmFriday;
    private TextView mTextAlarmSunday;
    private TextView mTextAlarmSaturday;
    private LinearLayout mLinearContinueLighting;
    private TextView mTextNameGradualLight;
    private TextView mTextDescriptionGradualLight;
    private Switch mSwitchGradualLight;
    private SeekBar mSeekIntensity;
    private FloatingActionButton mFabSaveProgramming;
    private LinearLayout mLinearDaysProgramming;
    private CoordinatorLayout mCoordinatorProgramming;


    private ProgrammingServiceInterface mProgrammingServiceInterface;

    private Realm mRealm;
    private Programming mRealmProgramming;

    private String[] mDays = {"Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche"};
    private boolean[] mDaysChecked;
    private int mBrightnessValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programming);
        this.initViews();
        this.initLayoutWithProgramming();
        mProgrammingServiceInterface = ApiClient.getClient().create(ProgrammingServiceInterface.class);
        mSwitchGradualLight.setOnCheckedChangeListener(this);
        mLinearDaysProgramming.setOnClickListener(this);
        mScrollProgramming.setScrollViewListener(this);
        mFabSaveProgramming.setOnClickListener(this);
        mSeekIntensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBrightnessValue = progress;
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.switch_gradual_light){
            if(isChecked){
                mSeekIntensity.setEnabled(false);
            }else{
                mSeekIntensity.setEnabled(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear_days_programming:
                new AlertDialog.Builder(this)
                        .setTitle("Répétition")
                        .setMultiChoiceItems(mDays, mDaysChecked, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                 mDaysChecked[which] = isChecked;
                            }


                        })
                        .setPositiveButton(getString(android.R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTextAlarmMonday.setEnabled(mDaysChecked[0]);
                                mTextAlarmTuesday.setEnabled(mDaysChecked[1]);
                                mTextAlarmWednesday.setEnabled(mDaysChecked[2]);
                                mTextAlarmThursday.setEnabled(mDaysChecked[3]);
                                mTextAlarmFriday.setEnabled(mDaysChecked[4]);
                                mTextAlarmSaturday.setEnabled(mDaysChecked[5]);
                                mTextAlarmSunday.setEnabled(mDaysChecked[6]);

                            }
                        })
                        .setNegativeButton(getString(android.R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Day day = mRealmProgramming.getmDaysEnabled();
                                mDaysChecked[0] = day.isMonday();
                                mDaysChecked[1] = day.isTuesday();
                                mDaysChecked[2] = day.isWednesday();
                                mDaysChecked[3] = day.isThursday();
                                mDaysChecked[4] = day.isFriday();
                                mDaysChecked[5] = day.isSaturday();
                                mDaysChecked[6] = day.isSunday();
                            }
                        })
                        .setCancelable(false)
                        .create().show();
                break;
            case R.id.fab_save_programming:
                Log.i(TAG, "onClick: ok");
                Programming programming = new Programming();
                Calendar calendar = Calendar.getInstance();
                if(Build.VERSION.SDK_INT >= 23){
                    calendar.set(0,0,0,mTimeProgramming.getHour(),mTimeProgramming.getMinute());

                }else{
                    calendar.set(0,0,0,mTimeProgramming.getCurrentHour(),mTimeProgramming.getCurrentMinute());
                }
                programming.setmTime(calendar.getTime());
                programming.setmTrigger(false);
                Day day = new Day(mDaysChecked);
                programming.setmDaysEnabled(day);
                programming.setmEnabled(mRealmProgramming.ismEnabled());
                programming.setmBrightnessValue(mBrightnessValue);
                programming.setmGradual(mSwitchGradualLight.isChecked());
                Call<ProgrammingResponse> programmingResponseCall = mProgrammingServiceInterface.updateProgramming(mRealmProgramming.getmId(), programming);
                programmingResponseCall.enqueue(this);
                break;
        }
    }

    @Override
    public void onScrollChanged(CustomScrollView scrollView, int x, int y, int oldX, int oldY) {
        // Position actuel par rapport à la taille du scrollview
        int currentPosition = y + mScrollProgramming.getHeight();
        // Position du bas du dernier enfant du scrollview par rapport à la taille du scrollview
        int lastPosition = mScrollProgramming.getChildAt(mScrollProgramming.getChildCount()-1).getBottom();
        // On ajoute 50 pour que le boutton d'afficher avant la fin du scroll
        if(currentPosition + 50 >= lastPosition){
            mFabSaveProgramming.setVisibility(View.VISIBLE);
        }else {
            mFabSaveProgramming.setVisibility(View.INVISIBLE);
        }
    }

    private void initViews(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mScrollProgramming = (CustomScrollView) findViewById(R.id.scroll_programming);
        mTimeProgramming = (TimePicker) findViewById(R.id.time_programming);
        mTextAlarmMonday = (TextView) findViewById(R.id.text_alarm_monday);
        mTextAlarmTuesday = (TextView) findViewById(R.id.text_alarm_tuesday);
        mTextAlarmWednesday = (TextView) findViewById(R.id.text_alarm_wednesday);
        mTextAlarmThursday = (TextView) findViewById(R.id.text_alarm_thursday);
        mTextAlarmFriday = (TextView) findViewById(R.id.text_alarm_friday);
        mTextAlarmSunday = (TextView) findViewById(R.id.text_alarm_sunday);
        mTextAlarmSaturday = (TextView) findViewById(R.id.text_alarm_saturday);
        mLinearContinueLighting = (LinearLayout) findViewById(R.id.linear_continue_lighting);
        mTextNameGradualLight = (TextView) findViewById(R.id.text_name_gradual_light);
        mTextDescriptionGradualLight = (TextView) findViewById(R.id.text_description_gradual_light);
        mSwitchGradualLight = (Switch) findViewById(R.id.switch_gradual_light);
        mSeekIntensity = (SeekBar) findViewById(R.id.seek_intensity);
            mSeekIntensity.setMax(15);
        mLinearDaysProgramming = (LinearLayout) findViewById(R.id.linear_days_programming);
        mFabSaveProgramming = (FloatingActionButton) findViewById(R.id.fab_save_programming);
        mCoordinatorProgramming = (CoordinatorLayout) findViewById(R.id.coordinator_programming);

    }

    private void initLayoutWithProgramming(){
        mRealm = Realm.getDefaultInstance();
        mRealmProgramming = mRealm.where(Programming.class).findFirst();

        if(mRealmProgramming != null){
            mTimeProgramming.setIs24HourView(true);

            if(Build.VERSION.SDK_INT >= 23){
                mTimeProgramming.setHour(DateUtil.getHourByDate(mRealmProgramming.getmTime()));
            }else{
                mTimeProgramming.setCurrentHour(DateUtil.getHourByDate(mRealmProgramming.getmTime()));
            }

            mSwitchGradualLight.setChecked(mRealmProgramming.ismGradual());

            if(!mRealmProgramming.ismGradual()){
                mSeekIntensity.setProgress(mRealmProgramming.getmBrightnessValue());
            }else{
                mSeekIntensity.setEnabled(false);
            }

            Day day = mRealmProgramming.getmDaysEnabled();
            mTextAlarmMonday.setEnabled(day.isMonday());
            mTextAlarmTuesday.setEnabled(day.isTuesday());
            mTextAlarmWednesday.setEnabled(day.isWednesday());
            mTextAlarmThursday.setEnabled(day.isThursday());
            mTextAlarmFriday.setEnabled(day.isFriday());
            mTextAlarmSaturday.setEnabled(day.isSaturday());
            mTextAlarmSunday.setEnabled(day.isSunday());

            mDaysChecked = new boolean[7];
            mDaysChecked[0] = day.isMonday();
            mDaysChecked[1] = day.isTuesday();
            mDaysChecked[2] = day.isWednesday();
            mDaysChecked[3] = day.isThursday();
            mDaysChecked[4] = day.isFriday();
            mDaysChecked[5] = day.isSaturday();
            mDaysChecked[6] = day.isSunday();

        }


    }

    @Override
    public void onResponse(Call<ProgrammingResponse> call, Response<ProgrammingResponse> response) {
        if(response.code() == 200){
            Log.i(TAG, "onResponse: OK  ");
            mRealm.beginTransaction();
            mRealm.clear(Programming.class);
            mRealm.copyToRealm(response.body().getProgramming());
            mRealm.commitTransaction();
            setResult(RESULT_OK);
            finish();
        }else{
            try {
                Log.e(TAG, "onResponse: " + response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Snackbar.make(mCoordinatorProgramming,"Un problème inconnu est survenu",Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<ProgrammingResponse> call, Throwable t) {
        if(t instanceof IOException){
            Log.i(TAG, "onResponse: IOException  ");
            Snackbar.make(mCoordinatorProgramming,"Problème de connexion",Snackbar.LENGTH_SHORT).show();
        }else{
            t.printStackTrace();
            Log.i(TAG, "onResponse: Autre exception  " + t.getMessage());
            Snackbar.make(mCoordinatorProgramming,"Un problème inconnu est survenu",Snackbar.LENGTH_SHORT).show();
        }
    }
}
