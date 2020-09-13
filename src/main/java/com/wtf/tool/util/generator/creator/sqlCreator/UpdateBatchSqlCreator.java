package com.wtf.tool.util.generator.creator.sqlCreator;

import com.wtf.tool.util.generator.creator.core.SqlCreator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
*
* @author: wang_tengfei
* @date: 2020/9/13 22:50
*/
public class UpdateBatchSqlCreator implements SqlCreator {

    private boolean isCreate;

    public UpdateBatchSqlCreator(boolean isCreate) {
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

        XmlElement update = new XmlElement("update");
        update.addAttribute(new Attribute("id", "updateBatch"));
        update.addAttribute(new Attribute("parameterType", "list"));
        update.addElement(new TextElement(
                "<foreach collection=\"list\" index=\"index\" item=\"item\" open=\"\" separator=\";\" close=\"\">\n\t  " +
                        "\t<include refid=\"" + tableName + "Update\" />\n\t" +
                        "</foreach>"));
        rootElement.addElement(update);
    }


}
