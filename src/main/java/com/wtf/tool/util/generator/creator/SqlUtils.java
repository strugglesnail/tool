package com.wtf.tool.util.generator.creator;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

public class SqlUtils {

    // 创建SQL
    public static XmlElement buildSql(String id, String sqlStr) {
        XmlElement sql = new XmlElement("sql");
        sql.addAttribute(new Attribute("id", id));
        sql.addElement(new TextElement(sqlStr));
        return sql;
    }

    /**
     *
     * @param document
     * @param table
     * @param methodName
     * @return
     */
    public static XmlElement buildSqlForList(Document document, IntrospectedTable table, String methodName) {
        XmlElement rootElement = document.getRootElement();
        // 表名
        String tableName = table.getFullyQualifiedTableNameAtRuntime();
        // 实体类
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());

        XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", methodName));
        select.addAttribute(new Attribute("parameterType", entityType.getFullyQualifiedName()));
        select.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        StringBuilder listPageSQL = new StringBuilder();
        listPageSQL.append("SELECT <include refid=\"" + tableName + SqlEnum.ATTRIBUTE_COLUMN.getAttributeId() +"\" /> FROM  ").append( "`" + tableName + "`");
        listPageSQL.append(" <include refid=\"" + tableName + SqlEnum.ATTRIBUTE_DYNAMIC_WHERE.getAttributeId() + "\" />");
        select.addElement(new TextElement(listPageSQL.toString()));
        rootElement.addElement(select);
        return rootElement;
    }


    /**
     *
     * 创建Dao方法
     * @param interfaze
     * @param table
     * @param methodName
     */
    public static void createMethodForCount(Interface interfaze, IntrospectedTable table, String methodName) {
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        Method method = new Method();
        method.setName(methodName);
        method.addParameter(new Parameter(entityType, entityType.getShortName()));
        method.setReturnType(new FullyQualifiedJavaType(Long.class.getSimpleName()));
        interfaze.addMethod(method);
    }
    /**
     *
     * @param interfaze
     * @param entityType
     * @param methodName
     * @return
     */
    public static void createMethodForUpdate(Interface interfaze, FullyQualifiedJavaType entityType, String methodName) {
        Method method = new Method();
        method.setName(methodName);
        method.addParameter(new Parameter(entityType, entityType.getShortName()));
        interfaze.addMethod(method);
    }

    /**
     *
     * @param interfaze
     * @param table
     * @param methodName
     */
    public static void createMethodForPage(Interface interfaze, IntrospectedTable table, String methodName) {
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        FullyQualifiedJavaType rowBoundsType = new FullyQualifiedJavaType(RowBounds.class.getName());
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(List.class.getName());
        Method method = new Method();
        method.setName(methodName);
        method.addParameter(new Parameter(entityType, SqlUtils.lowerFirst(entityType.getShortName())));
        method.addParameter(new Parameter(new FullyQualifiedJavaType(RowBounds.class.getSimpleName()), SqlUtils.lowerFirst(rowBoundsType.getShortName())));
        method.setReturnType(new FullyQualifiedJavaType(List.class.getSimpleName() + "<" + entityType + ">"));
        interfaze.addMethod(method);
        interfaze.addImportedType(listType);
        interfaze.addImportedType(rowBoundsType);
    }

    /**
     *
     * @param interfaze
     * @param table
     * @param methodName
     */
    public static void createMethodForUpdateBatch(Interface interfaze, IntrospectedTable table, String methodName) {
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(List.class.getName());

        Method method = new Method();
        method.setName(methodName);
        method.addParameter(new Parameter(new FullyQualifiedJavaType(List.class.getSimpleName() + "<" + entityType + ">"), "list"));
        interfaze.addMethod(method);
        interfaze.addImportedType(listType);
    }


    /**
    *
    * @param str
    * @return 首字母小写
    */
    public static String lowerFirst(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
