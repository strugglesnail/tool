package com.wtf.tool.util.excel.export.resolver.prop;


import com.wtf.tool.util.excel.export.param.PropertyParameter;

public interface PropertyArgumentResolver {

    // 是否支持参数
    boolean supportsProperty(PropertyParameter parameter);

    // 解析字段参数
    Object resolverProperty(PropertyParameter parameter);

    // 设置标题
    void setTitle(PropertyParameter parameter);
}
