package com.lkmotion.yesincar.config;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Documented
public @interface Cache {

    /**
     * 放入redis中得key,不设置则自动取类名+方法名称
     * 自己设定key的时候,自己要保证这是唯一的
     */
    String key() default "";

    /**
     * 从参数数组中取参数的索引
     */
    String[] paramIndex() default "";
}
