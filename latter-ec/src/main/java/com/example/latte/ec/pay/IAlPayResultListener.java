package com.example.latte.ec.pay;

/**
 * @author gentleman
 * @date 2018/8/6
 */

public interface IAlPayResultListener {
    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCannel();

    void onPayConnectError();


}
