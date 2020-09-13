package com.wtf.tool.util.generator.creator.sqlCreator;

import com.wtf.tool.util.generator.creator.core.SqlCreator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
*
* @author: wang_tengfei
* @date: 2020/9/13 22:54
*/
public class DeleteSqlCreator implements SqlCreator {

    private static final StringBuilder deleteSql = new StringBuilder();

    private boolean isCreate;

    public DeleteSqlCreator(boolean isCreate) {
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
        // 主键
        IntrospectedColumn pkColumn = table.getPrimaryKeyColumns().get(0);


        XmlElement delete = new XmlElement("delete");
        delete.addAttribute(new Attribute("id", "delete"));
        deleteSql.append("DELETE from ").append("`" + tableName + "`").append(" WHERE ").append(pkColumn.getActualColumnName())
                .append(" = #{").append(pkColumn.getActualColumnName()).append("}");
        delete.addElement(new TextElement(deleteSql.toString()));
        rootElement.addElement(delete);
    }


}
