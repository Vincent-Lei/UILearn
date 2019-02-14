package com.slib.network.http;

/**
 * Created by Vincent.Lei on 2017/11/2.
 * Title：
 * Note：
 */

public class FileLoadParam {
    public String mUrl;
    public String mFileSaveDir;
    public String mFileName;
    public String mTagForSameUrl;

    public FileLoadParam(String url, String fileSaveDir, String fileName, String tagForSameUrl) {
        this.mUrl = url;
        this.mFileSaveDir = fileSaveDir;
        this.mFileName = fileName;
        this.mTagForSameUrl = tagForSameUrl;
    }
}
