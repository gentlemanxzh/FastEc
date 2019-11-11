package com.example.latter.util.callback;

import android.support.annotation.Nullable;

/**
 * Created by Gentleman on 2018/9/16.
 * 跨moduel接口回調
 */

public interface IGlobalCallback<T> {

    void executeCallback(@Nullable T args);

}
