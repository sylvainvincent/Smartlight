package com.esgi.teamst.smartlight.utility;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sylvainvincent on 11/06/16.
 */
public class Util {

    public static String dateToTimeString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static int convertSeekBarValue(int seekValue){
        Log.i("test", "convertSeekBarValue: " + (float)seekValue*15/100);
        return Math.round((float)seekValue*15/100);
    }

}
