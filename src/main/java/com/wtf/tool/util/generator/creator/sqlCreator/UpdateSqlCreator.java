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

/**
*
* @author: wang_tengfei
* @date: 2020/9/13 22:47
*/
public class UpdateSqlCreator implements SqlCreator {
    private String attributeId;

    private boolean isCreate;

    public UpdateSqlCreator(String attributeId, boolean isCreate) {
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

        XmlElement update = new XmlElement("update");
        update.addAttribute(new Attribute("id", this.getAttributeId()));
        update.addAttribute(new Attribute("parameterType", entityType.getFullyQualifiedName()));
        update.addElement(new TextElement("<include refid=\"" + tableName + "Update\" />"));
        rootElement.addElement(update);
    }

    @Override
    public void createDao(Interface interfaze, IntrospectedTable table) {
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        SqlUtils.createMethodForUpdate(interfaze, entityType, this.getAttributeId());
    }

    public String getAttributeId() {
        return attributeId;
    }
}
