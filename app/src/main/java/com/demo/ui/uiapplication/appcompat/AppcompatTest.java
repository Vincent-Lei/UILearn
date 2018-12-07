package com.demo.ui.uiapplication.appcompat;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/6/14.
 * Title：
 * Note：
 */
public class AppcompatTest extends Activity implements View.OnClickListener {
    private AppCompatButton appCompatButton;
    private AppCompatSpinner appCompatSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appcompat_activity_test);
        appCompatButton = findViewById(R.id.acp_list_popupwindow);
        appCompatSpinner = findViewById(R.id.acs);
        appCompatSpinner.setDropDownVerticalOffset(50);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acp_list_popupwindow:
                showListPopupWindow();
                break;
            case R.id.BottomSheetDialog:
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
                bottomSheetDialog.setContentView(getLayoutInflater().inflate(R.layout.bottomsheetdialog, null));
                bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.white);
                bottomSheetDialog.show();
                break;
        }
    }

    private void showListPopupWindow() {
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setAdapter(new DefaultListAdapter(this));
        listPopupWindow.setAnchorView(appCompatButton);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AppcompatTest.this, "点击：" + position, Toast.LENGTH_SHORT).show();
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.show();
    }
}
