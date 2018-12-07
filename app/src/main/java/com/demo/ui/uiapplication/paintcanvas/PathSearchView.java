package com.demo.ui.uiapplication.paintcanvas;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.demo.ui.uiapplication.LogUtil;

/**
 * Created by Vincent.Lei on 2018/9/4.
 * Title：
 * Note：
 */
public class PathSearchView extends View {

    private Paint mPaint;
    private Path path_innerCircle;
    private Path path_innerClip;
    private Path path_outerCircle;
    private Path path_outerClip;
    private PathMeasure mPathMeasure;
    private int mWidth, mHeight;
    private float mInnerRate;
    private float mOuterRate;
    private boolean mReadyToFinish;
    private boolean mReadyToRestart;

    public PathSearchView(Context context) {
        this(context, null);
    }

    public PathSearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathSearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        path_innerCircle = new Path();
        path_outerCircle = new Path();
        path_innerClip = new Path();
        path_outerClip = new Path();
        mPathMeasure = new PathMeasure();

        RectF rect = new RectF(-40, -40, 40, 40);
        path_innerCircle.addArc(rect, 45f, 359f);
        rect = new RectF(-120, -120, 120, 120);
        path_outerCircle.addArc(rect, 45f, -359f);

        float[] pos = new float[2];
        mPathMeasure.setPath(path_outerCircle, false);  // 放大镜把手的位置
        mPathMeasure.getPosTan(0, pos, null);
        path_innerCircle.lineTo(pos[0], pos[1]);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation();
            }
        }, 1000);


    }

    public void tryToRestart() {
        if (mReadyToRestart) {
            mReadyToRestart = false;
            startAnimation();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);
        path_innerClip.reset();

        mPathMeasure.setPath(path_innerCircle, false);
        mPathMeasure.getSegment(mPathMeasure.getLength() * mInnerRate, mPathMeasure.getLength(), path_innerClip, true);
        canvas.drawPath(path_innerClip, mPaint);

        path_outerClip.reset();
        mPathMeasure.setPath(path_outerCircle, false);
        float stop = mPathMeasure.getLength() * mOuterRate;
        float start = (0.5f - Math.abs(mOuterRate - 0.5f)) * 300f;
        mPathMeasure.getSegment(stop - start, stop, path_outerClip, true);
        canvas.drawPath(path_outerClip, mPaint);

    }


    private void startAnimation() {
        mReadyToFinish = false;
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mReadyToFinish = true;
            }
        }, 11000);


        ValueAnimator valueAnimator_inner = ValueAnimator.ofFloat(0f, 1.0f);
        valueAnimator_inner.setDuration(1000);
        valueAnimator_inner.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mInnerRate = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator_inner.start();


        final ValueAnimator valueAnimator_outer = ValueAnimator.ofFloat(0f, 1.0f);
        valueAnimator_outer.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator_outer.setDuration(2000);
        valueAnimator_outer.setStartDelay(1000);
        valueAnimator_outer.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOuterRate = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator_outer.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                LogUtil.d("onAnimationEnd");
                endAnimation();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                LogUtil.d("onAnimationRepeat");
                if (mReadyToFinish)
                    valueAnimator_outer.cancel();

            }
        });
        valueAnimator_outer.start();

    }

    private void endAnimation() {
        ValueAnimator valueAnimator_inner = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimator_inner.setDuration(1000);
        valueAnimator_inner.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mInnerRate = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator_inner.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mReadyToRestart = true;
            }
        });
        valueAnimator_inner.start();
    }
}
