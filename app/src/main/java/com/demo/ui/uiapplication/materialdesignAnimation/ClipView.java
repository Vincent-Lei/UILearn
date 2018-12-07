package com.demo.ui.uiapplication.materialdesignAnimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Vincent.Lei on 2018/7/18.
 * Title：
 * Note：
 */
public class ClipView extends View {
    private Paint paint;
    private PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    private float revelLevel;

    public ClipView(Context context) {
        this(context, null);
    }

    public ClipView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFFFF0000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        setLayerType(LAYER_TYPE_HARDWARE, null);
//        paint.setXfermode(null);
//        if (bitmap_bg == null) {
//            bitmap_bg = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//            bitmap_bg2 = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//            Canvas c2 = new Canvas(bitmap_bg);
//            c2.drawCircle(getMeasuredWidth(), 0, 150, paint);
//            c2 = new Canvas(bitmap_bg2);
//            paint.setColor(Color.WHITE);
//            c2.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), paint);
//        }
//
//        canvas.drawBitmap(bitmap_bg2, 0, 0, paint);
//        paint.setXfermode(porterDuffXfermode);
//        canvas.drawBitmap(bitmap_bg, 0, 0, paint);


        paint.setXfermode(null);
        canvas.drawColor(Color.WHITE);
        paint.setXfermode(porterDuffXfermode);
        canvas.drawCircle(0, 0, (float) (revelLevel * Math.hypot(getMeasuredWidth(), getMeasuredHeight())), paint);
        canvas.restore();
//        super.onDraw(canvas);
    }

    public void setRevelLevel(float revelLevel) {
        this.revelLevel = revelLevel;
        invalidate();
    }
}
