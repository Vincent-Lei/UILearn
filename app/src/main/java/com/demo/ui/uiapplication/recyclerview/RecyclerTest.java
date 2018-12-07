package com.demo.ui.uiapplication.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.demo.ui.uiapplication.R;
import com.demo.ui.uiapplication.recyclerview.headFootTest.HeadFootRecycleActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Vincent.Lei on 2018/6/15.
 * Title：
 * Note：
 */
public class RecyclerTest extends Activity implements View.OnClickListener {
    private static final int TYPE_LINEAR_VERTICAL = 0;
    private static final int TYPE_LINEAR_HORIZONTAL = 1;
    private static final int TYPE_GRID = 2;
    private static final int TYPE_STAGIERED = 3;
    private RecyclerView recyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private int mType;
    private RecyclerView.ItemDecoration mItemDecoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_activity_test);
        recyclerView = findViewById(R.id.recycler_view);
        linearLayoutManagerVertical();
    }

    private void linearLayoutManagerHorizontal() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        Drawable drawable = getResources().getDrawable(R.drawable.recycler_view_linear_decoration);
        if (mItemDecoration != null)
            recyclerView.removeItemDecoration(mItemDecoration);
        mItemDecoration = new LinearItemDecoration(LinearLayoutManager.HORIZONTAL, drawable);
        recyclerView.addItemDecoration(mItemDecoration);
        mRecyclerAdapter = new RecyclerAdapter(this, true, false);
        recyclerView.setAdapter(mRecyclerAdapter);
    }

    private void linearLayoutManagerVertical() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Drawable drawable = getResources().getDrawable(R.drawable.recycler_view_linear_decoration);
        if (mItemDecoration != null)
            recyclerView.removeItemDecoration(mItemDecoration);
        mItemDecoration = new LinearItemDecoration(LinearLayoutManager.VERTICAL, drawable);
        recyclerView.addItemDecoration(mItemDecoration);
        mRecyclerAdapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(mRecyclerAdapter);
    }

    private void gridLayoutManager() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4) {

            /**
             * 解决增加删除Item导致ItemDecoration错误问题
             * https://www.jianshu.com/p/49c4c7d5ede3
             */

            private Method markItemDecorInsetsDirty = null;
            private boolean reflectError = false;

            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                if (markItemDecorInsetsDirty == null && !reflectError) {
                    try {
                        markItemDecorInsetsDirty = RecyclerView.class.getDeclaredMethod("markItemDecorInsetsDirty");
                        markItemDecorInsetsDirty.setAccessible(true);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                        reflectError = true;
                    }
                }
                // 更新条件
                if (markItemDecorInsetsDirty != null && state.willRunSimpleAnimations()) {
                    // noinspection TryWithIdenticalCatches
                    try {
                        markItemDecorInsetsDirty.invoke(recyclerView);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                super.onLayoutChildren(recycler, state);
            }

            @Override
            public void requestSimpleAnimationsInNextLayout() {
                super.requestSimpleAnimationsInNextLayout();
                if (markItemDecorInsetsDirty != null) {
                    // noinspection TryWithIdenticalCatches
                    try {
                        markItemDecorInsetsDirty.invoke(recyclerView);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        if (mItemDecoration != null)
            recyclerView.removeItemDecoration(mItemDecoration);
        Drawable drawable = getResources().getDrawable(R.drawable.recycler_view_linear_decoration);
        mItemDecoration = new GridItemDecoration(drawable, 4);
        recyclerView.addItemDecoration(mItemDecoration);
        mRecyclerAdapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(mRecyclerAdapter);
    }

    private void staggeredGridLayoutManager() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        if (mItemDecoration != null)
            recyclerView.removeItemDecoration(mItemDecoration);
        mRecyclerAdapter = new RecyclerAdapter(this, true);
        recyclerView.setAdapter(mRecyclerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change:
                if (mType == TYPE_LINEAR_VERTICAL) {
                    mType = TYPE_LINEAR_HORIZONTAL;
                    linearLayoutManagerHorizontal();
                    return;
                }

                if (mType == TYPE_LINEAR_HORIZONTAL) {
                    mType = TYPE_GRID;
                    gridLayoutManager();
                    return;
                }
                if (mType == TYPE_GRID) {
                    mType = TYPE_STAGIERED;
                    staggeredGridLayoutManager();
                    return;
                }
                mType = TYPE_LINEAR_VERTICAL;
                linearLayoutManagerVertical();
                break;
            case R.id.btn_add:
                mRecyclerAdapter.add(3, "ADD");
                break;
            case R.id.btn_remove:
                mRecyclerAdapter.remove(3);
                break;
            case R.id.btn_refresh_target:
//                mRecyclerAdapter.refreshTarget(3, "REFRESH");
                Intent intent = new Intent(this, HeadFootRecycleActivity.class);
                startActivity(intent);
                break;
        }
    }
}
