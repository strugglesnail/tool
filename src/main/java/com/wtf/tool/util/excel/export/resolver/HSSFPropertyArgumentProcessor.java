package com.wtf.tool.util.excel.export.resolver;

import com.wtf.tool.util.excel.export.PropertyParameter;
import com.wtf.tool.util.excel.export.annotation.HSSFExportExcel;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Objects;

/**
 * HSSF注解解析
 */
public class HSSFPropertyArgumentProcessor extends AbstractPropertyArgumentProcessor implements PropertyArgumentResolver {


    @Override
    public boolean supportsProperty(PropertyParameter parameter) {
        return parameter.hasPropertyAnnotation(HSSFExportExcel.class);
    }

    @Override
    public Object resolverProperty(PropertyParameter parameter) {
        HSSFExportExcel annotation = (HSSFExportExcel) parameter.getPropertyAnnotation(HSSFExportExcel.class);
        if (Objects.nonNull(annotation)) {
            Parameter parameter1 = new Parameter(parameter, annotation);
            this.setCell(parameter1);
            return parameter1;
        }
        return null;
    }

    @Override
    protected void setCell(ArgumentParameter argumentParameter) {
        this.setLocalCell((Parameter) argumentParameter);
    }

    @Override
    public void setTitle(PropertyParameter parameter) {
        Sheet sheet = parameter.getWorkbookParameter().getSheet();
        Row row = parameter.getRow();
        Field field = parameter.getField();
        HSSFExportExcel annotation = field.getDeclaredAnnotation(HSSFExportExcel.class);
            // 设置列标题
            if (annotation != null && annotation.title().length() > 0) {
                String title = annotation.title();
                int index = annotation.index();
                Cell cell = row.createCell(index);
                cell.setCellValue(title);
            }
            // 设置宽度
            if (annotation != null && annotation.width() != 0) {
                int index = annotation.index();
                int width = annotation.width();
                sheet.setColumnWidth(index, width * 256);
            }
    }

    //设置单元格
    private void setLocalCell(Parameter parameter) {
        Cell cell = parameter.getCell();
        Field field = parameter.getField();
        field.setAccessible(true);
        CellStyle style = parameter.getCellStyle();
        try {
            Object methodValue = field.get(parameter.getTarget());
            if (Objects.nonNull(methodValue)) {
                if (StringUtils.isNotBlank(parameter.getPattern())) {
                    if (methodValue instanceof Date) {
                    DataFormat dataFormat = parameter.getDateFormat();
                    style.setDataFormat(dataFormat.getFormat(parameter.getPattern()));
                    cell.setCellValue((Date) methodValue);
                    }
                } else {
                    cell.setCellValue(methodValue.toString());
                }
            }
            style.setAlignment(parameter.getAlign());
            cell.setCellStyle(style);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public static class Parameter<T> extends ArgumentParameter {
        private final int index;
        private final String title;
        private final int width;
        private final String pattern ;
        private final short align;

        private final T target;
        private final CellStyle cellStyle;
        private final Cell cell;
        private final Field field;
        private final DataFormat dateFormat;

        public Parameter(PropertyParameter<T> parameter, HSSFExportExcel annotation) {
            this.target = parameter.getTarget();
            this.cell = parameter.getRow().createCell(annotation.index());
            this.field = parameter.getField();
            this.index = annotation.index();
            this.title = annotation.title();
            this.width = annotation.width();
            this.pattern = annotation.pattern();
            this.dateFormat = parameter.getWorkbook().createDataFormat();
            this.cellStyle = parameter.getWorkbookParameter().getCellStyle();
            this.align = parameter.getWorkbookParameter().getBeanParameter().getAlign();
        }

        public int getIndex() {
            return index;
        }

        public String getTitle() {
            return title;
        }


        public int getWidth() {
            return width;
        }

         public String getPattern() {
             return pattern;
         }

         public DataFormat getDateFormat() {
            return dateFormat;
        }

         public Field getField() {
             return field;
         }

         public T getTarget() {
             return target;
         }

         public Cell getCell() {
             return cell;
         }

         public CellStyle getCellStyle() {
             return cellStyle;
         }

         public short getAlign() {
             return align;
         }
     }

}