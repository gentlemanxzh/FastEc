package com.example.latte.ec.main.index;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.example.latte.ec.R;
import com.example.latter.app.Latte;
import com.example.latter.ui.recycler.RgbValue;

/**
 * @author gentleman
 * @date 2018/6/13
 */

@SuppressWarnings("unused")
public class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar>{

    //顶部距离
    private int mOffset = 0;
    //延长滑动过程
    private static final int MORE =100;
    //定义颜色
    private final RgbValue RGB_VALUE = RgbValue.create(255,124,2);




    //一定要有两个参数的构造方法
    public TranslucentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        //选择依赖的View
        return dependency.getId() == R.id.rv_index;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        //接管他的事件
        return true;
    }

//    @Override
//    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
//         //增加滑动距离
//        mDistanceY+=dy;
//        //toolbar的高度,toolbar是依赖对象，RecyclerView是被依赖的对象
//        final int targetHeight = child.getBottom();
//
//        //滑动时，并且距离小于toolbar高度的时候，调整渐变色
//        if (mDistanceY>0&&mDistanceY<=targetHeight){
//            final float scale =(float) mDistanceY/targetHeight;
//            final float alpha = scale*255;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                child.setBackgroundColor(Color.argb(alpha,RGB_VALUE.red(),RGB_VALUE.green(),RGB_VALUE.blue()));
//            }
//        }else if (mDistanceY>targetHeight){
//            child.setBackgroundColor(Color.rgb(RGB_VALUE.red(),RGB_VALUE.green(),RGB_VALUE.blue()));
//        }
//    }

    @Override
    public void onNestedScroll(
            @NonNull CoordinatorLayout coordinatorLayout
            , @NonNull Toolbar toolbar
            , @NonNull View target
            , int dxConsumed
            , int dyConsumed
            , int dxUnconsumed
            , int dyUnconsumed
            , int type) {

        final int startOffset = 0;
        final Context context = Latte.getApplicationContext();
        final int endOffset = context.getResources().getDimensionPixelOffset(R.dimen.header_height) + MORE;
        mOffset += dyConsumed;
        if (mOffset <= startOffset) {
            toolbar.getBackground().setAlpha(0);
        } else if (mOffset > startOffset && mOffset < endOffset) {
            final float percent = (float) (mOffset - startOffset) / endOffset;
            final int alpha = Math.round(percent * 255);
            toolbar.getBackground().setAlpha(alpha);
        } else if (mOffset >= endOffset) {
            toolbar.getBackground().setAlpha(255);
        }
    }

}
