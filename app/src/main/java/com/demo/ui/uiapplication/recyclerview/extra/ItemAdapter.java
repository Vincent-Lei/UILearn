package com.demo.ui.uiapplication.recyclerview.extra;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.ui.uiapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Vincent.Lei on 2018/6/27.
 * Title：
 * Note：
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.VH> {
    private static final String SOURCE = "完成第一步后，运营可以在开放平台SAAS板块内设置UAV规则。通过添加事件及设置事件的类型、属性、参数等数值，即可非常方便地组合出企业的UAV规则。UAV规则不但支持日常运营的实时评估、企业营销活动的周期性评估，也支持定向条件（针对部分用户的行为）的评估，使用起来非常灵活";
    private Context mContext;
    private List<String> mItemList = new ArrayList<>(100);

    public ItemAdapter(Context context) {
        this.mContext = context;
        Random random = new Random();
        int length = SOURCE.length();
        int start, end;
        int temp;
        for (int i = 0; i < 100; i++) {
            start = random.nextInt(length);
            end = random.nextInt(length);
            if (start > end) {
                temp = start;
                start = end;
                end = temp;
            } else if (start == end) {
                start--;
            }


            mItemList.add(SOURCE.substring(start, end));
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(mContext).inflate(R.layout.recycler_list_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.textView.setText(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }


    //数据交换
    public void onItemMove(int fromPosition, int toPosition) {
        //交换位置
        Collections.swap(mItemList,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }


    //数据删除
    public void onItemDissmiss(int position) {
        //移除数据
        mItemList.remove(position);
        notifyItemRemoved(position);
    }


    static class VH extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        AppCompatButton appCompatButton;

        public VH(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView = itemView.findViewById(R.id.iv);
            textView = itemView.findViewById(R.id.tv);
            appCompatButton = itemView.findViewById(R.id.btn);
        }
    }
}
