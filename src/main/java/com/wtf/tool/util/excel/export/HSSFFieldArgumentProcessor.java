package com.wtf.tool.util.excel.export;

import java.lang.reflect.Field;

public class HSSFFieldArgumentProcessor implements FieldArgumentResolver {
    @Override
    public boolean supportsParameter(FieldParameter parameter) {
        return false;
    }

    @Override
    public Object resolverParameter(FieldParameter parameter, Field field) {
        return null;
    }
}
