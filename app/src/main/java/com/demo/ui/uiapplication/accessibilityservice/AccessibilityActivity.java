package com.demo.ui.uiapplication.accessibilityservice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.demo.ui.uiapplication.LogUtil;
import com.demo.ui.uiapplication.R;
import com.demo.ui.uiapplication.file.FileManager;

/**
 * Created by Vincent.Lei on 2018/10/21.
 * Title：
 * Note：
 */
public class AccessibilityActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accessibility_test);
        Test test = new Test<String>(){
            @Override
            public void onRequest(BaseBean<String> stringBaseBean) {
                LogUtil.d(stringBaseBean.getData());
            }
        };

        BaseBean<String>baseBean = new BaseBean<>();
        baseBean.data = "!!!!!!!!!!";

        test.onRequest(baseBean);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open:
                TestAccessibilityService.goAccess(this);
                break;
            case R.id.btn_install:
                FileOpenHelper.openFile(this, FileManager.getDownLoadFile("app-debug.apk", false));
                break;
        }
    }
}
