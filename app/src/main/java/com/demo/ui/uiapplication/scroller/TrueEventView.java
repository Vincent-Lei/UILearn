package com.demo.ui.uiapplication.scroller;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.demo.ui.uiapplication.LogUtil;

/**
 * Created by Vincent.Lei on 2018/8/7.
 * Title：
 * Note：
 */
public class TrueEventView extends View {
    public TrueEventView(Context context) {
        super(context);
    }

    public TrueEventView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TrueEventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean cost = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d("TrueEventView ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.d("TrueEventView ACTION_MOVE");
                cost = true;
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d("TrueEventView ACTION_UP");
                break;
        }
        return cost||super.onTouchEvent(event);
    }
}
