package com.example.latter.ui.recycler;

import android.support.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;
import com.example.latter.ui.banner.BannerCreator;

/**
 * @author gentleman
 * @date 2018/6/11
 */

public class BaseDecoration extends DividerItemDecoration {

    private BaseDecoration(@ColorInt int color, int size) {
        setDividerLookup(new DividerLookupImpl(color,size));
    }

    public static BaseDecoration create(@ColorInt int color, int size){
        return new BaseDecoration(color,size);
    }
}
