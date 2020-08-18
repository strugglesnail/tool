package com.wtf.tool.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wang_tengfei
 * @desc 用于设置Excel导出的列信息
 * @dete 2020-04-15 22:23
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportExcel {
    // 序号：决定excel值对应列的位置
    int index();
    // 对应的列标题
    String title();
    // 对应的日期列标题
    String date() default "";
    // 宽度 默认15
    int width() default 15;
}
