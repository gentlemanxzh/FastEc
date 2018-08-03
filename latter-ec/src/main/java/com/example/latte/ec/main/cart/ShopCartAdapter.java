package com.example.latte.ec.main.cart;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.latte.ec.R;
import com.example.latter.app.Latte;
import com.example.latter.ui.recycler.MultipleFields;
import com.example.latter.ui.recycler.MultipleItemEntity;
import com.example.latter.ui.recycler.MultipleRecyclerAdapter;
import com.example.latter.ui.recycler.MultipleViewHolder;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

/**
 * Created by Gentleman on 2018/7/8.
 */

public class ShopCartAdapter extends MultipleRecyclerAdapter {
    private boolean mIsSelectedAll = false;
    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    protected ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ShortCardItemType.SHOW_CART_ITEM, R.layout.item_shop_cart);
    }

    public void setIsSelectedAll(boolean isSelectedAll){
        this.mIsSelectedAll = isSelectedAll;
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case ShortCardItemType.SHOW_CART_ITEM:
                //取值
                final int id = entity.getField(MultipleFields.ID);
                final String thumb = entity.getField(MultipleFields.IMAGE_URL);
                final String title = entity.getField(ShortCartItemFields.TITLE);
                final String desc = entity.getField(ShortCartItemFields.DESC);
                final int count = entity.getField(ShortCartItemFields.COUNT);
                final double price = entity.getField(ShortCartItemFields.PRICE);

                //找到控件
                final AppCompatImageView imageThumb = holder.getView(R.id.image_item_shop_cart);
                final AppCompatTextView tvTitle = holder.getView(R.id.tv_item_shop_cart_title);
                final AppCompatTextView tvDesc = holder.getView(R.id.tv_item_shop_cart_desc);
                final AppCompatTextView tvPrice = holder.getView(R.id.tv_item_shop_cart_price);
                final AppCompatTextView tvCount = holder.getView(R.id.tv_item_shop_cart_count);
                final IconTextView iconMinus = holder.getView(R.id.icon_item_minus);
                final IconTextView iconPlus = holder.getView(R.id.icon_item_plus);
                final IconTextView iconIsSelected = holder.getView(R.id.icon_item_shop_cart);
                //赋值
                tvTitle.setText(title);
                tvDesc.setText(desc);
                tvPrice.setText(String.valueOf(price));
                tvCount.setText(String.valueOf(count));
                Glide.with(mContext)
                        .load(thumb)
                        .apply(OPTIONS)
                        .into(imageThumb);

                //在左侧勾勾渲染时改变全选与否状态
                entity.setField(ShortCartItemFields.IS_SELECTED,mIsSelectedAll);
                final boolean isSelected = entity.getField(ShortCartItemFields.IS_SELECTED);

                //根据数据状态显示左侧勾勾
                if (isSelected){
                    iconIsSelected.setTextColor
                            (ContextCompat.getColor(Latte.getApplicationContext(),R.color.app_main));
                }else {
                    iconIsSelected.setTextColor(Color.GRAY);
                }
                //添加点击事件
                iconIsSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      final boolean currentSelected = entity.getField(ShortCartItemFields.IS_SELECTED);
                        if (currentSelected){
                            iconIsSelected.setTextColor(Color.GRAY);
                            entity.setField(ShortCartItemFields.IS_SELECTED,false);
                        }else {
                            iconIsSelected.setTextColor
                                    (ContextCompat.getColor(Latte.getApplicationContext(),R.color.app_main));
                            entity.setField(ShortCartItemFields.IS_SELECTED,true);
                        }
                    }
                });


                break;

            default:
                break;
        }
    }
}
