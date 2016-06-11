package com.esgi.teamst.smartlight.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.esgi.teamst.smartlight.R;
import com.esgi.teamst.smartlight.Util;
import com.esgi.teamst.smartlight.models.Alarm;
import com.esgi.teamst.smartlight.models.Day;

import java.util.ArrayList;

/**
 * Created by sylvainvincent on 26/04/16.
 */
public class AlarmAdapter extends BaseAdapter {

    private ArrayList<Alarm> mAlarmsList;
    private Context mContext;

    public AlarmAdapter(Context context, ArrayList<Alarm> alarmsList){
        mAlarmsList = alarmsList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mAlarmsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAlarmsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(convertView == null){
            convertView = ((Activity) mContext).getLayoutInflater().inflate(R.layout.item_alarm, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mTextTime = (TextView) convertView.findViewById(R.id.text_time);
            viewHolder.mSwitchAlarm = (Switch) convertView.findViewById(R.id.switch_alarm);
            viewHolder.mTextMonday = (TextView) convertView.findViewById(R.id.text_alarm_monday);
            viewHolder.mTextTuesday = (TextView) convertView.findViewById(R.id.text_alarm_tuesday);
            viewHolder.mTextWednesday = (TextView) convertView.findViewById(R.id.text_alarm_wednesday);
            viewHolder.mTextThursday = (TextView) convertView.findViewById(R.id.text_alarm_thursday);
            viewHolder.mTextFriday = (TextView) convertView.findViewById(R.id.text_alarm_friday);
            viewHolder.mTextSunday = (TextView) convertView.findViewById(R.id.text_alarm_sunday);
            viewHolder.mTextSaturday = (TextView) convertView.findViewById(R.id.text_alarm_saturday);

            convertView.setTag(viewHolder);
        }else{
            try {
                viewHolder = (ViewHolder) convertView.getTag();
            }catch (Exception e){
                e.getStackTrace();
            }
        }

        Alarm alarm = mAlarmsList.get(position);

        viewHolder.mTextTime.setText(Util.dateToString(alarm.getmTime()));

        if(alarm.isActive())viewHolder.mSwitchAlarm.setChecked(true);
        else viewHolder.mSwitchAlarm.setChecked(false);

        Day day = alarm.getmDays();

        if(day.isMonday())  viewHolder.mTextMonday.setEnabled(true);
        else viewHolder.mTextMonday.setEnabled(false);

        if(day.isTuesday())  viewHolder.mTextTuesday.setEnabled(true);
        else viewHolder.mTextTuesday.setEnabled(false);

        if(day.isWednesday())  viewHolder.mTextWednesday.setEnabled(true);
        else viewHolder.mTextWednesday.setEnabled(false);

        if(day.isThursday())  viewHolder.mTextThursday.setEnabled(true);
        else viewHolder.mTextThursday.setEnabled(false);

        if(day.isFriday())  viewHolder.mTextFriday.setEnabled(true);
        else viewHolder.mTextFriday.setEnabled(false);

        if(day.isSunday())  viewHolder.mTextSunday.setEnabled(true);
        else viewHolder.mTextSunday.setEnabled(false);

        if(day.isMonday())  viewHolder.mTextMonday.setEnabled(true);
        else viewHolder.mTextSaturday.setEnabled(false);

        return convertView;
    }

    private class ViewHolder{
        private TextView mTextTime;
        private TextView mTextMonday;
        private TextView mTextTuesday;
        private TextView mTextWednesday;
        private TextView mTextThursday;
        private TextView mTextFriday;
        private TextView mTextSunday;
        private TextView mTextSaturday;
        private Switch mSwitchAlarm;
    }
}


