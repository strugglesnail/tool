package com.wtf.tool.util.excel.export.generator;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @author strugglesnail
 * @date 2020/10/12
 * @desc 用于自定义表头样式
 */
public interface StyleGenerator {

    // 设置表头颜色
    default void setHeaderColor(CellStyle style) {}

    // 设置表头边框
    default void setHeaderBorder(CellStyle style) {}

    // 设置表头颜色
    default void setCellColor(CellStyle style) {}

    // 设置表头边框
    default void setCellBorder(CellStyle style) {}

    // 设置合并单元格边框
    default void setMergedRegionBorder(CellStyle style, CellRangeAddress cra, Sheet sheet, Workbook workbook) {}

    // 设置居中
    default void setAlignment(CellStyle style) {
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    }
}
