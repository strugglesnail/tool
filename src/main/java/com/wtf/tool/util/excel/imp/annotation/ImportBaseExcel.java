package com.wtf.tool.util.excel.imp.annotation;

import com.wtf.tool.util.excel.imp.handler.DefaultImportDataHandler;
import com.wtf.tool.util.excel.imp.handler.ImportDataHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于excel导入
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportBaseExcel {

    // sheet名称
    String sheetName();

    // 设置字段所对应的行 默认从0开始
    int rowIndex() default 0;

    // 设置字段所对应的列 默认从0开始
    int colIndex() default 0;

    // 处理器：给一个处理导入数据的机会
    Class<? extends ImportDataHandler> handler() default DefaultImportDataHandler.class;
}
