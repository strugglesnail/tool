package com.wtf.tool.util.excel.export.resolver.prop;

import com.wtf.tool.util.excel.export.annotation.XSSFExportExcel;
import com.wtf.tool.util.excel.export.generator.StyleGenerator;
import com.wtf.tool.util.excel.export.param.PropertyParameter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Objects;

/**
 * @auther: strugglesnail
 * @date: 2020/10/8 16:42
 * @desc: XSSF注解解析
 */
public class XSSFPropertyArgumentProcessor extends AbstractPropertyArgumentProcessor implements PropertyArgumentResolver {


    @Override
    public boolean supportsProperty(PropertyParameter parameter) {
        return parameter.hasPropertyAnnotation(XSSFExportExcel.class);
    }

    @Override
    protected void setHeader(PropertyParameter parameter) {

    }

    @Override
    public void resolverHeader(PropertyParameter parameter) {

    }

    @Override
    public Object resolverProperty(PropertyParameter parameter) {
        XSSFExportExcel annotation = (XSSFExportExcel) parameter.getPropertyAnnotation(XSSFExportExcel.class);
        if (Objects.nonNull(annotation)) {
            this.setCell(new Parameter(parameter, annotation));
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
        XSSFExportExcel annotation = field.getDeclaredAnnotation(XSSFExportExcel.class);
            // 设置列标题
            if (annotation != null && annotation.title().length() > 0) {
                int index = annotation.index();
                String title = annotation.title();
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
            parameter.getStyleGenerator().setAlignment(style);
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

        private final T target;
        private final CellStyle cellStyle;
        private final Cell cell;
        private final Field field;
        private final DataFormat dateFormat;
        private final StyleGenerator styleGenerator;

         public Parameter(PropertyParameter<T> parameter, XSSFExportExcel annotation) {
            this.target = parameter.getTarget();
            this.cell = parameter.getRow().createCell(annotation.index());
            this.field = parameter.getField();
            this.index = annotation.index();
            this.title = annotation.title();
            this.width = annotation.width();
            this.pattern = annotation.pattern();
            this.dateFormat = parameter.getWorkbook().createDataFormat();
            this.cellStyle = parameter.getWorkbookParameter().getCellStyle();
            this.styleGenerator = parameter.getWorkbookParameter().getBeanParameter().getStyleGenerator();

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

         public StyleGenerator getStyleGenerator() {
             return styleGenerator;
         }
     }

}
