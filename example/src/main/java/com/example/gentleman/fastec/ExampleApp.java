package com.example.gentleman.fastec;

import android.app.Application;

import com.example.latte.ec.icon.FontEcModule;
import com.example.latter.app.ConfigType;
import com.example.latter.app.Latte;
import com.example.latter.net.interceptors.DebugInterceptor;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * @author gentleman
 * @date 2018/5/12
 */

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withApiHost("http://127.0.0.1/")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withInterceptor(new DebugInterceptor("index",R.raw.test))
                .configure();

    }


}
