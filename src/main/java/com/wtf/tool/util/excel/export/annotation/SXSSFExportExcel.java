package com.wtf.tool.util.excel.export.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auther: strugglesnail
 * @date: 2020/10/8 16:46
 * @desc: 用于设置Excel导出的列信息
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SXSSFExportExcel {
    // 序号：决定excel值对应列的位置
    int index();
    // 对应的列标题
    String[] title();
    // 位置:行开始-行结束，列开始-列结束
    String[] offset();
    // 对应的日期格式
    String pattern() default "";
    // 宽度 默认20
    int width() default 20;
}
