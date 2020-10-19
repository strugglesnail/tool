package com.wtf.tool;

import com.wtf.tool.util.excel.export.factory.DefaultWorkbookExportFactory;
import com.wtf.tool.util.excel.export.test.HSSFExportExcelDemo;
import com.wtf.tool.util.excel.export.test.SXSSFExportExcelDemo;
import com.wtf.tool.util.excel.export.test.XSSFExportExcelDemo;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@SpringBootTest
public class ExcelTest {

    @Test
    public void testHSSF() throws IOException {
        List<HSSFExportExcelDemo> demos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            HSSFExportExcelDemo excelDemo = new HSSFExportExcelDemo();
            excelDemo.setName("文件导出" + i);
            excelDemo.setSheetName("sheet名称" + i);
            excelDemo.setType("excel类型" + i);
            excelDemo.setDate(new Date());
            demos.add(excelDemo);
        }
        getTestExcel(demos, HSSFExportExcelDemo.class);
    }

    @Test
    public void testXSSF() throws IOException {
        List<XSSFExportExcelDemo> demos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            XSSFExportExcelDemo excelDemo = new XSSFExportExcelDemo();
            excelDemo.setName("文件导出" + i);
            excelDemo.setSheetName("sheet名称" + i);
            excelDemo.setType("excel类型" + i);
            excelDemo.setDate(new Date());
            demos.add(excelDemo);
        }
        getTestExcel(demos, XSSFExportExcelDemo.class);
    }

    @Test
    public void testSXSSF() throws IOException {
        List<SXSSFExportExcelDemo> demos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SXSSFExportExcelDemo excelDemo = new SXSSFExportExcelDemo();
            excelDemo.setName("文件导出" + i);
            excelDemo.setSheetName("sheet名称" + i);
            excelDemo.setType("excel类型" + i);
            excelDemo.setDate(new Date());
            demos.add(excelDemo);
        }
        getTestExcel(demos, SXSSFExportExcelDemo.class);
    }

    private void getTestExcel(List<?> demos, Class c) throws IOException {
        DefaultWorkbookExportFactory factory = new DefaultWorkbookExportFactory(c);
        Workbook workbook = factory.exportWorkbook(demos);
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\86188\\Desktop\\文件导出模板.xlsx");
        workbook.write(outputStream);
        outputStream.close();
    }

}
