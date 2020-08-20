package com.wtf.tool.util.excel.export.resolver;

import com.wtf.tool.util.excel.export.BeanParameter;
import com.wtf.tool.util.excel.export.PropertyParameter;
import com.wtf.tool.util.excel.export.annotation.HSSFExportExcel;

import java.util.Objects;

/**
 * HSSF注解解析
 */
public class HSSFBeanArgumentProcessor implements BeanArgumentResolver {


    @Override
    public boolean supportsBean(BeanParameter parameter) {
        return parameter.hasBeanAnnotation(HSSFExportExcel.class);
    }

    @Override
    public Object resolverBean(BeanParameter parameter) {
        HSSFExportExcel annotation = parameter.getBeanAnnotation(HSSFExportExcel.class);
        if (Objects.nonNull(annotation)) {
            return new Parameter(annotation.index(), annotation.title(), annotation.width(), annotation.date());
        }
        return null;
    }


    private static class Parameter {
        private final int index;
        private final String title;
        private final int width;
        private final String DateFormat ;

        public Parameter(int index, String title, int width, String dateFormat) {
            this.index = index;
            this.title = title;
            this.width = width;
            DateFormat = dateFormat;
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

        public String getDateFormat() {
            return DateFormat;
        }
    }

}
