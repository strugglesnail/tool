package com.wtf.tool.util.excel.imp.factory;

import com.wtf.tool.util.excel.ExcelDemo;
import com.wtf.tool.util.excel.imp.handler.ImportDataHandler;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkbookImportFactoryBuilder<T> {

    //
    private MultipartFile file;
    private T target;
    private WorkbookImportFactory factory;
    private DataHandler handler;

    private WorkbookImportFactoryBuilder(Builder builder) {
        file = builder.file;
        factory = builder.factory;
        handler = builder.handler;
    }

    public static final class Builder<T> {
        private MultipartFile file;
        private T target;
        private WorkbookImportFactory factory = new DefaultWorkbookImportFactory();;
        private DataHandler<T> handler;

        public Builder() {
        }

        public Builder file(MultipartFile file) {
            this.file = file;
            return this;
        }


        public Builder factory(WorkbookImportFactory factory) {
            this.factory = factory;
            return this;
        }

        public Builder handler(DataHandler<T> handler) {
            this.handler = handler;
            return this;
        }

        public WorkbookImportFactoryBuilder build() {
            return new WorkbookImportFactoryBuilder(this);
        }
    }


    public static void main(String[] args) {
        Map map = new HashMap();
        List list = new ArrayList();
        list.stream().filter()
        map.forEach(() ->{});
        WorkbookImportFactoryBuilder builder = new Builder<ExcelDemo>().file(null)
                .factory(null)
                .handler((e) -> {
            e.
        }).build();
    }

    @FunctionalInterface
    public interface DataHandler<T> {
        void accept(T t);
    }


}
