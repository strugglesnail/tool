package com.wtf.tool.util.excel.export;

import com.wtf.tool.util.excel.export.util.AnnotationUtils;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class PropertyParameter<T> {

    // 处理的属性
    private Field field;

    // 单元格所属的行
    private Row row;

    // 目标对象
    private T t;

    // 字段的所属类类型
    private Class<?> containingClass;

    // 字段类型
    private Class<?> fieldType;

    // 字段注解
    private Annotation[] fieldAnnotations;


    public PropertyParameter(Field field, Row row, T t) {
        this.field = field;
        this.row = row;
        this.t = t;
        this.containingClass = t.getClass();
        this.fieldType = field.getDeclaringClass();
        this.fieldAnnotations = field.getDeclaredAnnotations();
    }

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

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
