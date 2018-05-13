package com.example.latter.app;

import android.content.Context;

import java.util.HashMap;

/**
 * @author gentleman
 * @date 2018/5/12
 */

public final class Latte {

    public static Configurator init(Context context){
        getConfigurators().put(ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }

    private static HashMap<String,Object> getConfigurators(){
        return Configurator.getInstance().getLatteConfigs();
    }

    public static Context getApplicationContext(){
        return (Context)getConfigurators().get(ConfigType.APPLICATION_CONTEXT.name());
    }
}
