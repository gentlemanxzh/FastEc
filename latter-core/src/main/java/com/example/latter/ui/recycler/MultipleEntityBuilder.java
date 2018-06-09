package com.example.latter.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

/**
 * Created by Gentleman on 2018/6/9.
 */

public class MultipleEntityBuilder {

    private static final LinkedHashMap<Object,Object> FIELDS = new LinkedHashMap<>();

    public MultipleEntityBuilder() {

        //每次插入数据时要先清空数据
        FIELDS.clear();

    }

    public final MultipleEntityBuilder setItemType(int itemType){
        FIELDS.put(MultipleFields.ITEM_TYPE,itemType);
        return this;
    }

    public final MultipleEntityBuilder setField(Object key,Object value){
        FIELDS.put(key,value);
        return this;
    }

    public final MultipleEntityBuilder setFields(LinkedHashMap<?,?> map){
        FIELDS.putAll(map);
        return this;
    }

    public MultiItemEntity build(){
       return new MultipleItemEntity(FIELDS);
    }

}
