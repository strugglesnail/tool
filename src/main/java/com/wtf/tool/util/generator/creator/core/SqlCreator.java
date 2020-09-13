package com.wtf.tool.util.generator.creator.core;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

public interface SqlCreator {

    /**
    *
    * @desc 判断
    */
    boolean isCreate();

    void createSql(Document document, IntrospectedTable introspectedTable);
}
