package com.example.latter.app;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;

/**
 * @author gentleman
 * @date 2018/5/12
 * @function application调用这里的方法
 */

public final class Latte {

    public static Configurator init(Context context){
        getConfigurators().put(ConfigType.APPLICATION_CONTEXT,context.getApplicationContext());
        return Configurator.getInstance();
    }

    /**
     * 获取配置信息的HashMap集合
     */
    public static HashMap<Object,Object> getConfigurators(){
        return Configurator.getInstance().getLatteConfigs();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    /**
     * 获取配置信息中的getApplicationContext
     */
    public static Context getApplicationContext(){
        return Configurator.getInstance().getConfiguration(ConfigType.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigType.HANDLER);
    }
}
