package com.demo.ui.uiapplication.recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Vincent.Lei on 2018/6/15.
 * Title：
 * Note：
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {
    private int mOrientation;
    private Drawable mDrawable;

    public LinearItemDecoration(int orientation, Drawable drawable) {
        this.mOrientation = orientation;
        this.mDrawable = drawable;
        if (mOrientation != LinearLayoutManager.HORIZONTAL && mOrientation != LinearLayoutManager.VERTICAL)
            throw new IllegalArgumentException("orientation must be LinearLayoutManager.HORIZONTAL or  LinearLayoutManager.VERTICAL");
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == LinearLayoutManager.HORIZONTAL)
            outRect.set(0, 0, mDrawable.getIntrinsicWidth(), 0);
        else
            outRect.set(0, 0, 0, mDrawable.getIntrinsicHeight());
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.HORIZONTAL)
            drawHorizontal(c, parent);
        else
            drawVertical(c, parent);
//        super.onDraw(c, parent, state);
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        View child;
        RecyclerView.LayoutParams params;
        int left, right;
        for (int i = 0; i < childCount; i++) {
            child = parent.getChildAt(i);
            params = (RecyclerView.LayoutParams) child.getLayoutParams();
            left = child.getRight() + params.rightMargin;
            right = left + mDrawable.getIntrinsicWidth();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        View child;
        RecyclerView.LayoutParams params;
        int top, bottom;
        for (int i = 0; i < childCount; i++) {
            child = parent.getChildAt(i);
            params = (RecyclerView.LayoutParams) child.getLayoutParams();
            top = child.getBottom() + params.bottomMargin;
            bottom = top + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }
}
