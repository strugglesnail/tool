package com.wtf.tool.util.excel.export.factory;

import com.wtf.tool.util.excel.export.param.BeanParameter;
import com.wtf.tool.util.excel.export.param.PropertyParameter;
import com.wtf.tool.util.excel.export.param.WorkbookParameter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author strugglesnail
 * @date 2020/10/12
 * @desc 导出工厂
 */
public abstract class AbstractWorkbookExportFactory implements WorkbookExportFactory {

    private final Workbook workbook;

    private final Sheet sheet;

    private BeanParameter beanParameter;

    private WorkbookParameter workbookParameter;


    public AbstractWorkbookExportFactory(BeanParameter beanParameter) {
        this.workbook = createWorkbook(beanParameter);
        this.sheet = getSheet(beanParameter);
        this.beanParameter = beanParameter;
        this.workbookParameter = new WorkbookParameter(workbook, sheet, workbook.createCellStyle(), workbook.getCreationHelper(), beanParameter);

    }

    @Override
    public final Workbook createWorkbook(BeanParameter beanParameter) {
        Workbook workbook = null;
        if (beanParameter.getFormat() == null) {
            throw new IllegalArgumentException("Missing @HeaderExportExcel annotation info");
        }
        switch (beanParameter.getFormat()) {
            case HSSF:
                workbook = new HSSFWorkbook();
                break;
            case XSSF:
                workbook = new XSSFWorkbook();
                break;
            case SXSSF:
                workbook = new SXSSFWorkbook();
                break;
        }
        return workbook;
    }

    @Override
    public Workbook getWorkbook() {
        return workbook;
    }

    protected final Sheet getSheet(BeanParameter beanParameter) {
        return workbook.createSheet(beanParameter.getSheetName());
    }

    // 填充每一行
    protected <T> void setRow(List<T> rowList) {
        int index = beanParameter.getRowIndex();
        for (T target : rowList) {
            Row row = sheet.createRow(++index);
            this.setProperty(target, row);
        }
    }

    // 填充单元格
    private <T> void setProperty(T target, Row row) {
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            this.setCell(new PropertyParameter<>(workbookParameter, row, field, target));
        }
    }

    // 设置标题、表头
    private void setHeader() {
        setHeader(new PropertyParameter<>(workbookParameter));
    }

    // 获取工作簿
    public <T> Workbook exportWorkbook(List<T> dataList) {
        // 设置表头
        setHeader();
        // 设置单元格
        setRow(dataList);
        return workbook;
    }



    protected abstract <T> void setCell(PropertyParameter<T> propertyParameter);
    protected abstract <T> void setHeader(PropertyParameter<T> propertyParameter);

}
