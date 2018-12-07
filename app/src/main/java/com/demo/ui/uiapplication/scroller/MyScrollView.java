package com.demo.ui.uiapplication.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.demo.ui.uiapplication.LogUtil;

/**
 * Created by Vincent.Lei on 2018/8/7.
 * Title：
 * Note：
 */
public class MyScrollView extends HorizontalScrollView {
    private View view_menu;
    private View view_content;

    private int mMenuWidth;
    private int mScreenWidth;
    private float mLastDownX;
    private boolean intercept;
    private boolean isOpen;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (view_menu == null) {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            view_menu = wrapper.getChildAt(0);
            view_content = wrapper.getChildAt(1);
            mScreenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
            view_content.getLayoutParams().width = mScreenWidth;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMenuWidth = view_menu.getMeasuredWidth();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                mLastDownX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                intercept =  (ev.getX() - mLastDownX > 50);
                break;
        }
        return intercept || super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LogUtil.d("l =" + l);
        float percent = (1.0f - l * 1.0f / mMenuWidth) / 2;
        view_menu.setScaleX(0.5f + percent);
        view_menu.setScaleY(0.5f + percent);
        view_menu.setAlpha(0.5f + percent);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            scrollTo(mMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean cost = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                mLastX = ev.getX();
                LogUtil.d("MyScrollView ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                cost = true;
                LogUtil.d("MyScrollView ACTION_UP");
                if (getScrollX() > mMenuWidth / 2) {
                    smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                } else {
                    smoothScrollTo(0, 0);
                    isOpen = true;
                }
                break;
        }
        return cost || super.onTouchEvent(ev);
    }
}
