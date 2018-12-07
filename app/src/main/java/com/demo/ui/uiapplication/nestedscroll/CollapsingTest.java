package com.demo.ui.uiapplication.nestedscroll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/7/16.
 * Title：
 * Note：
 */
public class CollapsingTest extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nested_collapsing_test);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
