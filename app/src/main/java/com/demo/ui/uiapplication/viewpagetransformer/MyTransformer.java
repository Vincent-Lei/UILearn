package com.demo.ui.uiapplication.viewpagetransformer;

import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.demo.ui.uiapplication.LogUtil;
import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/7/13.
 * Title：
 * Note：
 */
public class MyTransformer implements ViewPager.PageTransformer, ViewPager.OnPageChangeListener {
    @Override
    public void transformPage(@NonNull View page, float position) {
        /**
         * position范围 -1 - 1
         *
         */
        final HorizontalScrollView horizontalScrollView = page.findViewById(R.id.mscv);
        final ImageView imageView1 = page.findViewById(R.id.imageView0_1);
        final ImageView imageView2 = page.findViewById(R.id.imageView0_2);
        page.setPivotX(page.getMeasuredWidth() / 2);
        page.setPivotY(page.getMeasuredHeight());
        page.setRotation(position * 30f);
        ValueAnimator valueAnimator;
        if (position == 0.0f && page.getTag() == null) {
            valueAnimator = ValueAnimator.ofFloat(0, 1f);
            valueAnimator.setDuration(300);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float f = (float) animation.getAnimatedValue();
                    imageView1.setTranslationX(-imageView1.getWidth() * f);
                    imageView2.setTranslationX(imageView1.getWidth() * (1 - f));
                    horizontalScrollView.scrollTo((int) (horizontalScrollView.getWidth() * f), 0);
                }
            });
            valueAnimator.start();
            page.setTag(valueAnimator);

        } else if ((position == 1.0f) || (position == -1.0f)) {
            valueAnimator = (ValueAnimator) page.getTag();
            if (valueAnimator != null && valueAnimator.isRunning())
                valueAnimator.cancel();
            horizontalScrollView.scrollTo(0, 0);
            imageView1.setTranslationX(0);
            imageView2.setTranslationX(0);
            page.setTag(null);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
