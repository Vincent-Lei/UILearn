package com.demo.ui.uiapplication.textview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/11/9.
 * Title：
 * Note：
 */
public class TextViewTest extends AppCompatActivity implements View.OnClickListener {
    FoldTextView foldTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textviewtest_activity);
        foldTextView = findViewById(R.id.fold_text);
        foldTextView.postDelayed(new Runnable() {
            @Override
            public void run() {
                foldTextView.setText("哈哈哈哈哈哈哈哈哈哈哈OIOIHIHIHIGHUIGUGUIGU欧锦赛佛耳机的偶然太古街破解头儿金融通坡道进入过二级融通平顶山让他家偶尔");
            }
        }, 1000);
    }

    private int line = 5;

    static String WORD = "尔推高房价饿哦日跑人家特然084506尔推高房价饿哦日跑人家特然084506尔推高房价饿哦日跑人家特然084506平均首尔推高房价饿哦日跑人家特然0845069580*&^&*^**……*（*）+——*）&oihjsfiteirthoierhotirhet欧式金佛何师傅";

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_test:
//                foldTextView.setFoldLine(2);
                foldTextView.setText(WORD);
                break;
        }
    }
}
