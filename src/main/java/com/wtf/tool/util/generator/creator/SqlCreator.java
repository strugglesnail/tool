package com.wtf.tool.util.generator.creator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Document;

public interface SqlCreator {

    boolean isCreate();

    void createCrudSql(Document document, IntrospectedTable introspectedTable);
}
