package com.demo.ui.uiapplication;

import android.Manifest;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.ui.uiapplication.accessibilityservice.AccessibilityActivity;
import com.demo.ui.uiapplication.appcompat.AppcompatTest;
import com.demo.ui.uiapplication.aspectj.AspectJTest;
import com.demo.ui.uiapplication.constraintlayout.ConstraintLayoutTest;
import com.demo.ui.uiapplication.drawerlayout.DrawerLayoutTest;
import com.demo.ui.uiapplication.dynamicload.DynamicLoadTest;
import com.demo.ui.uiapplication.file.FileManager;
import com.demo.ui.uiapplication.floatingactionbutton.FloatingActionButtonTest;
import com.demo.ui.uiapplication.materialdesignAnimation.MdAnimationTest;
import com.demo.ui.uiapplication.memoryfile.MemoryFileActivity;
import com.demo.ui.uiapplication.nestedscroll.NestedScrollTest;
import com.demo.ui.uiapplication.opengles.OpenGLESActivity;
import com.demo.ui.uiapplication.paintcanvas.PaintCanvasTest;
import com.demo.ui.uiapplication.palette.PaletteTest;
import com.demo.ui.uiapplication.recyclerview.RecyclerTest;
import com.demo.ui.uiapplication.scroller.ScrollerTest;
import com.demo.ui.uiapplication.tabLayout.TabLayoutTest;
import com.demo.ui.uiapplication.testdb.DBTest;
import com.demo.ui.uiapplication.testinputlayout.TextInputLayoutTest;
import com.demo.ui.uiapplication.textview.TextViewTest;
import com.demo.ui.uiapplication.toolbar.ToolBarTest;
import com.demo.ui.uiapplication.viewpagetransformer.PageTransformerTest;
import com.slib.memorycache.MemoryCacheUtil;
import com.slib.utils.NotificationUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    private static ArrayList<String> mItemList = new ArrayList<>();

    static {

        mItemList.add("Appcompat");
        mItemList.add("RecyclerView");
        mItemList.add("DrawerLayout");
        mItemList.add("TextInputLayout");
        mItemList.add("ToolBar");
        mItemList.add("Palette");
        mItemList.add("TabLayout");
        mItemList.add("FloatingActionButton");
        mItemList.add("PageTransformer");
        mItemList.add("NestedScroll");
        mItemList.add("MdAnimation");
        mItemList.add("ConstraintLayout");
        mItemList.add("Scroller");
        mItemList.add("PaintAndCanvas");
        mItemList.add("AspectJ");
        mItemList.add("Accessibility");
        mItemList.add("TextViewTest");
        mItemList.add("MemoryFile");
        mItemList.add("OpenGLES");
        mItemList.add("TestDb");
        mItemList.add("TestNotification");
        mItemList.add("TestCancelNotification");
        mItemList.add("DynamicLoadTest");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lv);
        listView.setAdapter(new MyAdapter(this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(MainActivity.this, AppcompatTest.class);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, RecyclerTest.class);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, DrawerLayoutTest.class);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, TextInputLayoutTest.class);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, ToolBarTest.class);
                        break;
                    case 5:
                        intent = new Intent(MainActivity.this, PaletteTest.class);
                        break;
                    case 6:
                        intent = new Intent(MainActivity.this, TabLayoutTest.class);
                        break;
                    case 7:
                        intent = new Intent(MainActivity.this, FloatingActionButtonTest.class);
                        break;
                    case 8:
                        intent = new Intent(MainActivity.this, PageTransformerTest.class);
                        break;
                    case 9:
                        intent = new Intent(MainActivity.this, NestedScrollTest.class);
                        break;
                    case 10:
                        intent = new Intent(MainActivity.this, MdAnimationTest.class);
                        break;
                    case 11:
                        intent = new Intent(MainActivity.this, ConstraintLayoutTest.class);
                        break;
                    case 12:
                        intent = new Intent(MainActivity.this, ScrollerTest.class);
                        break;
                    case 13:
                        intent = new Intent(MainActivity.this, PaintCanvasTest.class);
                        break;
                    case 14:
                        intent = new Intent(MainActivity.this, AspectJTest.class);
                        break;
                    case 15:
                        intent = new Intent(MainActivity.this, AccessibilityActivity.class);
                        break;
                    case 16:
                        intent = new Intent(MainActivity.this, TextViewTest.class);
                        break;
                    case 17:
                        intent = new Intent(MainActivity.this, MemoryFileActivity.class);
                        break;
                    case 18:
                        intent = new Intent(MainActivity.this, OpenGLESActivity.class);
                        break;
                    case 19:
                        DBTest.testDb(getApplicationContext());
                        break;
                    case 20:
                        testNotify();
                        break;
                    case 21:
                        testCancnelNotify();
                        break;
                    case 22:
                        intent = new Intent(MainActivity.this, DynamicLoadTest.class);
                        break;
                }
                if (intent != null)
                    startActivity(intent);
            }

        });

        String msg = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String seed = "Android";
        try {
            byte[] rawKey = AESEncryptorUtil.getRawKey(seed.getBytes());
            LogUtil.d("rawKey.length = " + rawKey.length);
            LogUtil.d("rawKey = " + AESEncryptorUtil.toHex(rawKey));
            String encrypt = AESEncryptorUtil.encrypt(rawKey, msg);
            LogUtil.d("encrypt = " + encrypt);
            String decrypt = AESEncryptorUtil.decrypt(rawKey, encrypt);
            LogUtil.d("decrypt = " + decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = am.getMemoryClass();
        if (MemoryCacheUtil.hasHoneycomb() && MemoryCacheUtil.isLargeHeap(this)) {
            memoryClass = MemoryCacheUtil.getLargeMemoryClass(am);
        }
        LogUtil.d("m_memoryClass = " + memoryClass);

        Runtime runtime = Runtime.getRuntime();
        LogUtil.d("runtime maxMemory= " + (runtime.maxMemory()) / (1024 * 1024));
        LogUtil.d("runtime freeMemory= " + (runtime.freeMemory()) / (1024 * 1024));
        LogUtil.d("runtime totalMemory= " + (runtime.totalMemory()) / (1024 * 1024));
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            FileManager.initFileSystem(getApplication(), null);
        }
    }

    int notificationIndex = 0;
    static final String CHANNEL_ID = "test_id";

    private void testNotify() {
        notificationIndex++;
        Uri uri = Uri.parse("android.resource://com.demo.ui.uiapplication/raw/msg");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, PaintCanvasTest.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationUtil.doNotification(this, CHANNEL_ID, "测试通知栏" + notificationIndex, "这是一个通知" + notificationIndex, pendingIntent, uri, R.mipmap.ic_launcher);
    }

    private void testCancnelNotify() {
        NotificationUtil.cancelNotification(this, CHANNEL_ID);
    }

    private static class MyAdapter extends BaseAdapter {
        private Context context;

        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return mItemList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if (convertView == null) {
                textView = new TextView(context);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setPadding(30, 30, 30, 30);
                convertView = textView;
            }
            textView = (TextView) convertView;
            textView.setText(mItemList.get(position));
            return convertView;
        }
    }

}
