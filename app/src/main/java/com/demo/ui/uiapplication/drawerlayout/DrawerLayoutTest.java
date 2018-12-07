package com.demo.ui.uiapplication.drawerlayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/6/29.
 * Title：
 * Note：
 */
public class DrawerLayoutTest extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    ViewGroup view_menu;
    View view_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout_test);
        drawerLayout = findViewById(R.id.drawlayout);
        toolbar = findViewById(R.id.toolbar);
        view_menu = findViewById(R.id.view_menu);
        view_content = findViewById(R.id.view_content);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        //给侧滑控件设置监听
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                float menuScale = 0.3f + 0.7f * slideOffset;
                float contentScale = 0.8f + 0.2f * (1f - slideOffset);
                view_menu.setScaleX(menuScale);
                view_menu.setScaleY(menuScale);
                view_content.setPivotX(0);
                view_content.setPivotX(view_content.getMeasuredHeight() / 2);
                view_content.setScaleX(contentScale);
                view_content.setScaleY(contentScale);

                view_content.setTranslationX(view_menu.getMeasuredWidth() * 0.7f * slideOffset);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        int menuCount = view_menu.getChildCount();
        for (int i = 0; i < menuCount; i++) {
            view_menu.getChildAt(i).setTag(i);
            view_menu.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    Toast.makeText(DrawerLayoutTest.this, "点击菜单" + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DrawerLayoutTest.this, NavigationViewTest.class);
                    startActivity(intent);
                }
            });


        }

    }
}
