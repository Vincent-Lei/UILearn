package com.demo.ui.uiapplication.paintcanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Vincent.Lei on 2018/8/9.
 * Title：
 * Note：
 */
public class PaintView extends View {
    private Paint mPaint;
    private Path mPath;
    private String text = "文本测试Text";

    public PaintView(Context context) {
        this(context, null);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);
        mPaint.setStrokeWidth(50);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        setLayerType(LAYER_TYPE_HARDWARE, null);
        super.onDraw(canvas);
        mPath.reset();
        mPaint.setAlpha(255);
        mPath.moveTo(100, 0);
        mPath.lineTo(500, 100);
        mPath.lineTo(100, 200);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        canvas.drawPath(mPath, mPaint);

        mPath.reset();
        mPath.moveTo(100, 250);
        mPath.lineTo(500, 350);
        mPath.lineTo(100, 450);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawPath(mPath, mPaint);

        mPath.reset();
        mPath.moveTo(100, 500);
        mPath.lineTo(500, 600);
        mPath.lineTo(100, 700);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        canvas.drawPath(mPath, mPaint);

        mPaint.setStrokeWidth(2);
        canvas.drawLine(0, 800, 1000, 800, mPaint);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        int top = 800;
        canvas.drawText(text, 0, top, mPaint);

        mPaint.setColor(Color.BLUE);
        float baseY = top - fontMetrics.top;
        canvas.drawText(text, 350, baseY, mPaint);


        mPaint.setColor(Color.RED);
        canvas.drawText(text, 700, top - (fontMetrics.top + fontMetrics.bottom) / 2, mPaint);
    }
}
