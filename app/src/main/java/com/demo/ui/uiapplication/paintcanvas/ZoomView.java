package com.demo.ui.uiapplication.paintcanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/8/17.
 * Title：
 * Note：
 */
public class ZoomView extends View {
    private Paint paint = new Paint();
    private ShapeDrawable drawable;
    //放大倍数
    private static final int FACTOR = 2;
    //放大镜的半径
    private static final int RADIUS = 100;
    private Matrix matrix = new Matrix();
    private Bitmap bitmap;

    public ZoomView(Context context) {
        this(context, null);
    }

    public ZoomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.palette);
        Bitmap bmp = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * FACTOR, bitmap.getHeight() * FACTOR, true);
        BitmapShader bitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setShader(bitmapShader);
        drawable.setBounds(0, 0, RADIUS * 2, RADIUS * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null);
        drawable.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            matrix.setTranslate(-x * FACTOR + RADIUS, -y * FACTOR + RADIUS);
            drawable.getPaint().getShader().setLocalMatrix(matrix);
            drawable.setBounds(x - RADIUS, y - RADIUS, RADIUS + x, RADIUS + y);
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }
}
