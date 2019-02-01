package com.demo.ui.uiapplication.memoryfile;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.demo.ui.uiapplication.LogUtil;
import com.demo.ui.uiapplication.R;
import com.memory.IMemoryFile;
import com.memory.ISignalListener;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Vincent.Lei on 2018/12/17.
 * Title：
 * Note：
 */
public class MemoryFileActivity extends AppCompatActivity {
    private ServiceConnection serviceConnection;
    private IMemoryFile memoryFile;
    private byte[] buff;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ISignalListener.Stub signalListener = new ISignalListener.Stub() {
        @Override
        public void onSignalCreated(int size) throws RemoteException {
//            bos.reset();
            LogUtil.d("size = " + size);
            FileInputStream fileInputStream = null;
            try {
                FileDescriptor fileDescriptor = memoryFile.getParcelFileDescriptor().getFileDescriptor();
                fileInputStream = new FileInputStream(fileDescriptor);
                if (buff == null || size > buff.length)
                    buff = new byte[size];
                fileInputStream.read(buff, 0, size);
                String msg = new String(buff, 0, size, "utf-8");
                LogUtil.d(msg);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_file);
        connect();
    }

    private void connect() {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                memoryFile = IMemoryFile.Stub.asInterface(service);
                try {
                    memoryFile.registerSignalListener(signalListener);
                    touchSignal();

                    for (int i = 0; i < 5; i++) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                touchSignal();
                            }
                        }, i * 2000);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        startService(new Intent(this, MemoryFileService.class));
        bindService(new Intent(this, MemoryFileService.class), serviceConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceConnection != null)
            unbindService(serviceConnection);
    }

    private void touchSignal() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (memoryFile != null) {
                    try {
                        memoryFile.noticeSignalReadAll();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 1000);

    }
}
