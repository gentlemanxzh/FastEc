package com.example.gentleman.fastec.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.latter.delegates.web.event.Event;
import com.example.latter.util.log.LatteLogger;
import com.example.ui.loader.LatteLoader;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by on 2018-09-27 下午 5:22.
 *
 * @author gentleman
 */

public class ShareEvent extends Event {
    @Override
    public String execute(String params) {

        LatteLogger.d("ShareEvent",params);

        final JSONObject object = JSON.parseObject(params).getJSONObject("params");
        final String title = object.getString("title");
        final String url = object.getString("url");
        final String imageUrl = object.getString("imageUrl");
        final String text = object.getString("text");

        ShareSDK.initSDK(getContext());
        final OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(title);
        oks.setText(text);
        oks.setImageUrl(imageUrl);
        oks.setUrl(url);
        oks.show(getContext());

        return null;
    }
}
