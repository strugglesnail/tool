package com.wtf.tool.util.generator.creator.sqlCreator;

import com.wtf.tool.util.generator.creator.SqlUtils;
import com.wtf.tool.util.generator.creator.core.SqlCreator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class CountSqlCreator implements SqlCreator {

    private String attributeId;

    private boolean isCreate;

    public CountSqlCreator(String attributeId, boolean isCreate) {
        this.attributeId = attributeId;
        this.isCreate = isCreate;
    }

    @Override
    public boolean isCreate() {
        return isCreate;
    }

    @Override
    public void createSql(Document document, IntrospectedTable table) {
        XmlElement rootElement = document.getRootElement();
        // 表名
        String tableName = table.getFullyQualifiedTableNameAtRuntime();
        // 实体类
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());

        XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", this.getAttributeId()));
        select.addAttribute(new Attribute("resultType", "java.lang.Long"));
        select.addAttribute(new Attribute("parameterType", entityType.getFullyQualifiedName()));

        StringBuilder selectStr = new StringBuilder();
        selectStr.append("SELECT COUNT(1) FROM ").append("`" + tableName + "`");
        selectStr.append(" <include refid=\"" + tableName + "DynamicWhere\" />");
        select.addElement(new TextElement(selectStr.toString()+""));
        rootElement.addElement(select);
    }

    @Override
    public void createDao(Interface interfaze, IntrospectedTable table) {
        SqlUtils.createMethodForCount(interfaze, table, this.getAttributeId());
    }

    public String getAttributeId() {
        return attributeId;
    }
}
