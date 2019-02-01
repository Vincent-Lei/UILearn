package com.baza.android.slib.storage.file;

import android.app.Application;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;


/**
 * Created by Vincent.Lei on 2016/11/5.
 * Title : 存储跟目录设置
 * Note :
 */

public class RootStorage {
    private static String mDefineRootPath, mRootPathHasExtraStorage, mRootPathNoExtraStorage;
    private static Application mApplication;

    static void init(Application application, String rootPath) {
        mDefineRootPath = rootPath;
        mApplication = application;
        String mRootPath = getRootPath();
        createSubFolds(mRootPath);
    }


    public static String getRootPath() {
        if (mApplication == null)
            throw new RuntimeException("please init first");
        boolean hasExtraStorage = hasExtraStorage();
        if (hasExtraStorage && mRootPathHasExtraStorage != null)
            return mRootPathHasExtraStorage;
        if (!hasExtraStorage && mRootPathNoExtraStorage != null)
            return mRootPathNoExtraStorage;
        String extraStoragePath = hasExtraStorage ? Environment.getExternalStorageDirectory().getPath() : null;
        String mRootPath = TextUtils.isEmpty(mDefineRootPath) ? (extraStoragePath != null ? (extraStoragePath + File.separator + mApplication.getPackageName()) : (mApplication.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath())) : mDefineRootPath;
        if (!mRootPath.endsWith(File.separator))
            mRootPath = mRootPath + File.separator;
        File file = new File(mRootPath);
        if (!file.exists())
            if (!file.mkdirs())
                Log.e("RootStorage", "can not make rootPath");
        Log.d("RootStorage", "mRootPath = " + mRootPath);
        if (hasExtraStorage)
            mRootPathHasExtraStorage = mRootPath;
        else
            mRootPathNoExtraStorage = mRootPath;
        return mRootPath;
    }

    /**
     * 该文件夹下存的是应用私有数据  只有获得root权限才可以看到
     */
    public static String getPrivatePath(String pathName, boolean isCreateAuto) {
        File file = new File(mApplication.getApplicationInfo().dataDir + (pathName.startsWith(File.separator) ? pathName : (File.separator + pathName)));
        if (!file.exists()) {
            if (isCreateAuto && file.mkdirs()) {
                Log.d("createPrivatePath", file.getAbsolutePath());
                return file.getAbsolutePath();
            }
            return null;
        }
        return file.getAbsolutePath();
    }

    public static boolean hasExtraStorage() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    private static void createSubFolds(String mRootPath) {
        if (TextUtils.isEmpty(mRootPath))
            return;
        createNoMediaFile(mRootPath);
        File filePath;
        for (StorageType storageType : StorageType.values()) {
            filePath = new File(mRootPath + storageType.getPath());
            if (!filePath.exists()) {
                if (filePath.mkdirs())
                    Log.d("createPath", filePath.getAbsolutePath());
            }
        }

    }

    public static final String NO_MEDIA_FILE_NAME = ".nomedia";

    private static void createNoMediaFile(String path) {
        path = (path.endsWith(File.separator) ? path : (path + File.separator));
        File noMediaFile = new File(path + NO_MEDIA_FILE_NAME);
        try {
            if (!noMediaFile.exists()) {
                noMediaFile.createNewFile();
                Log.d("nomedia", noMediaFile.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
