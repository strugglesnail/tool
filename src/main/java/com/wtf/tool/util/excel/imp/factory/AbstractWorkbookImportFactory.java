package com.wtf.tool.util.excel.imp.factory;

import com.wtf.tool.annotation.ImportExcel;
import com.wtf.tool.util.excel.imp.annotation.ImportBaseExcel;
import com.wtf.tool.util.excel.imp.handler.ImportDataHandler;
import com.wtf.tool.util.excel.imp.param.WorkbookParameter;
import com.wtf.tool.util.excel.util.AnnotationUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @auther strugglesnail
 * @date 2020/10/19 20:58
 * @desc Excel文件导入
 */
public abstract class AbstractWorkbookImportFactory implements WorkbookImportFactory {


//    private WorkbookParameter parameter;

    protected WorkbookParameter getParameter(InputStream dataSource, Class target) {
        ImportBaseExcel annotation = AnnotationUtils.getAnnotation(ImportBaseExcel.class, target);
        if (Objects.isNull(annotation)) {
            throw new IllegalArgumentException(target.getSimpleName() + " class missing @ImportBaseExcel annotation");
        }
        WorkbookParameter parameter = new WorkbookParameter(dataSource, annotation.sheetName(), annotation.rowIndex(), annotation.colIndex(), annotation.handler());
//        this.parameter = parameter;
        return parameter;
    }

    //获取Excel数据集合
    protected Map<Integer, List<Object>> getExcelCell(WorkbookParameter parameter){
        String sheetName = parameter.getSheetName();
        int rowIndex = parameter.getRowIndex();
        int colIndex = parameter.getColIndex();
//        if (parameter.getFile() == null || parameter.getFile().isEmpty()) {
//            throw new IllegalArgumentException("The MultipartFile can not be empty");
//        }
        Map<Integer, List<Object>> singleSheetData = null;
        try {
            Workbook book = createWorkbook(parameter.getDataSource());
            //获取sheet
            Sheet sheet = book.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("sheet can not be empty");
            }
            singleSheetData = this.getSingleSheetData(sheet, rowIndex, colIndex);
        } catch (OfficeXmlFileException ofe) {
            ofe.printStackTrace();
        } catch (InvalidFormatException ife) {
            ife.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return singleSheetData;
    }

    // 获取单个sheet的数据
    private Map<Integer, List<Object>> getSingleSheetData(Sheet sheet, int rowIndex, int colIndex) {
        //存储行列表
        Map<Integer, List<Object>> cellsMap = new HashMap<>();
        for (int i = rowIndex; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            //获取每一行的单元格
            List<Object> cells = getCells(row, colIndex);
            cellsMap.put(i, cells);
        }
        return cellsMap;
    }

    // 存储每一行的单元格
    private List<Object> getCells(Row row, int colIndex) {
        List<Object> cells = new ArrayList<>();//每一列的单元格数据
        for (int j = colIndex; j < row.getLastCellNum(); j++) {
            if (row.getCell(j) != null){
                cells.add(this.getCellValue(row.getCell(j)));
            }
        }
        return cells;
    }

    // 获取单元格数据
    private Object getCellValue(Cell cell) {
        Object value = null;
        int cellType = cell.getCellType();
        switch (cellType) {
            case Cell.CELL_TYPE_NUMERIC:
                value = cell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                value = cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            default:
                break;
        }
        return value;
    }

    //将单元格数据存储在list
    protected <T> List<T> createListData(Map<Integer, List<Object>> rowsMap, Class<T> clazz, ImportDataHandler<T> handler){
        if (rowsMap == null) {
            throw new IllegalArgumentException("get rows can not be empty");
        }
        List<T> tList = new ArrayList<>();
        try {
            // 每行的数据
            for(Map.Entry<Integer, List<Object>> entry : rowsMap.entrySet()) {
                List<Object> cells = entry.getValue();
                // 过滤一行数据都为空的行
                boolean anyMatch = cells.stream().anyMatch(cell -> Objects.nonNull(cell));
                if (!anyMatch) {
                    continue;
                }
                // 创建泛型对象
                T generic = createGeneric(clazz);
                // 每一行的单元格数据
                tList.add(this.getAttribute(generic, cells));

                // 给一个机会处理导入的行数据
                if (handler != null) {
                    handler.handlerRow(generic);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return tList;
    }

    //创建泛型对象
    private <T> T createGeneric(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }

    // 匹配对象属性并填充值
    private <T> T getAttribute(T t, List<Object> cellList) throws Exception{
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field: fields) {
            field.setAccessible(true);
            // 为字段赋值
            ImportExcel annotation = field.getDeclaredAnnotation(ImportExcel.class);
            // 对excel数据/对象属性进行匹配
            if (annotation != null && annotation.index() >= 0) {
                // 根据设置的下标获取单元格值并设置字段
                int index = annotation.index();
                if (index > cellList.size() - 1) {
                    continue;
                }
                this.setField(t, cellList.get(index), field);
            }
        }
        return t;
    }

    //获取字段值并传入对象
    private <T> void setField(T t, Object cellValue, Field field) throws IllegalAccessException {
        if (Objects.isNull(cellValue)) {
            return;
        }
        field.set(t, cellValue);

        // 给一个机会处理导入的字段值
//        parameter.getHandler().handlerCellValue(t);
    }

}
