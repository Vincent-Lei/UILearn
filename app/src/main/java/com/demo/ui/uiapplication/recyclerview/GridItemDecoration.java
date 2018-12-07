package com.demo.ui.uiapplication.recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by Vincent.Lei on 2018/6/15.
 * Title：
 * Note：
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDrawable;
    private int mSpanCount;

    public GridItemDecoration(Drawable drawable, int spanCount) {
        this.mDrawable = drawable;
        this.mSpanCount = spanCount;
        if (mSpanCount == 0)
            throw new IllegalArgumentException("spanCount can not ==0");
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int left;
        int top = 0;
        int right;
        int bottom;
        int eachWidth = (mSpanCount - 1) * mDrawable.getIntrinsicWidth() / mSpanCount;
        int dl = mDrawable.getIntrinsicWidth() - eachWidth;

        left = position % mSpanCount * dl;
        right = eachWidth - left;
        bottom = mDrawable.getIntrinsicHeight();
//        int allLineCount = (parent.getChildCount() / mSpanCount + (parent.getChildCount() % mSpanCount == 0 ? 0 : 1));
//        int currentLine = (position + 1) / mSpanCount + ((position + 1) % mSpanCount == 0 ? 0 : 1);
//        if ((currentLine == allLineCount)) {
//            bottom = 0;
//        }
        outRect.set(left, top, right, bottom);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        View child;
        RecyclerView.LayoutParams params;
        int left, right, top, bottom;
        boolean endOfLine;
        for (int i = 0; i < childCount; i++) {
            endOfLine = ((i + 1) % mSpanCount == 0);
            child = parent.getChildAt(i);
            params = (RecyclerView.LayoutParams) child.getLayoutParams();
            if (!endOfLine) {
                top = child.getTop();
                bottom = child.getBottom();
                left = child.getRight() + params.rightMargin;
                right = left + mDrawable.getIntrinsicWidth();
                mDrawable.setBounds(left, top, right, bottom);
                mDrawable.draw(c);
            }
            top = child.getBottom() + params.bottomMargin;
            bottom = top + mDrawable.getIntrinsicHeight();
            left = child.getLeft();
            right = child.getRight() + (endOfLine ? 0 : mDrawable.getIntrinsicWidth());
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }


    }

}
