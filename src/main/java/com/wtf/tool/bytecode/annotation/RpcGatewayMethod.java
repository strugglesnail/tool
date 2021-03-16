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
@Target(ElementType.METHOD)
public @interface RpcGatewayMethod {

    String methodName() default "";
    String methodDesc() default "";
}
