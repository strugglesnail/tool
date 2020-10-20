package com.wtf.tool.util.excel.imp.test;

import com.wtf.tool.annotation.ImportExcel;
import com.wtf.tool.util.excel.imp.annotation.ImportBaseExcel;

import java.util.Date;

/**
 * @auther strugglesnail
 * @date 2020/10/19 21:57
 * @desc
 */
@ImportBaseExcel(sheetName = "ExcelDemo", rowIndex = 2, handler = CustomImportDataHandler.class)
public class ImportDemo {

    @ImportExcel(index = 0)
    private String name;

    @ImportExcel(index = 1)
    private String sheetName;

    @ImportExcel(index = 2)
    private String type;

    @ImportExcel(index = 3)
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
