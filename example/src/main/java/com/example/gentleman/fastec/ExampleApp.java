package com.example.gentleman.fastec;

import android.app.Application;

import com.example.latte.ec.database.DatabaseManager;
import com.example.latte.ec.icon.FontEcModule;
import com.example.latter.app.ConfigType;
import com.example.latter.app.Latte;
import com.example.latter.net.interceptors.DebugInterceptor;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * @author gentleman
 * @date 2018/5/12
 */

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.addLogAdapter(new AndroidLogAdapter());

        Latte.init(this)
                .withApiHost("http://127.0.0.1/")
                .withIcon(new FontAwesomeModule())
                .withWeChatAppId("")
                .withWeChatAppSecret("")
                .withIcon(new FontEcModule())
//                .withInterceptor(new DebugInterceptor("index",R.raw.test))
                .configure();

        initStetho();
        DatabaseManager.getInstance().init(this);

    }

    private void initStetho(){
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        );
    }



}
