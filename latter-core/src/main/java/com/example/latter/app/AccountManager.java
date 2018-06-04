package com.example.latter.app;

import com.example.latter.util.storage.LatterPreference;

/**
 * Created by Gentleman on 2018/6/4.
 */

public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    //保存用户登录状态，登录后调用
    public static void setSignState(boolean state) {
        LatterPreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }


    private static boolean isSignIn() {

        return LatterPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker){
        if (isSignIn()){
            checker.onSign();
        }else {
            checker.onNotSignIn();
        }
    }
}
