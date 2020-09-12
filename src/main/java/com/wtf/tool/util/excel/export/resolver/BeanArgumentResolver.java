package com.wtf.tool.util.excel.export.resolver;


import com.wtf.tool.util.excel.export.BeanParameter;

public interface BeanArgumentResolver {

    // 是否支持参数
    boolean supportsBean(BeanParameter parameter);

    // 解析字段参数
    Object resolverBean(BeanParameter parameter);
}
