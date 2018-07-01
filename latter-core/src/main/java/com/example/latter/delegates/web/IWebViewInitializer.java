package com.example.latter.delegates.web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Gentleman on 2018/6/23.
 * @function WebView初始化的接口
 */

public interface IWebViewInitializer {

    /**
     * 初始化WebView
     */
    WebView initWebView(WebView webView);

    /**
     * 初始化WebView的WebClient
     */
    WebViewClient initWebViewClient();


    /**
     * 初始化WebView的WebViewChrome
     */
    WebChromeClient initWebChromeClient();
}
