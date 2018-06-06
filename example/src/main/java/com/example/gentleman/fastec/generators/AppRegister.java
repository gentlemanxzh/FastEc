package com.example.gentleman.fastec.generators;

import com.example.latte.annotations.AppRegisterGenerator;
import com.example.latter.wechat.templates.AppRegisterTemplate;
import com.example.latter.wechat.templates.WXEntryTemplate;

/**
 * @author gentleman
 * @date 2018/6/6
 */

@AppRegisterGenerator(
        packageName = "com.example.gentleman.fastec",
        registerTemplate = AppRegisterTemplate.class
)
public interface AppRegister {
}
