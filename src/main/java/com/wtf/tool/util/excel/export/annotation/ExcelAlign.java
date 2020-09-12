package com.wtf.tool.util.excel.export.annotation;

import org.apache.poi.ss.usermodel.CellStyle;

public enum ExcelAlign {
    ALIGN_LEFT(CellStyle.ALIGN_LEFT),
    ALIGN_CENTER(CellStyle.ALIGN_CENTER),
    ALIGN_RIGHT(CellStyle.ALIGN_RIGHT);

    private short code;

    ExcelAlign(short code) {
        this.code = code;
    }

    public short getCode() {
        return code;
    }
}
