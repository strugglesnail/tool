package com.wtf.tool.util.excel.export.factory;

import com.wtf.tool.util.excel.export.param.BeanParameter;
import com.wtf.tool.util.excel.export.InvocableHandlerProperty;
import com.wtf.tool.util.excel.export.PropertyArgumentResolverComposite;
import com.wtf.tool.util.excel.export.param.PropertyParameter;
import com.wtf.tool.util.excel.export.resolver.prop.PropertyArgumentResolver;
import com.wtf.tool.util.excel.export.resolver.prop.HSSFPropertyArgumentProcessor;
import com.wtf.tool.util.excel.export.resolver.prop.SXSSFPropertyArgumentProcessor;
import com.wtf.tool.util.excel.export.resolver.prop.XSSFPropertyArgumentProcessor;
import com.wtf.tool.util.excel.export.test.ExportExcelDemo;
import com.wtf.tool.util.excel.export.test.SXSSFExportExcelDemo;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefaultWorkbookExportFactory extends AbstractWorkbookExportFactory {

    private PropertyArgumentResolverComposite propertyArgumentResolverComposite = new PropertyArgumentResolverComposite();

    private InvocableHandlerProperty handlerProperty = new InvocableHandlerProperty();

    public DefaultWorkbookExportFactory(Class<?> target) {
        super(new BeanParameter(target));
        // 初始化属性参数解析器
        this.initArgumentResolverComposite();
    }


    private void initArgumentResolverComposite() {
        propertyArgumentResolverComposite = (new PropertyArgumentResolverComposite()).addResolvers(getDefaultPropertyArgumentResolvers());
        handlerProperty.setPropertyArgumentResolverComposite(this.propertyArgumentResolverComposite);
    }

    // 注册属性解析器
    private List<PropertyArgumentResolver> getDefaultPropertyArgumentResolvers() {
        List<PropertyArgumentResolver> resolvers = new ArrayList<>();
        resolvers.add(new HSSFPropertyArgumentProcessor());
        resolvers.add(new XSSFPropertyArgumentProcessor());
        resolvers.add(new SXSSFPropertyArgumentProcessor());
        return resolvers;
    }

    @Override
    protected <T> void setTitle(PropertyParameter<T> propertyParameter) {
        handlerProperty.handlerTitle(propertyParameter);
    }

    @Override
    protected <T> void setCell(PropertyParameter<T> propertyParameter) {
        handlerProperty.handlerProperty(propertyParameter);
    }

    public static void main(String[] args) throws IOException {
        List<SXSSFExportExcelDemo> demos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SXSSFExportExcelDemo excelDemo = new SXSSFExportExcelDemo();
            excelDemo.setName("文件导出" + i);
            excelDemo.setSheetName("sheet名称" + i);
            excelDemo.setType("excel类型" + i);
            excelDemo.setDate(new Date());
            demos.add(excelDemo);
        }
        DefaultWorkbookExportFactory factory = new DefaultWorkbookExportFactory(SXSSFExportExcelDemo.class);
        Workbook workbook = factory.exportWorkbook(demos);
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\86188\\Desktop\\文件导出模板.xlsx");
        workbook.write(outputStream);
        outputStream.close();

//        System.out.println(ExportExcelDemo.class.getDeclaredFields().length);
    }
}
