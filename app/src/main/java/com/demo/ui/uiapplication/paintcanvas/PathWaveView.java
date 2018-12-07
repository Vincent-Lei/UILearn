package com.demo.ui.uiapplication.paintcanvas;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Vincent.Lei on 2018/9/4.
 * Title：
 * Note：
 */
public class PathWaveView extends View {

    private Paint mPaint;
    private Path mPath;
    private int mWaveLength;
    private int mTop;
    private int mDx;
    private int mDy;

    public PathWaveView(Context context) {
        this(context, null);
    }

    public PathWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();

        mWaveLength = 200;
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation();
            }
        }, 1000);
    }

    private void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mWaveLength);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        //无限循环
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDx = (int) animation.getAnimatedValue();
//                if (mTop - mDy <= 0)
//                    mDy -= 30;
//                else if (mTop - mDy >= mTop)
                if (mTop > getMeasuredHeight() / 4)
                    mDy += 1;
                postInvalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mTop = getMeasuredHeight() / 3 * 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();

        mPath.moveTo(-mWaveLength + mDx, mTop - mDy);
        int width = getMeasuredWidth() + mWaveLength;
        for (int i = -mWaveLength; i < width; i += mWaveLength) {
            mPath.rQuadTo(mWaveLength / 2, -100, mWaveLength, 0);
            mPath.rQuadTo(mWaveLength / 2, 100, mWaveLength, 0);
        }
        mPath.lineTo(getMeasuredWidth(), getMeasuredHeight());
        mPath.lineTo(-mWaveLength, getMeasuredHeight());
        mPath.close();

        canvas.drawPath(mPath, mPaint);
    }
}
