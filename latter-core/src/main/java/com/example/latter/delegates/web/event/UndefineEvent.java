package com.example.latter.delegates.web.event;

import com.example.latter.util.log.LatteLogger;

/**
 * Created by Gentleman on 2018/6/24.
 */

public class UndefineEvent extends Event {
    @Override
    public String execute(String params) {
        LatteLogger.e("UndefineEvent",params);
        return null;
    }
}
