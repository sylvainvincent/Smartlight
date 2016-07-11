package com.esgi.teamst.smartlight.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by sylvainvincent on 09/07/16.
 */
public class AutoStartReceiver extends BroadcastReceiver{

    private static final String TAG = AutoStartReceiver.class.getSimpleName();
    ProgrammingReceiver programmingReceiver = new ProgrammingReceiver();


    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.i(TAG, "onReceive: OK");
        /*if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            programmingReceiver.setAlarm(context);
        }*/
        if(intent.getAction().equals(Intent.ACTION_TIME_CHANGED)){
            Log.i(TAG, "onReceive: OK OK");
        }

        if(intent.getAction().equals(Intent.ACTION_TIME_TICK)){
            Log.i(TAG, "onReceive: OK OK OK");
        }
    }
}
