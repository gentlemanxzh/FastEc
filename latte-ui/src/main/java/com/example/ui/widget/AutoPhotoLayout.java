package com.example.ui.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.latter.delegates.LatteDelegate;
import com.example.ui.R;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by on 2018-09-25 下午 3:39.
 *
 * @author gentleman
 */

public class AutoPhotoLayout extends LinearLayout{

    private int mCurrentNum =  0;
    private int mMaxNum = 0;
    private int mMaxLineNum = 0;
    private IconTextView mIconAdd = null;
    private LayoutParams mParams = null;
    //要删除的图片ID
    private int mDeleteId = 0;
    private AppCompatImageView mTargetImageView = null;
    //图片之间的空隙
    private int mImageMargin = 0;
    private LatteDelegate mDelegate = null;
    private List<View> mLineViews = null;
    private AlertDialog mTargetDialog = null;
    private static final String ICON_TEXT = "{fa-plus}";
    private float mIconSize = 0;

    //每一行代表一个List<View>
    private static final List<List<View>> ALL_Views = new ArrayList<>();
    private static final List<Integer> LINE_HEIGHTS = new ArrayList<>();

    public AutoPhotoLayout(Context context) {
        this(context,null);
    }

    public AutoPhotoLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AutoPhotoLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.camera_flow_layout);
        mMaxNum = typedArray.getInt(R.styleable.camera_flow_layout_max_count,1);
        mMaxLineNum = typedArray.getInt(R.styleable.camera_flow_layout_line_count,3);
        mImageMargin = typedArray.getInt(R.styleable.camera_flow_layout_item_margin,0);
        mIconSize = typedArray.getInt(R.styleable.camera_flow_layout_icon_size,20);
        typedArray.recycle();
    }



}
