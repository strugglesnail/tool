package com.wtf.tool.util.excel.export.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wang_tengfei
 * @desc 用于设置Excel导出的列信息
 * @dete 2020-08-20 22:20
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HeaderExportExcel {
    // 序号：决定excel值对应列的位置
    int index();
    // sheet名称
    String sheetName();
    // 对应的列标题
    String title();
    // 宽度 默认15
    int width() default 15;
}
