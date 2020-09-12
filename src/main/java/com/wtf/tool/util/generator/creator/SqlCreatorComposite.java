package com.wtf.tool.util.generator.creator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.*;

public class SqlCreatorComposite extends AbstractSqlCreator{

    private static final List<IfSqlCreator> SQL_CHAIN_LIST = new LinkedList<>();

    static {
        SQL_CHAIN_LIST.add(new IfSqlCreator());
    }



    @Override
    protected void createSql(Document document,  IntrospectedTable table) {
        List<IntrospectedColumn> allColumns = table.getAllColumns();
        Iterator<IntrospectedColumn> iterator = allColumns.iterator();
        do {
            IntrospectedColumn column = iterator.next();
//            columnName = MyBatis3FormattingUtilities.getEscapedColumnName(column);
            // 最后一个
            if (!iterator.hasNext()) {

            }

            String columnName = MyBatis3FormattingUtilities.getEscapedColumnName(column);
            String javaProperty = column.getJavaProperty();
            int jdbcType = column.getJdbcType();

        } while (iterator.hasNext());


    }
}
