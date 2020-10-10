package com.wtf.tool.util.generator.creator.sqlCreator;

import com.wtf.tool.util.generator.creator.SqlEnum;
import com.wtf.tool.util.generator.creator.core.SqlCreator;
import org.mybatis.generator.api.IntrospectedColumn;
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
* @date: 2020/9/13 20:33
*/
public class GetByIdSqlCreator implements SqlCreator {

    // likeIf判断语句
    private static final StringBuilder getIdSQL = new StringBuilder();

    private String attributeId;

    private boolean isCreate;

    public GetByIdSqlCreator(String attributeId, boolean isCreate) {
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
//        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        // 主键
        IntrospectedColumn pkColumn = table.getPrimaryKeyColumns().get(0);

        XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", this.getAttributeId()));
        select.addAttribute(new Attribute("parameterType", pkColumn.getFullyQualifiedJavaType().toString()));
        select.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        getIdSQL.append("SELECT <include refid=\"" + tableName + SqlEnum.ATTRIBUTE_COLUMN.getAttributeId() + "\" /> FROM  ").append( "`" + tableName + "`");
        getIdSQL.append(" <include refid=\"" + tableName + SqlEnum.ATTRIBUTE_DYNAMIC_WHERE.getAttributeId() + "\" />");
        getIdSQL.append(" WHERE ").append(pkColumn.getActualColumnName()).append(" = #{").append(pkColumn.getJavaProperty()).append("}");
        select.addElement(new TextElement(getIdSQL.toString()));
        rootElement.addElement(select);
    }

    @Override
    public void createDao(Interface interfaze, IntrospectedTable table) {
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        Method method = new Method();
        method.setName(this.getAttributeId());
        method.addParameter(new Parameter(new FullyQualifiedJavaType(Long.class.getSimpleName()), "id"));
        method.setReturnType(entityType);
        interfaze.addMethod(method);
    }

    public String getAttributeId() {
        return attributeId;
    }
}
