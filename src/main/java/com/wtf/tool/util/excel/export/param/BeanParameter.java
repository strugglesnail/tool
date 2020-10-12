package com.wtf.tool.util.excel.export.param;

import com.wtf.tool.util.excel.export.annotation.ExcelFormat;
import com.wtf.tool.util.excel.export.annotation.HeaderExportExcel;
import com.wtf.tool.util.excel.export.generator.StyleGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 类注解信息
 */
public class BeanParameter {

    // 行起始位置
    private int rowIndex;

    // 列起始位置
    private int columnIndex;

    // sheet名称
    private String sheetName;

    // 标题
    private String title;

    private ExcelFormat format;

    // 单元格位置
//    private short align;

    private StyleGenerator styleGenerator;

    // 当前类的类型
    private Class<?> classType;

    // 当前类的注解
    private Annotation[] classAnnotations;

    // 属性
    private Field[] fields;


    public BeanParameter() {
    }

    public BeanParameter(Class<?> resourceClass) {
        this.classType = resourceClass;
        this.classAnnotations = resourceClass.getAnnotations();
        this.fields = resourceClass.getDeclaredFields();
        HeaderExportExcel header = resourceClass.getDeclaredAnnotation(HeaderExportExcel.class);
        if (header != null) {
            this.rowIndex = header.rowIndex();
            this.columnIndex = header.columnIndex();
            this.sheetName = header.sheetName();
            this.title = header.title();
            this.format = header.format();
//            this.align = header.align().getCode();
            try {
                this.styleGenerator = header.styleGenerator().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    // 判断是否有指定的注解类型
    public <A extends Annotation> boolean hasBeanAnnotation(Class<A> annotationType) {
        return this.getBeanAnnotation(annotationType) != null;
    }

    // 获取指定的注解类型
    public <T extends Annotation> T getBeanAnnotation(Class<T> annotationType) {
        Annotation[] beanAnns = this.classAnnotations;
        for (Annotation beanAnn : beanAnns) {
            if (annotationType.isInstance(beanAnn)) {
                return (T)beanAnn;
            }
        }
        return null;
    }


    public Class<?> getClassType() {
        return classType;
    }

    public Annotation[] getClassAnnotations() {
        return classAnnotations;
    }

    public Field[] getFields() {
        return fields;
    }

    public String getSheetName() {
        return sheetName;
    }

    public int getRowIndex() {
        return rowIndex;
    }

//    public short getAlign() {
//        return align;
//    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public String getTitle() {
        return title;
    }

    public ExcelFormat getFormat() {
        return format;
    }

    public StyleGenerator getStyleGenerator() {
        return styleGenerator;
    }
}
