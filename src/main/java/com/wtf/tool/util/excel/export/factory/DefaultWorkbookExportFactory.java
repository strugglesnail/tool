package com.wtf.tool.util.excel.export.factory;

import com.wtf.tool.util.excel.export.BeanParameter;
import com.wtf.tool.util.excel.export.PropertyArgumentResolverComposite;
import com.wtf.tool.util.excel.export.PropertyParameter;
import com.wtf.tool.util.excel.export.resolver.BeanArgumentResolver;
import com.wtf.tool.util.excel.export.resolver.HSSFBeanArgumentProcessor;
import com.wtf.tool.util.excel.export.resolver.PropertyArgumentResolver;
import com.wtf.tool.util.excel.export.resolver.HSSFPropertyArgumentProcessor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DefaultWorkbookExportFactory extends AbstractWorkbookExportFactory {

    public DefaultWorkbookExportFactory(Class<?> target) {
        super(new BeanParameter(target));
    }


    private List<BeanArgumentResolver> getDefaultArgumentResolvers() {
        List<BeanArgumentResolver> resolvers = new ArrayList<>();
        resolvers.add(new HSSFBeanArgumentProcessor());
        return resolvers;
    }

    @Override
    protected Workbook createWorkbookInternal(BeanParameter beanParameter) {
        Workbook workbook = null;
        List<BeanArgumentResolver> resolvers = getDefaultArgumentResolvers();
        for (BeanArgumentResolver resolver : resolvers) {
            if (resolver.supportsBean(new BeanParameter())) {
                if (resolver instanceof HSSFBeanArgumentProcessor) {
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
