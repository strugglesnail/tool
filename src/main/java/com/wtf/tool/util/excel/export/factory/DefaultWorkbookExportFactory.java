package com.wtf.tool.util.excel.export.factory;

import com.wtf.tool.util.excel.export.BeanParameter;
import com.wtf.tool.util.excel.export.InvocableHandlerProperty;
import com.wtf.tool.util.excel.export.PropertyArgumentResolverComposite;
import com.wtf.tool.util.excel.export.PropertyParameter;
import com.wtf.tool.util.excel.export.resolver.BeanArgumentResolver;
import com.wtf.tool.util.excel.export.resolver.HSSFBeanArgumentProcessor;
import com.wtf.tool.util.excel.export.resolver.PropertyArgumentResolver;
import com.wtf.tool.util.excel.export.resolver.HSSFPropertyArgumentProcessor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

public class DefaultWorkbookExportFactory extends AbstractWorkbookExportFactory {

    private PropertyArgumentResolverComposite propertyArgumentResolverComposite = new PropertyArgumentResolverComposite();

    public DefaultWorkbookExportFactory(Class<?> target) {
        super(new BeanParameter(target));
    }

    private void initArgumentResolverComposite() {
        propertyArgumentResolverComposite = (new PropertyArgumentResolverComposite()).addResolvers(getDefaultPropertyArgumentResolvers());
    }

    private List<BeanArgumentResolver> getDefaultArgumentResolvers() {
        List<BeanArgumentResolver> resolvers = new ArrayList<>();
        resolvers.add(new HSSFBeanArgumentProcessor());
        return resolvers;
    }
    private List<PropertyArgumentResolver> getDefaultPropertyArgumentResolvers() {
        List<PropertyArgumentResolver> resolvers = new ArrayList<>();
        resolvers.add(new HSSFPropertyArgumentProcessor());
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
    protected <T> void setCell(PropertyParameter<T> propertyParameter) {
        InvocableHandlerProperty handlerProperty = new InvocableHandlerProperty();
        handlerProperty.setPropertyArgumentResolverComposite(this.propertyArgumentResolverComposite);
        handlerProperty
//        List<PropertyArgumentResolver> resolvers = getDefaultPropertyArgumentResolvers();
//        for (PropertyArgumentResolver resolver : resolvers) {
//            if (resolver.supportsProperty(propertyParameter)) {
//
//            }
//        }

    }
}
