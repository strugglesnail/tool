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

    // 获取工作簿实例
    @Override
    public Workbook createWorkbook(InputStream inputStream) throws IOException, InvalidFormatException {
        return WorkbookFactory.create(inputStream);
    }

    // 获取封装Excel数据
    @Override
    public <T> List<T> getExcelData(MultipartFile file, Class<T> target) {
        return getTs(file, target, null);
    }

    // 获取封装Excel数据(可处理)
    @Override
    public <T> List<T> getExcelData(MultipartFile file, Class<T> target, ImportDataHandler<T> handler) {
        return getTs(file, target, handler);
    }

    private <T> List<T> getTs(MultipartFile file, Class<T> target, ImportDataHandler<T> handler) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("The MultipartFile can not be empty");
        }
        if (target == null) {
            throw new IllegalArgumentException("The target class can not be empty");
        }
        InputStream dataSource = null;
        try {
            dataSource = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        WorkbookParameter parameter = getParameter(dataSource, target);
        return createListData(getExcelCell(parameter), target, handler);
    }


}
