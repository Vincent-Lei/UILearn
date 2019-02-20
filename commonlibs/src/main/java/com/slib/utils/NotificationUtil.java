package com.slib.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.util.HashMap;

/**
 * Created by Vincent.Lei on 2019/2/14.
 * Title：
 * Note：
 */
public class NotificationUtil {
    private static int DEFAULT_ID = 1809;

    private static HashMap<String, Integer> mCachedId = new HashMap<>();

    private synchronized static int getIncrementId() {
        DEFAULT_ID++;
        return DEFAULT_ID;
    }

    public static void doNotification(Context context, String channelId, String title, String msg, PendingIntent intent, Uri soundUrl, int smallIcon) {
        NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelId + "_name", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), channelId);
        //设置通知栏点击意图
        if (intent != null) builder.setContentIntent(intent);
        if (soundUrl != null)
            builder.setSound(soundUrl);
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setSmallIcon(smallIcon);
        builder.setContentTitle(title).setContentText(msg).setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        Integer id = mCachedId.get(channelId);
        if (id == null) {
            id = getIncrementId();
            mCachedId.put(channelId, id);
        }
        notificationManager.notify(id, builder.build());
    }

    public static void cancelNotification(Context context, String channelId) {
        Integer id = mCachedId.get(channelId);
        if (id == null)
            return;
        NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
        mCachedId.remove(id);
    }
}
