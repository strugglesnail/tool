package com.wtf.tool.util.excel.imp.factory;

import com.wtf.tool.util.excel.imp.handler.ImportDataHandler;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class WorkbookImportFactoryBuilder<T> {

    //
    private MultipartFile file;
    private Class<T> target;
    private WorkbookImportFactory factory;

    private WorkbookImportFactoryBuilder(Builder builder) {
        file = builder.file;
        target = builder.target;
        factory = builder.factory != null ? builder.factory : new DefaultWorkbookImportFactory();
    }

    // 创建导入工厂
    public List<T> get(){
        return factory.getExcelData(file, target, null);
    }
    // 获取导入数据（）
    public List<T> get(ImportDataHandler<T> handler){
        return factory.getExcelData(file, target, handler);
    }


    public static final class Builder<T> {
        private MultipartFile file;
        private Class<T> target;
        private WorkbookImportFactory factory;

        public Builder() {
        }

        public Builder file(MultipartFile val) {
            file = val;
            return this;
        }

        public Builder target(Class<T> val) {
            target = val;
            return this;
        }

        public Builder factory(WorkbookImportFactory val) {
            factory = val;
            return this;
        }

        public WorkbookImportFactoryBuilder build() {
            return new WorkbookImportFactoryBuilder(this);
        }
    }

    public static void main(String[] args) {
        WorkbookImportFactoryBuilder builder = new Builder().file(null)
                .build();
        builder.get();
    }
}
