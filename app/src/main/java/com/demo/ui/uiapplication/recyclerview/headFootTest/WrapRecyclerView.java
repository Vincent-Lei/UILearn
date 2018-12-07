package com.demo.ui.uiapplication.recyclerview.headFootTest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent.Lei on 2018/6/25.
 * Title：
 * Note：
 */
public class WrapRecyclerView extends RecyclerView {

    private List<View> mHeadViews;
    private List<View> mFootViews;
    private Adapter mAdapter;


    public WrapRecyclerView(Context context) {
        this(context, null);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addHeadView(View view) {
        if (mHeadViews == null)
            mHeadViews = new ArrayList<>();
        mHeadViews.add(view);
    }

    public void addFootView(View view) {
        if (mFootViews == null)
            mFootViews = new ArrayList<>();
        mFootViews.add(view);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if ((mHeadViews != null && !mHeadViews.isEmpty()) || (mFootViews != null && !mFootViews.isEmpty())) {
            if (adapter != null && !(adapter instanceof WrapHeadViewAdapter)) {
                mAdapter = new WrapHeadViewAdapter(mHeadViews, mFootViews,adapter);
            }
        }
        super.setAdapter(mAdapter);
    }


}
