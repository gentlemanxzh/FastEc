package com.example.latte.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.latte.ec.R;
import com.example.latter.delegates.LatteDelegate;

/**
 * @author gentleman
 * @date 2018/8/9
 */

public class NameDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_name;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
