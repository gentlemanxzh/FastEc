package com.example.latter.delegates;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.latter.app.Latte;
import com.example.latter.ui.camera.CameraImageBean;
import com.example.latter.ui.camera.LatteCamera;
import com.example.latter.ui.camera.RequestCodes;
import com.example.latter.util.callback.CallbackManager;
import com.example.latter.util.callback.CallbackType;
import com.example.latter.util.callback.IGlobalCallback;
import com.yalantis.ucrop.UCrop;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author gentleman
 * @date 2018/5/13
 * @function 用来进行Android 6.0之后的权限判定
 */

@RuntimePermissions
public abstract class PermissionCheckerDelegate extends BaseDelegate {

    @NeedsPermission(Manifest.permission.CAMERA)
    void startCamera() {
        LatteCamera.start(this);
    }

    //这个才是真正的调用方法
    public void startCameraWithCheck() {
        PermissionCheckerDelegatePermissionsDispatcher.startCameraWithPermissionCheck(this);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraDenied() {
        Toast.makeText(_mActivity, "不允许拍照", Toast.LENGTH_SHORT).show();
    }


    @OnShowRationale(Manifest.permission.CAMERA)
    void onCameraRationale(PermissionRequest request) {
        showRationaleDialog(request);
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNever() {
        Toast.makeText(getContext(), "永久拒绝权限", Toast.LENGTH_LONG).show();
    }

    void showRationaleDialog(final PermissionRequest request) {

        if (getContext() != null) {
            new AlertDialog.Builder(getContext())
                    .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            request.proceed();
                        }
                    })
                    .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            request.cancel();
                        }
                    })
                    .setCancelable(false)
                    .setMessage("权限管理")
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheckerDelegatePermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TAKE_PHOTO:
                    final Uri resultUri = CameraImageBean.getInstance().getPath();
                    UCrop.of(resultUri,resultUri)
                            .withMaxResultSize(400,400)
                            .start(getContext(),this);
                    break;

                case RequestCodes.PICK_PHOTO:
                    if (data!=null){
                        final Uri picPath = data.getData();
                        //从相册选择后需要有个路径存放剪裁过的照片
                        final String pickCroupPath = LatteCamera.createCropFile().getPath();
                        UCrop.of(picPath,Uri.parse(pickCroupPath))
                                .withMaxResultSize(400,400)
                                .start(getContext(),this);

                    }

                    break;

                case RequestCodes.CROP_PHOTO:
                    final Uri croupUri = UCrop.getOutput(data);
                    //拿到剪裁后的数据进行处理
                    @SuppressWarnings("unchecked")
                    final IGlobalCallback<Uri> callback = CallbackManager
                            .getInstance()
                            .getCallback(CallbackType.ON_CROP);

                    if (callback!=null){
                        callback.exeuteCallback(croupUri);
                    }



                    break;

                case RequestCodes.CROP_ERROR:

                    Toast.makeText(_mActivity, "剪裁出错", Toast.LENGTH_SHORT).show();

                    break;
                default:
                    break;


            }
        }
    }
}
