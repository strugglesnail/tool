package com.wtf.tool.util.generator.creator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.Iterator;
import java.util.List;

public abstract class AbstractSqlCreator implements SqlCreator {

    @Override
    public boolean isCreate() {
        return true;
    }

    @Override
    public void createCrudSql(Document document, IntrospectedTable introspectedTable) {
        String columnName;
        String javaProperty;
        String jdbcProperty;


        XmlElement rootElement = document.getRootElement();

    }

    protected abstract void createSql(Document document, IntrospectedTable table);
}
