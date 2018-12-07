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
public class TranslationBehavior2 extends CoordinatorLayout.Behavior<View> {
    public TranslationBehavior2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return true;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        LogUtil.d("onDependentViewChanged：" + dependency.getTop());

//            child.setTranslationY(dependency.getTranslationY());
        return true;
    }
}
