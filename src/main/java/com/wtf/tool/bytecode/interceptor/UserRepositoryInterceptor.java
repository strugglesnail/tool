package com.wtf.tool.bytecode.interceptor;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;

import java.lang.reflect.Method;

/**
 * @author strugglesnail
 * @date 2021/2/2
 * @desc
 */
public class UserRepositoryInterceptor {

    public static String intercept(@Origin Method method, @AllArguments Object[] arguments) {
        return "查询文章数据：https://bugstack.cn/?id=" + arguments[0];
    }
}
