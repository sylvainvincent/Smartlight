package com.esgi.teamst.smartlight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.esgi.teamst.smartlight.R;
import com.esgi.teamst.smartlight.adapters.AlarmAdapter;
import com.esgi.teamst.smartlight.models.Alarm;
import com.esgi.teamst.smartlight.models.Day;

import java.util.ArrayList;
import java.util.Date;

public class AlarmListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String TAG = AlarmListActivity.class.getSimpleName();
    private ListView mListAlarm;
    private ArrayList<Alarm> mAlarmArrayList;
    //private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_alarm_list);
        initView();
        Date date = new Date();
        mAlarmArrayList = new ArrayList<>();
        mAlarmArrayList.add(new Alarm(0, true, date, new Day(true, true, false, true, true, true, false)));
        mAlarmArrayList.add(new Alarm(1, false, date, new Day(false, false, false, false, false, true, true)));
        mListAlarm.setOnItemClickListener(this);
        mListAlarm.setAdapter(new AlarmAdapter(this, mAlarmArrayList));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: OK");
        startActivity(new Intent(AlarmListActivity.this, AlarmActivity.class));
    }

    private void initView() {
        mListAlarm = (ListView) findViewById(R.id.list_alarm);
        //fab = (FloatingActionButton) findViewById(R.id.fab_list_alarm);
    }
}
