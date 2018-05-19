package com.example.latter.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.latter.app.Latte;

/**
 * @author gentleman
 * @date 2018/5/15
 * @function 计算屏幕宽高的工具类
 */

public class DimenUtil {

    public static int getScreenWidth(){
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(){
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

}
