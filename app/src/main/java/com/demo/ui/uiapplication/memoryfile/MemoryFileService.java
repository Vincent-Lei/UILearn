package com.demo.ui.uiapplication.memoryfile;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.demo.ui.uiapplication.LogUtil;
import com.memory.IMemoryFile;
import com.memory.ISignalListener;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

/**
 * Created by Vincent.Lei on 2018/12/17.
 * Title：
 * Note：
 */
public class MemoryFileService extends Service {

    private static int MSG_ID = 0;

    private class MemoryFileBinder extends IMemoryFile.Stub {
        private RemoteCallbackList<ISignalListener> mCallbackList = new RemoteCallbackList<>();

        @Override
        public ParcelFileDescriptor getParcelFileDescriptor() throws RemoteException {
            FileDescriptor fileDescriptor = null;
            try {
                Method method = MemoryFile.class.getDeclaredMethod("getFileDescriptor");
                fileDescriptor = (FileDescriptor) method.invoke(memoryFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                return ParcelFileDescriptor.dup(fileDescriptor);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void noticeSignalReadAll() throws RemoteException {
            if (memoryFile != null)
                memoryFile.close();
            try {
                memoryFile = new MemoryFile(MEMORY_FILE_NAME, MEMORY_LENGTH);
            } catch (IOException e) {
                e.printStackTrace();
            }
            MSG_ID++;
            String msg = "消息测试，编号：" + MSG_ID;
            try {
                byte[] buff = msg.getBytes("utf-8");
                LogUtil.d("buff.length = " + buff.length);
                memoryFile.writeBytes(buff, 0, 0, buff.length);
                final int N = mCallbackList.beginBroadcast();
                for (int i = 0; i < N; i++) {
                    mCallbackList.getBroadcastItem(i).onSignalCreated(buff.length);
                }
                mCallbackList.finishBroadcast();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void registerSignalListener(ISignalListener listener) throws RemoteException {
            mCallbackList.register(listener);
        }
    }

    private static final String MEMORY_FILE_NAME = "test_memory_file_121";
    private static final int MEMORY_LENGTH = 64;
    private MemoryFileBinder mBinder = new MemoryFileBinder();
    MemoryFile memoryFile = null;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("MemoryFileService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("MemoryFileService onStartCommand");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
