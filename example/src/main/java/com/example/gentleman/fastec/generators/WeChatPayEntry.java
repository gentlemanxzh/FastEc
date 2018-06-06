package com.example.gentleman.fastec.generators;

import com.example.latte.annotations.EntryGenerator;
import com.example.latte.annotations.PayEntryGenerator;
import com.example.latter.wechat.templates.WPayXEntryTemplate;
import com.example.latter.wechat.templates.WXEntryTemplate;

/**
 * @author gentleman
 * @date 2018/6/6
 */

@PayEntryGenerator(
        packageName = "com.example.gentleman.fastec",
        payEntryTemplate = WPayXEntryTemplate.class
)
public interface WeChatPayEntry {
}
