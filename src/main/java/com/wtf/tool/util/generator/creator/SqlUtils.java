package com.wtf.tool.util.generator.creator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class SqlUtils {

    // 创建公共类
    public static XmlElement buildSql(String id, String sqlStr) {
        XmlElement sql = new XmlElement("sql");
        sql.addAttribute(new Attribute("id", id));
        sql.addElement(new TextElement(sqlStr));
        return sql;
    }

    /**
     * 创建Dao方法
     * @param interfaze
     * @param entityType
     * @param methodName
     * @return
     */
    public static void createMethod(Interface interfaze, FullyQualifiedJavaType entityType, String methodName) {
        Method method = new Method();
        method.setName(methodName);
        method.addParameter(new Parameter(entityType, entityType.getShortName()));
        interfaze.addMethod(method);
    }
}
