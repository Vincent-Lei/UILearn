package com.demo.ui.uiapplication.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.ui.uiapplication.LogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Vincent.Lei on 2018/6/15.
 * Title：
 * Note：
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RHolder> {

    private List<String> mItemStrs;
    private Context mContext;
    private Random mRandom = new Random();
    private boolean mStaggered;
    private boolean mHorizontal;

    public RecyclerAdapter(Context context) {
        this(context, false);
    }

    public RecyclerAdapter(Context context, boolean staggered) {
        this(context, false, staggered);
    }

    public RecyclerAdapter(Context context, boolean horizontal, boolean staggered) {
        this.mContext = context;
        this.mStaggered = staggered;
        this.mHorizontal = horizontal;
        mItemStrs = new ArrayList<>(50);

        for (int i = 0; i < 50; i++) {
            mItemStrs.add(i + "\nITEM");
        }
    }

    //数据交换
    public void onItemMove(int fromPosition, int toPosition) {
        //交换位置
        Collections.swap(mItemStrs,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }


    //数据删除
    public void onItemDissmiss(int position) {
        //移除数据
        mItemStrs.remove(position);
        notifyItemRemoved(position);
    }


    public void refreshTarget(int position, String data) {
        if (position >= 0 && position < mItemStrs.size())
            mItemStrs.set(position, data);
        notifyItemChanged(position);
    }

    public void add(int position, String data) {
        if (position >= 0 && position < mItemStrs.size()) {
            mItemStrs.add(position, data);
            notifyItemInserted(position);
        } else {
            mItemStrs.add(data);
            notifyItemInserted(mItemStrs.size() - 1);
        }
    }

    public void remove(int position) {
        if (position >= 0 && position < mItemStrs.size()) {
            mItemStrs.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public RHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LogUtil.d("onCreateViewHolder = " + viewType);
        TextView textView = new TextView(mContext);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(20, 20, 20, 20);
        if (mHorizontal)
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        else
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setBackgroundColor(Color.argb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255)));
        return new RHolder(textView);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RHolder holder, int position) {
//        LogUtil.d("onBindViewHolder = " + position);
        if (mStaggered) {
            ViewGroup.LayoutParams lp = holder.textView.getLayoutParams();
            if (lp.height == ViewGroup.LayoutParams.WRAP_CONTENT)
                lp.height = mRandom.nextInt(200) + 200;
            holder.textView.setLayoutParams(lp);
        }
        holder.textView.setText(mItemStrs.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemStrs.size();
    }

    static class RHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public RHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
