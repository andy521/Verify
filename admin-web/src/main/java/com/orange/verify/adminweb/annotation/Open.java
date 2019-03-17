package com.orange.verify.adminweb.annotation;

import java.lang.annotation.*;

/**
 * 如果要Ip限流的注解这个
 * @author Ornage软件
 * @date 2019.3.15
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Open {

    /**
     * 接口说明
     */
    String explain() default "...";

}
