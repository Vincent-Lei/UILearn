package com.demo.ui.uiapplication.nestedscroll.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.demo.ui.uiapplication.LogUtil;
import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/7/17.
 * Title：
 * Note：
 */
public class TranslationBehavior extends CoordinatorLayout.Behavior<View> {
    public TranslationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return (dependency.getId() == R.id.tv_dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        LogUtil.d("onDependentViewChanged");

            child.setTranslationY(dependency.getTranslationY());
        return true;
    }
}
