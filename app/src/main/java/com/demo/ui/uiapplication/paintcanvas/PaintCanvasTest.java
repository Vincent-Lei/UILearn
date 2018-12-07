package com.demo.ui.uiapplication.paintcanvas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/8/9.
 * Title：
 * Note：
 */
public class PaintCanvasTest extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paint_canvas_activity_test);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, PaintZoomActivity.class);
        startActivity(intent);
    }
}
