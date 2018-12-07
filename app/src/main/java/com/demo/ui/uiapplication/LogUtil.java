package com.demo.ui.uiapplication;

import android.util.Log;

/**
 * Created by Vincent.Lei on 2018/6/25.
 * Title：
 * Note：
 */
public class LogUtil {
    private static final String TAG = "LogUtil";

    public static void d(String msg) {
        Log.d(TAG, msg != null ? msg : "");
    }
}
