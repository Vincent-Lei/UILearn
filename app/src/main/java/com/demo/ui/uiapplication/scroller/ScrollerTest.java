package com.demo.ui.uiapplication.scroller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/8/7.
 * Title：
 * Note：
 */
public class ScrollerTest extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroller_activity_test);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.trueeventview:
                Toast.makeText(this, "R.id.trueeventview", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_side_menu_click:
                Toast.makeText(this, "R.id.tv_side_menu_click", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
