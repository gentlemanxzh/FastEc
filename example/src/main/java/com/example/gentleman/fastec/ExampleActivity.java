package com.example.gentleman.fastec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.example.latte.ec.launcher.LauncherDelegate;
import com.example.latte.ec.launcher.LauncherScrollDelegate;
import com.example.latte.ec.sign.ISignListener;
import com.example.latte.ec.sign.SignInDelegate;
import com.example.latte.ec.sign.SignUpDelegate;
import com.example.latter.activity.ProxyActivity;
import com.example.latter.app.Latte;
import com.example.latter.delegates.LatteDelegate;

public class ExampleActivity extends ProxyActivity implements ISignListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }

    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new SignUpDelegate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        
    }

    @Override
    public void onSignInSuccess() {
        
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
    }
}
