package com.wtf.tool.util.excel.export;

import com.wtf.tool.util.excel.export.util.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 类注解信息
 */
public class ClassParameter {

    // 当前类的类型
    private Class<?> classType;

    // 当前类的注解
    private Annotation[] classAnnotations;



    // 判断是否有指定的注解类型
    public <A extends Annotation> boolean hasClassAnnotation(Class<A> annotationType) {
        return this.getClassAnnotation(annotationType) != null;
    }

    // 获取指定的注解类型
    public <T extends Annotation> T getClassAnnotation(Class<T> annotationType) {
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
}
