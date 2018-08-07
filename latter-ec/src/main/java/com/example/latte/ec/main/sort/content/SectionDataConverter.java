package com.example.latte.ec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gentleman on 2018/6/22.
 */

public class SectionDataConverter {

    final List<SectionBean> convert(String json){

        final List<SectionBean> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(json).getJSONArray("data");
        final int size = dataArray.size();

        for (int i= 0;i<size;i++){
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String title = data.getString("section");

            //添加title
            final SectionBean sectionTitleBean = new SectionBean(true,title);
            sectionTitleBean.setId(id);
            sectionTitleBean.setIsMore(true);
            dataList.add(sectionTitleBean);


            final JSONArray goods = data.getJSONArray("goods");
            //商品内容循环
            int goodSize = goods.size();
            for (int j = 0;j<goodSize;j++){
                final JSONObject contentItem = goods.getJSONObject(j);
                final int goodsId = contentItem.getInteger("goods_id");
                final String goodsName = contentItem.getString("goods_name");
                final String goodsThumb = contentItem.getString("goods_thumb");

                final SectionContentItemEntity itemEntity = new SectionContentItemEntity();
                itemEntity.setGoodId(goodsId);
                itemEntity.setGoodsThumb(goodsThumb);
                itemEntity.setName(goodsName);

                //添加内容
                dataList.add(new SectionBean(itemEntity));

            }


        }

        return dataList;
    }

}
