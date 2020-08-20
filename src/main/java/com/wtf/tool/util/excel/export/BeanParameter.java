package com.wtf.tool.util.excel.export;

import com.wtf.tool.util.excel.export.annotation.HSSFExportExcel;
import com.wtf.tool.util.excel.export.annotation.HeaderExportExcel;
import com.wtf.tool.util.excel.export.util.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 类注解信息
 */
public class BeanParameter {

    private String sheetName;

    // 当前类的类型
    private Class<?> classType;

    // 当前类的注解
    private Annotation[] classAnnotations;

    private Field[] fields;


    public BeanParameter() {
    }

    public BeanParameter(Class<?> classType) {
        this.classType = classType;
        this.classAnnotations = classType.getAnnotations();
        this.fields = classType.getFields();
        HeaderExportExcel header = classType.getDeclaredAnnotation(HeaderExportExcel.class);
        if (header != null) this.sheetName = header.sheetName();
    }

    // 判断是否有指定的注解类型
    public <A extends Annotation> boolean hasBeanAnnotation(Class<A> annotationType) {
        return this.getBeanAnnotation(annotationType) != null;
    }

    // 获取指定的注解类型
    public <T extends Annotation> T getBeanAnnotation(Class<T> annotationType) {
        Annotation[] fieldAnns = this.classAnnotations;
        for (Annotation fieldAnn : fieldAnns) {
            if (annotationType.isInstance(fieldAnn)) {
                return AnnotationUtils.getAnnotation(fieldAnn, annotationType);
            }
        }
        return null;
    }


    public Class<?> getClassType() {
        return classType;
    }

    public void setClassType(Class<?> classType) {
        this.classType = classType;
    }

    public Annotation[] getClassAnnotations() {
        return classAnnotations;
    }

    public void setClassAnnotations(Annotation[] classAnnotations) {
        this.classAnnotations = classAnnotations;
    }

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
