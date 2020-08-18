package com.wtf.tool.proxy;

import com.wtf.tool.model.UserDO;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Jdk动态代理
 */
public class JdkDyProxy implements InvocationHandler {

    // 目标对象
    private ProxyTarget proxyTarget;

    private UserDO userDO;

    public JdkDyProxy(ProxyTarget proxyTarget) {
        this.proxyTarget = proxyTarget;
    }

    public JdkDyProxy(UserDO userDO) {
        this.userDO = userDO;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object o = null;
        o = method.invoke(proxyTarget, args);
        return o;
    }


    public static void main(String[] args) {
//        ProxyTarget target = (ProxyTarget) Proxy.newProxyInstance(JdkDyProxy.class.getClassLoader(), new Class[]{ProxyTarget.class}, new JdkDyProxy(new UserDO(1L, "")));
        ProxyTarget target = (ProxyTarget) Proxy.newProxyInstance(JdkDyProxy.class.getClassLoader(), new Class[]{ProxyTarget.class}, new JdkDyProxy(new ProxyTargetImpl()));
        target.proxyTargetMethod();
    }
}
