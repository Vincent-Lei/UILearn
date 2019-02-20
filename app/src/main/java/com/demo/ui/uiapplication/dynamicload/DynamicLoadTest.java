package com.demo.ui.uiapplication.dynamicload;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.demo.ui.uiapplication.LogUtil;
import com.demo.ui.uiapplication.R;
import com.plugin.dynamic.PluginManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Vincent.Lei on 2019/2/18.
 * Title：
 * Note：
 */
public class DynamicLoadTest extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_load_test);

        String apk = downloadApkPlugin("dynamicapk-debug.apk");
        PluginManager.getInstance().setContext(this);
        LogUtil.d("apk = " + apk);
        PluginManager.getInstance().loadApk(apk);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ProxyActivity.class);
        String otherApkMainActivityName = PluginManager.getInstance().getPluginPackageArchiveInfo().activities[0].name;
        LogUtil.d(otherApkMainActivityName);
        intent.putExtra("className", otherApkMainActivityName);
        startActivity(intent);
    }

    private String downloadApkPlugin(String assetName) {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        String fileSavePath = getApplicationInfo().dataDir + "/" + assetName;
        if (new File(fileSavePath).exists())
            return fileSavePath;
        try {
            inputStream = getAssets().open(assetName);
            fileOutputStream = new FileOutputStream(fileSavePath);
            byte[] buff = new byte[2048];
            int len;
            while ((len = inputStream.read(buff)) > 0) {
                fileOutputStream.write(buff, 0, len);
            }
            fileOutputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
            fileSavePath = null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileSavePath;

    }
}
