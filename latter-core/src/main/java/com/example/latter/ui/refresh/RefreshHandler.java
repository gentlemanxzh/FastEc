package com.example.latter.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.latter.app.Latte;
import com.example.latter.net.rx.RxRestClient;
import com.example.latter.ui.recycler.DataConverter;
import com.example.latter.ui.recycler.MultipleRecyclerAdapter;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Gentleman on 2018/6/9.
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;

    private RefreshHandler(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView,
                           DataConverter dataConverter,
                           PagingBean pagingBean) {

        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.BEAN = pagingBean;
        this.CONVERTER = dataConverter;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                        RecyclerView recyclerView, DataConverter dataConverter) {
        return new RefreshHandler(swipeRefreshLayout, recyclerView, dataConverter, new PagingBean());
    }


    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                REFRESH_LAYOUT.setRefreshing(false);

            }
        }, 2000);
    }

    public void firstPage(String url) {
        BEAN.setDelayed(1000);
        RxRestClient.builder()
                .url(url)
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
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));
                                //设置Adapter
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this,RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
