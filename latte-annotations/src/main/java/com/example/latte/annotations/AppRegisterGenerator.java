package com.example.latte.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gentleman
 * @date 2018/6/5
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface AppRegisterGenerator {
    String packageName();

    Class<?> payEntryTemplate();
}
