package com.wtf.tool.util.excel.export.resolver;


import com.wtf.tool.util.excel.export.PropertyParameter;

public interface PropertyArgumentResolver {

    // 是否支持参数
    boolean supportsProperty(PropertyParameter parameter);

    // 解析字段参数
    Object resolverProperty(PropertyParameter parameter);
}
