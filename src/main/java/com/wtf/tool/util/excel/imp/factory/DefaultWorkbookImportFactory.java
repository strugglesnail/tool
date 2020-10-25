package com.wtf.tool.util.excel.imp.factory;

import com.wtf.tool.util.excel.imp.handler.ImportDataHandler;
import com.wtf.tool.util.excel.imp.param.WorkbookParameter;
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



    // 获取封装Excel数据
    @Override
    public <T> List<T> getExcelData(InputStream stream, Class<T> target) {
        return getTargetData(stream, target, null);
    }

    // 获取封装Excel数据(可处理)
    @Override
    public <T> List<T> getExcelData(InputStream stream, Class<T> target, ImportDataHandler<T> handler) {
        return getTargetData(stream, target, handler);
    }

    // 获取目标数据
    private <T> List<T> getTargetData(InputStream stream, Class<T> target, ImportDataHandler<T> handler) {
        if (stream == null) {
            throw new IllegalArgumentException("The InputStream can not be empty");
        }
        if (target == null) {
            throw new IllegalArgumentException("The target class can not be empty");
        }
        WorkbookParameter parameter = getParameter(stream, target);
        return createListData(getExcelCell(parameter), target, handler);
    }


}
