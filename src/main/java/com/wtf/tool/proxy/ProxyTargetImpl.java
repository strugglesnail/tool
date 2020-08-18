package com.wtf.tool.proxy;

public class ProxyTargetImpl implements ProxyTarget{

    @Override
    public void proxyTargetMethod() {
        System.out.println("我是目标对象！");
    }

}
