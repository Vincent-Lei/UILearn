package com.demo.ui.uiapplication.aspectj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.demo.ui.uiapplication.LogUtil;
import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/10/12.
 * Title：
 * Note：
 */
public class AspectJTest extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aspectj_test);
        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debug("click R.id.btn_click");
            }
        });

        testA();
        testB(100, 89);
        printMsg("--printMsg--");
        method1();
        method2();
    }

    public void testA() {
        LogUtil.d("--testA--");
    }

    public void testB(int x, int y) {
        LogUtil.d("--x+y--：" + (x + y));
    }

    @DebugLog(tag = "debug_info")
    public void debug(String msg){
        LogUtil.d(msg);
    }

    public void printMsg(String msg){
        LogUtil.d(msg);
    }


    public void method1(){
        method3();
        LogUtil.d("---method1---");
    }

    public void method2(){
        method3();
        LogUtil.d("---method2---");
    }

    public void method3(){
        LogUtil.d("---method3---");
    }
}
