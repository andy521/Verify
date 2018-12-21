package com.orange.verify.adminweb.annotation;

import java.lang.annotation.*;

/**
 * rsp 返回操作
 * @author Orange
 * @date 2018/10/13
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RspHandle {

    //方法体报错 是否返回错误信息
    boolean isSetErrorInfo() default false;

    //ip访问控制
    boolean ipHandle() default false;

    //ip访问间隔频率设置 毫秒 默认3秒...
    long ipHandleInterval() default 3000L;

    //ip redis缓存多少分钟 默认10分钟
    long ipRedisInterval() default 10L;

}
