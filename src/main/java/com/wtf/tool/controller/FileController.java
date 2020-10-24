package com.wtf.tool.controller;

import com.wtf.tool.annotation.ImportExcel;
import com.wtf.tool.model.EchartExcel;
import com.wtf.tool.util.excel.ExcelDemo;
import com.wtf.tool.util.excel.ImportExcelUtils;
import com.wtf.tool.util.excel.imp.factory.DefaultWorkbookImportFactory;
import com.wtf.tool.util.excel.imp.factory.WorkbookImportFactoryBuilder;
import com.wtf.tool.util.excel.imp.handler.ImportDataHandler;
import com.wtf.tool.util.excel.imp.test.ImportDemo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping("/importExcel")
    public List importExcel(MultipartFile file) {
        ImportExcelUtils utils = new ImportExcelUtils(file, "sheet1", 1);
        List<EchartExcel> listData = utils.getExcelData(EchartExcel.class);
//        return listData.stream().collect(Collectors.toMap(EchartExcel::getConsName, Function.identity()));
//        return listData.stream().map(ect -> ect.getConsName()).collect(Collectors.toList());
//        return listData.stream().map(ect -> ect.getConsName()).collect(Collectors.toMap(String::new, Function.identity()));
        return covertMap(listData, new int[] {0});
    }

    @PostMapping("/importTest")
    public List importTest(MultipartFile file) {
        DefaultWorkbookImportFactory factory = new DefaultWorkbookImportFactory();
        List<ImportDemo> excelData = factory.getExcelData(file, ImportDemo.class);
        return excelData;
    }
    @PostMapping("/importTestBuilder")
    public List importTestBuilder(MultipartFile file) {
        WorkbookImportFactoryBuilder<ImportDemo> builder = new WorkbookImportFactoryBuilder.Builder()
                .file(file)
                .target(ImportDemo.class)
                .build();
        List<ImportDemo> excelDemos = builder.get();
        return excelDemos;
    }
    @PostMapping("/importTestHandlerBuilder")
    public List<ImportDemo> importTestHandlerBuilder(MultipartFile file) {
        WorkbookImportFactoryBuilder<ImportDemo> builder = new WorkbookImportFactoryBuilder.Builder()
                .file(file)
                .target(ImportDemo.class)
                .build();
        List<ImportDemo> excelDemos = builder.get(e -> {
            e.setDate(new Date());
        });
        return excelDemos;
    }


    private <T> List<Map<String, Object>> covertMap(List<T> listData, int[] indexArray) {
        List<Map<String, Object>> mapList = new ArrayList<>(listData.size());
        for (T data : listData) {
            mapList.add(this.buildDefaultParamsMap(data, indexArray));
        }
        return mapList;
    }

    protected <T> Map<String, Object> buildDefaultParamsMap(T params, int[] indexArray) {
        Map<String, Object> map = new HashMap<String, Object>();
        if(params == null) {
            return map;
        }
        try {
//            BeanInfo beanInfo = Introspector.getBeanInfo(params.getClass(), Object.class);
//            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
//            for(PropertyDescriptor pd : pds) {
////                ImportExcel annotation = pd
//                map.put(pd.getName(), pd.getReadMethod().invoke(params));
//            }
            Field[] fields = params.getClass().getDeclaredFields();
            for (Field field : fields) {
                ImportExcel annotation = field.getDeclaredAnnotation(ImportExcel.class);
                if (annotation != null) {
                    boolean anyMatch = Arrays.stream(indexArray).anyMatch(index -> index == annotation.index());
                    if (anyMatch) {
                        field.setAccessible(true);
                        map.put(field.getName(), field.get(params));
                    }
                }
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
