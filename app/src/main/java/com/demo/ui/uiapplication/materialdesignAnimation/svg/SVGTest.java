package com.demo.ui.uiapplication.materialdesignAnimation.svg;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/7/23.
 * Title：
 * Note：
 */
public class SVGTest extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.md_animation_svg);
        imageView = findViewById(R.id.iv);
        findViewById(R.id.tv_svg_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Drawable drawable = imageView.getDrawable();
                    if (drawable != null && (drawable instanceof AnimatedVectorDrawable))
                        ((AnimatedVectorDrawable) drawable).start();
                }


            }
        });
    }
}
