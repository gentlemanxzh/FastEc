package com.example.gentleman.fastec;

import com.example.latter.activity.ProxyActivity;
import com.example.latter.app.Latte;
import com.example.latter.delegates.LatteDelegate;
import com.example.latter.net.HttpMethod;
import com.example.latter.net.RestClient;

import com.example.latter.net.callback.IFailure;
import com.example.latter.net.callback.ISuccess;

public class ExampleActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }

}
