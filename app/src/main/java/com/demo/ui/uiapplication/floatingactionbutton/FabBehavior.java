package com.demo.ui.uiapplication.floatingactionbutton;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Vincent.Lei on 2018/7/11.
 * Title：
 * Note：
 */
public class FabBehavior extends FloatingActionButton.Behavior {
    private boolean mShown;

    public FabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {

        return (axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type));
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
//        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        int lastVisibleItemPosition = 0;
        if (target instanceof RecyclerView) {
            RecyclerView.LayoutManager layoutManager = ((RecyclerView) target).getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager)
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }

        if (lastVisibleItemPosition >= 15 && !mShown) {
            mShown = true;
            ViewCompat.animate(child).translationX(0).setDuration(100);
        } else if (lastVisibleItemPosition < 15 && mShown) {
            mShown = false;
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
            ViewCompat.animate(child).translationX(child.getWidth() + lp.rightMargin).setDuration(100);
        }


//        if (dyConsumed > 0 && !mShown) {
//            mShown = true;
//            ViewCompat.animate(child).translationX(0).setDuration(100);
//        } else if (dyConsumed < 0 && mShown) {
//            mShown = false;
//            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
//            ViewCompat.animate(child).translationX(child.getWidth() + lp.rightMargin).setDuration(100);
//        }

    }
}
