package com.orange.verify.adminweb.annotation;

import java.lang.annotation.*;

/**
 * 自动插入response 时间差
 * @author Orange
 * @date 2018/10/13
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RspSetTime {

}
