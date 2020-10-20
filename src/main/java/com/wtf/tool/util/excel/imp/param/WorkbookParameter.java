package com.wtf.tool.util.excel.imp.param;

import com.wtf.tool.util.excel.imp.handler.ImportDataHandler;

import java.io.InputStream;

public class WorkbookParameter {

    // 源数据
    private InputStream dataSource;
    // workbook
//    private Workbook workbook;
    // Excel sheet名
    private String sheetName;
    // 从第几行获取数据
    private int rowIndex;
    // 从第几列获取数据
    private int colIndex;

    private ImportDataHandler handler;

    public WorkbookParameter(InputStream dataSource, String sheetName, int rowIndex, int colIndex, Class<? extends ImportDataHandler> handler) {
        this.dataSource = dataSource;
        this.sheetName = sheetName;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        try {
            this.handler = handler.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public InputStream getDataSource() {
        return dataSource;
    }

    public String getSheetName() {
        return sheetName;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public ImportDataHandler getHandler() {
        return handler;
    }
}
