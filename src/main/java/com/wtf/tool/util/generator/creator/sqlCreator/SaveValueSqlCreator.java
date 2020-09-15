package com.wtf.tool.util.generator.creator.sqlCreator;

import com.wtf.tool.util.generator.creator.core.SqlCreator;
import com.wtf.tool.util.generator.creator.SqlUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
*
* @author: wang_tengfei
* @date: 2020/9/13 22:25
*/
public class SaveValueSqlCreator implements SqlCreator {

    // saveValueSQL语句
    private static final StringBuilder saveValuesSQL = new StringBuilder("(\n");

    private String attributeId;

    private boolean isCreate;

    public SaveValueSqlCreator(String attributeId, boolean isCreate) {
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
        // 字段列表
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
                saveValuesSQL.append("\t  ").append("#{ ").append(javaProperty)
                        .append(", jdbcType=").append(jdbcTypeName).append(suffix);
            }
        }
        rootElement.addElement(SqlUtils.buildSql("save_" + tableName + "_values", saveValuesSQL.append("\t)").toString()));
    }


    @Override
    public void createDao(Interface interfaze, IntrospectedTable table) {

    }

    public String getAttributeId() {
        return attributeId;
    }
}
