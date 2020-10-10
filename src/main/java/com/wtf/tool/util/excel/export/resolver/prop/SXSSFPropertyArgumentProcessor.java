package com.wtf.tool.util.excel.export.resolver.prop;

import com.wtf.tool.util.excel.export.annotation.SXSSFExportExcel;
import com.wtf.tool.util.excel.export.param.PropertyParameter;
import com.wtf.tool.util.excel.export.test.SXSSFExportExcelDemo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @auther: strugglesnail
 * @date: 2020/10/8 16:43
 * @desc: SXSSF注解解析
 */
public class SXSSFPropertyArgumentProcessor extends AbstractPropertyArgumentProcessor implements PropertyArgumentResolver {


    @Override
    public boolean supportsProperty(PropertyParameter parameter) {
        return parameter.hasPropertyAnnotation(SXSSFExportExcel.class);
    }

    @Override
    public Object resolverProperty(PropertyParameter parameter) {
        SXSSFExportExcel annotation = (SXSSFExportExcel) parameter.getPropertyAnnotation(SXSSFExportExcel.class);
        if (Objects.nonNull(annotation)) {
            Parameter parameter1 = new Parameter(parameter, annotation);
            this.setCell(parameter1);
            return parameter1;
        }
        return null;
    }

    // 设置单元格
    @Override
    protected void setCell(ArgumentParameter argumentParameter) {
        this.setLocalCell((Parameter) argumentParameter);
    }


    // 解析标题
    @Override
    public void setTitle(PropertyParameter parameter) {
        Field[] fields = parameter.getFields();

        Sheet sheet = parameter.getWorkbookParameter().getSheet();
        Row row = parameter.getRow();
        for (Field field : fields) {
            SXSSFExportExcel annotation = field.getDeclaredAnnotation(SXSSFExportExcel.class);

            if ("merge0".equals(field.getName())) {
                return;
            }
            // 设置列标题
//            if (annotation != null && annotation.title().length() > 0) {
//////                String offset = annotation.offset();
//////                OffsetModel offsetModel = getOffsetModel(offset);
//////                sheet.addMergedRegion(new CellRangeAddress(offsetModel.getStartRow(), offsetModel.getEndRow(), offsetModel.getStartCol(), offsetModel.getEndCol()));
//////
//////                int index = annotation.index();
//////                String title = annotation.title();
//////                Cell cell = row.createCell(index);
//////                cell.setCellValue(title);
//////            }
            // 设置宽度
            if (annotation != null && annotation.width() != 0) {
                int index = annotation.index();
                int width = annotation.width();
                sheet.setColumnWidth(index, width * 256);
            }
        }
    }

    // 解析单元格偏移位置
    private static OffsetModel getOffsetModel(String title, String offset) {
//        String[] titles = title.split(",");
        String[] offsets = offset.split(",");
        OffsetModel model = new OffsetModel();
        model.setTitle(title);
        model.setStartRow(Integer.valueOf(offsets[0]));
        model.setEndRow(Integer.valueOf(offsets[1]));
        model.setStartCol(Integer.valueOf(offsets[2]));
        model.setEndCol(Integer.valueOf(offsets[3]));
        return model;
    }

    //设置单元格
    private void setLocalCell(Parameter parameter) {
        Cell cell = parameter.getCell();
        Field field = parameter.getField();
        if ("merge0".equals(field.getName())) {
            return;
        }
        System.out.println(field.getName());
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
//        private final String title;
        private final String[] titles;
        private final int width;
        private final String pattern ;
        private final short align;

        private final T target;
        private final CellStyle cellStyle;
        private final Cell cell;
        private final Field field;
        private final DataFormat dateFormat;

        public Parameter(PropertyParameter<T> parameter, SXSSFExportExcel annotation) {
            this.target = parameter.getTarget();
            this.cell = parameter.getRow().createCell(annotation.index());
            this.field = parameter.getField();
            this.index = annotation.index();
            this.titles = annotation.title();
            this.width = annotation.width();
            this.pattern = annotation.pattern();
            this.dateFormat = parameter.getWorkbook().createDataFormat();
            this.cellStyle = parameter.getWorkbookParameter().getCellStyle();
            this.align = parameter.getWorkbookParameter().getBeanParameter().getAlign();
        }

        public int getIndex() {
            return index;
        }

//        public String getTitle() {
//            return title;
//        }
        public String[] getTitles() {
            return titles;
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

    private static class OffsetModel {
        private String title;
        private int startRow;
        private int endRow;
        private int startCol;
        private int endCol;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getStartCol() {
            return startCol;
        }

        public void setStartCol(int startCol) {
            this.startCol = startCol;
        }

        public int getEndCol() {
            return endCol;
        }

        public void setEndCol(int endCol) {
            this.endCol = endCol;
        }

        @Override
        public String toString() {
            return "OffsetModel{" +
                    "title='" + title + '\'' +
                    ", startRow=" + startRow +
                    ", endRow=" + endRow +
                    ", startCol=" + startCol +
                    ", endCol=" + endCol +
                    '}';
        }
    }

    public static void main(String[] args) {
        Field[] fields = SXSSFExportExcelDemo.class.getDeclaredFields();
        int maxRow = getMaxRow(fields);
        System.out.println(maxRow);
        Map<Integer, List<OffsetModel>> modelMap = new HashMap<>();
        for (int k = 0; k < maxRow; k++) {
            List<OffsetModel> models = new ArrayList<>();
            for (int i = 0; i < fields.length; i++) {
                SXSSFExportExcel annotation = fields[i].getDeclaredAnnotation(SXSSFExportExcel.class);
                if (annotation != null) {
                    String[] titles = annotation.title();
                    String[] offsets = annotation.offset();
                    if (titles.length > 1 && k >= 1) {
                        OffsetModel offsetModel = getOffsetModel(titles[k], offsets[k]);
                        models.add(offsetModel);
                    } else if (titles.length == 1 && k == 0){
                        OffsetModel offsetModel = getOffsetModel(titles[0], offsets[0]);
                        models.add(offsetModel);
                    }
                }
            }
            modelMap.put(k, models);
        }
        System.out.println(modelMap);
    }

    // 获取最大行数
    private static int getMaxRow(Field[] fields) {
        int[] maxRow = new int[10];
        for (int i = 0; i < fields.length; i++) {
            SXSSFExportExcel annotation = fields[i].getDeclaredAnnotation(SXSSFExportExcel.class);
            if (annotation != null) {
                String[] title = annotation.title();
                maxRow[i] = title.length;
            }
        }
        return Arrays.stream(maxRow).max().getAsInt();
    }

}
