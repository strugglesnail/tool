package com.wtf.tool.util.excel.export.test;

import com.wtf.tool.util.excel.export.annotation.HSSFExportExcel;
import com.wtf.tool.util.excel.export.annotation.HeaderExportExcel;

import java.util.Date;
@HeaderExportExcel(rowIndex = 1, colIndex = 1, title = "HSSF")
public class HSSFExportExcelDemo {

    @HSSFExportExcel(title = "文本名称", index = 0)
    private String name;

    @HSSFExportExcel(title = "sheet名称", index = 1)
    private String sheetName;

    @HSSFExportExcel(title = "文本类型", index = 2)
    private String type;

    @HSSFExportExcel(title = "时间", index = 3, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ExcelDemo{" +
                "name='" + name + '\'' +
                ", sheetName='" + sheetName + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                '}';
    }
}
