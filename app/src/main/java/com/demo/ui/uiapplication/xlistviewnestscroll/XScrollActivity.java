package com.demo.ui.uiapplication.xlistviewnestscroll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.ui.uiapplication.LogUtil;
import com.demo.ui.uiapplication.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.slib.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class XScrollActivity extends AppCompatActivity {

    List<Fragment> mFragmentList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xscroll);
        ViewPager viewPager = findViewById(R.id.vp);
        for (int i = 0; i < 7; i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            TestFragment testFragment = new TestFragment();
            testFragment.setArguments(bundle);
            mFragmentList.add(testFragment);
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });
//

    }

    public static class TestFragment extends Fragment {
        private int index;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle bundle = getArguments();
            if (bundle != null) {
                index = bundle.getInt("index");
            }
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            boolean list = index % 2 == 0;
            View view = inflater.inflate(list ? R.layout.xscroll_fragment_list : R.layout.xscroll_fragment_sc, null);

            if (!list)
                return view;
            PullToRefreshListView pullToRefreshListView = view.findViewById(R.id.pull_to_refresh_list_view);
            ListView listView = pullToRefreshListView.getRefreshableView();
            listView.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return 50;
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView textView;
                    if (convertView == null) {
                        LogUtil.d("create item -------");
                        textView = new TextView(getActivity());
                        textView.setGravity(Gravity.CENTER);
                        textView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dip2px(80)));
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                        convertView = textView;
                    }
                    textView = (TextView) convertView;
                    textView.setText("TEXT：" + position);
                    return convertView;
                }
            });

            return view;
        }
    }


}
