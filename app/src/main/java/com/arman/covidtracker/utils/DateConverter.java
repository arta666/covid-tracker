package com.arman.covidtracker.utils;

import android.content.Context;
import android.text.format.DateUtils;

import com.arman.covidtracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateConverter {


    public static long convertToTimesTamp(Context context,String dataSet){

        SimpleDateFormat format = new SimpleDateFormat(context.getString(R.string.format_mongo_time), Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = format.parse(dataSet);
            format.setTimeZone(TimeZone.getDefault());
        } catch (ParseException e) {
            e.printStackTrace();
            return System.currentTimeMillis() /1000;
        }
        assert date != null;
        return date.getTime();
    }

    public static boolean isToday(Context context,String date){
        return DateUtils.isToday(convertToTimesTamp(context,date));
    }
}
