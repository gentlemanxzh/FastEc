package com.example.ui.launcher;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * @author gentleman
 * @date 2018/5/27
 */

public class LauncherHolderCreator implements CBViewHolderCreator {

    @Override
    public Object createHolder() {
        return new LauncherHolder();
    }
}
