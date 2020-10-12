package com.wtf.tool.util.excel.export.resolver.prop;

import com.wtf.tool.util.excel.export.annotation.SXSSFExportExcel;
import com.wtf.tool.util.excel.export.generator.StyleGenerator;
import com.wtf.tool.util.excel.export.param.PropertyParameter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

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


    // 解析标题
    @Override
    public void resolverHeader(PropertyParameter parameter) {
        // 设置标题
        this.setTitle(parameter);
        // 设置表头
        this.setHeader(parameter);
    }

    @Override
    protected void setTitle(PropertyParameter parameter) {
    }

    // 设置表头
    @Override
    protected void setHeader(PropertyParameter parameter) {
        Field[] fields = parameter.getFields();
        Workbook workbook = parameter.getWorkbookParameter().getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        // 获取自定义表头样式
        StyleGenerator styleGenerator = parameter.getWorkbookParameter().getBeanParameter().getStyleGenerator();
        styleGenerator.setColor(cellStyle);
        styleGenerator.setBorder(cellStyle);
        styleGenerator.setAlignment(cellStyle);
        Sheet sheet = parameter.getWorkbookParameter().getSheet();

        // 默认标题为空：标题为空情况下不会向下移一行
        String title = parameter.getWorkbookParameter().getBeanParameter().getTitle();
        boolean isTitleEmpty = StringUtils.isNotBlank(title) ? false: true;
        List<Integer> lastCellCols = new ArrayList<>();
        Map<Integer, List<OffsetModel>> headerRowMap = new HashMap<>();
        getRowTitleMap(fields, headerRowMap, lastCellCols);

        // 标题不为空情况
        if (!isTitleEmpty) {
            // 获取最大表头单元格最后一列下标
            int maxLastColIndex = getMaxHeaderCellColIndex(lastCellCols);
            // 创建标题
            OffsetModel model = new OffsetModel();
            List<OffsetModel> titleOffsetList = new ArrayList<>();
            model.setTitle(title);
            model.setStartRow(0);
            model.setEndRow(0);
            model.setStartCol(0);
            model.setEndCol(maxLastColIndex);
            titleOffsetList.add(model);
            Map<Integer,List<OffsetModel>> titleMap = new HashMap<>();
            titleMap.put(0, titleOffsetList);
            // 重新创建表头
            getRowTitleMap(fields, titleMap, null);
            headerRowMap = titleMap;
        }

        headerRowMap.forEach((k, v) -> {
            int index = k;
            for (OffsetModel offsetModel : v) {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(offsetModel.getStartRow(), offsetModel.getEndRow(), offsetModel.getStartCol(), offsetModel.getEndCol());
                sheet.addMergedRegion(cellRangeAddress);
                sheet.setColumnWidth(offsetModel.getStartCol(), offsetModel.getWidth() * 256);
                styleGenerator.setMergedRegionBorder(cellStyle, cellRangeAddress, sheet, workbook);
            }
            Row row = sheet.getRow(index);
            if (row == null) {
                row = sheet.createRow(index);
            }

            System.out.println("------------" + index + row);
            for (OffsetModel offsetModel : v) {
                Cell cell = row.createCell(offsetModel.getStartCol());
                cell.setCellValue(offsetModel.getTitle());
                cell.setCellStyle(cellStyle);
                System.out.println(offsetModel.getTitle() + "---" + offsetModel.getStartCol());
            }
        });
    }

    @Override
    public Object resolverProperty(PropertyParameter parameter) {
        SXSSFExportExcel annotation = (SXSSFExportExcel) parameter.getPropertyAnnotation(SXSSFExportExcel.class);
        if (Objects.nonNull(annotation)) {
            this.setCell(new Parameter(parameter, annotation));
        }
        return null;
    }

    // 设置单元格
    @Override
    protected void setCell(ArgumentParameter argumentParameter) {
        this.setLocalCell((Parameter) argumentParameter);
    }




    // 解析单元格偏移位置
    private static OffsetModel getOffsetModel(String title, int width, String offset) {
        String[] offsets = offset.split(",");
        OffsetModel model = new OffsetModel();
        model.setTitle(title);
        model.setStartRow(Integer.valueOf(offsets[0]));
        model.setEndRow(Integer.valueOf(offsets[1]));
        model.setStartCol(Integer.valueOf(offsets[2]));
        model.setEndCol(Integer.valueOf(offsets[3]));
        model.setWidth(width);
        return model;
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
            parameter.styleGenerator.setAlignment(style);
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
        private final StyleGenerator styleGenerator;

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
            this.styleGenerator = parameter.getWorkbookParameter().getBeanParameter().getStyleGenerator();
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

    }

    private static class OffsetModel {
        private String title;
        private int startRow;
        private int endRow;
        private int startCol;
        private int endCol;
        private int width;

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

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

    }

    public static void main(String[] args) {
//        Field[] fields = SXSSFExportExcelDemo.class.getDeclaredFields();
//        Map<Integer, List<OffsetModel>> modelMap = getRowTitleMap(fields);
//        System.out.println(modelMap);
    }

    private static void getRowTitleMap(Field[] fields, Map<Integer, List<OffsetModel>> modelMap, List<Integer> lastCellCol) {
        int maxRow = getMaxRow(fields);
        for (int k = 0; k < maxRow; k++) {
            int index = k;
            List<OffsetModel> models = getOffsetModels(fields, k);
            // 获取当前行最后一个单元格所在列
            if (lastCellCol != null) {
                getMaxHeaderRowCellCount(models, lastCellCol);
            }
            // 获取表头每一行的单元格数
            if (modelMap.containsKey(k)) {
                index ++;
            }
            modelMap.put(index, models);
        }
    }

    // 获取表头列表
    private static List<OffsetModel> getOffsetModels(Field[] fields, int k) {
        List<OffsetModel> models = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            SXSSFExportExcel annotation = fields[i].getDeclaredAnnotation(SXSSFExportExcel.class);
            if (annotation != null) {
                String[] titles = annotation.title();
                String[] offsets = annotation.offset();
                int width = annotation.width();
                if (titles.length - 1 >= k) {
                    OffsetModel offsetModel = getOffsetModel(titles[k], width, offsets[k]);
                    models.add(offsetModel);
                }
            }

            List<OffsetModel> modelList = new ArrayList<>(models.size());
            for (OffsetModel model : models) {
                boolean b = modelList.stream().anyMatch(ml -> ml.getTitle().equals(model.getTitle()));
                if (!b) {
                    modelList.add(model);
                }
            }
            models = modelList;
        }
        return models;
    }

    // 获取表头最后一个单元格所在的列
    private static void getMaxHeaderRowCellCount(List<OffsetModel> models, List<Integer> lastCellCol) {
        for (int i = 0; i < models.size(); i++) {
            lastCellCol.add(models.get(i).getEndCol());
        }
    }

    // 获取数组最大值
    private int getMaxHeaderCellColIndex(List<Integer> lastCellCols) {
        return lastCellCols.stream().reduce((curr, next) -> curr < next ? next : curr).get();
    }

    // 如果是默认宽度，则合并单元格也统一此宽度
//    private static int getDefaultWidth(int[] widths, int index) {
//        if (widths.length == 1) {
//            return widths[0];
//        }
//        System.out.println(Arrays.toString(widths) + " " + widths[index]);
//        return widths[index];
//    }

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
