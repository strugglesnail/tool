package com.wtf.tool.bytecode.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author strugglesnail
 * @date 2021/2/2
 * @desc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RpcGatewayClazz {

    String clazzDesc() default "";
    String alias() default "";
    long timeOut() default 350;
}
