package com.wtf.tool.util.excel.export;


import com.wtf.tool.util.excel.export.annotation.HSSFHeaderExportExcel;
import com.wtf.tool.util.excel.export.annotation.HeaderExportExcel;
import com.wtf.tool.util.excel.export.util.AnnotationUtils;

import javax.annotation.*;
import java.lang.annotation.*;

public class HandlerBean extends BeanParameter{

//    public static void getAnns(Class c) {
//        Annotation[] annotations = c.getDeclaredAnnotations();
//        for (Annotation annotation : annotations) {
//            System.out.println(annotation.annotationType());
//            if (annotation.annotationType() != Target.class &&
//                    annotation.annotationType() != Retention.class &&
//                    annotation.annotationType() != Documented.class &&
//                    annotation.annotationType() != Inherited.class
//            ) {
//                if (annotation.annotationType() == HeaderExportExcel.class) {
//                    System.out.println(annotation);
//                } else {
//                    System.out.println("111: "+annotation.annotationType());
//                    getAnns(annotation.annotationType());
//                }
//            }
//        }
//    }

    public static void main(String[] args) {
//        getAnns(HandlerBean.class);
//        HeaderExportExcel composeAnnotation = AnnotationUtils.getComposeAnnotation(HandlerBean.class, HeaderExportExcel.class);
//        System.out.println(composeAnnotation);
        Annotation[] annotations = HandlerBean.class.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
    }


}
