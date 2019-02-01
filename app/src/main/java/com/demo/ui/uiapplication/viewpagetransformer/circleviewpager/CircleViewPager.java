package com.demo.ui.uiapplication.viewpagetransformer.circleviewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Vincent.Lei on 2018/12/7.
 * Title：
 * Note：
 */
public class CircleViewPager extends ViewPager {
    public CircleViewPager(@NonNull Context context) {
        this(context, null);
    }

    public CircleViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    private abstract class CirclePagerAdapter extends PagerAdapter implements OnPageChangeListener {

        protected abstract int getRealCount();

        protected abstract Object doInstantiateItem(@NonNull ViewGroup container, int position);

        protected abstract void doDestroyItem(@NonNull ViewGroup container, int position, @NonNull Object object);

        @Override
        public final int getCount() {
            int count = getRealCount();
            return (count == 0 ? 0 : count + 2);
        }


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position == 0 && positionOffset == 0.0) {
                setCurrentItem(getCount() - 2, false);
            } else if (position == getCount() - 1 && positionOffset == 0) {
                setCurrentItem(1, false);
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        //1 2 3
        //3 1 2 3 1
        @NonNull
        @Override
        public final Object instantiateItem(@NonNull ViewGroup container, int position) {
            if (position == 0)
                position = getCount() - 3;
            else if (position == getCount() - 1)
                position = 0;
            else
                position--;
            return doInstantiateItem(container, position);
        }

        @Override
        public final void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            doDestroyItem(container, position, object);
        }
    }

}
