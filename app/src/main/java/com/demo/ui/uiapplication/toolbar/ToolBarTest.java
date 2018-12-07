package com.demo.ui.uiapplication.toolbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.demo.ui.uiapplication.LogUtil;
import com.demo.ui.uiapplication.R;

import java.lang.reflect.Method;

/**
 * Created by Vincent.Lei on 2018/7/5.
 * Title：
 * Note：
 */
public class ToolBarTest extends AppCompatActivity {
    Toolbar toolbar;
    int count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_test);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        toolbar.setOverflowIcon(getResources().getDrawable(R.mipmap.icon_has_attachment));
        //不显示标题
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        LogUtil.d("onPrepareOptionsMenu");
        if (menu != null) {
            count++;
            if (count % 2 == 0) {
                MenuItem menuItem = menu.findItem(R.id.menu_camera);
                menuItem.setVisible(false);
                menuItem = menu.findItem(R.id.menu_help);
                menuItem.setTitle("添加");
                menuItem.setIcon(android.R.drawable.ic_menu_add);
            } else {
                MenuItem menuItem = menu.findItem(R.id.menu_camera);
                menuItem.setVisible(true);
                menuItem = menu.findItem(R.id.menu_help);
                menuItem.setTitle("帮助");
                menuItem.setIcon(android.R.drawable.ic_menu_help);
            }
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        LogUtil.d("onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.toolbar_test_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        //设置searchView处于展开状态
//        searchView.setIconified(false);
        //进来就呈现搜索框并且不能被隐藏
//        searchView.setIconifiedByDefault(false);
        //当展开无输入内容的时候，没有关闭的图标
//        searchView.onActionViewExpanded();
        //显示提交按钮
//        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("请输入内容");

        //内容变化以及点击提交的监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //提交按钮的点击事件
                LogUtil.d("onQueryTextSubmit:" + query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //当输入框内容改变的时候回调
                LogUtil.d("onQueryTextChange:" + newText);
                return true;
            }
        });

        /**
         * 搜索图标要通过style修改
         *     <style name="SearchViewStyle" parent="Widget.AppCompat.SearchView">
         <item name="searchIcon">@android:drawable/ic_media_previous</item>
         </style>
         */
//        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(R.id.search_src_text);
//        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) searchAutoComplete.getLayoutParams();
//        lp.height = 30;
//        searchAutoComplete.setLayoutParams(lp);
//        searchAutoComplete.setHint("HAHAHHA");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
