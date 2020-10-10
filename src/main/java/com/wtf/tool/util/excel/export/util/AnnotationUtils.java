package com.wtf.tool.util.excel.export.util;


import java.lang.annotation.*;

public class AnnotationUtils {
    public static <A extends Annotation> A getAnnotation(Annotation annotation, Class<A> annotationType) {
        A metaAnn = (A)annotation;
        return metaAnn;
    }

    // 获取组合注解
    public static <T extends Annotation> T getComposeAnnotation(Class<?> resource, Class<T> target) {
        Annotation[] annotations = resource.getDeclaredAnnotations();
        Annotation resultAnnotation = null;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() != Target.class &&
                    annotation.annotationType() != Retention.class &&
                    annotation.annotationType() != Documented.class &&
                    annotation.annotationType() != Inherited.class
            ) {
                if (annotation.annotationType() == target) {
                    resultAnnotation = annotation;
                    break;
                } else {
                    resultAnnotation = getComposeAnnotation(annotation.annotationType(), target);
                }
            }
        }
        return (T)resultAnnotation;
    }
}
