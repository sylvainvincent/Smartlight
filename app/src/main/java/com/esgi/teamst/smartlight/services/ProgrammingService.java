package com.esgi.teamst.smartlight.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.esgi.teamst.smartlight.activities.ProgrammingActivity;
import com.esgi.teamst.smartlight.activities.ProgrammingTriggerActivity;
import com.esgi.teamst.smartlight.models.Programming;
import com.esgi.teamst.smartlight.receivers.ProgrammingReceiver;
import com.esgi.teamst.smartlight.utility.DateUtil;

import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;

/**
 * Created by sylvainvincent on 09/07/16.
 */
public class ProgrammingService extends Service {

    private static final String TAG = ProgrammingService.class.getSimpleName();
    private Realm mRealm;
    private Programming mRealmProgramming;
    private String mProgrammingId;
    private Timer timer;
    private Calendar c;
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Log.i(TAG, "run: OK");
            mRealm = Realm.getDefaultInstance();
            mRealmProgramming = mRealm.where(Programming.class).equalTo("mId", mProgrammingId).findFirst();
            test();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "oncreate",Toast.LENGTH_LONG).show();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "running", Toast.LENGTH_LONG).show();
        Log.i(TAG, "onStartCommand: running");
        mProgrammingId  = intent.getStringExtra("id");
        if(timer == null){
            timer = new Timer();
            timer.schedule(timerTask, 60000, 60000);
        }else{
            timer.cancel();
            timerTask.cancel();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    Log.i(TAG, "run: OK");
                    mRealm = Realm.getDefaultInstance();
                    mRealmProgramming = mRealm.where(Programming.class).equalTo("mId", mProgrammingId).findFirst();
                    test();
                }
            };
            timer = new Timer();
            timer.schedule(timerTask, 60000, 60000);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "test", Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: destroy");
        try {
            timer.cancel();
            timerTask.cancel();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void test(){
        c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK)-1;
        if(day == 0){
            day = 7;
        }
        Log.i(TAG, "test: heure actuel" + day + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
        Log.i(TAG, "test: alarm" + mRealmProgramming.getmTime());
        for(boolean b : mRealmProgramming.getmDaysEnabled().getBooleanDays()){
                Log.i(TAG, "boolean : " + b);
        }
        if(mRealmProgramming.getmDaysEnabled().getBooleanDays()[day-1]){
            if((c.get(Calendar.HOUR_OF_DAY) == DateUtil.getHourByDate(mRealmProgramming.getmTime()))
                    && (c.get(Calendar.MINUTE) == DateUtil.getMinutesByDate(mRealmProgramming.getmTime()))){
                PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
                wakeLock.acquire();
                KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
                KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
                keyguardLock.disableKeyguard();
                Intent dialogIntent = new Intent(this, ProgrammingTriggerActivity.class);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dialogIntent);
                Log.i(TAG, "test: ok");
                onDestroy();
            }

        }
    }

}
