package com.wtf.tool.util.generator.creator.core;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.xml.Document;

public abstract class AbstractCreator implements SqlCreator {

    @Override
    public boolean isCreate() {
        return true;
    }

    @Override
    public void createSql(Document document, IntrospectedTable table) {
        createSqlInternal(document, table);
    }

    @Override
    public void createDao(Interface interfaze, IntrospectedTable table) {
        createDaoInternal(interfaze, table);
    }

    protected abstract void createSqlInternal(Document document, IntrospectedTable table);

    protected abstract void createDaoInternal(Interface interfaze, IntrospectedTable table);
}
