package com.wtf.tool.util.excel.export.generator;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

/**
 * @author strugglesnail
 * @date 2020/10/12
 * @desc 默认的样式
 */
public class DefaultStyleGenerator implements StyleGenerator {

    @Override
    public void setHeaderColor(CellStyle style) {
        style.setFillForegroundColor(IndexedColors.TAN.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
    }

    @Override
    public void setHeaderBorder(CellStyle style) {
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
    }

    @Override
    public void setCellColor(CellStyle style) {

    }

    @Override
    public void setCellBorder(CellStyle style) {
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
    }

    @Override
    public void setMergedRegionBorder(CellStyle style, CellRangeAddress cra, Sheet sheet, Workbook workbook) {
        RegionUtil.setBorderTop(1, cra, sheet, workbook);
        RegionUtil.setBorderBottom(1, cra, sheet, workbook);
        RegionUtil.setBorderLeft(1, cra, sheet, workbook);
        RegionUtil.setBorderRight(1, cra, sheet, workbook);
    }

    @Override
    public void setAlignment(CellStyle style) {
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    }
}
