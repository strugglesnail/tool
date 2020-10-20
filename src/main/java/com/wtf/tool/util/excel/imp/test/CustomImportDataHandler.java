package com.wtf.tool.util.excel.imp.test;

import com.wtf.tool.util.excel.imp.handler.ImportDataHandler;

import java.util.Date;

/**
 * @auther strugglesnail
 * @date 2020/10/18 10:03
 * @desc
 */
public class CustomImportDataHandler implements ImportDataHandler<ImportDemo> {

    @Override
    public void handlerRow(ImportDemo demo) {
        demo.setDate(new Date());
        System.out.println(demo);
    }
}
