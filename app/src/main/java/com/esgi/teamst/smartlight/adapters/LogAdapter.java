package com.esgi.teamst.smartlight.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.esgi.teamst.smartlight.R;
import com.esgi.teamst.smartlight.models.Record;
import com.esgi.teamst.smartlight.utility.Util;

import java.util.List;

/**
 * Created by sylvainvincent on 05/07/16.
 */
public class LogAdapter extends BaseAdapter {

    private static final String TAG = LogAdapter.class.getSimpleName();
    private List<Record> mRecordList;
    private Context mContext;

    public LogAdapter(Context context, List<Record> recordList){
        mContext = context;
        mRecordList = recordList;
    }

    @Override
    public int getCount() {
        return mRecordList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRecordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(convertView == null){
            convertView = ((Activity) mContext).getLayoutInflater().inflate(R.layout.item_record, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mTextDate = (TextView) convertView.findViewById(R.id.text_date_record);
            viewHolder.mTextBrightness = (TextView) convertView.findViewById(R.id.text_brightness_record);
            convertView.setTag(viewHolder);
        }else{
            try {
                viewHolder = (ViewHolder) convertView.getTag();
            }catch (Exception e){
                e.getStackTrace();
            }
        }

        Record record = mRecordList.get(position);
        Log.i(TAG, "getView: " + record.toString());
        assert viewHolder != null;
        viewHolder.mTextDate.setText(Util.dateToTimeString(record.getmPresenceDate()));
        viewHolder.mTextBrightness.setText(String.valueOf(record.getBrightness()));

        return convertView;
    }

    private class ViewHolder{
        private TextView mTextDate;
        private TextView mTextBrightness;
    }

}
