package com.wtf.tool.util.excel;

import com.wtf.tool.annotation.ImportExcel;
import com.wtf.tool.annotation.ExportExcel;

import java.util.Date;

public class ExcelDemo {

    @ImportExcel(index = 0)
    @ExportExcel(title = "文本名称", index = 0)
    private String name;

    @ImportExcel(index = 1)
    @ExportExcel(title = "sheet名称", index = 1)
    private String sheetName;

    @ImportExcel(index = 2)
    @ExportExcel(title = "文本类型", index = 2)
    private String type;

    @ImportExcel(index = 3)
    @ExportExcel(title = "时间", index = 3, date = "yyyy-MM-dd HH:mm:ss")
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
