/*
 *  The MIT License (MIT)
 *
 *  Copyright (c) 2015 cpoopc
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package com.demo.ui.uiapplication.xlistviewnestscroll.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;


/**
 * Created by yangdongxin691.
 */
public class ScrollableLayout extends LinearLayout {

    private static final String TAG = "ScrollableLayout";
    private float mDownX;
    private float mDownY;
    private float mLastY;

    private int minY = 0;
    private int maxY = 0;
    private int marginMaxTop = 0;
    private int mHeadHeight;
    private int mExpandHeight;
    private int mTouchSlop;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    // 方向
    private DIRECTION mDirection;
    private int mCurY;
    private int mLastScrollerY;
    private boolean needCheckUpdown;
    private boolean updown;
    private boolean mDisallowIntercept;
    private boolean isClickHead;
    private boolean isClickHeadExpand;

    private View mHeadView;
    private ViewPager childViewPager;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    private boolean mHasCancelTouch = false;//是否停止滑动

    private int mMoveY;

    private int mMinYOnLoading;

    private boolean mCanLoading = false;

    private boolean mIsLoading = false;

    private boolean mIsTouching = false;

    public static final int UNSCROLL = 0; // 没滚动

    public static final int SCROLLING = 1; // 滚动中

    private int mCurScrollState = UNSCROLL;

    private boolean mCanScroll = true; // 能否滚动

    /**
     * 滑动方向 *
     */
    enum DIRECTION {
        UP,// 向上划
        DOWN// 向下划
    }

    public interface OnScrollListener {

        void onScroll(int currentY, int maxY, int moveY);

        void onLoading();

        void onScrollableStateChange(int state);
    }

    /**
     * '触摸状态改变'callback
     */
    public interface OnTouchStateChangeListener {
        boolean onTouchStateChange(boolean isTouchFinished);
    }

    public interface OnLayoutTouchListener {
        void layoutDispatchTouchEvent(MotionEvent ev);
    }


    private OnScrollListener onScrollListener;

    private OnLayoutTouchListener onTouchListener;

    private OnTouchStateChangeListener mTouchChangeListener;

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public void setOnLayoutTouchListener(OnLayoutTouchListener touchListener) {
        this.onTouchListener = touchListener;
    }

    public void setOnTouchStateChangeListener(OnTouchStateChangeListener listener) {
        this.mTouchChangeListener = listener;
    }

    private ScrollableHelper mHelper;

    public ScrollableHelper getHelper() {
        return mHelper;
    }

    public ScrollableLayout(Context context) {
        super(context);
        init(context);
    }

