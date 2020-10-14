package com.wtf.tool.util.excel.export.generator;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

/**
 * @author strugglesnail
 * @date 2020/10/12
 * @desc 用于自定义表头样式
 */
public interface StyleGenerator {

    // 设置表头颜色
    default void setHeaderColor(CellStyle style) {}

    // 设置表头边框
    default void setHeaderBorder(CellStyle style) {
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
    }

    // 设置单元格
    default void setHeaderFont(CellStyle style, Font font) {
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        style.setFont(font);
    }

    // 设置单元格颜色
    default void setCellColor(CellStyle style) {}

    // 设置单元格边框
    default void setCellBorder(CellStyle style) {
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
    }

    // 设置单元格
    default void setCellFont(CellStyle style, Font font) {
//        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
//        font.setFontName("宋体");
//        style.setFont(font);
    }

    // 设置合并单元格边框
    default void setMergedRegionBorder(CellStyle style, CellRangeAddress cra, Sheet sheet, Workbook workbook) {
        RegionUtil.setBorderTop(1, cra, sheet, workbook);
        RegionUtil.setBorderBottom(1, cra, sheet, workbook);
        RegionUtil.setBorderLeft(1, cra, sheet, workbook);
        RegionUtil.setBorderRight(1, cra, sheet, workbook);
    }

    // 设置居中
    default void setAlignment(CellStyle style) {
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    }
}
