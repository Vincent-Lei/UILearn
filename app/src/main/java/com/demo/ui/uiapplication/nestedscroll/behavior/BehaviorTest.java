package com.demo.ui.uiapplication.nestedscroll.behavior;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.ui.uiapplication.LogUtil;
import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/7/17.
 * Title：
 * Note：
 */
public class BehaviorTest extends AppCompatActivity {
    TextView textView_dependency;
    NestedScrollView nestedScrollView;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nested_behavior_test);
        textView_dependency = findViewById(R.id.tv_dependency);
        textView_dependency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_dependency.setTranslationY(textView_dependency.getTranslationY() + 50);
            }
        });

        nestedScrollView = findViewById(R.id.NestedScrollView);
        linearLayout = findViewById(R.id.ll_1);
        nestedScrollView.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.d("getMeasuredHeight："+nestedScrollView.getMeasuredHeight());
                LogUtil.d("getHeight："+linearLayout.getHeight());
            }
        });
    }
}
