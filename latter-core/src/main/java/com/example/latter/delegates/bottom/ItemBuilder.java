package com.example.latter.delegates.bottom;

import java.util.LinkedHashMap;

/**
 * @author gentleman
 * @date 2018/6/8
 */

public final class ItemBuilder {
    private final LinkedHashMap<BottomTabBean,BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    static ItemBuilder builder(){
        return new ItemBuilder();
    }

    public final ItemBuilder addItem(BottomTabBean bottomTabBean,BottomItemDelegate delegate){
        ITEMS.put(bottomTabBean,delegate);
        return this;
    }
    public final ItemBuilder addItems(LinkedHashMap<BottomTabBean,BottomItemDelegate> items){
        ITEMS.putAll(items);
        return this;
    }

    public final  LinkedHashMap<BottomTabBean,BottomItemDelegate>build(){
       return ITEMS;
    }

}
