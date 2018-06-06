package com.example.gentleman.fastec.generators;

import com.example.latte.annotations.EntryGenerator;
import com.example.latter.wechat.templates.WXEntryTemplate;

/**
 * @author gentleman
 * @date 2018/6/6
 */
@EntryGenerator(
        packageName = "com.example.gentleman.fastec",
        entryTemplate = WXEntryTemplate.class
)
public interface WeChatEntry {
}
