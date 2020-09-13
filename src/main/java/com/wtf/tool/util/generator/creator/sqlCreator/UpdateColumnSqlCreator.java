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
* @date: 2020/9/13 22:13
*/
public class UpdateColumnSqlCreator implements SqlCreator {

    private boolean isCreate;

    public UpdateColumnSqlCreator(boolean isCreate) {
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

        // 字段列表
        List<IntrospectedColumn> columns = table.getAllColumns();

        StringBuilder updateColumnSQL = new StringBuilder("UPDATE ").append("`" + tableName + "`").append(" \n\t<set> ").append("\n");

        // 字段拼接
        for (IntrospectedColumn column : columns) {
            String columnName = MyBatis3FormattingUtilities.getEscapedColumnName(column);
            String javaProperty = column.getJavaProperty();
            String jdbcTypeName = column.getJdbcTypeName();
            if (!column.isAutoIncrement()) {
                updateColumnSQL.append("      <if test=\"null != ").append(javaProperty).append("\">");
                updateColumnSQL.append(columnName).append(" = #{ ").append(javaProperty).append(", jdbcType=")
                        .append(jdbcTypeName);
                updateColumnSQL.append("}");
                if(!(columns.size() - 1 == columns.indexOf(column))){
                    updateColumnSQL.append(",</if>");
                }else {
                    updateColumnSQL.append("</if>");
                    updateColumnSQL.append("\n\t</set>");
                }
                updateColumnSQL.append("\n");
            }
        }
        updateColumnSQL.append("\tWHERE ").append(pkColumn.getActualColumnName()).append(" = #{").append(pkColumn.getJavaProperty()).append("}");
        rootElement.addElement(SqlUtils.buildSql(tableName + "Update", updateColumnSQL.toString()));
    }


}
