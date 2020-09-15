package com.wtf.tool.util.generator.creator.sqlCreator;

import com.wtf.tool.util.generator.creator.core.DaoCreator;
import com.wtf.tool.util.generator.creator.core.SqlCreator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
*
* @author: wang_tengfei
* @date: 2020/9/13 22:50
*/
public class UpdateBatchSqlCreator implements SqlCreator, DaoCreator {

    private String attributeId;

    private boolean isCreate;

    public UpdateBatchSqlCreator(String attributeId, boolean isCreate) {
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

        XmlElement update = new XmlElement("update");
        update.addAttribute(new Attribute("id", this.getAttributeId()));
        update.addAttribute(new Attribute("parameterType", "list"));
        update.addElement(new TextElement(
                "<foreach collection=\"list\" index=\"index\" item=\"item\" open=\"\" separator=\";\" close=\"\">\n\t  " +
                        "\t<include refid=\"" + tableName + "Update\" />\n\t" +
                        "</foreach>"));
        rootElement.addElement(update);
    }


    @Override
    public void createDao(Interface interfaze, IntrospectedTable table) {
//        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        Method method = new Method();
        method.setName(this.getAttributeId());
        method.addParameter(new Parameter(FullyQualifiedJavaType.getNewArrayListInstance(), "list"));
//        method.setReturnType(new FullyQualifiedJavaType("Long"));
        interfaze.addMethod(method);
    }

    public String getAttributeId() {
        return attributeId;
    }
}
