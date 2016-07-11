package com.esgi.teamst.smartlight.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.teamst.smartlight.R;
import com.esgi.teamst.smartlight.activities.ProgrammingActivity;
import com.esgi.teamst.smartlight.models.Light;
import com.esgi.teamst.smartlight.models.LightResponse;
import com.esgi.teamst.smartlight.models.ProgrammingResponse;
import com.esgi.teamst.smartlight.rest.ApiClient;
import com.esgi.teamst.smartlight.rest.LightServiceInterface;
import com.esgi.teamst.smartlight.rest.ProgrammingServiceInterface;
import com.esgi.teamst.smartlight.services.ProgrammingService;
import com.esgi.teamst.smartlight.utility.DateUtil;
import com.esgi.teamst.smartlight.utility.Util;
import com.esgi.teamst.smartlight.models.Programming;
import com.esgi.teamst.smartlight.models.Day;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sylvainvincent on 26/04/16.
 */
public class ProgrammingAdapter extends BaseAdapter {

    private static final String TAG = ProgrammingActivity.class.getSimpleName();
    private ArrayList<Programming> mProgrammingArrayList;
    private ProgrammingServiceInterface mProgrammingServiceInterface;
    private Realm realm;
    private Context mContext;
    private Programming programming;

    public ProgrammingAdapter(Context context, ArrayList<Programming> programmingArrayList){
        mContext = context;
        mProgrammingArrayList = programmingArrayList;
        mProgrammingServiceInterface = ApiClient.getClient().create(ProgrammingServiceInterface.class);
        realm = Realm.getDefaultInstance();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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

        programming = mProgrammingArrayList.get(position);

        assert viewHolder != null;
        viewHolder.mTextTime.setText(DateUtil.dateToTimeString(programming.getmTime()));

        viewHolder.mSwitchAlarm.setChecked(programming.ismEnabled());
        viewHolder.mSwitchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                Programming pro = new Programming();
                pro.setmEnabled(isChecked);
                pro.setmGradual(programming.ismGradual());
                pro.setmBrightnessValue(programming.getmBrightnessValue());
                pro.setmTrigger(programming.ismTrigger());
                Call<ProgrammingResponse> programmingResponseCall = mProgrammingServiceInterface.updateProgramming(programming.getmId(),pro);
                programmingResponseCall.enqueue(new Callback<ProgrammingResponse>() {
                    @Override
                    public void onResponse(Call<ProgrammingResponse> call, Response<ProgrammingResponse> response) {
                        if(response.code() == 200){
                            Log.e(TAG, "onResponse: on response OK" );
                            realm.beginTransaction();
                            mProgrammingArrayList.get(position).setmEnabled(isChecked);
                            realm.commitTransaction();
                            Intent intent = new Intent(mContext, ProgrammingService.class);
                            if(isChecked){
                                intent.putExtra("id",response.body().getProgramming().getmId());
                                mContext.startService(intent);
                            }else{
                                mContext.stopService(intent);
                            }
                            refreshList(mProgrammingArrayList);
                        }else{
                            Log.e(TAG, "onResponse: on response NON OK" + response.errorBody());
                            refreshList(mProgrammingArrayList);
                            Toast.makeText(mContext, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ProgrammingResponse> call, Throwable t) {
                        if(t instanceof IOException){
                            Log.i(TAG, "onResponse: IOException  ");
                            refreshList(mProgrammingArrayList);
                            Toast.makeText(mContext, "Problème de connexion", Toast.LENGTH_SHORT).show();

                        }else{
                            Log.i(TAG, "onResponse: autre probleme ");
                        }
                    }
                });
            }
        });

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


