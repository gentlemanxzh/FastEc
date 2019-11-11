package com.example.latte.ec.main.cart;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.latte.ec.R;
import com.example.latte.ec.R2;
import com.example.latte.ec.pay.FastPay;
import com.example.latte.ec.pay.IAlPayResultListener;
import com.example.latter.delegates.bottom.BottomItemDelegate;
import com.example.latter.net.RestClient;
import com.example.latter.net.rx.RxRestClient;
import com.example.latter.util.log.LatteLogger;
import com.example.ui.recycler.MultipleItemEntity;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Gentleman on 2018/7/8.
 */

public class ShopCartDelegate extends BottomItemDelegate implements ICartItemListener,IAlPayResultListener {

    private ShopCartAdapter mAdapter;
    private int mCurrentCount;//需要删除的Item数量
    private int mTotalCount = 0;//总的Item数量
    private double mTotalPrice = 0.00;

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView;
    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelectAll = null;
    @BindView(R2.id.stub_no_item)
    ViewStubCompat mStubNoItem = null;
    @BindView(R2.id.tv_shop_cart_total_price)
    AppCompatTextView mTvTotalPrice = null;

    @OnClick(R2.id.icon_shop_cart_select_all)
    void onClickSelectAll() {
        final int tag = (int) mIconSelectAll.getTag();
        if (tag == 0) {
            mIconSelectAll.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
            mIconSelectAll.setTag(1);
            mAdapter.setIsSelectedAll(true);
            //更新选中状态
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        } else {
            mIconSelectAll.setTextColor(Color.GRAY);
            mIconSelectAll.setTag(0);
            mAdapter.setIsSelectedAll(false);
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        }
    }

    @OnClick(R2.id.tv_top_shop_cart_remove_selected)
    void onClickRemoveSelectedItem() {
        final List<MultipleItemEntity> data = mAdapter.getData();
        final int size = data.size();
        //要删除的数据
        List<MultipleItemEntity> deleteEntities = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            MultipleItemEntity entity = data.get(i);
            final boolean isSelected = entity.getField(ShortCartItemFields.IS_SELECTED);
            entity.setField(ShortCartItemFields.POSITION, i);
            if (isSelected) {
                deleteEntities.add(entity);
            }
        }

        int deleteSize = deleteEntities.size();
        for (int i = deleteSize - 1; i >= 0; i--) {
            int removePosition = deleteEntities.get(i).getField(ShortCartItemFields.POSITION);
            mAdapter.remove(removePosition);

        }
        checkItemCount();
    }

    @OnClick(R2.id.tv_top_shop_cart_clear)
    void onClickClear() {
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        checkItemCount();
    }

    @OnClick(R2.id.tv_shop_cart_pay)
    void onClickPay(){
       createOrder();
    }

    //创建订单，和支付没有关系
    private void createOrder(){
        final String orderUrl = "";
        final WeakHashMap<String,Object> orderParams = new WeakHashMap<>();
        orderParams.put("userId","");
        orderParams.put("amount",0.01);
        orderParams.put("comment","测试支付");
        orderParams.put("type",1);
        orderParams.put("ordertype",0);
        orderParams.put("isanonymous",true);
        orderParams.put("followeduser",0);
        RxRestClient.builder()
                .url(orderUrl)
                .params(orderParams)
                .loader(getContext())
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        LatteLogger.d("ORDER",s);
                        final int orderId = JSON.parseObject(s).getInteger("result");
                        FastPay.create(ShopCartDelegate.this)
                                .setPayResultListener(ShopCartDelegate.this)
                                .setOrderId(orderId)
                                .beginPayDialog();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @SuppressLint("RestrictedApi")
    private void checkItemCount(){
        final int count = mAdapter.getItemCount();
        if (count==0){
             final View stubView = mStubNoItem.inflate();
             final AppCompatTextView tvToBuy = stubView.findViewById(R.id.tv_stub_to_buy);
             tvToBuy.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Toast.makeText(getContext(), "你该购物啦！", Toast.LENGTH_SHORT).show();
                 }
             });
             mRecyclerView.setVisibility(View.GONE);
        }else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mIconSelectAll.setTag(0);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RxRestClient.builder()
                .url("http://mock.fulingjie.com/mock/data/shop_cart_data.json")
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
                        mAdapter.setCartItemListener(ShopCartDelegate.this);
                        mRecyclerView.setAdapter(mAdapter);
                        mTotalPrice = mAdapter.getTotalPrice();
                        mTvTotalPrice.setText(String.valueOf(mTotalPrice));
                        checkItemCount();
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
    public void onItemClick(double itemTotalPrice) {
        final double price = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(price));

    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCannel() {

    }

    @Override
    public void onPayConnectError() {

    }

}
