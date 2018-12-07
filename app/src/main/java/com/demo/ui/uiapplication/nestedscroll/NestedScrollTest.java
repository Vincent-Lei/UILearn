package com.demo.ui.uiapplication.nestedscroll;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.demo.ui.uiapplication.LogUtil;
import com.demo.ui.uiapplication.R;
import com.demo.ui.uiapplication.nestedscroll.behavior.BehaviorTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent.Lei on 2018/7/13.
 * Title：
 * Note：
 */
public class NestedScrollTest extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>(3);
    private static final String[] mTitles = {"热点", "推荐", "新闻", "体育", "科技"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nested_scroll_test);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.view_pager);
        viewPager = findViewById(R.id.vp);
        setSupportActionBar(toolbar);
        createFragment();
        viewPager.setAdapter(new NestedAdapter(getSupportFragmentManager(), mTitles, fragmentList));
        tabLayout.setupWithViewPager(viewPager);
    }


    private void createFragment() {
        Fragment fragment;
        Bundle bundle;
        for (int i = 0; i < mTitles.length; i++) {
            fragment = new NestedFragment();
            bundle = new Bundle();
            bundle.putInt("index", i);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nested_scroll_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_collapsingToolBar:
                LogUtil.d("item_collapsingToolBar");
                Intent intent = new Intent(this, CollapsingTest.class);
                startActivity(intent);
                break;
            case R.id.item_behavior:
                 intent = new Intent(this, BehaviorTest.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
