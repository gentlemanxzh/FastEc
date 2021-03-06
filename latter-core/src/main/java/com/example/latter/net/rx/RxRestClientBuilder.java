package com.example.latter.net.rx;

import android.content.Context;

import com.example.latter.net.RestCreator;
import com.example.latter.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author gentleman
 * @date 2018/5/14
 * @function 建造者模式传参数
 */

public class RxRestClientBuilder {

    private final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private String mUrl = null;
    private RequestBody mBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;

    RxRestClientBuilder() {

    }


    public final RxRestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }


    public final RxRestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }


    public final RxRestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }


    public final RxRestClientBuilder loader(Context context, LoaderStyle loaderStyle) {
        mContext = context;
        mLoaderStyle = loaderStyle;
        return this;
    }

    public final RxRestClientBuilder file(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RxRestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RxRestClientBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }


    public final RxRestClientBuilder loader(Context context) {
        mContext = context;
        mLoaderStyle = LoaderStyle.BallClipRotateMultipleIndicator;
        return this;
    }

    public final RxRestClient build() {
        return new RxRestClient(mUrl, PARAMS, mBody,mFile, mLoaderStyle, mContext);
    }


}
