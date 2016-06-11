package com.esgi.teamst.smartlight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.esgi.teamst.smartlight.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener {

    protected FloatingActionButton modifyEventButton;
    protected FloatingActionButton joinEventButton;
    protected FloatingActionsMenu multipleActionsEvent;
    protected Switch switchAutomaticLighting;
    protected Switch switchContinueLighting;
    protected Switch switchTimeLighting;
    protected FloatingActionButton fabLightingProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
         this.initView();
        switchAutomaticLighting.setOnCheckedChangeListener(this);
        switchContinueLighting.setOnCheckedChangeListener(this);
        fabLightingProgram.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.switch_automatic_lighting:
                if(isChecked){
                    switchContinueLighting.setChecked(false);
                }
                break;
            case R.id.switch_continue_lighting:
                if(isChecked){
                    switchAutomaticLighting.setChecked(false);
                }
                break;

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_lighting_program:
                startActivity(new Intent(MainActivity.this, AlarmListActivity.class));
                break;
        }
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        modifyEventButton = (FloatingActionButton) findViewById(R.id.modify_event_button);
        joinEventButton = (FloatingActionButton) findViewById(R.id.join_event_button);
        multipleActionsEvent = (FloatingActionsMenu) findViewById(R.id.multiple_actions_event);
        switchAutomaticLighting = (Switch) findViewById(R.id.switch_automatic_lighting);
        switchContinueLighting = (Switch) findViewById(R.id.switch_continue_lighting);
        switchTimeLighting = (Switch) findViewById(R.id.switch_time_lighting);
        fabLightingProgram = (FloatingActionButton) findViewById(R.id.fab_lighting_program);
    }
}
