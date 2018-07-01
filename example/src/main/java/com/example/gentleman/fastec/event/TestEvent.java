package com.example.gentleman.fastec.event;

import android.webkit.WebView;
import android.widget.Toast;

import com.example.latter.delegates.web.event.Event;

/**
 * Created by Gentleman on 2018/6/25.
 */

public class TestEvent extends Event {
    @Override
    public String execute(String params) {
        Toast.makeText(getContext(),params,Toast.LENGTH_SHORT).show();
        if(getAction().equals("test")){
            final WebView webView = getWebView();
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.evaluateJavascript("nativeCall();",null);
                }
            });
        }
        return null;
    }
}
