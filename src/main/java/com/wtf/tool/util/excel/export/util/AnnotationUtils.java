package com.wtf.tool.util.excel.export.util;

import java.lang.annotation.Annotation;

public class AnnotationUtils {
    public static <A extends Annotation> A getAnnotation(Annotation annotation, Class<A> annotationType) {
        if (!annotationType.isInstance(annotation)) {
            Class annotatedElement = annotation.annotationType();
            try {
                A metaAnn = (A)annotatedElement.getAnnotation(annotationType);
                return metaAnn;
            } catch (Throwable e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
