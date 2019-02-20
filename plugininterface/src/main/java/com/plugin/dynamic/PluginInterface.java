package com.plugin.dynamic;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Vincent.Lei on 2019/2/18.
 * Title：
 * Note：
 */
public interface PluginInterface {
    void onCreate(Bundle saveInstance);

    void attachContext(FragmentActivity context);

    void onStart();

    void onResume();

    void onRestart();

    void onDestroy();

    void onStop();

    void onPause();
}