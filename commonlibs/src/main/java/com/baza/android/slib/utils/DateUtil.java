package com.baza.android.slib.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    public static final SimpleDateFormat SDF_YMD_HMS = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.CHINA);
    //    public static final SimpleDateFormat SDF_RABBIMQ = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss EEEE", Locale.ENGLISH);
    //    public static final SimpleDateFormat SDF_YMD_HMS_SSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS", Locale.CHINA);
    public static final SimpleDateFormat SDF_YMD = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
    public static final SimpleDateFormat SDF_MD_HM = new SimpleDateFormat("MM.dd  HH:mm", Locale.CHINA);
    public static final SimpleDateFormat SDF_HM = new SimpleDateFormat("HH:mm", Locale.CHINA);
    public static final SimpleDateFormat SDF_MD = new SimpleDateFormat("MM.dd", Locale.CHINA);
    public static final SimpleDateFormat SDF_Y = new SimpleDateFormat("yyyy", Locale.CHINA);
    public static final SimpleDateFormat SDF_YMD_HM = new SimpleDateFormat("yyyy.MM.dd  HH:mm", Locale.CHINA);
    public static final SimpleDateFormat SDF_YMD_B = new SimpleDateFormat("yyyy.MM", Locale.CHINA);
    public static final SimpleDateFormat SDF_YMD_H = new SimpleDateFormat("yyyy.MM.dd  HH", Locale.CHINA);
//    public static final int ONE_DAY_TIME_MILLIONS = 24 * 60 * 60 * 1000;
    public static final SimpleDateFormat DEFAULT_API_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    private static void setTimeZone() {
        TimeZone tz = TimeZone.getTimeZone("GMT+8");
        TimeZone.setDefault(tz);
    }

    static {
        setTimeZone();
    }

    public static String strMillions2FormatDate(String millionStr, SimpleDateFormat simpleDateFormat) {
        if (millionStr == null)
            return null;
        try {
            long lcc_time = Long.valueOf(millionStr);
            millionStr = simpleDateFormat.format(new Date(lcc_time));
        } catch (Exception e) {
            millionStr = null;
        }
        return millionStr;
    }

    public static String longMillions2FormatDate(long longMillions, SimpleDateFormat simpleDateFormat) {
        String result;
        try {
            result = simpleDateFormat.format(new Date(longMillions));
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    public static String formatMsgFriendlyTime(long timeMillions) {
        String time;
        try {
            Date dateNow = new Date();
            Date needFormat = new Date(timeMillions);

            if (SDF_YMD.format(dateNow).equals(SDF_YMD.format(needFormat)))
                time = SDF_HM.format(needFormat);
            else if (SDF_Y.format(dateNow).equals(SDF_Y.format(new Date(timeMillions))))
                time = SDF_MD.format(needFormat);
            else
                time = SDF_YMD.format(needFormat);

        } catch (Exception e) {
            time = null;
        }
        return time;
    }

    public static boolean isInSameDay(long time1, long time2) {
        try {
            return SDF_YMD.format(new Date(time1)).equals(SDF_YMD.format(new Date(time2)));
        } catch (Exception e) {
            //ignore
        }
        return false;
    }

    public static long parseLongDate(String date, SimpleDateFormat simpleDateFormat) {
        if (TextUtils.isEmpty(date))
            return 0;
        try {
            return simpleDateFormat.parse(date).getTime();
        } catch (Exception e) {
            //ignore
        }
        return 0;
    }

    public static boolean isInSameYear(long time1, long time2) {
        try {
            return SDF_Y.format(new Date(time1)).equals(SDF_Y.format(new Date(time2)));
        } catch (Exception e) {
            //ignore
        }
        return false;
    }
}
