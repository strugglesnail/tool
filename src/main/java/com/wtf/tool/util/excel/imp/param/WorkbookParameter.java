package com.wtf.tool.util.excel.imp.param;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

public class WorkbookParameter {

    // 源数据
    private MultipartFile file;
    // workbook
//    private Workbook workbook;
    // Excel sheet名
    private String[] sheetName;
    // 从第几行获取数据
    private int[] rowIndex;
    // 从第几列获取数据
    private int[] colIndex;

    public WorkbookParameter(MultipartFile file, String[] sheetName, int[] rowIndex, int[] colIndex) {
        this.file = file;
        this.sheetName = sheetName;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public MultipartFile getFile() {
        return file;
    }

    public String[] getSheetName() {
        return sheetName;
    }

    public int[] getRowIndex() {
        return rowIndex;
    }

    public int[] getColIndex() {
        return colIndex;
    }
}
