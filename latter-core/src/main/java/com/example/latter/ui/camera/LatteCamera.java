package com.example.latter.ui.camera;

import android.net.Uri;

import com.example.latter.delegates.PermissionCheckerDelegate;
import com.example.latter.util.file.FileUtil;

/**
 * @author gentleman
 * @date 2018/8/9
 * @function 照相机调用类
 */

public class LatteCamera {

    public static Uri createCropFile(){
        return Uri.parse(FileUtil.createFile("crop_image",
                FileUtil.getFileNameByTime("IMG","jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate){
        new CameraHandler(delegate).beginCameraDialog();
    }
}
