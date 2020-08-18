package com.wtf.tool.util.excel;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.wtf.tool.annotation.ImportExcel;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
/**
 * @author wangtengfei
 * @date 2019/3/15 11:53
 * @description: Excel导入模板
 */
public class ImportExcelUtils {

    // 源数据
    private MultipartFile file;
    // Excel sheet名
    private String sheetName;
    // 从第几行获取数据
    private int rowIndex;
    // 从第几列获取数据
    private int colIndex;

    // 只设置file、sheetName时rowIndex默认1 colIndex默认0
    public ImportExcelUtils(MultipartFile file, String sheetName) {
        this(file, sheetName, 1 , 0);
    }

    // 只设置file、sheetName、rowIndex， colIndex默认0
    public ImportExcelUtils(MultipartFile file, String sheetName, int rowIndex) {
        this(file, sheetName, rowIndex , 0);
    }

    public ImportExcelUtils(MultipartFile file, String sheetName, int rowIndex, int colIndex) {
        this.file = file;
        this.sheetName = sheetName;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }


    //将单元格数据list与泛型合并入口API
    public <T> List<T> getExcelData(Class<T> clazz){
        return createListData(getExcelCell(), clazz);
    }

    //获取Excel数据集合
    private List<List<Object>> getExcelCell(){
        List<List<Object>> cellsList = null;
        try {
            InputStream inputStream = file.getInputStream();
            //获取Excel并解析内容
            Workbook book = WorkbookFactory.create(inputStream);
            //获取sheet
            Sheet sheet = book.getSheet(sheetName);
            if (sheet != null){
                cellsList = new ArrayList<>();//存储行列表
                for (int i = rowIndex; i <= sheet.getLastRowNum(); i++) {
                    List<Object> cells = new ArrayList<>();//每一列的单元格数据
                    Row row = sheet.getRow(i);
                    //存储每一列的单元格到list
                    for (int j = colIndex; j < row.getLastCellNum(); j++) {
                        if (row.getCell(j) != null){
                            cells.add(this.getCellValue(row.getCell(j)));
                        }
                    }
                    cellsList.add(cells);
                }
            }
        } catch (OfficeXmlFileException ofe) {
            ofe.printStackTrace();
        } catch (InvalidFormatException ife) {
            ife.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellsList;
    }

    /**
     *
     *   CELL_TYPE_NUMERIC 数值型 0
     *   CELL_TYPE_STRING 字符串型 1
     *   CELL_TYPE_FORMULA 公式型 2
     *   CELL_TYPE_BLANK 空值 3
     *   CELL_TYPE_BOOLEAN 布尔型 4
     *   CELL_TYPE_ERROR 错误 5
     *   @return 根据单元格类型获取相应值
     **/
    public Object getCellValue(Cell cell) {
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
    private <T> List<T> createListData(List<List<Object>> cellsList, Class<T> clazz){
        List<T> tList = new ArrayList<>();
        try {
            // 每行的数据
            for (List<Object> cells: cellsList) {
                // 过滤一行数据都为空的行
                boolean anyMatch = cells.stream().anyMatch(cell -> Objects.nonNull(cell));
                if (!anyMatch) {
                    continue;
                }
                //创建泛型对象
                T generic = createGeneric(clazz);
                //每一行的单元格数据
                tList.add(this.getAttribute(generic, cells));
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
                this.setField(t, cellList.get(index), field);
            }
        }
        return t;
    }

    //获取字段值并传入对象
    private <T> void setField(T t, Object cellValue, Field field) throws IllegalAccessException, ParseException {
        if (Objects.isNull(cellValue)) {
            return;
        }
        field.set(t, cellValue);
//        String type = field.getGenericType().toString();
//        if (type.contains("String")){
//            field.set(t, cellArg);
//        } else if (type.contains("Byte") || type.contains("byte")){
//            field.set(t, Byte.valueOf(cellArg));
//        } else if (type.contains("Short") || type.contains("short")){
//            field.set(t, Short.valueOf(cellArg));
//        } else if (type.contains("Integer") || type.contains("int")){
//            field.set(t, Integer.valueOf(cellArg));
//        } else if (type.contains("Long") || type.contains("long")){
//            field.set(t, Long.valueOf(cellArg));
//        } else if (type.contains("Float") || type.contains("float")){
//            field.set(t, Float.valueOf(cellArg));
//        } else if (type.contains("Double") || type.contains("double")){
//            field.set(t, Double.valueOf(cellArg));
//        } else if (type.contains("BigDecimal")){
//            field.set(t, BigDecimal.valueOf(Long.valueOf(cellArg)));
//        } else if (type.contains("Boolean") || type.contains("boolean")){
//            field.set(t, Boolean.valueOf(cellArg));
//        } else if (type.contains("Character") || type.contains("char")){
//            field.set(t, cellArg.toCharArray()[0]);
//        }  else if (type.contains("Date")){
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            Date data = format.parse(cellArg);
//            java.sql.Date sqlDate = new java.sql.Date(data.getTime());
//            field.set(t, sqlDate);
//        }
    }

    //测试导入模板
    public <T> List<T> testInsertExcel(Class t){
        //单元格测试数据
        List<Object> dataCell = new ArrayList<>();
        dataCell.add("admin");
        dataCell.add("123456");
        dataCell.add("593616590@qq.com");
        dataCell.add(new Date());
        List<Object> dataCell2 = new ArrayList<>();
        dataCell2.add("root");
        dataCell2.add("111111");
        dataCell2.add("13856890104@163.com");
        dataCell2.add(new Date());
        List<List<Object>> lists = new ArrayList<>();
        lists.add(dataCell);
        lists.add(dataCell2);
        return this.createListData(lists, t);
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ImportExcelUtils utils = new ImportExcelUtils(null, "11");
        System.out.println(utils.testInsertExcel(ExcelDemo.class));
//        getListData(ExcelDemo.class);
    }
}


