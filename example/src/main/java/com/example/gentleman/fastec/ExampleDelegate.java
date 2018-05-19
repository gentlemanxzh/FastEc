package com.example.gentleman.fastec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.latter.delegates.LatteDelegate;
import com.example.latter.net.RestClient;
import com.example.latter.net.callback.IFailure;
import com.example.latter.net.callback.ISuccess;

/**
 * @author gentleman
 * @date 2018/5/13
 */

public class ExampleDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        RestClient.builder()
                .url("https://www.baidu.com/")
                .loader(getContext())
                .onSuccess(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    }
                })
                .onFailure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .build()
                .get();
    }
}
