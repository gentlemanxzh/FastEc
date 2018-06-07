package com.example.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;

import com.example.latte.ec.R;
import com.example.latte.ec.R2;
import com.example.latter.delegates.LatteDelegate;
import com.example.latter.net.rx.RxRestClient;
import com.example.latter.util.log.LatteLogger;
import com.example.latter.wechat.LatteWeChat;
import com.example.latter.wechat.callbacks.IWeChatSignInCallback;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author gentleman
 * @date 2018/5/31
 * @function 登录界面
 */

public class SignInDelegate extends LatteDelegate {

    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmail = null;

    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;

    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }

    }

    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn(){
        if (checkForm()){

            RxRestClient.builder()
                    .url("http://oxjde2kpq.bkt.clouddn.com/user_profile.json")
                    .params("email", mEmail.getText().toString())
                    .params("password", mPassword.getText().toString())
                    .build()
                    .get()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String response) {
                            LatteLogger.d("TAG", response);
                            SignHandle.onSignIn(response, mISignListener);
                        }

                        @Override
                        public void onError(Throwable e) {
                            LatteLogger.e("TAG", e.toString());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeChat(){
        LatteWeChat.getInstance().onSignSuccess(new IWeChatSignInCallback() {
            @Override
            public void onSignInSuccess(String userInfo) {

            }
        }).signIn();
    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink(){
        start(new SignUpDelegate());
    }

    private boolean checkForm(){
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        }else {
            mEmail.setError(null);
        }

        if (password.isEmpty()||password.length()<6){
            mPassword.setError("请至少填入6位密码");
            isPass = false;
        }else {
            mPassword.setError(null);
        }

        return isPass;

    }



    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
