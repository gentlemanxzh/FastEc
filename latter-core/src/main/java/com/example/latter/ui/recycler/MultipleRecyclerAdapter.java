package com.example.latter.ui.recycler;

import android.support.v7.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by Gentleman on 2018/6/10.
 */

public class MultipleRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity,MultipleViewHolder>
implements BaseQuickAdapter.SpanSizeLookup{


    protected MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
    }

    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data){
        return new MultipleRecyclerAdapter(data);
    }


    public static MultipleRecyclerAdapter create(DataConverter converter){
        return new MultipleRecyclerAdapter(converter.convert());
    }


    private void init(){
        //设置不同的item布局
//        addItemType();
    }


    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {

    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return 0;
    }
}
