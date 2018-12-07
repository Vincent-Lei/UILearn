package com.demo.ui.uiapplication.palette;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.ui.uiapplication.LogUtil;
import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/7/9.
 * Title：
 * Note：
 */
public class PaletteTest extends Activity {

    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.palette_test);
        textView = findViewById(R.id.tv);
        imageView = findViewById(R.id.iv);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.palette);
        imageView.setImageBitmap(bitmap);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@NonNull Palette palette) {
                //有活力的
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                //有活力的，暗色
                Palette.Swatch vibrantDark = palette.getDarkVibrantSwatch();
                //有活力的，亮色
                Palette.Swatch vibrantLight = palette.getLightVibrantSwatch();
                //柔和的
                Palette.Swatch muted = palette.getMutedSwatch();
                //柔和的，暗色
                Palette.Swatch mutedDark = palette.getDarkMutedSwatch();
                //柔和的,亮色
                Palette.Swatch mutedLight = palette.getLightMutedSwatch();

                setTextView(muted);

            }
        });
    }

    private void setTextView(Palette.Swatch swatch) {
//        swatch.getPopulation(): 样本中的像素数量
//        swatch.getRgb(): 颜色的RBG值
//        swatch.getHsl(): 颜色的HSL值
//        swatch.getBodyTextColor(): 主体文字的颜色值
//        swatch.getTitleTextColor(): 标题文字的颜色值
        if (swatch == null) {
            LogUtil.d("get  Swatch  failed");
            return;
        }

        textView.setBackgroundColor(getTransluanceColor(swatch.getRgb(), 0.5f));
        textView.setTextColor(swatch.getBodyTextColor());
    }


    private int getTransluanceColor(int color, float percent) {
        int blue = color & 0xff;
        int green = color >> 8 & 0xff;
        int red = color >> 16 & 0xff;
        int alpha = color >>> 24;
//        Color.blue(color);
//        Color.green(color);
//        Color.red(color);
//        Color.alpha(color);
        return Color.argb((int) (alpha * percent), red, green, blue);
    }
}
