package com.example.latter.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Gentleman on 2018/6/10.
 * 使用静态工厂的方式来初始化
 */

public class MultipleViewHolder extends BaseViewHolder {

    private MultipleViewHolder(View view) {
        super(view);
    }

    public static MultipleViewHolder create(View view){
        return new MultipleViewHolder(view);
    }
}
