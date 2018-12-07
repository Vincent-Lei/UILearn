package com.demo.ui.uiapplication.drawerlayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/7/2.
 * Title：
 * Note：
 */
public class NavigationViewTest extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_test);
        drawerLayout = findViewById(R.id.drawlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        //给侧滑控件设置监听
//        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
    }
}
