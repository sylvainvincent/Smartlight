package com.esgi.teamst.smartlight;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sylvainvincent on 11/06/16.
 */
public class Util {

    public static String dateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
        return dateFormat.format(date);
    }

}
