package com.wtf.tool.util.excel.imp.factory;

import com.wtf.tool.util.excel.imp.handler.ImportDataHandler;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public interface WorkbookImportFactory {

    // 创建工作簿
    Workbook createWorkbook(InputStream inputStream) throws IOException, InvalidFormatException;

    // 获取导入的数据
    default <T> List<T> getExcelData(InputStream stream, Class<T> target) { return null;}

    // 获取导入的数据
    default <T> List<T> getExcelData(InputStream stream, Class<T> target, ImportDataHandler<T> handler) {return null;}

}
