package com.wtf.tool.util.generator.creator.sqlCreator;

import com.wtf.tool.util.generator.creator.core.SqlCreator;
import com.wtf.tool.util.generator.creator.SqlUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.List;

public class ColumnSqlCreator implements SqlCreator {

    // if判断语句
    private final StringBuilder columnSQL = new StringBuilder();


    private boolean isCreate;

    public ColumnSqlCreator(boolean isCreate) {
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

        List<IntrospectedColumn> columns = table.getAllColumns();
        for (IntrospectedColumn column : columns) {
            String columnName = MyBatis3FormattingUtilities.getEscapedColumnName(column);
            columnSQL.append(columnName);
            // 非最后一个
            if (!(columns.size() - 1 == columns.indexOf(column))) {
                columnSQL.append(", ");
            }
        }
        rootElement.addElement(SqlUtils.buildSql(tableName + "Columns", columnSQL.toString()));
    }


}
