package com.slib.hooktrack;

import android.util.Log;

/**
 * Created by Vincent.Lei on 2019/2/22.
 * Title：
 * Note：
 */
public class TrackLog {
    private static final String TAG = "TrackDebug";
    private static boolean logEnable = true;

    public static void d(String msg) {
        if (logEnable)
            Log.d(TAG, msg == null ? "" : msg);
    }

    public static void e(String msg) {
        if (logEnable)
            Log.e(TAG, msg == null ? "" : msg);
    }
}
