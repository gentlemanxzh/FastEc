package com.example.latte.ec.pay;

import android.app.Activity;
import android.os.AsyncTask;

import com.alipay.sdk.app.PayTask;
import com.example.latter.ui.loader.LatteLoader;
import com.example.latter.util.log.LatteLogger;

/**
 * @author gentleman
 * @date 2018/8/7
 */

public class PayAsyncTask extends AsyncTask<String, Void, String> {

    private final Activity ACTIVITY;
    private final IAlPayResultListener LISTENER;

    //订单支付成功
    private static final String AL_PAY_STATUS_SUCCESS = "9000";
    //订单处理中
    private static final String AL_PAY_STATUS_PAYING = "8000";
    //订单失败
    private static final String AL_PAY_STATUS_FAIL = "4000";
    //用户取消订单
    private static final String AL_PAY_STATUS_CANCEL = "6001";
    //网络错误
    private static final String AL_PAY_STATUS_CONNECT_ERROR = "6002";


    public PayAsyncTask(Activity activity, IAlPayResultListener listener) {
        this.ACTIVITY = activity;
        this.LISTENER = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        final String alPaySign = strings[0];
        final PayTask payTask = new PayTask(ACTIVITY);
        return payTask.pay(alPaySign, true);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        LatteLoader.stopLoading();
        final PayResult payResult = new PayResult(result);
        //支付宝返回此次支付结果及加签，建议对支付宝签名信息签约时支付宝提供的公钥做验签
        final String resultInfo = payResult.getResult();
        final String resultStatus = payResult.getResultStatus();
        LatteLogger.d("AL_PAT_RESULT", resultInfo);
        LatteLogger.d("AL_PAT_RESULT", resultStatus);

        switch (resultStatus) {
            case AL_PAY_STATUS_SUCCESS:
                if (LISTENER!=null){
                    LISTENER.onPaySuccess();
                }
                break;

            case AL_PAY_STATUS_PAYING:
                if (LISTENER!=null){
                    LISTENER.onPaying();
                }
                break;

            case AL_PAY_STATUS_FAIL:
                if (LISTENER!=null){
                    LISTENER.onPayFail();
                }
                break;

            case AL_PAY_STATUS_CANCEL:
                if (LISTENER!=null){
                    LISTENER.onPayCannel();
                }
                break;

            case AL_PAY_STATUS_CONNECT_ERROR:
                if (LISTENER!=null){
                    LISTENER.onPayConnectError();
                }
                break;

            default:
                break;
        }

    }
}
