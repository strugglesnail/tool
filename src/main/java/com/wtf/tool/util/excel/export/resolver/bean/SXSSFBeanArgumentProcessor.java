package com.wtf.tool.util.excel.export.resolver.bean;

import com.wtf.tool.util.excel.export.annotation.SXSSFHeaderExportExcel;
import com.wtf.tool.util.excel.export.param.BeanParameter;

import java.util.Objects;

/**
 * @auther: strugglesnail
 * @date: 2020/10/8 15:16
 * @desc: XSSF注解解析
 */
public class SXSSFBeanArgumentProcessor implements BeanArgumentResolver {


    @Override
    public boolean supportsBean(BeanParameter parameter) {
        return parameter.hasBeanAnnotation(SXSSFHeaderExportExcel.class);
    }

    @Override
    public Object resolverBean(BeanParameter parameter) {
        SXSSFHeaderExportExcel annotation = parameter.getBeanAnnotation(SXSSFHeaderExportExcel.class);
        if (Objects.nonNull(annotation)) {
            return new Parameter(annotation.index(), annotation.title(), annotation.width());
        }
        return null;
    }


    private static class Parameter {
        private final int index;
        private final String title;
        private final int width;

        public Parameter(int index, String title, int width) {
            this.index = index;
            this.title = title;
            this.width = width;
        }

        public int getIndex() {
            return index;
        }

        public String getTitle() {
            return title;
        }


        public int getWidth() {
            return width;
        }

    }

}