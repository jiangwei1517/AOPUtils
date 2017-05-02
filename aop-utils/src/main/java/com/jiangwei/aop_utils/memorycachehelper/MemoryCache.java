package com.jiangwei.aop_utils.memorycachehelper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:  jiangwei18 on 17/5/2 20:36
 * email:  jiangwei18@baidu.com
 * Hi:   jwill金牛
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface MemoryCache {
}
