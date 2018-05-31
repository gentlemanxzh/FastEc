package com.example.latter.util.time;

import java.util.TimerTask;

/**
 * @author gentleman
 * @date 2018/5/26
 */

public class BaseTimerTask extends TimerTask {

    private ITimeListener mITimeListener = null;

    public BaseTimerTask(ITimeListener mITimeListener) {
        this.mITimeListener = mITimeListener;
    }

    @Override
    public void run() {
        if (mITimeListener != null) {
            mITimeListener.onTimer();
        }
    }
}
