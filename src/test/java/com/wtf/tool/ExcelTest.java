package com.wtf.tool;

import com.wtf.tool.util.excel.ImportExcelUtils;
import com.wtf.tool.util.excel.export.factory.DefaultWorkbookExportFactory;
import com.wtf.tool.util.excel.export.test.HSSFExportExcelDemo;
import com.wtf.tool.util.excel.export.test.SXSSFExportExcelDemo;
import com.wtf.tool.util.excel.export.test.XSSFExportExcelDemo;
import com.wtf.tool.util.excel.imp.factory.DefaultWorkbookImportFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static void getTestExcel(List<?> demos, Class c) throws IOException {
        DefaultWorkbookExportFactory factory = new DefaultWorkbookExportFactory(c);
        Workbook workbook = factory.exportWorkbook(demos);
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\user\\Desktop\\文件导出模板.xlsx");
        workbook.write(outputStream);
        outputStream.close();
    }


    // 文件导入
    @Test
    private void testImport() throws FileNotFoundException {
        System.out.println(1);
        FileInputStream inputStream = new FileInputStream("C:\\Users\\user\\Desktop\\产品信息导出(1)(1).xlsx");
        DefaultWorkbookImportFactory importFactory = new DefaultWorkbookImportFactory();
        List<ProductDemo> excelData = importFactory.getExcelData(inputStream, ProductDemo.class);
        System.out.println(excelData);
    }

    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("C:\\Users\\user\\Desktop\\产品信息导出.xlsx");
        DefaultWorkbookImportFactory importFactory = new DefaultWorkbookImportFactory();
        List<ProductDemo> excelData = importFactory.getExcelData(inputStream, ProductDemo.class);
        for (ProductDemo excelDatum : excelData) {
            Object detail = excelDatum.getDetail();
//            String s = detail == null ? "" : detail.toString();
            if (Objects.nonNull(detail)) {
                excelDatum.setDetail(delHTMLTag(detail.toString()));
            }
        }
        System.out.println(excelData);
        getTestExcel(excelData, ProductDemo.class);
    }


    public static String delHTMLTag(String htmlStr) {
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签
        htmlStr = htmlStr.replace(" ", "");
        htmlStr = htmlStr.replaceAll("\\s*|\t|\r|\n", "");
        htmlStr = htmlStr.replace("“", "");
        htmlStr = htmlStr.replace("”", "");
        htmlStr = htmlStr.replaceAll("　", "");
        htmlStr = htmlStr.replaceAll("●", "");
        return htmlStr.trim(); //返回文本字符串
    }
}
