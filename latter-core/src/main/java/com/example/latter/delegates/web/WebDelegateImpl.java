package com.example.latter.delegates.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.latter.delegates.web.chromeclient.WebChromeClientImpl;
import com.example.latter.delegates.web.client.WebViewClientImpl;
import com.example.latter.delegates.web.route.RouteKeys;
import com.example.latter.delegates.web.route.Router;
import com.example.latter.delegates.web.route.WebViewInitializer;

/**
 * Created by Gentleman on 2018/6/23.
 * @function WebView的具体实现
 */

public class WebDelegateImpl extends WebDelegate {
    private IPageLoadListener mIPageLoadListener = null;

    /**
     * 工厂方法创建WebView
     * @param url URL地址
     */
    public static WebDelegateImpl create(String url){
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(),url);
        final WebDelegateImpl delegate = new WebDelegateImpl();
        delegate.setArguments(args);
        return delegate;
    }

    public void setPageLoadListener(IPageLoadListener listener){
        this.mIPageLoadListener = listener;
    }


    /**
     * 获取WebView
     */
    @Override
    public Object setLayout() {
        //相当于直接在setContentView中直接传入一个WebView
        return getWebView();
    }



    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        if (getUrl()!=null){
            //用原生的方式模拟Web跳转,并进行页面加载
            Router.getInstance().loadPage(this,getUrl());

        }

    }

    @Override
    public IWebViewInitializer setInitializer() {
        return this;
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebViewClientImpl client = new WebViewClientImpl(this);
        client.setPageLoadListener(mIPageLoadListener);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }


}
