package com.lei.dynamicapk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.plugin.dynamic.BaseDynamicActivity;

public class MainActivity extends BaseDynamicActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LogUtil", "load apk --0");
        setContentView(R.layout.activity_main);
        Log.d("LogUtil", "load apk --1");
        findViewById(R.id.btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(thisContext, SecondActivity.class);
        startActivity(intent);
    }
}
