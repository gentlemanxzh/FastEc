package com.example.latter.ui.camera;

import android.net.Uri;

/**
 * @author gentleman
 * @date 2018/8/9
 * @function 存储一些中间值
 */

public final class CameraImageBean {
    private Uri mPath = null;

    private static final CameraImageBean INSTANCE = new CameraImageBean();

    public static CameraImageBean getInstance(){
        return INSTANCE;
    }

    public Uri getPath(){
        return mPath;
    }

    public void setPath(Uri path){
        this.mPath = path;
    }
}
