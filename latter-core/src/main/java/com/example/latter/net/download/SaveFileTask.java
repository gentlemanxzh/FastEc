package com.example.latter.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.latter.app.Latte;
import com.example.latter.net.callback.IRequest;
import com.example.latter.net.callback.ISuccess;
import com.example.latter.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Url;

/**
 * @author gentleman
 * @date 2018/5/20
 */

public class SaveFileTask extends AsyncTask<Object,Void,File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest request, ISuccess success) {
        this.REQUEST = request;
        this.SUCCESS = success;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];
        final InputStream is = body.byteStream();
        if (downloadDir == null||downloadDir.equals("")){
            downloadDir = "down_loads";
        }
        if (extension == null||extension.equals("")){
            extension = "";
        }
        if (name == null){
            return FileUtil.writeToDisk(is,downloadDir,extension.toUpperCase(),extension);
        }else {
            return FileUtil.writeToDisk(is,downloadDir,name);
        }

    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS!=null){
            SUCCESS.onSuccess(file.getPath());
        }

        if (REQUEST!=null){
            REQUEST.onRequestStart();
        }

        autoInstallApk(file);

    }

    private void autoInstallApk(File file){
        if (FileUtil.getExtension(file.getPath()).equals("apk")){
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Latte.getApplicationContext().startActivity(install);
        }
    }
}
