package com.baza.android.slib.storage.file;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;

/**
 * Created by Vincent.Lei on 2016/11/7.
 * Title : 本地文件操作类
 * Note :
 */

public class FileManager {
    private static final String APP_PRIVATE_DATA_PATH = "appData/";

    /**
     * 初始化文件根目录
     */
    public static void initFileSystem(Application application, String rootPath) {
        RootStorage.init(application, rootPath);
    }

    public static File getFile(String fileName, StorageType storageType, boolean isCreateAuto) {
        return getFile(null, fileName, storageType, isCreateAuto);
    }

    public static File getFile(String fileName, String fileSaveDir, boolean isCreateAuto) {
        return getFile(fileSaveDir, fileName, null, isCreateAuto);
    }

    private static File getFilePath(String fileSaveDirs, StorageType storageType) {
        String filePath;
        if (TextUtils.isEmpty(fileSaveDirs))
            filePath = RootStorage.getRootPath() + storageType.getPath();
        else
            filePath = fileSaveDirs;
        File file = new File(filePath);
        if (!file.exists() && !file.mkdirs())
            return null;
        return file;
    }

    private static File getFile(String fileSaveDir, String fileName, StorageType storageType, boolean isCreateAuto) {
        if (storageType != null & storageType == StorageType.TYPE_PRIVATE)
            return getPrivateFile(fileName, isCreateAuto);
        File filePath = getFilePath(fileSaveDir, storageType);
        if (filePath == null)
            return null;
        File file = new File(filePath.getAbsolutePath() + File.separator + fileName);
        if (!file.exists())
            try {
                if (isCreateAuto && file.createNewFile())
                    return file;
                file = null;
            } catch (IOException e) {
                e.printStackTrace();
                file = null;
            }
        return file;
    }

    public static String getFileAbsolutePath(String fileName, StorageType storageType) {
        return getFileAbsolutePath(null, fileName, storageType);
    }

    public static String getFileAbsolutePath(String fileName, String fileSaveDir) {
        return getFileAbsolutePath(fileSaveDir, fileName, null);
    }

    private static String getFileAbsolutePath(String fileSaveDir, String fileName, StorageType storageType) {
        if (storageType != null && storageType == StorageType.TYPE_PRIVATE)
            return getPrivateFileAbsolutePath(fileName);
        File filePath = getFilePath(fileSaveDir, storageType);
        if (filePath == null)
            return null;
        return filePath.getAbsolutePath() + File.separator + fileName;
    }

    public static String getRootDir() {
        return RootStorage.getRootPath();
    }


    private static File getPrivateFile(String fileName, boolean isCreateAuto) {
        String fileAbsolutePath = getPrivateFileAbsolutePath(fileName);
        if (fileAbsolutePath == null)
            return null;
        File file = new File(fileAbsolutePath);
        if (!file.exists())
            try {
                if (isCreateAuto && file.createNewFile())
                    return file;
                file = null;
            } catch (IOException e) {
                e.printStackTrace();
                file = null;
            }
        return file;
    }

    private static String getPrivateFileAbsolutePath(String fileName) {
        String fileDir = RootStorage.getPrivatePath(APP_PRIVATE_DATA_PATH, true);
        if (fileDir == null)
            return null;
        return fileDir + File.separator + fileName;
    }

    /**
     * 存储对象
     */
    public static void saveObject(String fileName, Object o) {
        if (TextUtils.isEmpty(fileName) || o == null)
            return;
        File file = getFile(fileName, StorageType.TYPE_DATA, true);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(o);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null)
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 读取对象
     */
    public static Object readObject(String fileName) {
        if (TextUtils.isEmpty(fileName))
            return null;
        File file = getFile(fileName, StorageType.TYPE_DATA, false);
        if (file == null || !file.exists())
            return null;
        ObjectInputStream ois = null;
        Object o = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            o = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ois != null)
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        return o;
    }

    /**
     * 获取文件扩展名
     */

    public static String getExtensionName(String filename) {
        if (!TextUtils.isEmpty(filename)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return null;
    }

    public enum SizeUnit {
        Byte,
        KB,
        MB,
        GB,
        TB,
        Auto,
    }

    public static String formatFileSize(long size) {
        return formatFileSize(size, SizeUnit.Auto);
    }

    public static String formatFileSize(long size, SizeUnit unit) {
        final double KB = 1024;
        final double MB = KB * 1024;
        final double GB = MB * 1024;
        final double TB = GB * 1024;
        if (unit == SizeUnit.Auto) {
            if (size < KB) {
                unit = SizeUnit.Byte;
            } else if (size < MB) {
                unit = SizeUnit.KB;
            } else if (size < GB) {
                unit = SizeUnit.MB;
            } else if (size < TB) {
                unit = SizeUnit.GB;
            } else {
                unit = SizeUnit.TB;
            }
        }

        switch (unit) {
            case Byte:
                return size + "B";
            case KB:
                return String.format(Locale.US, "%.2fKB", size / KB);
            case MB:
                return String.format(Locale.US, "%.2fMB", size / MB);
            case GB:
                return String.format(Locale.US, "%.2fGB", size / GB);
            case TB:
                return String.format(Locale.US, "%.2fPB", size / TB);
            default:
                return size + "B";
        }
    }

    /**
     * 清除缓存
     */
    public static boolean clearAllCache() {
        String rootPath = getRootDir();
        if (TextUtils.isEmpty(rootPath))
            return false;
        File file = new File(rootPath);
        return deleteDir(file, false);
    }

    public static boolean clearPrivatePath(String path, boolean deleteRootPath) {
        String rootPath = RootStorage.getPrivatePath(path, false);
        if (TextUtils.isEmpty(rootPath))
            return false;
        File file = new File(rootPath);
        return deleteDir(file, deleteRootPath);
    }

    /**
     * 清除目录下内容
     */
    public static boolean deleteDir(File dir, boolean deleteRoot) {
        if (dir == null || !dir.exists())
            return false;
        boolean success = true;
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                success = deleteDir(new File(dir, children[i]), false);
                if (!success)
                    return false;

            }
            if (deleteRoot) {
                Log.d("delete dir", dir.getAbsolutePath());
                success = dir.delete();
            }
            // 目录此时为空，可以删除
        } else if (!dir.getName().endsWith(RootStorage.NO_MEDIA_FILE_NAME)) {
            success = dir.delete();
            Log.d("delete file", dir.getAbsolutePath());
        }
        return success;
    }

    /**
     * 获取缓存内容大小
     */
    public static long getCacheSize() {
        return getFilesSizeInPath(getRootDir());
    }

    /**
     * 获取路径下的内容大小
     */
    public static long getFilesSizeInPath(String rootPath) {
        if (TextUtils.isEmpty(rootPath))
            return 0;
        File file = new File(rootPath);
        if (!file.exists())
            return 0;
        CacheSizeData cacheSizeData = new CacheSizeData();
        getPathFileSize(file, cacheSizeData);
        return cacheSizeData.size;
    }

    private static void getPathFileSize(File dir, CacheSizeData cacheSizeData) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
                getPathFileSize(new File(dir, children[i]), cacheSizeData);
            return;
        }
        cacheSizeData.size += dir.length();
    }


    private static class CacheSizeData {
        public int size;
    }

    public static boolean deleteFile(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return false;
        File file = new File(filePath);
        return deleteFile(file);
    }

    public static boolean deleteFile(File file) {
        if (file == null || !file.exists())
            return false;
        return file.delete();
    }

    public static boolean hasExtentsion(String filename) {
        int dot = filename.lastIndexOf('.');
        return (dot > -1) && (dot < (filename.length() - 1));
    }
}
