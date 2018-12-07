package com.demo.ui.uiapplication.paintcanvas;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/9/4.
 * Title：
 * Note：
 */
public class PathWave2View extends View {

    private Paint mPaint;
    private Path mPath;
    private int mWaveLength;
    private int mTop;
    private int mCount = 4;
    private int mWaveHeight = 200;
    private Matrix mMatrix;
    private Bitmap mBitmap;
    private PathMeasure mPathMeasure;
    private float mPercent;

    public PathWave2View(Context context) {
        this(context, null);
    }

    public PathWave2View(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathWave2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();

        mMatrix = new Matrix();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.timg, options);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation();
            }
        }, 1000);
    }

    private void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(5000);
        animator.setInterpolator(new LinearInterpolator());
        //无限循环
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPercent = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mWaveLength == 0) {
            mWaveLength = getMeasuredWidth() / mCount;
            mTop = getMeasuredHeight() / 3 * 2;
            mPath.reset();

            mPath.moveTo(0, mTop);
            int width = getMeasuredWidth();
            for (int i = 0; i < width; i += mWaveLength) {
                mPath.rQuadTo(mWaveLength / 2, -mWaveHeight, mWaveLength, 0);
                mPath.rQuadTo(mWaveLength / 2, mWaveHeight, mWaveLength, 0);
            }
            mPathMeasure = new PathMeasure(mPath, false);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath, mPaint);
        mMatrix.reset();
        float length = mPathMeasure.getLength();
        mPathMeasure.getMatrix(length * mPercent, mMatrix, PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);
        mMatrix.preTranslate(-mBitmap.getWidth(), -mBitmap.getHeight());
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
    }
}
