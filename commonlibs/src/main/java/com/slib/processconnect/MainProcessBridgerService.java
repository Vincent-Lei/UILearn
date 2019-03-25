package com.slib.processconnect;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Vincent.Lei on 2019/3/4.
 * Title：
 * Note：
 */
public class MainProcessBridgerService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
