package com.wtf.tool.util.excel.export;

import com.wtf.tool.util.excel.export.util.AnnotationUtils;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class PropertyParameter {

    private Field field;

    // 字段的所属类类型
    private Class<?> containingClass;

    // 字段类型
    private Class<?> fieldType;

    // 字段注解
    private Annotation[] fieldAnnotations;



    // 判断是否有指定的注解类型
    public <A extends Annotation> boolean hasPropertyAnnotation(Class<A> annotationType) {
        return this.getPropertyAnnotation(annotationType) != null;
    }

    // 获取指定的注解类型
    public <T extends Annotation> T getPropertyAnnotation(Class<T> annotationType) {
        Annotation[] fieldAnns = this.fieldAnnotations;
        for (Annotation fieldAnn : fieldAnns) {
            if (annotationType.isInstance(fieldAnn)) {
                return AnnotationUtils.getAnnotation(fieldAnn, annotationType);
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
