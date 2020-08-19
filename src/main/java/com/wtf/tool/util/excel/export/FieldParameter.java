package com.wtf.tool.util.excel.export;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class FieldParameter {

    private Field field;

    // 字段的所属类类型
    private Class<?> containingClass;

    // 字段类型
    private Class<?> fieldType;

    // 字段注解
    private Annotation[] fieldAnnotations;



    public <A extends Annotation> boolean hasFieldAnnotation(Class<A> annotationType) {
        return this.getFieldAnnotation(annotationType) != null;
    }

    private <A extends Annotation> A getFieldAnnotation(Class<A> annotationType) {
        Annotation[] fieldAnns = this.fieldAnnotations;
        for (Annotation fieldAnn : fieldAnns) {
            if (annotationType.isInstance(fieldAnn)) {
                return fieldAnn;
            }
        }
        return null;
    }


    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Class<?> getContainingClass() {
        return containingClass;
    }

    public void setContainingClass(Class<?> containingClass) {
        this.containingClass = containingClass;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
    }

    public Annotation[] getFieldAnnotations() {
        return fieldAnnotations;
    }

    public void setFieldAnnotations(Annotation[] fieldAnnotations) {
        this.fieldAnnotations = fieldAnnotations;
    }
}
