package com.wtf.tool.util.excel.export.annotation;

public enum ExcelFormat {
    HSSF(1),
    XSSF(2),
    SXSSF(3);

    private int code;

    ExcelFormat(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
