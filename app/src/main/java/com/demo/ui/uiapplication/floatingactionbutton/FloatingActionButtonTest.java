package com.demo.ui.uiapplication.floatingactionbutton;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.demo.ui.uiapplication.LogUtil;
import com.demo.ui.uiapplication.R;
import com.demo.ui.uiapplication.recyclerview.LinearItemDecoration;
import com.demo.ui.uiapplication.recyclerview.RecyclerAdapter;

/**
 * Created by Vincent.Lei on 2018/7/11.
 * Title：
 * Note：
 */
public class FloatingActionButtonTest extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floatingactionbutton_test);
        recyclerView = findViewById(R.id.recycler_view);
        toolbar = findViewById(R.id.toolbar);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Drawable drawable = getResources().getDrawable(R.drawable.recycler_view_linear_decoration);
        recyclerView.addItemDecoration(new LinearItemDecoration(LinearLayoutManager.VERTICAL, drawable));
        recyclerView.setAdapter(new RecyclerAdapter(this));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        LogUtil.d("onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.toolbar_test_menu, menu);
        return true;
    }
}
