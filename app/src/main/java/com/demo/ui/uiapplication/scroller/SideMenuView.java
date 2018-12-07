package com.demo.ui.uiapplication.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.demo.ui.uiapplication.LogUtil;

/**
 * Created by Vincent.Lei on 2018/8/8.
 * Title：
 * Note：
 */
public class SideMenuView extends LinearLayout {
    private int mTouchSlop;
    private float mLastX, mLastY;
    private float mDx, mDy;
    private int mMenuWidth;
    private Scroller mScroller;
    private View mContentView, mMenuView;
    private boolean mIntercept;

    public SideMenuView(Context context) {
        this(context, null);
    }

    public SideMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context, null, true);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = ev.getX();
                mLastY = ev.getY();
                mIntercept = false;
                super.dispatchTouchEvent(ev);
                return true;
//                break;
            case MotionEvent.ACTION_MOVE:
                mDx = ev.getX() - mLastX;
                mDy = ev.getY() - mLastY;
                if (Math.abs(mDx) - Math.abs(mDy) > mTouchSlop) {
                    mIntercept = true;
                    if (getScrollX() - mDx <= mMenuWidth && getScrollX() - mDx >= 0) {
                        scrollBy((int) -mDx, 0);
                        mLastX = ev.getX();
                        mLastY = ev.getY();
                    }
                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:
                boolean open = false;
                int offset;
                if (getScrollX() > mMenuWidth / 2) {
                    offset = mMenuWidth - getScrollX();
                    open = true;
                } else
                    offset = getScrollX();
                mScroller.startScroll(getScrollX(), 0, open ? offset : -offset, 0);
                invalidate();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mIntercept || super.onInterceptTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mContentView == null) {
            mContentView = getChildAt(0);
            mMenuView = getChildAt(1);
            mContentView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMenuWidth = mMenuView.getMeasuredWidth();
        LogUtil.d("mMenuWidth = " + mMenuWidth);
    }
}
