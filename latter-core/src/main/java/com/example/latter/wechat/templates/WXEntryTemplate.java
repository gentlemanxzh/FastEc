package com.example.latter.wechat.templates;

import com.example.latter.activity.ProxyActivity;
import com.example.latter.delegates.LatteDelegate;
import com.example.latter.wechat.BaseWXEntryActivity;
import com.example.latter.wechat.LatteWeChat;

/**
 * @author gentleman
 * @date 2018/6/6
 */

public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0,0);

    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        LatteWeChat.getInstance().getmSignInCallback().onSignInSuccess(userInfo);
    }
}
