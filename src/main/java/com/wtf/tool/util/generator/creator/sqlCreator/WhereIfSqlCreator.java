package com.wtf.tool.util.generator.creator.sqlCreator;

import com.wtf.tool.util.generator.creator.core.SqlCreator;
import com.wtf.tool.util.generator.creator.SqlUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

public class WhereIfSqlCreator implements SqlCreator {

    // if判断语句
    private final StringBuilder ifSQL = new StringBuilder();

    private String attributeId;

    private boolean isCreate;

    public WhereIfSqlCreator(String attributeId, boolean isCreate) {
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
        String tableName = table.getFullyQualifiedTableNameAtRuntime();
        List<IntrospectedColumn> allColumns = table.getAllColumns();
        Iterator<IntrospectedColumn> iterator = allColumns.iterator();
        while (iterator.hasNext()) {
            IntrospectedColumn column = iterator.next();
            String columnName = MyBatis3FormattingUtilities.getEscapedColumnName(column);
            String javaProperty = column.getJavaProperty();

            ifSQL.append("      <if test=\"null != ").append(javaProperty).append(" and '' !=  ").append(javaProperty).append("\">");
            ifSQL.append("AND ").append(columnName).append(" = #{ ").append(javaProperty).append("}</if>\n");
        }
        String whereSQL = MessageFormat.format("<where>\n{0}\t</where>", ifSQL.toString());
        rootElement.addElement(SqlUtils.buildSql(tableName + "DynamicWhere", whereSQL));
    }

    @Override
    public void createDao(Interface interfaze, IntrospectedTable table) {

    }

    public String getAttributeId() {
        return attributeId;
    }
}
