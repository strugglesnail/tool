package com.wtf.tool.util.generator.creator.sqlCreator;

import com.wtf.tool.util.generator.creator.core.SqlCreator;
import com.wtf.tool.util.generator.creator.SqlUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.List;
/**
*
* @author: wang_tengfei
* @date: 2020/9/13 22:06
*/
public class SaveColumnSqlCreator implements SqlCreator {

    private boolean isCreate;

    public SaveColumnSqlCreator(boolean isCreate) {
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
        // 字段列表
        List<IntrospectedColumn> columns = table.getAllColumns();
        // 插入SQL
        StringBuilder saveColumn = new StringBuilder("INSERT INTO ").append("`" + tableName + "`").append("(\n\t\t");

        // 字段拼接
        for (IntrospectedColumn column : columns) {
            String columnName = MyBatis3FormattingUtilities.getEscapedColumnName(column);
            if (!column.isAutoIncrement()) {
                // 最后一个
                if (columns.size() - 1 == columns.indexOf(column)) {
                    saveColumn.append(columnName);
                } else {
                    saveColumn.append(columnName + ", ");
                }
            }
        }
        rootElement.addElement(SqlUtils.buildSql("save_" + tableName + "_columns", saveColumn.append("\n\t) values").toString()));
    }


}
