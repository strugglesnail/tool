package com.wtf.tool.util.excel.export.factory;

import com.wtf.tool.util.excel.export.BeanParameter;
import org.apache.poi.ss.usermodel.Workbook;

public interface WorkbookExportFactory {
    Workbook createWorkbook(BeanParameter beanParameter);
    Workbook getWorkbook();
}
