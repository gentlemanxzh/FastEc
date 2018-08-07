package com.example.latte.ec.pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.example.latte.ec.R;
import com.example.latter.app.Latte;
import com.example.latter.delegates.LatteDelegate;
import com.example.latter.net.rx.RxRestClient;
import com.example.latter.util.log.LatteLogger;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author gentleman
 * @date 2018/8/6
 */

public class FastPay implements View.OnClickListener {
    //设置回调监听
    private IAlPayResultListener mIAlPayResultListener = null;
    private Activity mActivity = null;

    private AlertDialog mDialog = null;
    private int mOrderID = -1;


    private FastPay(LatteDelegate delegate){
        this.mActivity = delegate.getProxyActivity();
        this.mDialog = new AlertDialog.Builder(delegate.getContext()).create();
    }

    public static FastPay create(LatteDelegate delegate){
        return new FastPay(delegate);
    }

    public  void beginPayDialog(){
        mDialog.show();
        final Window window = mDialog.getWindow();
        if (window!=null){
            window.setContentView(R.layout.dialog_pay_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);

            window.findViewById(R.id.btn_doalog_pay_alpay).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_wechat).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_cancel).setOnClickListener(this);
        }
    }

    public FastPay setPayResultListener(IAlPayResultListener listener){
        this.mIAlPayResultListener = listener;
        return this;
    }

    public FastPay setOrderId(int  orderId){
        this.mOrderID = orderId;
        return this;
    }

    public final void alPay(int orderId){
        final String singUrl = "";
        //获取签名字符串
        RxRestClient.builder()
                .url(singUrl)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String response) {
                        final String paySign = JSON.parseObject(response).getString("result");
                        LatteLogger.d("PAY_SIGN",paySign);
                        //调用支付宝的支付接口最好是异步调用
                        final PayAsyncTask payAsyncTask = new PayAsyncTask(mActivity,mIAlPayResultListener);
                        payAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,paySign);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

//    library中不可以使用switch，因为library中id不是常量
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id==R.id.btn_dialog_pay_wechat){
            alPay(mOrderID);
            mDialog.cancel();
        }else if (id==R.id.btn_doalog_pay_alpay){
            mDialog.cancel();
        }else if (id==R.id.btn_dialog_pay_cancel){
            mDialog.cancel();
        }
    }
}
