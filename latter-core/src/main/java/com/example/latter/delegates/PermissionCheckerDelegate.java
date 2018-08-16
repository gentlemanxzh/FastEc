package com.example.latter.delegates;

import android.Manifest;
import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.latter.ui.camera.LatteCamera;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author gentleman
 * @date 2018/5/13
 * @function 用来进行Android 6.0之后的权限判定
 */

@RuntimePermissions
public abstract class PermissionCheckerDelegate extends BaseDelegate {

    @NeedsPermission(Manifest.permission.CAMERA)
    void startCamera(){
        LatteCamera.start(this);
    }

    //这个才是真正的调用方法
    public void startCameraWithCheck(){
        PermissionCheckerDelegatePermissionsDispatcher.startCameraWithPermissionCheck(this);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraDenied(){
        Toast.makeText(_mActivity, "不允许拍照", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheckerDelegatePermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
