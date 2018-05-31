package com.example.latter.ui.launcher;

import android.view.View;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;

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
