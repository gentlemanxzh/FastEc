package com.example.latter.delegates.web.client;

import android.graphics.Bitmap;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.latter.app.ConfigType;
import com.example.latter.app.Latte;
import com.example.latter.delegates.web.IPageLoadListener;
import com.example.latter.delegates.web.WebDelegate;
import com.example.latter.delegates.web.route.Router;
import com.example.latter.ui.loader.LatteLoader;
import com.example.latter.util.log.LatteLogger;
import com.example.latter.util.storage.LatterPreference;

/**
 * Created by Gentleman on 2018/6/23.
 * @function WebViewClient的具体实现
 */

public class WebViewClientImpl extends WebViewClient {

    private final WebDelegate DELEGATE;
    private IPageLoadListener mIPageLoadListener = null;

    public void setPageLoadListener(IPageLoadListener listener){
        this.mIPageLoadListener = listener;
    }

    public WebViewClientImpl(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //进行拦截，然后使用Android原生的方式进行网页跳转
        LatteLogger.d("shouldOverrideUrlLoading",url);
        return Router.getInstance().handleWebUrl(DELEGATE,url);

    }

    /*
     * 获取浏览器cookie
     */
    private void syncCookie(){

        final CookieManager manager = CookieManager.getInstance();
        //注意这里的Cookie和API请求的Cookie是不一样的，这个网页不可见
        final String webHost = Latte.getConfiguration(ConfigType.WEB_HOST);

        if (webHost!=null){
          if (manager.hasCookies()){
              final String cookieStr = manager.getCookie(webHost);
              if (cookieStr!=null&&!cookieStr.equals("")){
                  LatterPreference.addCustomAppProfile("cookie",cookieStr);
              }
          }
        }



    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener!=null){
            mIPageLoadListener.onLoadStart();
        }
        LatteLoader.showLoading(view.getContext());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        syncCookie();
        if (mIPageLoadListener!=null){
            mIPageLoadListener.onLoadEnd();
        }
        LatteLoader.stopLoading();
    }
}
