package com.wtf.tool.util.excel.imp.factory;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;


public interface WorkbookImportFactory {

    Workbook createWorkbook(InputStream inputStream) throws IOException, InvalidFormatException;
}
