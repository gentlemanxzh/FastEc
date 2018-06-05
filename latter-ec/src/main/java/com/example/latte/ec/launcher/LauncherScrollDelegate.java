package com.example.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.latte.ec.R;
import com.example.latter.app.AccountManager;
import com.example.latter.app.IUserChecker;
import com.example.latter.delegates.LatteDelegate;
import com.example.latter.ui.launcher.ILauncherListener;
import com.example.latter.ui.launcher.LauncherHolderCreator;
import com.example.latter.ui.launcher.OnLauncherFinishTag;
import com.example.latter.ui.launcher.ScrollLauncherTag;
import com.example.latter.util.storage.LatterPreference;

import java.util.ArrayList;

/**
 * @author gentleman
 * @date 2018/5/27
 */

public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener {

    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();
    private ConvenientBanner<Integer> mConvenientBanner = null;
    private ILauncherListener mILauncherListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }
    }


    private void initBanner() {
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);

        mConvenientBanner
                .setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);

    }

    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<Integer>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initBanner();
    }

    @Override
    public void onItemClick(int position) {
        //如果是最后一个
        if (position == INTEGERS.size() - 1) {
            LatterPreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            //检查用户是否登录
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSign() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        }

    }
}
