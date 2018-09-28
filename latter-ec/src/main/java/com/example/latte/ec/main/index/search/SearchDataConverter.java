package com.example.latte.ec.main.index.search;

import com.alibaba.fastjson.JSONArray;
import com.example.latter.util.storage.LatterPreference;
import com.example.ui.recycler.DataConverter;
import com.example.ui.recycler.MultipleFields;
import com.example.ui.recycler.MultipleItemEntity;


import java.util.ArrayList;

/**
 * Created by on 2018-09-28 下午 5:19.
 *
 * @author gentleman
 */

public class SearchDataConverter extends DataConverter {

    public static final String TAG_SEARCH_HISTORY = "search_history";

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final String jsonStr = LatterPreference.getCustomAppProfile(TAG_SEARCH_HISTORY);
        if (!jsonStr.equals("")){
            final JSONArray array = JSONArray.parseArray(jsonStr);
            final int size = array.size();
            for (int i = 0;i<size;i++){
                final String historyItemText = array.getString(i);
                final MultipleItemEntity entity =  MultipleItemEntity.builder()
                        .setItemType(SearchItemType.ITEM_SEARCH)
                        .setField(MultipleFields.TEXT,historyItemText)
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
