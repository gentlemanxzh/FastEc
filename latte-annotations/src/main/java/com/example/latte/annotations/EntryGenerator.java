package com.example.latte.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.xml.bind.Element;

/**
 * @author gentleman
 * @date 2018/6/5
 * @function
 * @Target(ElementType.TYPE) 代表这个注解是用在类上面的
 * @Retention(RetentionPolicy.SOURCE) 这个注解是在源码阶段进行处理的
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface EntryGenerator {

    String packageName();

    Class<?> entryTemplate();
}
