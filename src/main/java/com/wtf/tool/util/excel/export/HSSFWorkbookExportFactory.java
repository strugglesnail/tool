package com.wtf.tool.util.excel.export;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;

public class HSSFWorkbookExportFactory extends AbstractWorkbookExportFactory {

    private FieldArgumentResolverComposite fieldArgumentResolverComposite;

    @Override
    protected Workbook createWorkbookInternal() {
        return new HSSFWorkbook();
    }

    public HSSFWorkbookExportFactory(String sheetName) {
        super(sheetName);
    }

    @Override
    protected <T> void setCell(Field field, T t, Row row) {

    }
}
