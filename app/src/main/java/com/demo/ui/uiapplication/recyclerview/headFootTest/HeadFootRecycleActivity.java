package com.demo.ui.uiapplication.recyclerview.headFootTest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.ui.uiapplication.R;
import com.demo.ui.uiapplication.recyclerview.RecyclerAdapter;
import com.demo.ui.uiapplication.recyclerview.extra.ExtraRecyclerViewActivity;

/**
 * Created by Vincent.Lei on 2018/6/25.
 * Title：
 * Note：
 */
public class HeadFootRecycleActivity extends Activity implements View.OnClickListener {
    private WrapRecyclerView wrapRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_activity_head_and_foot);
        wrapRecyclerView = findViewById(R.id.recycler_view);
        wrapRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        for (int i = 0; i < 10; i++) {
            wrapRecyclerView.addHeadView(createView("I AM HEADVIEW " + i));
        }
        for (int i = 0; i < 10; i++) {
            wrapRecyclerView.addFootView(createView("I AM FOOTVIEW " + i));
        }
        wrapRecyclerView.setAdapter(new RecyclerAdapter(this));
    }


    private TextView createView(String text) {
        TextView textView = new TextView(this);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (getResources().getDisplayMetrics().density * 50)));
        return textView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_touch:
                Intent intent = new Intent(this, ExtraRecyclerViewActivity.class);
                startActivity(intent);
                break;
        }
    }
}
