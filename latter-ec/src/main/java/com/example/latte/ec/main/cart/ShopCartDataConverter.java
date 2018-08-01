package com.example.latte.ec.main.cart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.latter.ui.recycler.DataConverter;
import com.example.latter.ui.recycler.MultipleFields;
import com.example.latter.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by Gentleman on 2018/7/8.
 */

public class ShopCartDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");

        final int size = dataArray.size();
        for (int i = 0;i<size;i++){
            final JSONObject data = dataArray.getJSONObject(i);
            final String thumb = data.getString("thumb");
            final String desc = data.getString("desc");
            final String title = data.getString("title");
            final int id = data.getInteger("id");
            final int count = data.getInteger("count");
            final double price = data.getDouble("price");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE,ShortCardItemType.SHOW_CART_ITEM)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.IMAGE_URL,thumb)
                    .setField(ShortCartItemFields.DESC,desc)
                    .setField(ShortCartItemFields.TITLE,title)
                    .setField(ShortCartItemFields.COUNT,count)
                    .setField(ShortCartItemFields.PRICE,price)
                    .build();

            dataList.add(entity);
        }

        return dataList;
    }
}
