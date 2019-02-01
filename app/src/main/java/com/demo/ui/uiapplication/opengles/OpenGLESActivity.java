package com.demo.ui.uiapplication.opengles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2019/1/2.
 * Title：
 * Note：
 */
public class OpenGLESActivity extends AppCompatActivity {
    MyGLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_gles);
        glSurfaceView = findViewById(R.id.glSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (glSurfaceView != null)
            glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (glSurfaceView != null)
            glSurfaceView.onResume();
    }
}
