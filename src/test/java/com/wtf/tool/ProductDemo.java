package com.wtf.tool;

import com.wtf.tool.annotation.ImportExcel;
import com.wtf.tool.util.excel.export.annotation.HeaderExportExcel;
import com.wtf.tool.util.excel.export.annotation.XSSFExportExcel;
import com.wtf.tool.util.excel.imp.annotation.ImportBaseExcel;
//import com.wtf.tool.util.excel.imp.annotation.ImportExcel;
import com.wtf.tool.util.excel.imp.test.CustomImportDataHandler;

/**
 * @author strugglesnail
 * @date 2021/1/28
 * @desc
 */
@HeaderExportExcel(title = "XSSF")
@ImportBaseExcel(sheetName = "changpin128", rowIndex = 1, handler = CustomImportDataHandler.class)
public class ProductDemo {

    @ImportExcel(index = 0)
    @XSSFExportExcel(title = "产品服务名称", index = 0)
    private Object name;

    @ImportExcel(index = 1)
    @XSSFExportExcel(title = "产品服务领域", index = 1)
    private String area;

    @ImportExcel(index = 2)
    @XSSFExportExcel(title = "产品服务详情", index = 2)
    private Object detail;

    @ImportExcel(index = 3)
    @XSSFExportExcel(title = "所属服务商名称", index = 3)
    private String serviceName;

    @ImportExcel(index = 4)
    @XSSFExportExcel(title = "联系方式", index = 4)
    private Double phone;

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Double getPhone() {
        return phone;
    }

    public void setPhone(Double phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ProductDemo{" +
                "name='" + name + '\'' +
                ", area='" + area + '\'' +
                ", detail='" + detail + '\'' +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }
}
