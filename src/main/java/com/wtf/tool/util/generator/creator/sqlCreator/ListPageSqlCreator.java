package com.wtf.tool.util.generator.creator.sqlCreator;

import com.wtf.tool.util.generator.creator.SqlUtils;
import com.wtf.tool.util.generator.creator.core.SqlCreator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.xml.Document;

import java.util.List;

public class ListPageSqlCreator implements SqlCreator {

    private String attributeId;

    private boolean isCreate;

    public ListPageSqlCreator(String attributeId, boolean isCreate) {
        this.attributeId = attributeId;
        this.isCreate = isCreate;
    }

    @Override
    public boolean isCreate() {
        return isCreate;
    }

    @Override
    public void createSql(Document document, IntrospectedTable table) {
        SqlUtils.buildSqlForList(document, table, this.getAttributeId());
    }

    @Override
    public void createDao(Interface interfaze, IntrospectedTable table) {
        SqlUtils.createMethodForPage(interfaze, table, this.getAttributeId());
    }

    public String getAttributeId() {
        return attributeId;
    }

}
