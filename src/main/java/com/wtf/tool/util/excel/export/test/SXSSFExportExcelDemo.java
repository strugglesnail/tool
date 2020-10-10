package com.wtf.tool.util.excel.export.test;

import com.wtf.tool.util.excel.export.annotation.*;
import com.wtf.tool.util.excel.export.annotation.SXSSFExportExcel;

import java.util.Date;

@SXSSFHeaderExportExcel(index = 0, title = "复杂表头")
@HeaderExportExcel
public class SXSSFExportExcelDemo {

    @SXSSFExportExcel(title = {"文本名称", "哇啊"}, offset = {"0,2,0,0", "0,0,0,0"}, index = 0)
    private String name;

    @SXSSFExportExcel(title = {"sheet名称", "综合", "haha"}, offset = {"0,1,1,1", "0,0,1,2", "0,0,0,0"}, index = 1)
    private String sheetName;

    @SXSSFExportExcel(title = "文本类型", offset = "1,2,2,2", index = 2)
    private String type;

    @SXSSFExportExcel(title = "时间01", offset = "0,2,3,3", index = 3, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

//    @SXSSFExportExcel(title = "综合", offset = "0,0,1,2", index = 1)
    private String merge0;

    public String getMerge0() {
        return merge0;
    }

    public void setMerge0(String merge0) {
        this.merge0 = merge0;
    }

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
