package com.demo.ui.uiapplication.recyclerview.headFootTest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.demo.ui.uiapplication.LogUtil;

import java.util.List;

/**
 * Created by Vincent.Lei on 2018/6/25.
 * Title：
 * Note：
 */
public class WrapHeadViewAdapter extends RecyclerView.Adapter {

    private static final int TYPE_HEAD_VIEW_TOP = -1000;
    private static final int TYPE_FOOT_VIEW_TOP = -3000;

    private static int TYPE_HEAD_VIEW = TYPE_HEAD_VIEW_TOP;
    private static int TYPE_FOOT_VIEW = TYPE_FOOT_VIEW_TOP;
    private List<View> mHeadViews;
    private List<View> mFootViews;
    private RecyclerView.Adapter mAdapter;
    private SparseArray<View> mSparseArray = new SparseArray<>();

    public WrapHeadViewAdapter(List<View> headViews, List<View> footViews, RecyclerView.Adapter adapter) {
        this.mHeadViews = headViews;
        this.mFootViews = footViews;
        this.mAdapter = adapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType < TYPE_FOOT_VIEW_TOP) {
            LogUtil.d("foot viewType = " + viewType);
            return new HFHolder(mSparseArray.get(viewType));
        }
        if (viewType < TYPE_HEAD_VIEW_TOP) {
            LogUtil.d("head viewType = " + viewType);
            return new HFHolder(mSparseArray.get(viewType));
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int headViewCount = getHeadViewCount();
        if (headViewCount > 0 && position < headViewCount)
            return;
        position -= headViewCount;
        if (mAdapter != null && position < mAdapter.getItemCount()) {
            mAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int headViewCount = getHeadViewCount();
        if (headViewCount > 0 && position < headViewCount) {
            int index = mSparseArray.indexOfValue(mHeadViews.get(position));
            if (index >= 0)
                return mSparseArray.keyAt(index);
            TYPE_HEAD_VIEW--;
            mSparseArray.put(TYPE_HEAD_VIEW, mHeadViews.get(position));
            return TYPE_HEAD_VIEW;
        }
        position -= headViewCount;
        if (mAdapter != null) {
            if (position < mAdapter.getItemCount()){
                return mAdapter.getItemViewType(position);
            }
            position -= mAdapter.getItemCount();
        }
        int footViewCount = getFootViewCount();
        if (footViewCount > 0 && position < footViewCount) {
            int index = mSparseArray.indexOfValue(mFootViews.get(position));
            if (index >= 0)
                return mSparseArray.keyAt(index);
            TYPE_FOOT_VIEW--;
            mSparseArray.put(TYPE_FOOT_VIEW, mFootViews.get(position));
            return TYPE_FOOT_VIEW;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return (getHeadViewCount() + getFootViewCount() + (mAdapter != null ? mAdapter.getItemCount() : 0));
    }

    public int getHeadViewCount() {
        return (mHeadViews != null ? mHeadViews.size() : 0);
    }

    public int getFootViewCount() {
        return (mFootViews != null ? mFootViews.size() : 0);
    }

    private static class HFHolder extends RecyclerView.ViewHolder {
        public HFHolder(View itemView) {
            super(itemView);
        }
    }
}
