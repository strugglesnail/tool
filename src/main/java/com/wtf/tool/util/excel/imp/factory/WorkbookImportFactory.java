package com.wtf.tool.util.excel.imp.factory;

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
    <T> List<T> getExcelData(MultipartFile file, Class<T> target);

}
