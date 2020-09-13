package com.wtf.tool.util.generator.creator.core;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Document;

public abstract class AbstractSqlCreator implements SqlCreator {

    @Override
    public boolean isCreate() {
        return true;
    }

    @Override
    public void createSql(Document document, IntrospectedTable table) {
        createSqlInternal(document, table);
    }

    protected abstract void createSqlInternal(Document document, IntrospectedTable table);
}
