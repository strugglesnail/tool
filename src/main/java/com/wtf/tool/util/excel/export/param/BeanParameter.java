package com.wtf.tool.util.excel.export.param;

import com.wtf.tool.util.excel.export.annotation.ExcelFormat;
import com.wtf.tool.util.excel.export.annotation.HeaderExportExcel;
import com.wtf.tool.util.excel.export.annotation.SXSSFExportExcel;
import com.wtf.tool.util.excel.export.generator.StyleGenerator;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 类注解信息
 */
public class BeanParameter {

    // 行起始位置
    private int rowIndex;

    // 原始的行位置
    private int originalIndex;

    // 列起始位置
    private int colIndex;

    // sheet名称
    private String sheetName;

    // 标题
    private String title;

    private ExcelFormat format;

    private StyleGenerator styleGenerator;

    // 当前类的类型
    private Class<?> classType;

    // 当前类的注解
    private Annotation[] classAnnotations;

    // 属性
    private Field[] fields;


    public BeanParameter() {}

    public BeanParameter(Class<?> resourceClass) {
        this.classType = resourceClass;
        this.classAnnotations = resourceClass.getAnnotations();
        this.fields = resourceClass.getDeclaredFields();
        HeaderExportExcel header = resourceClass.getDeclaredAnnotation(HeaderExportExcel.class);
        if (header != null) {
            int maxRow = getMaxRow(fields);
            System.out.println("maxRow: " + maxRow);
            this.originalIndex = header.rowIndex();
            this.rowIndex = header.rowIndex() + maxRow;
            this.title = header.title();
            // 如果存在标题，则行下标 + 1
            if (StringUtils.isNotBlank(this.title)) {
                this.rowIndex ++;
            }
            this.colIndex = header.colIndex();
            this.sheetName = header.sheetName();
            this.format = header.format();
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

    // 获取最大行数
    private static int getMaxRow(Field[] fields) {
        int[] maxRow = new int[10];
        for (int i = 0; i < fields.length; i++) {
            SXSSFExportExcel annotation = fields[i].getDeclaredAnnotation(SXSSFExportExcel.class);
            if (annotation != null) {
                String[] title = annotation.title();
                maxRow[i] = title.length;
            }
        }
        return Arrays.stream(maxRow).max().getAsInt();
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

    public int getOriginalIndex() {
        return originalIndex;
    }

    public int getColIndex() {
        return colIndex;
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
