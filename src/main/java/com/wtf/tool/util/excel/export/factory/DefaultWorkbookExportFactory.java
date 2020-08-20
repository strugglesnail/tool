package com.wtf.tool.util.excel.export.factory;

import com.wtf.tool.util.excel.export.PropertyArgumentResolverComposite;
import com.wtf.tool.util.excel.export.PropertyParameter;
import com.wtf.tool.util.excel.export.resolver.PropertyArgumentResolver;
import com.wtf.tool.util.excel.export.resolver.HSSFPropertyArgumentProcessor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DefaultWorkbookExportFactory extends AbstractWorkbookExportFactory {

    private PropertyArgumentResolverComposite fieldArgumentResolverComposite = new PropertyArgumentResolverComposite();

    public DefaultWorkbookExportFactory(String sheetName) {
        super(sheetName);
    }

    private List<PropertyArgumentResolver> getDefaultArgumentResolvers() {
        List<PropertyArgumentResolver> resolvers = new ArrayList<>();
        resolvers.add(new HSSFPropertyArgumentProcessor());
        return resolvers;
    }

    @Override
    protected Workbook createWorkbookInternal() {
        Workbook workbook = null;
        List<PropertyArgumentResolver> resolvers = getDefaultArgumentResolvers();
        for (PropertyArgumentResolver resolver : resolvers) {
            if (resolver.supportsProperty(new PropertyParameter())) {
                if (resolver instanceof HSSFPropertyArgumentProcessor) {
                    workbook = new HSSFWorkbook();
                } else {

                }
            }
        }
        return workbook;
    }


    @Override
    protected <T> void setCell(Field field, T t, Row row) {

    }
}
