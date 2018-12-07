package com.demo.ui.uiapplication.viewpagetransformer;

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
public class TransformerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public TransformerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
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


}
