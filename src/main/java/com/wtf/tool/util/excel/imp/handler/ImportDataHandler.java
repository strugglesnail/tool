package com.wtf.tool.util.excel.imp.handler;

import java.util.List;

/**
 * @auther strugglesnail
 * @date 2020/10/18 10:03
 * @desc 解析导入数据
 */
public interface ImportDataHandler<T> {


    default boolean handlerCellValue(T t) {
        return false;
    }


    default void handlerRow(T data) {
    }


}
