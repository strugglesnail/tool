package com.wtf.tool.util.excel.export;

import com.wtf.tool.util.excel.export.param.PropertyParameter;

import java.lang.reflect.Field;

public class HandlerProperty {

    private final Object bean;

    private final Field field;

    private final PropertyParameter propertyParameter;

    public HandlerProperty(HandlerProperty property) {
        this.bean = null;
        this.field = property.field;
        this.propertyParameter = property.propertyParameter;
    }

    public HandlerProperty(Field field, PropertyParameter propertyParameter) {
        this.bean = null;
        this.field = field;
        this.propertyParameter = propertyParameter;
    }

    public HandlerProperty(Object bean, Field field, PropertyParameter propertyParameter) {
        this.bean = bean;
        this.field = field;
        this.propertyParameter = propertyParameter;
    }


}
