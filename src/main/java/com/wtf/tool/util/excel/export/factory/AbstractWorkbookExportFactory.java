package com.wtf.tool.util.excel.export.factory;

import com.wtf.tool.util.excel.export.BeanParameter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;
import java.util.List;

public abstract class AbstractWorkbookExportFactory implements WorkbookExportFactory {

    private final Workbook workbook;

    private final Sheet sheet;


    public AbstractWorkbookExportFactory(BeanParameter beanParameter) {
        this.workbook = createWorkbook(beanParameter);
        this.sheet = getSheet(beanParameter);
    }

    @Override
    public final Workbook createWorkbook(BeanParameter beanParameter) {
        return createWorkbookInternal(beanParameter);
    }

    protected Sheet getSheet(BeanParameter beanParameter) {
        return createWorkbookInternal(beanParameter).createSheet(beanParameter.getSheetName());
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

    protected abstract Workbook createWorkbookInternal(BeanParameter beanParameter);
}
