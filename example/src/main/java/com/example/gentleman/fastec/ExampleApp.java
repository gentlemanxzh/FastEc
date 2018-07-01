package com.example.gentleman.fastec;

import android.app.Application;

import com.example.latte.ec.database.DatabaseManager;
import com.example.latte.ec.icon.FontEcModule;
import com.example.latter.app.Latte;
import com.example.gentleman.fastec.event.TestEvent;
import com.example.latter.net.rx.AddCookieInterceptor;
import com.facebook.stetho.Stetho;
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
                .withWeChatAppId("")
                .withWeChatAppSecret("")
                .withJavaScriptInterface("latte")
                .withWebEvent("test",new TestEvent())
                //添加cookie同步拦截器
                .withInterceptor(new AddCookieInterceptor())
                .withWebHost("https://www.baidu.com/")
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
