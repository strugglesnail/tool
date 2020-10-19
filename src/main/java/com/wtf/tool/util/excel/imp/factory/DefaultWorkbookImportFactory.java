package com.wtf.tool.util.excel.imp.factory;

import com.wtf.tool.util.excel.imp.param.WorkbookParameter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @auther strugglesnail
 * @date 2020/10/19 21:43
 * @desc 默认导入工厂
 */
public class DefaultWorkbookImportFactory extends AbstractWorkbookImportFactory {

    public DefaultWorkbookImportFactory() {

    }

    //将单元格数据list与泛型合并入口API
    public <T> List<T> getExcelData(MultipartFile file, Class<T> clazz){
        WorkbookParameter parameter = new WorkbookParameter(file, )
        return createListData(getExcelCell(null), clazz);
    }
}
