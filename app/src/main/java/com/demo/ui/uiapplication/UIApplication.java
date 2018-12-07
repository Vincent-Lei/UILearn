package com.demo.ui.uiapplication;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Vincent.Lei on 2018/10/12.
 * Title：
 * Note：
 */
public class UIApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
