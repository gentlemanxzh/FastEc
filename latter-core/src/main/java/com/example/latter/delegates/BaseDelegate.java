package com.example.latter.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * @author gentleman
 * @date 2018/5/13
 * @function 相当于BaseFragment
 */

public abstract class BaseDelegate extends SwipeBackFragment {

    @SuppressWarnings("SpellCheckingInspection")
    private Unbinder mUnbinder;

    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState,View rootView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = null;

       if (setLayout()instanceof Integer){
           rootView = inflater.inflate((Integer) setLayout(),container,false);
       }else if (setLayout()instanceof View){
           rootView = (View) setLayout();
       }

       if (rootView != null){
           //绑定视图
           mUnbinder = ButterKnife.bind(this,rootView);
           //用来绑定视图中的控件
           onBindView(savedInstanceState,rootView);
       }

        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder!=null){
            mUnbinder.unbind();
        }
    }
}
