package com.wtf.tool.util.excel.imp.factory;

import com.wtf.tool.util.excel.imp.handler.ImportDataHandler;
import org.springframework.web.multipart.MultipartFile;

public class WorkbookImportFactoryBuilder {

    private MultipartFile file;
    private Class<?> target;
    private DefaultWorkbookImportFactory factory;
    private ImportDataHandler handler;

    public WorkbookImportFactoryBuilder(MultipartFile file, Class<?> target) {
        this.file = file;
        this.target = target;
    }

    private WorkbookImportFactoryBuilder(Builder builder) {
        factory = builder.factory;
        handler = builder.handler;
    }


    public static final class Builder {
        private DefaultWorkbookImportFactory factory;
        private ImportDataHandler handler;

        public Builder() {
        }

        public Builder factory(DefaultWorkbookImportFactory val) {
            factory = val;
            return this;
        }

        public Builder handler(ImportDataHandler val) {
            handler = val;
            return this;
        }

        public WorkbookImportFactoryBuilder build() {
            return new WorkbookImportFactoryBuilder(this);
        }
    }
}
