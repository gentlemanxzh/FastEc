package com.example.latter.wechat;

import android.app.Activity;

import com.example.latter.app.ConfigType;
import com.example.latter.app.Latte;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author gentleman
 * @date 2018/6/7
 */

public class LatteWeChat {
    static final String APP_ID = Latte.getConfiguration(ConfigType.WE_CHAT_APP_ID);
    static final String APP_SECRET = Latte.getConfiguration(ConfigType.WE_CHAT_APP_SECRET);
    private final IWXAPI WXAPI;

    private static final class Holder{
        private static final LatteWeChat INSTANCE = new LatteWeChat();
    }

    public static LatteWeChat getInstance(){
      return   Holder.INSTANCE;
    }

    private LatteWeChat(){
        final Activity activity = Latte.getConfiguration(ConfigType.WE_CHAT_APP_SECRET);
        WXAPI = WXAPIFactory.createWXAPI(activity,APP_ID,true);
        WXAPI.registerApp(APP_ID);
    }

    public final IWXAPI getWXAPI(){
        return WXAPI;
    }

    public final void signIn(){
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        WXAPI.sendReq(req);

    }



}
