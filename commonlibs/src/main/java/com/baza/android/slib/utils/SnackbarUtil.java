package com.baza.android.slib.utils;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Vincent.Lei on 2018/1/12.
 * Title：
 * Note：
 */

public class SnackbarUtil {
    public static void shortSdShow(Activity activity, View view) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.removeAllViews();
        snackbarLayout.setPadding(0, 0, 0, 0);
        snackbarLayout.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        snackbar.show();
    }
}
