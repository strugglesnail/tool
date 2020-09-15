package com.wtf.tool.util.generator.creator.sqlCreator;

import com.wtf.tool.util.generator.creator.SqlUtils;
import com.wtf.tool.util.generator.creator.core.SqlCreator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.Document;

import java.util.List;

public class ListSqlCreator implements SqlCreator {

    private String attributeId;

    private boolean isCreate;

    public ListSqlCreator(String attributeId, boolean isCreate) {
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
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(List.class.getName());
        Method method = new Method();
        method.setName(this.getAttributeId());
        method.addParameter(new Parameter(entityType, entityType.getShortName()));
        method.setReturnType(new FullyQualifiedJavaType(List.class.getSimpleName() + "<" + entityType + ">"));
        interfaze.addMethod(method);
        interfaze.addImportedType(listType);
    }

    public String getAttributeId() {
        return attributeId;
    }
}
