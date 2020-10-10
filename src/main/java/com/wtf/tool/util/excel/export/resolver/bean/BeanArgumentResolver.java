package com.wtf.tool.util.excel.export.resolver.bean;


import com.wtf.tool.util.excel.export.param.BeanParameter;

public interface BeanArgumentResolver {

    // 是否支持参数
    boolean supportsBean(BeanParameter parameter);

    // 解析字段参数
    Object resolverBean(BeanParameter parameter);
}