    public ScrollableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ScrollableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScrollableLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mHelper = new ScrollableHelper();
        mScroller = new Scroller(context);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mMinYOnLoading = -1 * ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , 56, context.getResources().getDisplayMetrics()));
    }

    /**
     * 增加滑动最小停留位置
     */
    public void addMinYLoading(int addValue) {
        mMinYOnLoading += addValue;
    }

    public void setLoadingEnabled(boolean enabled) {
        this.mCanLoading = enabled;
    }

    public void stopLoading() {
        this.mIsLoading = false;
        minY = 0;
        if (!mIsTouching) {
            checkScroll(500);
        }
    }

    public void setMarginMaxTop(int marginTop) {
        this.marginMaxTop = marginTop;
        if (mHeadView != null) {
            maxY = mHeadView.getMeasuredHeight() - marginMaxTop;
        }
    }


    public void scrollForceFinished() {
        if (mScroller != null) {
            setScrollState(UNSCROLL);
            mScroller.forceFinished(true);
        }
    }

    public void setLoading(boolean isLoading) {
        this.mIsLoading = isLoading;
        if (mIsLoading) {
            minY = mMinYOnLoading;
        } else {
            minY = 0;
        }
    }

    public boolean isLoading() {
        return mIsLoading;
    }

    public boolean isSticked() {
        return mCurY == maxY;
    }

    /**
     * 扩大头部点击滑动范围
     *
     * @param expandHeight
     */
    public void setClickHeadExpand(int expandHeight) {
        mExpandHeight = expandHeight;
    }

    public int getMaxY() {
        return maxY;
    }

    public boolean isHeadTop() {
        return mCurY == minY;
    }

    public int getCurY() {
        return mCurY;
    }


    public boolean canPtr() {
        return updown && mCurY == minY && mHelper.isTop();
    }

    public void requestScrollableLayoutDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
        mDisallowIntercept = disallowIntercept;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //LogUtil.i(TAG, "dispatchTouchEvent:" + ev.getAction());
        mHasCancelTouch = false;
        if (onTouchListener != null) {
            onTouchListener.layoutDispatchTouchEvent(ev);
        }
        if (!mCanScroll) {
            super.dispatchTouchEvent(ev);
            return true;
        }
        float currentX = ev.getX();
        float currentY = ev.getY();
        float deltaY;
        int shiftX = (int) Math.abs(currentX - mDownX);
        int shiftY = (int) Math.abs(currentY - mDownY);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsTouching = true;
                mDisallowIntercept = false;
                needCheckUpdown = true;
                updown = true;
                mDownX = currentX;
                mDownY = currentY;
                mLastY = currentY;
                checkIsClickHead((int) currentY, mHeadHeight, getScrollY());
                checkIsClickHeadExpand((int) currentY, mHeadHeight, getScrollY());
                initOrResetVelocityTracker();
                mVelocityTracker.addMovement(ev);
                mScroller.forceFinished(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mDisallowIntercept) {
                    break;
                }
                initVelocityTrackerIfNotExists();
                mVelocityTracker.addMovement(ev);
                deltaY = mLastY - currentY;
                if (needCheckUpdown) {
                    if (shiftX > mTouchSlop && shiftX > shiftY) {
                        needCheckUpdown = false;
                        updown = false;
                    } else if (shiftY > mTouchSlop && shiftY > shiftX) {
                        needCheckUpdown = false;
                        updown = true;
                    }
                }

                boolean isTop = mHelper.isTop();
                if (mCanLoading) {
                    if (isTop && minY != -1000) {
                        minY = -1000;
                    } else if (!isTop && minY != 0) {
                        if (mIsLoading) {
                            minY = mMinYOnLoading;
                        } else {
                            minY = 0;
                        }
                    }
                }
                if (updown && shiftY > mTouchSlop && shiftY > shiftX &&
                        (!isSticked() || isTop || isClickHeadExpand)) {

                    if (childViewPager != null) {
                        childViewPager.requestDisallowInterceptTouchEvent(true);
                    }
                    if (getScrollY() < 0) {
                        // 下拉阻力
                        scrollBy(0, (int) (deltaY / 1.8f + 0.5));
                    } else {
                        scrollBy(0, (int) (deltaY + 0.5));
                    }
                }
                mLastY = currentY;
                break;
            case MotionEvent.ACTION_UP:
                boolean needStopScroll = false;
                if (this.mTouchChangeListener != null) {
                    needStopScroll = this.mTouchChangeListener.onTouchStateChange(true);
                }
                if (!needStopScroll) {
                    if (updown && shiftY > shiftX && shiftY > mTouchSlop) {
                        mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                        float yVelocity = -mVelocityTracker.getYVelocity();
                        boolean dislowChild = false;
                        if (Math.abs(yVelocity) > mMinimumVelocity) {
                            mDirection = yVelocity > 0 ? DIRECTION.UP : DIRECTION.DOWN;
                            if ((mDirection == DIRECTION.UP && isSticked()) || (!isSticked() && getScrollY() == 0 && mDirection == DIRECTION.DOWN)) {
                                dislowChild = true;
                            } else {
                                mScroller.fling(0, getScrollY(), 0, (int) yVelocity, 0, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
                                mScroller.computeScrollOffset();
                                mLastScrollerY = getScrollY();
                                invalidate();
                            }
                        }
                        if (!dislowChild && (isClickHead || !isSticked())) {
                            int action = ev.getAction();
                            ev.setAction(MotionEvent.ACTION_CANCEL);
                            boolean dispatchResult = super.dispatchTouchEvent(ev);
                            ev.setAction(action);

                            checkScroll(500);
                            setScrollState(UNSCROLL);

                            return dispatchResult;
                        }
                    }

                    checkScroll(200);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (this.mTouchChangeListener != null) {
                    this.mTouchChangeListener.onTouchStateChange(true);
                }
                break;
            default:
                mIsTouching = false;
                break;
        }
        if (ev.getAction() == MotionEvent.ACTION_CANCEL) {
            setScrollState(UNSCROLL);
        }
        super.dispatchTouchEvent(ev);
        return true;
    }

    private void checkScroll(int duration) {
        mIsTouching = false;
        if (mCanLoading) {
            if (mCurY < mMinYOnLoading) {
                mIsLoading = true;
                if (onScrollListener != null) {
                    onScrollListener.onLoading();
                }
            }
            if (mIsLoading) {
                minY = mMinYOnLoading;
            } else {
                minY = 0;
            }
            int currY = mScroller.getCurrY();
            //LogUtil.i(TAG, "checkScroll ========startScroll currY:" + currY + " ,minY:" + minY + " ,duration:" + duration);
            if (mCurY < minY) {
                //LogUtil.i(TAG, "ScrollTag startScroll");
                mHasCancelTouch = true;
                mScroller.startScroll(0, currY, 0, minY - currY, duration);
                invalidate();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private int getScrollerVelocity(int distance, int duration) {
        if (mScroller == null) {
            return 0;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return (int) mScroller.getCurrVelocity();
        } else {
            return distance / duration;
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            final int currY = mScroller.getCurrY();
            int scrollY = getScrollY();
            //LogUtil.i(TAG, "computeScroll mDirection: " + mDirection + " ,mCurY: " + mCurY + " ,currY: " + currY + " ,minY: " + minY + " ,scrollY: " + scrollY);

            if (mHasCancelTouch) {
                mMoveY = currY - mCurY;
                if (mCurY == 0) {
                    return;
                }
                mCurY = currY;
                if (onScrollListener != null) {
                    onScrollListener.onScroll(currY, maxY, mMoveY);
                }
                if (currY == 0) {
                    super.scrollTo(0, currY);
                    invalidate();
                    scrollForceFinished();
                    return;
                }
                if (currY < 0) {
                    setScrollState(SCROLLING);
                    super.scrollTo(0, currY);
                    invalidate();
                    return;
                }
            }

            if (mDirection == DIRECTION.UP) {
                // 手势向上划
                if (isSticked()) {
                    int distance = mScroller.getFinalY() - currY;
                    int duration = calcDuration(mScroller.getDuration(), mScroller.timePassed());
                    //LogUtil.i(TAG, "ScrollTag smoothScrollBy");
                    mHelper.smoothScrollBy(getScrollerVelocity(distance, duration), distance, duration);
                    scrollForceFinished();
                    return;
                } else {
                    scrollTo(0, currY);
                }
            } else {
                // 手势向下划
                if (mHelper.isTop() || isClickHeadExpand) {
                    int deltaY = (currY - mLastScrollerY);
                    int toY = scrollY + deltaY;
                    //LogUtil.i(TAG, "computeScroll >>>>>>>>1 mCurY: " + mCurY + " ,minY: " + minY + " ,deltaY: " + deltaY);

                    scrollTo(0, toY);
                    if (mCurY <= minY) {
                        scrollForceFinished();
                        return;
                    }
                }
                //LogUtil.i(TAG, "computeScroll >>>>>>>>2 currY :" + currY);
                invalidate();
            }
            mLastScrollerY = currY;
        } else {
            setScrollState(UNSCROLL);
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        //LogUtil.i(TAG, "scrollTo y: " + y + " ,maxY: " + maxY + " ,minY: " + minY);
        if (y >= maxY) {
            y = maxY;
        } else if (y <= minY) {
            y = minY;
        }
        mMoveY = y - mCurY;
        mCurY = y;
        if (onScrollListener != null) {
            onScrollListener.onScroll(y, maxY, mMoveY);
        }
        if (getScrollX() == x && getScrollY() == y) {
            setScrollState(UNSCROLL);
        } else {
            setScrollState(SCROLLING);
        }
        super.scrollTo(x, y);
    }

    @Override
    public void scrollBy(int x, int y) {
        //LogUtil.i(TAG, "ScrollTag scrollBy");
        int scrollY = getScrollY();
        int toY = scrollY + y;
        if (toY >= maxY) {
            toY = maxY;
        } else if (toY <= minY) {
            toY = minY;
        }
        y = toY - scrollY;
        super.scrollBy(x, y);
    }

    private void setScrollState(int state) {
        if (mIsTouching && state == UNSCROLL) {
            // 触摸中
            return;
        }
        if (mCurScrollState == state) {
            return;
        }
        mCurScrollState = state;
        if (onScrollListener != null) {
            onScrollListener.onScrollableStateChange(mCurScrollState);
        }
    }

    private void initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private void checkIsClickHead(int downY, int headHeight, int scrollY) {
        isClickHead = downY + scrollY <= headHeight;
    }

    private void checkIsClickHeadExpand(int downY, int headHeight, int scrollY) {
        if (mExpandHeight <= 0) {
            isClickHeadExpand = false;
        }
        isClickHeadExpand = downY + scrollY <= headHeight + mExpandHeight;
    }

    private int calcDuration(int duration, int timepass) {
        return duration - timepass;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mHeadView = getChildAt(0);
        measureChildWithMargins(mHeadView, widthMeasureSpec, 0, MeasureSpec.UNSPECIFIED, 0);
        maxY = mHeadView.getMeasuredHeight() - marginMaxTop;
        mHeadHeight = mHeadView.getMeasuredHeight();
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec) + maxY, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onFinishInflate() {
        if (mHeadView != null && !mHeadView.isClickable()) {
            mHeadView.setClickable(true);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt != null && childAt instanceof ViewPager) {
                childViewPager = (ViewPager) childAt;
            }
        }
        super.onFinishInflate();
    }

    public void setCanScroll(boolean canScroll) {
        this.mCanScroll = canScroll;
    }

}
