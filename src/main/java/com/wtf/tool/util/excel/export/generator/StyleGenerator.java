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

    // 设置颜色
    void setColor(CellStyle style);

    // 设置普通边框
    void setBorder(CellStyle style);

    // 设置合并单元格边框
    void setMergedRegionBorder(CellStyle style, CellRangeAddress cra, Sheet sheet, Workbook workbook);

    // 设置居中
    void setAlignment(CellStyle style);
}
