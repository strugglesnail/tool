package com.wtf.tool.util.excel.export.factory;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;
import java.util.List;

public abstract class AbstractWorkbookExportFactory implements WorkbookExportFactory {

    private final Workbook workbook;

    private final Sheet sheet;


    public AbstractWorkbookExportFactory(String sheetName) {
        this.workbook = createWorkbook();
        this.sheet = getSheet(sheetName);
    }

    @Override
    public final Workbook createWorkbook() {
        return createWorkbookInternal();
    }

    protected Sheet getSheet(String sheetName) {
        return createWorkbookInternal().createSheet(sheetName);
    }

    protected <T> void setRow(List<T> rowList) {
        int index = 0;
        for (T t : rowList) {
            Row row = sheet.createRow(++index);
            this.setField(t, row);
        }
    }

    private <T> void setField(T t, Row row) {
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            this.setCell(field, t, row);
        }
    }

    protected abstract <T> void setCell(Field field, T t, Row row);

    protected abstract Workbook createWorkbookInternal();
}
