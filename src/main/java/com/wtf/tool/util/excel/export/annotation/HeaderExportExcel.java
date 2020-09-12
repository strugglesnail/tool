package com.wtf.tool.util.excel.export.annotation;

import java.lang.annotation.*;

/**
 * @author wang_tengfei
 * @desc 用于设置Excel导出的列信息
 * @dete 2020-08-20 22:20
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HeaderExportExcel {
    // 决定excel值对应行的起始位置
    int rowIndex() default 0;

    // sheet名称
    String sheetName() default "file export";


    // Excel单元格位置(默认居中)
    ExcelAlign align() default ExcelAlign.ALIGN_CENTER;
}
