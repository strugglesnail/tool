package com.wtf.tool.util.generator.creator.sqlCreator;

import com.wtf.tool.util.generator.creator.core.DaoCreator;
import com.wtf.tool.util.generator.creator.core.SqlCreator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class CountLikeSqlCreator implements SqlCreator, DaoCreator {

    // likeIf判断语句
    private final StringBuilder countLikeSQL = new StringBuilder();

    private String attributeId;

    private boolean isCreate;

    public CountLikeSqlCreator(String attributeId, boolean isCreate) {
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
        select.addAttribute(new Attribute("id", "countLike"));
        select.addAttribute(new Attribute("resultType", "java.lang.Long"));
        select.addAttribute(new Attribute("parameterType", entityType.getFullyQualifiedName()));

        countLikeSQL.append("select count(1) from ").append("`" + tableName + "`");
        countLikeSQL.append(" <include refid=\"" + tableName + "DynamicLikeWhere\" />");
        select.addElement(new TextElement(countLikeSQL.toString()+""));
        rootElement.addElement(select);
    }

    @Override
    public void createDao(Interface interfaze, IntrospectedTable table) {

    }

    public String getAttributeId() {
        return attributeId;
    }
}
