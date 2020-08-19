package com.wtf.tool.util.excel.export;

import java.lang.reflect.Field;

public interface FieldArgumentResolver {

    // 是否支持参数
    boolean supportsParameter(FieldParameter parameter);

    // 解析字段参数
    Object resolverParameter(FieldParameter parameter, Field field);
}
