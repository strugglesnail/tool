package com.wtf.tool.util.excel.export.resolver;

import com.wtf.tool.util.excel.export.BeanParameter;
import com.wtf.tool.util.excel.export.PropertyParameter;
import com.wtf.tool.util.excel.export.annotation.HSSFExportExcel;
import com.wtf.tool.util.excel.export.annotation.HSSFHeaderExportExcel;
import com.wtf.tool.util.excel.export.annotation.HeaderExportExcel;
import org.apache.poi.hssf.usermodel.HSSFHeader;

import java.util.Objects;

/**
 * HSSF注解解析
 */
public class HSSFBeanArgumentProcessor implements BeanArgumentResolver {


    @Override
    public boolean supportsBean(BeanParameter parameter) {
        return parameter.hasBeanAnnotation(HSSFHeaderExportExcel.class);
    }

    @Override
    public Object resolverBean(BeanParameter parameter) {
        HSSFHeaderExportExcel annotation = parameter.getBeanAnnotation(HSSFHeaderExportExcel.class);
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
