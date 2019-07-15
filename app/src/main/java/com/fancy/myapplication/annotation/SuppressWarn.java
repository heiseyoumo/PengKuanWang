package com.fancy.myapplication.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author pengkuanwang
 * @date 2019-07-14
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface SuppressWarn {
    String value();
}
