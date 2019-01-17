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

}
