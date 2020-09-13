package com.wtf.tool.util.generator.creator.sqlCreator;

import com.wtf.tool.util.generator.creator.core.SqlCreator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
*
* @author: wang_tengfei
* @date: 2020/9/13 22:24
*/
public class SaveSqlCreator implements SqlCreator {

    private boolean isCreate;

    public SaveSqlCreator(boolean isCreate) {
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
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        // 主键
        IntrospectedColumn pkColumn = table.getPrimaryKeyColumns().get(0);

        XmlElement save = new XmlElement("insert");
        save.addAttribute(new Attribute("id", "save"));
        save.addAttribute(new Attribute("keyProperty", pkColumn.getJavaProperty()));
        save.addAttribute(new Attribute("useGeneratedKeys", "true"));
        save.addAttribute(new Attribute("parameterType", entityType.getFullyQualifiedName()));
        save.addElement(new TextElement("<include refid=\"save_" + tableName + "_columns\" /><include refid=\"save_" + tableName + "_values\" />"));
        rootElement.addElement(save);
    }

}
