package com.example.gentleman.fastec;

import android.app.Application;
import android.support.annotation.Nullable;

import com.example.gentleman.fastec.event.ShareEvent;
import com.example.latte.ec.database.DatabaseManager;
import com.example.latte.ec.icon.FontEcModule;
import com.example.latter.app.Latte;
import com.example.gentleman.fastec.event.TestEvent;
import com.example.latter.net.rx.AddCookieInterceptor;
import com.example.latter.util.callback.CallbackManager;
import com.example.latter.util.callback.CallbackType;
import com.example.latter.util.callback.IGlobalCallback;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import cn.jpush.android.api.JPushInterface;

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
                .withWebEvent("share",new ShareEvent())
                //添加cookie同步拦截器
                .withInterceptor(new AddCookieInterceptor())
                .withWebHost("https://www.baidu.com/")
                .withIcon(new FontEcModule())
//                .withInterceptor(new DebugInterceptor("index",R.raw.test))
                .configure();

        initStetho();
        DatabaseManager.getInstance().init(this);

        //开启极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        CallbackManager.getInstance()
                .addCallback(CallbackType.TAG_OPEN_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        if (JPushInterface.isPushStopped(Latte.getApplicationContext())) {
                            //开启极光推送
                           JPushInterface.resumePush(Latte.getApplicationContext());
                        }
                    }
                })
                .addCallback(CallbackType.TAG_STOP_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        if (!JPushInterface.isPushStopped(Latte.getApplicationContext())) {
                            JPushInterface.stopPush(Latte.getApplicationContext());
                        }
                    }
                });

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
