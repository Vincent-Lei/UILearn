package com.demo.ui.uiapplication.viewpagetransformer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.demo.ui.uiapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent.Lei on 2018/7/13.
 * Title：
 * Note：
 */
public class PageTransformerTest extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_transformer_test);
        viewPager = findViewById(R.id.view_pager);

        List<Fragment> list = new ArrayList<>();
        list.add(new PageFragment());
        list.add(new PageFragment());
        list.add(new PageFragment());
        viewPager.setAdapter(new TransformerAdapter(getSupportFragmentManager(), list));
        viewPager.setPageTransformer(true, new MyTransformer());
    }
}
