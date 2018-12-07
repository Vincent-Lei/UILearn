package com.demo.ui.uiapplication.paintcanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
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
public class MaskFilterView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;
    private Bitmap bitmapOld;
    private ColorMatrix matrix = new ColorMatrix();
    private ColorMatrixColorFilter colorMatrixColorFilter;

    public MaskFilterView(Context context) {
        this(context, null);
    }

    public MaskFilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmapOld = BitmapFactory.decodeResource(context.getResources(), R.mipmap.palette);
        /**模糊遮罩滤镜效果
         * BlurMaskFilter.Blur.INNER
         * BlurMaskFilter.Blur.NORMAL
         * BlurMaskFilter.Blur.OUTER
         * BlurMaskFilter.Blur.SOLID
         */
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (bitmap == null) {
            bitmap = Bitmap.createScaledBitmap(bitmapOld, getMeasuredWidth(), getMeasuredHeight(), true);
            bitmapOld.recycle();
            if (getId() == R.id.mfv_bh_0) {
                //饱和度设置 0灰色  1原色  >1饱和度增加
                matrix.setSaturation(0);
            } else if (getId() == R.id.mfv_bh_1) {
                //饱和度设置 0灰色  1原色  >1饱和度增加
                matrix.setSaturation(3f);
            } else if (getId() == R.id.mfv_scsf_r) {
                //色彩缩放 red
                matrix.setScale(1f, 0f, 0f, 1f);
            } else if (getId() == R.id.mfv_scsf_g) {
                //色彩缩放 green
                matrix.setScale(0f, 1f, 0f, 1f);
            } else if (getId() == R.id.mfv_scsf_b) {
                //色彩缩放 blue
                matrix.setScale(0f, 0f, 1f, 1f);
            } else if (getId() == R.id.mfv_xz_45) {
                //axis代表旋转轴0、1、2  R G B
                matrix.setRotate(1, 45);
            } else if (getId() == R.id.mfv_xz_90) {
                //axis代表旋转轴0、1、2  R G B
                matrix.setRotate(2, 90);
            } else if (getId() == R.id.mfv_xz_135) {
                //axis代表旋转轴0、1、2  R G B
                matrix.setRotate(1, 135);
            }
            colorMatrixColorFilter = new ColorMatrixColorFilter(matrix);
            paint.setColorFilter(colorMatrixColorFilter);

            if (getId() == R.id.mfv_pd) {
                bitmapOld = BitmapFactory.decodeResource(getResources(), R.mipmap.phone1);
                bitmap = Bitmap.createScaledBitmap(bitmapOld, getMeasuredWidth(), getMeasuredHeight(), true);
                bitmapOld.recycle();
                paint.setColorFilter(new PorterDuffColorFilter(Color.rgb(122, 233, 211), PorterDuff.Mode.SRC_IN));
            }

            /**
             * mul,multiply相乘 ---缩放
             * add，相加---平移
             */
//		paint.setColorFilter(new LightingColorFilter(0x00ff00, 0xff0000));
//		paint.setColorFilter(new LightingColorFilter(0xffffff, 0x000000));

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

}
