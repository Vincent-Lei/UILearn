package com.slib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
    private static final HashMap<String, SimpleDateFormat> dateFormatMap = new HashMap<>();
    private static final String DEFAULT_SDF_YMD = "yyyy.MM.dd";
    private static final String DEFAULT_SDF_Y = "yyyy";

    static {
        TimeZone tz = TimeZone.getTimeZone("GMT+8");
        TimeZone.setDefault(tz);
    }

    private static SimpleDateFormat getFormatAndCreateWhenNull(String formatPattern) {
        SimpleDateFormat simpleDateFormat = dateFormatMap.get(formatPattern);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(formatPattern, Locale.CHINA);
            dateFormatMap.put(formatPattern, simpleDateFormat);
        }
        return simpleDateFormat;
    }

    public static String parseLongMillions2FormatDate(long longMillions, String formatPattern) {
        SimpleDateFormat simpleDateFormat = getFormatAndCreateWhenNull(formatPattern);
        String result;
        try {
            result = simpleDateFormat.format(new Date(longMillions));
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    public static long parseTimeToLongMillions(String strData, String formatPattern) {
        Date date = parseTimeToDate(strData, formatPattern);
        return (date != null ? date.getTime() : 0);
    }

    public static Date parseTimeToDate(String strData, String formatPattern) {
        SimpleDateFormat simpleDateFormat = getFormatAndCreateWhenNull(formatPattern);
        try {
            return simpleDateFormat.parse(strData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isInSameDay(long time1, long time2) {
        SimpleDateFormat simpleDateFormat = getFormatAndCreateWhenNull(DEFAULT_SDF_YMD);
        try {
            return simpleDateFormat.format(new Date(time1)).equals(simpleDateFormat.format(new Date(time2)));
        } catch (Exception e) {
            //ignore
        }
        return false;
    }

    public static boolean isInSameYear(long time1, long time2) {
        SimpleDateFormat simpleDateFormat = getFormatAndCreateWhenNull(DEFAULT_SDF_Y);
        try {
            return simpleDateFormat.format(new Date(time1)).equals(simpleDateFormat.format(new Date(time2)));
        } catch (Exception e) {
            //ignore
        }
        return false;
    }
}
