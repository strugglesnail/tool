package com.wtf.tool.util.generator.creator.core;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.xml.Document;

public interface DaoCreator {

    void createDao(Interface interfaze, IntrospectedTable table);
}
