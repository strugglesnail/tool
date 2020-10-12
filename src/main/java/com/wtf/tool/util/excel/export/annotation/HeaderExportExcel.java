package com.wtf.tool.util.excel.export.annotation;

import com.wtf.tool.util.excel.export.generator.DefaultStyleGenerator;
import com.wtf.tool.util.excel.export.generator.StyleGenerator;

import java.lang.annotation.*;

/**
 * @author strugglesnail
 * @desc 用于设置Excel导出的列信息
 * @dete 2020-08-20 22:20
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HeaderExportExcel {
    // 行的起始位置
    int rowIndex() default 0;

    // 列的起始位置
    int colIndex() default 0;

    // sheet名称
    String sheetName() default "file export";

    // 标题
    String title() default "";

    // 工作簿类型
    ExcelFormat format() default ExcelFormat.XSSF;



    // 设置表头背样式
    Class<? extends StyleGenerator> styleGenerator() default DefaultStyleGenerator.class;
}
