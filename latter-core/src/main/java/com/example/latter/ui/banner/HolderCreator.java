package com.example.latter.ui.banner;

import android.media.Image;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * @author gentleman
 * @date 2018/6/11
 */

public class HolderCreator implements CBViewHolderCreator<ImageHolder> {
    @Override
    public ImageHolder createHolder() {
        return new ImageHolder();
    }


}
