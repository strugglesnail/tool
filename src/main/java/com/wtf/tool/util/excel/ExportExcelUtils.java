package com.wtf.tool.util.excel;



import com.wtf.tool.annotation.ExportExcel;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author wang_tengfei
 * @desc 导出工具类
 */
public class ExportExcelUtils {

    private Workbook book;

    private CellStyle style;

    private Sheet sheet;

    // 用于设置日期
    private CreationHelper createHelper;

    private String sheetName;

    // 靠左
    private boolean alignLeft = false;
    // 靠右
    private boolean alignRight = false;

    // 设置列标题所在行：默认第二行
    private int rowIndex = 1;


    // 构建建造者
    private ExportExcelUtils(Builder builder) {
        sheetName = builder.sheetName;
        alignLeft = builder.alignLeft;
        alignRight = builder.alignRight;
        rowIndex = builder.rowIndex;

        // 获取工作簿
        this.book = new HSSFWorkbook();
        // 设置sheet名称
        this.sheet = book.createSheet(sheetName);
        // 设置日期格式
        this.createHelper = book.getCreationHelper();
        // 设置样式
        this.style = setStyle(book);
    }

    // 获取工作簿
    public <T> Workbook buildWorkbook(List<T> dataList, Class<T> clazz) {
        // 设置标题
        setTitleAndWidth(clazz);
        // 设置单元格
        setCell(dataList);
        return book;
    }


    // 设置列标题
    private void setTitleAndWidth(Class t) {
        Row row = sheet.createRow(rowIndex);
        Field[] fields = t.getDeclaredFields();
        for (Field field : fields) {
            ExportExcel annotation = field.getDeclaredAnnotation(ExportExcel.class);
            // 设置列标题
            if (annotation != null && annotation.title().length() > 0) {
                String title = annotation.title();
                int index = annotation.index();
                Cell cell = row.createCell(index);
                cell.setCellStyle(style);
                style.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
                style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                cell.setCellValue(title);
            }
            // 设置宽度
            if (annotation != null && annotation.width() != 0) {
                int index = annotation.index();
                int width = annotation.width();
                sheet.setColumnWidth(index,width * 256);
            }
        }
    }

    //
    private <T> void setCell(List<T> dataList) {
        int index = rowIndex;
        for (T t: dataList) {
            Row row = sheet.createRow(++index);
            Field[] fields = t.getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    this.setCell(row, t, field);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 设置单元格
    private <T> void setCell(Row row, T t, Field field) throws IllegalAccessException {
        ExportExcel annotation = field.getDeclaredAnnotation(ExportExcel.class);
        if (annotation != null) {
            field.setAccessible(true);
            Object methodValue = field.get(t);
            if (Objects.nonNull(methodValue)) {
                // 决定导出内容所在列
                int index = annotation.index();
                String format = annotation.date();
                setCell(row, methodValue, index, format);
            }
        }
    }


    //设置单元格
    private void setCell(Row row, Object val, Integer col, String format) {
        Cell cell = row.createCell(col);
        if (StringUtils.isNotBlank(format)) {
            if (val instanceof Date) {
                DataFormat dataFormat = createHelper.createDataFormat();
                style.setDataFormat(dataFormat.getFormat(format));
                cell.setCellValue((Date) val);

            }
        } else {
            cell.setCellValue(val.toString());
        }
        cell.setCellStyle(style);

    }


    // 设置表格样式
    private CellStyle setStyle(Workbook book) {
        CellStyle style = book.createCellStyle();
        short align;
        if (alignLeft) {
            align = CellStyle.ALIGN_LEFT;
        } else if (alignRight) {
            align = CellStyle.ALIGN_RIGHT;
        } else {
            align = CellStyle.ALIGN_CENTER;
        }
        style.setAlignment(align); // 靠左/居中/靠右
        return style;
    }



    public static final class Builder {
        private String sheetName;
        private boolean alignLeft;
        private boolean alignRight;
        private int rowIndex;

        public Builder() {
        }


        public Builder sheetName(String val) {
            sheetName = val;
            return this;
        }

        public Builder alignLeft(boolean val) {
            alignLeft = val;
            return this;
        }

        public Builder alignRight(boolean val) {
            alignRight = val;
            return this;
        }

        public Builder rowIndex(int val) {
            rowIndex = val;
            return this;
        }

        public ExportExcelUtils build() {
            return new ExportExcelUtils(this);
        }
    }


    // 测试
    public static void main(String[] args) throws IOException, IllegalAccessException {
        List<ExcelDemo> excelDemos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ExcelDemo excelDemo = new ExcelDemo();
            excelDemo.setName("文件导出" + i);
            excelDemo.setSheetName("sheet名称" + i);
            excelDemo.setType("excel类型" + i);
            excelDemo.setDate(new Date());
            excelDemos.add(excelDemo);
        }
        ExportExcelUtils utils = new Builder()
                .sheetName("文件导出")
                .rowIndex(0)
                .alignLeft(true)
                .build();
        Workbook workbook = utils.buildWorkbook(excelDemos, ExcelDemo.class);
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\wtf\\Desktop\\文件导出模板.xlsx");
        workbook.write(outputStream);
//        ExcelDemo excelDemo = new ExcelDemo();
//        excelDemo.setType("1");
//        excelDemo.setSheetName("sheet");
//        excelDemo.setName("name");
//        Field[] fields = excelDemo.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            field.setAccessible(true);
//            Object o = field.get(excelDemo);
//            System.out.println(field.getName() + "-----" + field.get(excelDemo));
//        }
    }
}
