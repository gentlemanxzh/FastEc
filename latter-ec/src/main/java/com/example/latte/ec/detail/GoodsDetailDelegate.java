package com.example.latte.ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.latte.ec.R;
import com.example.latter.delegates.LatteDelegate;

/**
 * @author gentleman
 * @date 2018/6/14
 * @function 商品详细信息
 */

public class GoodsDetailDelegate extends LatteDelegate {

    public static GoodsDetailDelegate create(){
        return new GoodsDetailDelegate();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
