package com.wtf.tool.util.generator.creator.sqlCreator;

import com.wtf.tool.util.generator.creator.core.SqlCreator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class ListLikePageSqlCreator implements SqlCreator {

    // likeIf判断语句
    private static final StringBuilder listLikePageSQL = new StringBuilder();


    private boolean isCreate;

    public ListLikePageSqlCreator(boolean isCreate) {
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
        select.addAttribute(new Attribute("id", "listLikePage"));
        select.addAttribute(new Attribute("parameterType", entityType.getFullyQualifiedName()));
        select.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        listLikePageSQL.append("select <include refid=\"" + tableName + "Columns\" /> from  ").append( "`" + tableName + "`");
        listLikePageSQL.append(" <include refid=\"" + tableName + "DynamicLikeWhere\" />");
        select.addElement(new TextElement(listLikePageSQL.toString()));
        rootElement.addElement(select);
    }


}
