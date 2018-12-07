package com.demo.ui.uiapplication.recyclerview.extra;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.demo.ui.uiapplication.LogUtil;
import com.demo.ui.uiapplication.R;
import com.demo.ui.uiapplication.recyclerview.LinearItemDecoration;

/**
 * Created by Vincent.Lei on 2018/6/26.
 * Title：
 * Note：
 */
public class ExtraRecyclerViewActivity extends Activity {
    RecyclerView recyclerView;
    private ItemAdapter recyclerAdapter;
    private ItemTouchHelper itemTouchHelper;
    private int mItemDeleteWidth;
    private float mLastDx;
    RecyclerView.ViewHolder mLastViewHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_activity_extra_recycler);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Drawable drawable = getResources().getDrawable(R.drawable.recycler_view_linear_decoration);
        recyclerView.addItemDecoration(new LinearItemDecoration(LinearLayoutManager.VERTICAL, drawable));
        recyclerAdapter = new ItemAdapter(this);
        recyclerView.setAdapter(recyclerAdapter);
        mItemDeleteWidth = (int) (getResources().getDisplayMetrics().density * 100);
        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags((ItemTouchHelper.DOWN | ItemTouchHelper.UP), ItemTouchHelper.LEFT);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                recyclerAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                recyclerAdapter.onItemDissmiss(viewHolder.getAdapterPosition());
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
//                viewHolder.itemView.setBackgroundColor(getResources().getColor(android.R.color.white));
//                viewHolder.itemView.setAlpha(1.0f);
//                viewHolder.itemView.setScaleX(1.0f);
//                viewHolder.itemView.setScaleY(1.0f);
                if (viewHolder == mLastViewHolder) {
                    if (Math.abs(mLastDx) > mItemDeleteWidth / 2)
                        viewHolder.itemView.scrollTo(-mItemDeleteWidth, 0);
                    else
                        viewHolder.itemView.scrollTo(0, 0);
                }
            }

//            @Override
//            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//                    float percent = 1 - (Math.abs(dX) / viewHolder.itemView.getWidth());
//                    viewHolder.itemView.setAlpha(percent);
//                    viewHolder.itemView.setScaleX(percent);
//                    viewHolder.itemView.setScaleY(percent);
//                }
//            }


            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    if (viewHolder != mLastViewHolder)
                        mLastDx = 0;
                    LogUtil.d("dX = " + dX);
                    LogUtil.d("isCurrentlyActive = " + isCurrentlyActive);
                    if (dX > mLastDx)
                        viewHolder.itemView.scrollTo(-(int) (dX - mLastDx), 0);
                    else if (Math.abs(dX) <= mItemDeleteWidth)
                        viewHolder.itemView.scrollTo(-(int) dX, 0);
                    mLastDx = dX;
                    mLastViewHolder = viewHolder;
//                    else
//                        viewHolder.itemView.scrollTo(-(int) dX, 0);

                } else
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
