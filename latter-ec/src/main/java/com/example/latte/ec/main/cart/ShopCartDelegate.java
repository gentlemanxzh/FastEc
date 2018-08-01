package com.example.latte.ec.main.cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.latte.ec.R;
import com.example.latte.ec.R2;
import com.example.latter.delegates.bottom.BottomItemDelegate;
import com.example.latter.net.rx.RxRestClient;
import com.example.latter.ui.recycler.MultipleItemEntity;
import com.example.latter.util.log.LatteLogger;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Gentleman on 2018/7/8.
 */

public class ShopCartDelegate extends BottomItemDelegate{

    private ShopCartAdapter mAdapter;

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView;


    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RxRestClient.builder()
                .url("http://oxjde2kpq.bkt.clouddn.com/shop_cart_data.json")
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        LatteLogger.d(s);
                        final ArrayList<MultipleItemEntity> dataList = new ShopCartDataConverter().setJsonData(s).convert();
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(manager);
                        mAdapter = new ShopCartAdapter(dataList);
                        mRecyclerView.setAdapter(mAdapter);


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
