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
* @date: 2020/9/13 20:33
*/
public class GetByIdSqlCreator implements SqlCreator {

    // likeIf判断语句
    private static final StringBuilder getIdSQL = new StringBuilder();


    private boolean isCreate;

    public GetByIdSqlCreator(boolean isCreate) {
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
//        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        // 主键
        IntrospectedColumn pkColumn = table.getPrimaryKeyColumns().get(0);

        XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", "getId"));
        select.addAttribute(new Attribute("parameterType", pkColumn.getFullyQualifiedJavaType().toString()));
        select.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        getIdSQL.append("SELECT <include refid=\"" + tableName + "Columns\" /> FROM  ").append( "`" + tableName + "`");
        getIdSQL.append(" <include refid=\"" + tableName + "DynamicWhere\" />");
        getIdSQL.append(" WHERE ").append(pkColumn.getActualColumnName()).append(" = #{").append(pkColumn.getJavaProperty()).append("}");
        select.addElement(new TextElement(getIdSQL.toString()));
        rootElement.addElement(select);
    }


}
