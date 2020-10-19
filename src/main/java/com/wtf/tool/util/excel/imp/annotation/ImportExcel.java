package com.wtf.tool.util.excel.imp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于excel导入
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportExcel {
    // 表示要导入的字段：要和字段名一样，大小写不限
//    String value() default "";

    // 设置字段所对应的列 从0开始
    int index();
}
