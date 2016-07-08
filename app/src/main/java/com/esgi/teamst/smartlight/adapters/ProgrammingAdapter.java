package com.esgi.teamst.smartlight.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.esgi.teamst.smartlight.R;
import com.esgi.teamst.smartlight.utility.Util;
import com.esgi.teamst.smartlight.models.Programming;
import com.esgi.teamst.smartlight.models.Day;

import java.util.ArrayList;

/**
 * Created by sylvainvincent on 26/04/16.
 */
public class ProgrammingAdapter extends BaseAdapter {

    private ArrayList<Programming> mProgrammingArrayList;
    private Context mContext;

    public ProgrammingAdapter(Context context, ArrayList<Programming> programmingArrayList){
        mContext = context;
        mProgrammingArrayList = programmingArrayList;
    }

    @Override
    public int getCount() {
        return mProgrammingArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProgrammingArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(convertView == null){
            convertView = ((Activity) mContext).getLayoutInflater().inflate(R.layout.item_programming, parent, false);
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

        Programming programming = mProgrammingArrayList.get(position);

        assert viewHolder != null;
        viewHolder.mTextTime.setText(Util.dateToTimeString(programming.getmTime()));

        viewHolder.mSwitchAlarm.setChecked(programming.ismEnabled());

        Day day = programming.getmDaysEnabled();

        viewHolder.mTextMonday.setEnabled(day.isMonday());

        viewHolder.mTextTuesday.setEnabled(day.isTuesday());

        viewHolder.mTextWednesday.setEnabled(day.isWednesday());

        viewHolder.mTextThursday.setEnabled(day.isThursday());

        viewHolder.mTextFriday.setEnabled(day.isFriday());

        viewHolder.mTextSaturday.setEnabled(day.isSaturday());

        viewHolder.mTextSunday.setEnabled(day.isSunday());

        return convertView;
    }

    public void refreshList(ArrayList<Programming> programmingArrayList){
        mProgrammingArrayList = programmingArrayList;
        notifyDataSetChanged();
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


