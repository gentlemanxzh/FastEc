package com.example.ui.camera;

import android.app.AlertDialog;
import android.support.v4.content.PermissionChecker;

import com.example.latter.delegates.PermissionCheckerDelegate;

/**
 * @author gentleman
 * @date 2018/8/9
 * @function 照片处理类
 */

public class CameraHandler {

    private final AlertDialog DIALOG;
    private final PermissionCheckerDelegate DELEGATE;

    public CameraHandler(AlertDialog dialog,PermissionCheckerDelegate delegate){
        this.DIALOG = dialog;
        this.DELEGATE = delegate;
    }

    final void beginCameraDialog(){
        DIALOG.show();
        
    }
}
