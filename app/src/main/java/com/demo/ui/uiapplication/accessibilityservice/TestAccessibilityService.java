package com.demo.ui.uiapplication.accessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.provider.Settings;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.demo.ui.uiapplication.LogUtil;

import java.util.List;

/**
 * Created by Vincent.Lei on 2018/10/21.
 * Title：
 * Note：
 */
public class TestAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
//        LogUtil.d(event.toString());
        //自动安转
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getPackageName().equals("com.android.packageinstaller")) {
            AccessibilityNodeInfo nodeInfo = findViewByText("取消", true);
            if (nodeInfo != null) {
                performViewClick(nodeInfo);
            }
        } else if (event.getPackageName().equals("com.bznet.android.rcbox")) {
            if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
                Notification notification = (Notification) event.getParcelableData();
                String content = notification.tickerText.toString();
                LogUtil.d(content);
                List<CharSequence> texts = event.getText();
                if (texts != null) {
                    for (int i = 0; i < texts.size(); i++) {
                        LogUtil.d(texts.get(i).toString());
                        if (texts.get(i).toString().contains("图片")) {
                            PendingIntent pendingIntent = notification.contentIntent;
                            try {
                                pendingIntent.send();
                            } catch (PendingIntent.CanceledException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            } else if (event.getClassName().equals("com.baza.android.bzw.businesscontroller.message.ChatActivity")) {
                AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
                if (accessibilityNodeInfo == null) {
                    return;
                }
                List<CharSequence> texts = event.getText();
                if (texts != null) {
                    for (int i = 0; i < texts.size(); i++) {
                        LogUtil.d(texts.get(i).toString());
                    }
                }
                int count = accessibilityNodeInfo.getChildCount();
                LogUtil.d("accessibilityNodeInfo.getChildCount() = " + count);
//                for (int i = 0; i < count; i++) {
//                    CharSequence charSequence = accessibilityNodeInfo.getChild(i).getText();
//                    if (charSequence != null)
//                        LogUtil.d(charSequence.toString());
//                }
            }else{
                LogUtil.d(event.getClassName().toString());
            }
        }
    }

    @Override
    public void onInterrupt() {

    }

    public static void goAccess(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public void performViewClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        while (nodeInfo != null) {
            if (nodeInfo.isClickable()) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            }
            nodeInfo = nodeInfo.getParent();
        }
    }

    public AccessibilityNodeInfo findViewByText(String text) {
        return findViewByText(text, false);
    }

    public AccessibilityNodeInfo findViewByText(String text, boolean clickable) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return null;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null && (nodeInfo.isClickable() == clickable)) {
                    return nodeInfo;
                }
            }
        }
        return null;
    }

}
