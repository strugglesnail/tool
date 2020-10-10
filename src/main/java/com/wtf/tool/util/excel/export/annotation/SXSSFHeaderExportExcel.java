package com.wtf.tool.util.excel.export.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auther: strugglesnail
 * @date: 2020/10/8 16:29
 * @desc:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SXSSFHeaderExportExcel {
    // 序号：决定excel值对应列的位置
    int index();
    // 对应的列标题
    String title();
    // 宽度 默认15
    int width() default 15;
}
