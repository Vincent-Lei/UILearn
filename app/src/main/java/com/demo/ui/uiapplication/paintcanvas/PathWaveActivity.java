package com.demo.ui.uiapplication.paintcanvas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/9/4.
 * Title：
 * Note：
 */
public class PathWaveActivity extends AppCompatActivity {
    PathSearchView pathSearchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canvas_paint_path_wave);
        pathSearchView = findViewById(R.id.path_search_view);
        pathSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pathSearchView.tryToRestart();
            }
        });
    }
}
