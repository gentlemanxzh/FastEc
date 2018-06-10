package com.example.latter.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * Created by Gentleman on 2018/6/9.
 * 参数实体类，每一个参数就是一个实体类
 */

public class MultipleItemEntity implements MultiItemEntity {

    //引用队列
    private final ReferenceQueue<LinkedHashMap<Object,Object>> ITEM_QUEUE = new ReferenceQueue<>();
    private final LinkedHashMap<Object,Object> MULTIPLE_FIELDS = new LinkedHashMap<>();
    //软引用
    private final SoftReference<LinkedHashMap<Object,Object>> FIELDS_REFERENCE =
            new SoftReference<LinkedHashMap<Object, Object>>(MULTIPLE_FIELDS,ITEM_QUEUE);


    MultipleItemEntity(LinkedHashMap<Object,Object> fields) {
        FIELDS_REFERENCE.get().putAll(fields);
    }


    public static MultipleEntityBuilder builder(){
        return new MultipleEntityBuilder();
    }

    /**
     * 返回Item的类型
     */
    @Override
    public int getItemType() {
        return (int) FIELDS_REFERENCE.get().get(MultipleFields.ITEM_TYPE);
    }





    @SuppressWarnings("unchecked")
    public final <T> T getField(Object key){
        return (T) FIELDS_REFERENCE.get().get(key);
    }

    public final LinkedHashMap<?,?> getFields(){
        return FIELDS_REFERENCE.get();
    }

    public final MultiItemEntity setFields(Object key, Object value){
        FIELDS_REFERENCE.get().put(key,value);
        return this;
    }



}
