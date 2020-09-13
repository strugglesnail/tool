package com.wtf.tool.util.generator.creator.sqlCreator;

import com.wtf.tool.util.generator.creator.core.SqlCreator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
*
* @author: wang_tengfei
* @date: 2020/9/13 22:36
*/
public class SaveBatchSqlCreator implements SqlCreator {

    private final StringBuilder saveBatchSQL = new StringBuilder();

    private boolean isCreate;

    public SaveBatchSqlCreator(boolean isCreate) {
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

        XmlElement save = new XmlElement("insert");
        save.addAttribute(new Attribute("id", "saveBatch"));
        save.addAttribute(new Attribute("parameterType", "list"));
                saveBatchSQL.append("<include refid=\"save_" + tableName + "_columns\"/>\n\t")
                .append("<foreach collection=\"list\" index=\"index\" item=\"item\" separator=\",\">\n\t")
                        .append("\t<include refid=\"batch_save_" + tableName + "_values\"/>\n\t")
                        .append("</foreach>");
        save.addElement(new TextElement(saveBatchSQL.toString()));
        rootElement.addElement(save);
    }

}
