package com.wtf.tool.util.generator.creator.sqlCreator;

import com.wtf.tool.util.generator.creator.core.SqlCreator;
import com.wtf.tool.util.generator.creator.SqlUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
*
* @author: wang_tengfei
* @date: 2020/9/13 22:13
*/
public class SaveBatchColumnSqlCreator implements SqlCreator {

    // saveBatchColumnSQL语句
    private static final StringBuilder saveBatchColumnSQL = new StringBuilder("(\n");


    private boolean isCreate;

    public SaveBatchColumnSqlCreator(boolean isCreate) {
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
        List<IntrospectedColumn> columns = table.getAllColumns();

        // 字段拼接
        for (IntrospectedColumn column : columns) {
            String javaProperty = column.getJavaProperty();
            String jdbcTypeName = column.getJdbcTypeName();
            if (!column.isAutoIncrement()) {
                String suffix;
                // 最后一个
                if (columns.size() - 1 == columns.indexOf(column)) {
                    suffix = "}\n";
                } else {
                    suffix = "},\n";
                }
                saveBatchColumnSQL.append("\t  ").append("#{ item.").append(javaProperty)
                        .append(", jdbcType=").append(jdbcTypeName).append(suffix);
            }
        }
        rootElement.addElement(SqlUtils.buildSql("batch_save_" + tableName + "_values", saveBatchColumnSQL.append("\t)").toString()));
    }


}
