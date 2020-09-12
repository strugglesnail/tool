package com.wtf.tool.util.excel.export.factory;

import com.wtf.tool.annotation.ExportExcel;
import com.wtf.tool.util.excel.export.BeanArgumentResolverComposite;
import com.wtf.tool.util.excel.export.BeanParameter;
import com.wtf.tool.util.excel.export.PropertyParameter;
import com.wtf.tool.util.excel.export.resolver.BeanArgumentResolver;
import com.wtf.tool.util.excel.export.resolver.HSSFBeanArgumentProcessor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出工厂
 */
public abstract class AbstractWorkbookExportFactory implements WorkbookExportFactory {

    private final Workbook workbook;

    private final Sheet sheet;

    private BeanParameter beanParameter;

    private WorkbookParameter workbookParameter;

    private BeanArgumentResolverComposite beanArgumentResolverComposite = new BeanArgumentResolverComposite();



    public AbstractWorkbookExportFactory(BeanParameter beanParameter) {
        this.workbook = createWorkbook(beanParameter);
        this.sheet = getSheet(beanParameter);
        this.beanParameter = beanParameter;
        System.out.println(beanParameter.getFields().length);
        this.workbookParameter = new WorkbookParameter(workbook, sheet, workbook.createCellStyle(), workbook.getCreationHelper(), beanParameter);

    }

    private List<BeanArgumentResolver> getDefaultArgumentResolvers() {
        List<BeanArgumentResolver> resolvers = new ArrayList<>();
        resolvers.add(new HSSFBeanArgumentProcessor());
        return resolvers;
    }

    @Override
    public final Workbook createWorkbook(BeanParameter beanParameter) {
        Workbook workbook = null;
        beanArgumentResolverComposite = new BeanArgumentResolverComposite().addResolvers(getDefaultArgumentResolvers());
        BeanArgumentResolver argumentResolver = beanArgumentResolverComposite.getArgumentResolver(beanParameter);
        if (argumentResolver instanceof HSSFBeanArgumentProcessor) {
            workbook = new HSSFWorkbook();
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

    protected <T> void setRow(List<T> rowList) {
        int index = beanParameter.getRowIndex();
        for (T target : rowList) {
            Row row = sheet.createRow(++index);
            this.setProperty(target, row);
        }
    }

    private <T> void setProperty(T target, Row row) {
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            this.setCell(new PropertyParameter<>(workbookParameter, field, row, target));
        }
    }

    // 设置列标题
    private void setTitleAndWidth() {
        Row row = sheet.createRow(beanParameter.getRowIndex());
        Field[] fields = beanParameter.getFields();
        System.out.println(fields.length);
        for (Field field : fields) {
            System.out.println("field:: " + field);
            setTitle(new PropertyParameter<>(workbookParameter, field, row, null));
//            // 设置列标题
//            if (annotation != null && annotation.title().length() > 0) {
//                String title = annotation.title();
//                int index = annotation.index();
//                Cell cell = row.createCell(index);
//                cell.setCellStyle(style);
//                style.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
//                style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//                cell.setCellValue(title);
//            }
//            // 设置宽度
//            if (annotation != null && annotation.width() != 0) {
//                int index = annotation.index();
//                int width = annotation.width();
//                sheet.setColumnWidth(index,width * 256);
//            }
        }
    }

    // 获取工作簿
    protected <T> Workbook exportWorkbook(List<T> dataList) {
        // 设置表头
        // 设置标题
        setTitleAndWidth();
        // 设置单元格
        setRow(dataList);
        return workbook;
    }

    public static class WorkbookParameter {
        private final Workbook workbook;

        private final Sheet sheet;

        private final CellStyle cellStyle;

        private final CreationHelper creationHelper;

        private final BeanParameter beanParameter;

        public WorkbookParameter(Workbook workbook, Sheet sheet, CellStyle cellStyle, CreationHelper creationHelper, BeanParameter beanParameter) {
            this.workbook = workbook;
            this.sheet = sheet;
            this.cellStyle = cellStyle;
            this.creationHelper = creationHelper;
            this.beanParameter = beanParameter;
        }

        public Workbook getWorkbook() {
            return workbook;
        }

        public Sheet getSheet() {
            return sheet;
        }

        public CellStyle getCellStyle() {
            return cellStyle;
        }

        public CreationHelper getCreationHelper() {
            return creationHelper;
        }

        public BeanParameter getBeanParameter() {
            return beanParameter;
        }
    }

    protected abstract <T> void setCell(PropertyParameter<T> propertyParameter);
    protected abstract <T> void setTitle(PropertyParameter<T> propertyParameter);

}
