package com.lkmotion.yesincar.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间戳
 * @author LiHeng
 **/
public class DateFormatUtils {
    static DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getTimeString(long time){
        return timeFormat.format(new Date(time));
    }

    public static Date parseTime(String timeStr) throws Exception {
        return timeFormat.parse(timeStr);
    }
}
