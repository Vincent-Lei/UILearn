package com.demo.ui.uiapplication.materialdesignAnimation.activityAnimation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/7/19.
 * Title：
 * Note：
 */
public class AnimationThirdActivity extends AppCompatActivity {

    View viewAvatar;
    View viewUserInfo;
    View viewThird;
    View viewParent;
    View viewBg1;
    View viewBg2;
    View viewBg3;
    View viewBg4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.md_animation_third);
        viewParent = findViewById(R.id.cl_parent);
        viewAvatar = findViewById(R.id.iv_avatar);
        viewUserInfo = findViewById(R.id.ll_user_info);
        viewThird = findViewById(R.id.ll_third);
        viewBg1 = findViewById(R.id.view_bg_1);
        viewBg2 = findViewById(R.id.view_bg_2);
        viewBg3 = findViewById(R.id.view_bg_3);
        viewBg4 = findViewById(R.id.view_bg_4);
        viewThird.post(new Runnable() {
            @Override
            public void run() {
                animate();
            }
        });

        findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate();
            }
        });
    }

    private void animate() {
        viewParent.setTranslationY(-viewParent.getHeight());
        viewAvatar.setTranslationY(-viewAvatar.getHeight());
        viewUserInfo.setTranslationY(-viewUserInfo.getHeight());
        viewThird.setAlpha(0);
        viewUserInfo.setAlpha(0);

        viewBg1.setScaleX(0);
        viewBg1.setScaleY(0);
//        viewBg1.setPivotX(0);
//        viewBg1.setPivotY(0);

        viewBg2.setScaleX(0);
        viewBg2.setScaleY(0);
//        viewBg2.setPivotX(viewBg2.getMeasuredWidth());
//        viewBg2.setPivotY(0);

        viewBg3.setScaleX(0);
        viewBg3.setScaleY(0);
//        viewBg3.setPivotX(0);
//        viewBg3.setPivotY(viewBg3.getMeasuredHeight());

        viewBg4.setScaleX(0);
        viewBg4.setScaleY(0);
//        viewBg4.setPivotX(viewBg4.getMeasuredWidth());
//        viewBg4.setPivotY(viewBg4.getMeasuredHeight());

        ViewCompat.animate(viewParent).setDuration(400).translationY(0).setInterpolator(new AccelerateInterpolator()).start();
        ViewCompat.animate(viewAvatar).setStartDelay(200).setDuration(500).translationY(0).setInterpolator(new AccelerateDecelerateInterpolator()).start();
        ViewCompat.animate(viewUserInfo).setStartDelay(300).setDuration(300).translationY(0).setInterpolator(new AccelerateInterpolator()).start();
        ViewCompat.animate(viewUserInfo).setStartDelay(350).setDuration(200).alpha(1f).setInterpolator(new AccelerateInterpolator()).start();
        ViewCompat.animate(viewThird).setStartDelay(400).setDuration(400).alpha(1f).setInterpolator(new AccelerateInterpolator()).start();

        ViewCompat.animate(viewBg1).setStartDelay(500).setDuration(300).scaleX(1f).setInterpolator(new AccelerateDecelerateInterpolator()).start();
        ViewCompat.animate(viewBg1).setStartDelay(500).setDuration(300).scaleY(1f).setInterpolator(new AccelerateDecelerateInterpolator()).start();

        ViewCompat.animate(viewBg2).setStartDelay(500).setDuration(300).scaleX(1f).setInterpolator(new AccelerateDecelerateInterpolator()).start();
        ViewCompat.animate(viewBg2).setStartDelay(500).setDuration(300).scaleY(1f).setInterpolator(new AccelerateDecelerateInterpolator()).start();

        ViewCompat.animate(viewBg3).setStartDelay(500).setDuration(300).scaleX(1f).setInterpolator(new AccelerateDecelerateInterpolator()).start();
        ViewCompat.animate(viewBg3).setStartDelay(500).setDuration(300).scaleY(1f).setInterpolator(new AccelerateDecelerateInterpolator()).start();

        ViewCompat.animate(viewBg4).setStartDelay(500).setDuration(300).scaleX(1f).setInterpolator(new AccelerateDecelerateInterpolator()).start();
        ViewCompat.animate(viewBg4).setStartDelay(500).setDuration(300).scaleY(1f).setInterpolator(new AccelerateDecelerateInterpolator()).start();
    }

}
