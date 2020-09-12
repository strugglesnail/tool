package com.wtf.tool.util.excel.export;

import com.wtf.tool.util.excel.export.annotation.HeaderExportExcel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 类注解信息
 */
public class BeanParameter {

    private int rowIndex;

    // sheet名称
    private String sheetName;

    // 单元格位置
    private short align;

    // 当前类的类型
    private Class<?> classType;

    // 当前类的注解
    private Annotation[] classAnnotations;

    // 属性
    private Field[] fields;


    public BeanParameter() {
    }

    public BeanParameter(Class<?> resourceClass) {
        this.classType = resourceClass;
        this.classAnnotations = resourceClass.getAnnotations();
        this.fields = resourceClass.getDeclaredFields();
        HeaderExportExcel header = resourceClass.getDeclaredAnnotation(HeaderExportExcel.class);
        if (header != null) {
            this.sheetName = header.sheetName();
            this.rowIndex = header.rowIndex();
            this.align = header.align().getCode();
        }
    }

    // 判断是否有指定的注解类型
    public <A extends Annotation> boolean hasBeanAnnotation(Class<A> annotationType) {
        return this.getBeanAnnotation(annotationType) != null;
    }

    // 获取指定的注解类型
    public <T extends Annotation> T getBeanAnnotation(Class<T> annotationType) {
        Annotation[] beanAnns = this.classAnnotations;
        for (Annotation beanAnn : beanAnns) {
            if (annotationType.isInstance(beanAnn)) {
                return (T)beanAnn;
            }
        }
        return null;
    }


    public Class<?> getClassType() {
        return classType;
    }

    public Annotation[] getClassAnnotations() {
        return classAnnotations;
    }


    public Field[] getFields() {
        return fields;
    }

    public String getSheetName() {
        return sheetName;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public short getAlign() {
        return align;
    }
}
