package com.demo.ui.uiapplication.nestedscroll;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.ui.uiapplication.R;
import com.demo.ui.uiapplication.recyclerview.LinearItemDecoration;
import com.demo.ui.uiapplication.recyclerview.RecyclerAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;

/**
 * Created by Vincent.Lei on 2018/7/10.
 * Title：
 * Note：
 */
public class NestedFragment extends Fragment {

    PullToRefreshRecyclerView pullToRefreshRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int index = getArguments().getInt("index");
        View view = inflater.inflate((index < 3 ? R.layout.nested_fragment_1 : R.layout.nested_fragment_2), null);
        if (index < 3) {
            pullToRefreshRecyclerView = (PullToRefreshRecyclerView) ((ViewGroup)view).getChildAt(1);
            pullToRefreshRecyclerView.setFootReboundInsteadLoad(true);
            RecyclerView recyclerView = pullToRefreshRecyclerView.getRefreshableView();
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            Drawable drawable = getResources().getDrawable(R.drawable.recycler_view_linear_decoration);
            recyclerView.addItemDecoration(new LinearItemDecoration(LinearLayoutManager.VERTICAL, drawable));
            recyclerView.setAdapter(new RecyclerAdapter(getActivity()));
            pullToRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                    pullToRefreshRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pullToRefreshRecyclerView.onRefreshComplete();
                        }
                    }, 2000);
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {

                }
            });

        }
        return view;
    }
}
