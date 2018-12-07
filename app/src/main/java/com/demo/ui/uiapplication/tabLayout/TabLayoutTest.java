package com.demo.ui.uiapplication.tabLayout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.ui.uiapplication.LogUtil;
import com.demo.ui.uiapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent.Lei on 2018/7/10.
 * Title：
 * Note：
 */
public class TabLayoutTest extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    View view;
    private List<Fragment> mFragments = new ArrayList<>();
    private static final String[] mTitles = {"热点", "推荐", "新闻", "体育", "科技"};
    private static final int[] mDrawable = {R.mipmap.icon_tab_account_checked, R.mipmap.icon_tab_candidate_checked, R.mipmap.icon_tab_find_checked, R.mipmap.icon_tab_home_checked, R.mipmap.icon_tab_message_checked};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_test);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.view_pager);
        setSupportActionBar(toolbar);
        createFragment();
        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), mTitles, mFragments));
        tabLayout.setupWithViewPager(viewPager);
        view = findViewById(R.id.view);
        selfDefine();
    }

    private void selfDefine() {
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                lp.width = tabLayout.getWidth() / mTitles.length;
                view.setLayoutParams(lp);
            }
        });


        int count = tabLayout.getTabCount();
        TabLayout.Tab tab;
        TextView textView;
        Drawable drawable;
        for (int i = 0; i < count; i++) {
            tab = tabLayout.getTabAt(i);
            textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setTextColor(getResources().getColor((0 == i ? android.R.color.white : android.R.color.darker_gray)));
            textView.setText(mTitles[i]);
            textView.setCompoundDrawablePadding(10);
            drawable = getResources().getDrawable(mDrawable[i]);
            drawable.setBounds(0, 0, 30, 30);
            textView.setCompoundDrawables(null, drawable, null, null);
            tab.setCustomView(textView);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int dx = tabLayout.getWidth() / mTitles.length;
                view.setTranslationX(dx * position + dx * positionOffset);
                LogUtil.d("positionOffset:" + positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                int count = tabLayout.getTabCount();
                TextView textView;
                for (int i = 0; i < count; i++) {
                    textView = (TextView) tabLayout.getTabAt(i).getCustomView();
                    textView.setTextColor(getResources().getColor((position == i ? android.R.color.white : android.R.color.darker_gray)));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void createFragment() {
        Bundle bundle;
        Fragment fragment;
        for (int i = 0; i < mTitles.length; i++) {
            fragment = new TabFragment();
            bundle = new Bundle();
            bundle.putString("des", mTitles[i]);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
    }
}
