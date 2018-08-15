package com.example.ui.camera;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.PermissionChecker;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.latter.delegates.PermissionCheckerDelegate;
import com.example.latter.util.file.FileUtil;
import com.example.ui.R;

import java.io.File;

/**
 * @author gentleman
 * @date 2018/8/9
 * @function 照片处理类
 */

public class CameraHandler implements View.OnClickListener {

    private final AlertDialog DIALOG;
    private final PermissionCheckerDelegate DELEGATE;

    public CameraHandler(AlertDialog dialog,PermissionCheckerDelegate delegate){
        this.DIALOG = dialog;
        this.DELEGATE = delegate;
    }

    final void beginCameraDialog(){
        DIALOG.show();
        final Window window = DIALOG.getWindow();
        if (window!=null){
            window.setContentView(R.layout.dialog_camera_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            params.dimAmount = 0.5f;
            window.setAttributes(params);

            window.findViewById(R.id.photodialog_btn_cancel).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_take).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_native).setOnClickListener(this);
        }
        
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.photodialog_btn_cancel) {
            DIALOG.cancel();
        } else if (id == R.id.photodialog_btn_take) {
            takePhoto();
            DIALOG.cancel();
        } else if (id == R.id.photodialog_btn_native) {
            pickPhoto();
            DIALOG.cancel();
        }
    }

    private void pickPhoto() {

    }

    private void takePhoto() {
        final String currentPhotoName = getPhotoName();
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final File tempFile = new File(FileUtil.CAMERA_PHOTO_DIR,currentPhotoName);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            final ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA,tempFile.getPath());
            final Uri uri = DELEGATE.getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues);

        }
    }

    private String getPhotoName(){
        return FileUtil.getFileNameByTime("IMG","JPG");
    }
}
