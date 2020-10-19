package com.wtf.tool.util.excel.imp.handler;

/**
 * @auther strugglesnail
 * @date 2020/10/18 10:03
 * @desc 解析导入数据
 */
public interface ImportDataHandler {


    default boolean filterCellValue(Object obj) {
        return false;
    }


}
