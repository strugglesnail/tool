package com.wtf.tool.util.generator.creator.sqlCreator;

import com.wtf.tool.util.generator.creator.core.SqlCreator;
import com.wtf.tool.util.generator.creator.SqlUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

public class WhereIfLikeSqlCreator implements SqlCreator {

    // likeIfSQL判断语句
    private final StringBuilder likeIfSQL = new StringBuilder();


    private boolean isCreate;

    public WhereIfLikeSqlCreator(boolean isCreate) {
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

            likeIfSQL.append("      <if test=\"null != ").append(javaProperty).append(" and '' !=  ").append(javaProperty).append("\">");
            likeIfSQL.append("AND ").append(columnName).append(" LIKE #{ ").append(javaProperty).append("}</if>\n");
        }
        String whereLikeSQL = MessageFormat.format("<where>\n{0}\t</where>", likeIfSQL.toString());
        rootElement.addElement(SqlUtils.buildSql(tableName + "DynamicLikeWhere", whereLikeSQL));
    }


}
