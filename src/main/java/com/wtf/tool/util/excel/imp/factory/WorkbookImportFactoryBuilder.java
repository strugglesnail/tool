package com.wtf.tool.util.excel.imp.factory;

import com.wtf.tool.util.excel.imp.handler.ImportDataHandler;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public class WorkbookImportFactoryBuilder<T> {

    //
    private InputStream stream;
    private Class<T> target;
    private WorkbookImportFactory factory;

    private WorkbookImportFactoryBuilder(Builder builder) {
        stream = builder.stream;
        target = builder.target;
        factory = builder.factory != null ? builder.factory : new DefaultWorkbookImportFactory();
    }

    // 创建导入工厂
    public List<T> get(){
        return factory.getExcelData(stream, target, null);
    }
    // 获取导入数据（）
    public List<T> get(ImportDataHandler<T> handler){
        return factory.getExcelData(stream, target, handler);
    }


    public static final class Builder<T> {
        private InputStream stream;
        private Class<T> target;
        private WorkbookImportFactory factory;

        public Builder() {
        }

        public Builder stream(InputStream val) {
            stream = val;
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
        WorkbookImportFactoryBuilder builder = new Builder().stream(null)
                .build();
        builder.get();
    }
}
