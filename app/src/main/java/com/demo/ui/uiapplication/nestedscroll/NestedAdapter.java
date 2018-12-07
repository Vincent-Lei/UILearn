package com.demo.ui.uiapplication.nestedscroll;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Vincent.Lei on 2018/7/10.
 * Title：
 * Note：
 */
public class NestedAdapter extends FragmentPagerAdapter {
    private String[] mTitles;
    private List<Fragment> mFragments;

    public NestedAdapter(FragmentManager fm, String[] titles, List<Fragment> fragments) {
        super(fm);
        this.mTitles = titles;
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
