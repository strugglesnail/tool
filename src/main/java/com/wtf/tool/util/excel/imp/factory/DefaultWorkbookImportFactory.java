package com.wtf.tool.util.excel.imp.factory;

import com.wtf.tool.util.excel.imp.handler.ImportDataHandler;
import com.wtf.tool.util.excel.imp.param.WorkbookParameter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @auther strugglesnail
 * @date 2020/10/19 21:43
 * @desc 默认导入工厂
 */
public class DefaultWorkbookImportFactory extends AbstractWorkbookImportFactory {

    public DefaultWorkbookImportFactory() {

    }

    @Override
    public Workbook createWorkbook(InputStream inputStream) throws IOException, InvalidFormatException {
        return WorkbookFactory.create(inputStream);
    }

    //将单元格数据list与泛型合并入口API
    @Override
    public <T> List<T> getExcelData(MultipartFile file, Class<T> target) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("The MultipartFile can not be empty");
        }
        InputStream dataSource = null;
        try {
            dataSource = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        WorkbookParameter parameter = getParameter(dataSource, target);
        return createListData(getExcelCell(parameter), target);
    }

    @Override
    public <T> List<T> getExcelData(MultipartFile file, Class<T> target, ImportDataHandler handler) {

        return null;
    }
}
