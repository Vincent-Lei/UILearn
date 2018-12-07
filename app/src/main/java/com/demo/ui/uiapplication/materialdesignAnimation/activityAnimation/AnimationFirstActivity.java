package com.demo.ui.uiapplication.materialdesignAnimation.activityAnimation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/7/19.
 * Title：
 * Note：
 */
public class AnimationFirstActivity extends FragmentActivity implements View.OnClickListener {
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.md_animation_first);
        imageView = findViewById(R.id.iv);
    }

    @Override
    public void onClick(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch (v.getId()) {
                case R.id.iv:
//                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, "image_large");
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair.<View, String>create(imageView, "image_large"), Pair.<View, String>create(findViewById(R.id.tv_1), "textview_tv_1"));
                    Intent intent = new Intent(this, AnimationSecondActivity.class);
                    startActivity(intent, activityOptionsCompat.toBundle());
                    break;
                case R.id.tv_1:
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
                    intent = new Intent(this, AnimationSecondActivity.class);
                    intent.putExtra("transition", "explode");
                    startActivity(intent, optionsCompat.toBundle());
                    break;
            }
        }
    }
}
