package com.example.latter.delegates.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

import com.example.latter.app.ConfigType;
import com.example.latter.app.Latte;
import com.example.latter.delegates.LatteDelegate;
import com.example.latter.delegates.web.route.RouteKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by Gentleman on 2018/6/23.
 * @function 发现页面的WebFragment
 */

public abstract class WebDelegate extends LatteDelegate implements IWebViewInitializer {

    private WebView mWebView = null;
    //引用队列
    private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
    private String mUrl = null;
    //WebView初始化变量
    private boolean mIsWebViewAvailable = false;
    private LatteDelegate mTopDelegate = null;

    public WebDelegate() {

    }

    /**
     * 进行WebView的初始化
     */
    public abstract IWebViewInitializer setInitializer();

    public abstract  IUrlHandler setUrlHandler();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取传进来的URL
        Bundle args = getArguments();
        mUrl = args.getString(RouteKeys.URL.name());
        initWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final IUrlHandler handler = setUrlHandler();
        if (handler != null) {
            handler.handleUrl(this);
        }
    }


    /**
     * 初始化WebView
     */
    @SuppressLint("JavascriptInterface")
    private void initWebView() {

        if (mWebView != null) {
            //如果WebView不为null的话就清除所有的View
            mWebView.removeAllViews();
            mWebView.destroy();
        } else {
            final IWebViewInitializer initializer = setInitializer();
            if (initializer != null) {
                //弱引用
                final WeakReference<WebView> webViewWeakReference = new WeakReference<>(new WebView(getContext()), WEB_VIEW_QUEUE);
                mWebView = webViewWeakReference.get();
                mWebView = initializer.initWebView(mWebView);
                mWebView.setWebChromeClient(initializer.initWebChromeClient());
                mWebView.setWebViewClient(initializer.initWebViewClient());
                //webView与JavaScript进行交互
                final String name = (String) Latte.getConfiguration(ConfigType.JAVASCRIPT_INTERFACE);
                mWebView.addJavascriptInterface(LatteWebInterface.create(this), name);
                //Web已经初始化完成
                mIsWebViewAvailable = true;
            } else {
                throw new NullPointerException("Initializer is null");
            }
        }
    }

    public void setTopDelegate(LatteDelegate delegate){
        mTopDelegate = delegate;
    }

    public LatteDelegate getTopDelegate(){
        if (mTopDelegate==null){
            mTopDelegate = this;
        }
        return mTopDelegate;
    }

    public WebView getWebView() {
        if (mWebView == null) {
            throw new NullPointerException("WebView is null");
        }
        return mIsWebViewAvailable ? mWebView : null;
    }

    public String getUrl(){
        if (mUrl==null){
            throw new NullPointerException("URL is null");
        }

        return mUrl;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebViewAvailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }
}
