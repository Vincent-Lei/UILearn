package com.demo.ui.uiapplication.materialdesignAnimation.activityAnimation;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.demo.ui.uiapplication.LogUtil;
import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/7/19.
 * Title：
 * Note：
 */
public class AnimationSecondActivity extends AppCompatActivity {
    TextView textView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.md_animation_second);
        textView = findViewById(R.id.tv_1);
        frameLayout = findViewById(R.id.fl);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String transition = getIntent().getStringExtra("transition");
            if ("slide".equals(transition)) {
                Slide slide = new Slide();
                slide.setDuration(300);
//                getWindow().setExitTransition(slide);//出去的动画
                getWindow().setEnterTransition(slide);//进来的动画
            } else if ("explode".equals(transition)) {
                Explode explode = new Explode();
                explode.setDuration(1000);
                getWindow().setEnterTransition(explode);
            } else {
                Fade fade = new Fade();
                fade.setDuration(1000);
                getWindow().setEnterTransition(fade);
            }
            textView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setText("****************");
                }
            }, 200);
        }

    }

}
