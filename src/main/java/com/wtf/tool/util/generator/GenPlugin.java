package com.wtf.tool.util.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Context;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class GenPlugin extends PluginAdapter {
    private Set<String> mappers = new HashSet<String>();
    // 注释生成器
    private CommentGeneratorConfiguration commentCfg;
    private String tableName;
    private FullyQualifiedJavaType entityType;


    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    // 设置注释
    @Override
    public void setContext(Context context) {
        super.setContext(context);
        // 设置默认的注释生成器
        commentCfg = new CommentGeneratorConfiguration();
        commentCfg.setConfigurationType(GenCommentGenerator.class.getCanonicalName());
        context.setCommentGeneratorConfiguration(commentCfg);
        // 支持oracle获取注释#114
        context.getJdbcConnectionConfiguration().addProperty("remarksReporting", "true");
    }

    // 获取plugin属性值
    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String mappers = this.properties.getProperty("mappers");
        for (String mapper : mappers.split(",")) {
            this.mappers.add(mapper);
        }
    }
    /**
     * 生成的Mapper接口
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // 获取实体类
        entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        // import接口
        for (String mapper : mappers) {
            interfaze.addImportedType(new FullyQualifiedJavaType(mapper));
            interfaze.addSuperInterface(new FullyQualifiedJavaType(mapper + "<" + entityType.getShortName() + ">"));
        }
        // import实体类
        interfaze.addImportedType(entityType);
        return true;
    }

    /**
     * 拼装SQL语句生成Mapper接口映射文件
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement rootElement = document.getRootElement();
        // 数据库表名
        tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        // 主键
        IntrospectedColumn pkColumn = introspectedTable.getPrimaryKeyColumns().get(0);
        // 公共字段
        StringBuilder columnSQL = new StringBuilder();
        columnSQL.append("<![CDATA[ ");
        // if判断语句
        StringBuilder ifSQL = new StringBuilder();
        //LIKE if判断语句
        StringBuilder likeIfSQL = new StringBuilder();
        // 要插入的字段(排除自增主键)
        StringBuilder saveColumn = new StringBuilder("insert into ").append("`" + tableName + "`").append("(\n\t\t");
        // 要保存的值
        StringBuilder saveValue = new StringBuilder("(\n");
        // 批量要保存的值
        StringBuilder batchSaveValue = new StringBuilder("(\n");
        // 拼装更新字段
        StringBuilder updateSQL = new StringBuilder("update ").append("`" + tableName + "`").append(" \n\t<set> ").append("\n");
        // 数据库字段名
        String columnName = null;
        // java字段名
        String javaProperty = null;
        // jdbc字段名
        String jdbcProperty = null;
        int tableColumnSize = introspectedTable.getAllColumns().size();
        int tag = 0;
//        int counts = introspectedTable.getAllColumns().size();
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
            tag ++;
            columnName = MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn);
            javaProperty = introspectedColumn.getJavaProperty();
            jdbcProperty = introspectedColumn.getJdbcTypeName();
            // 拼接字段
            columnSQL.append(columnName).append(",");
            // 拼接if语句
            ifSQL.append("      <if test=\"null != ").append(javaProperty).append(" and '' !=  ").append(javaProperty).append("\">");
            ifSQL.append("and ").append(columnName).append(" = #{ ").append(javaProperty).append("}</if>\n");
            likeIfSQL.append("      <if test=\"null != ").append(javaProperty).append(" and '' !=  ").append(javaProperty).append("\">");
            likeIfSQL.append("and ").append(columnName).append(" LIKE #{ ").append(javaProperty).append("}</if>\n");
            // 拼接SQL
            if (!introspectedColumn.isAutoIncrement()) {
                String suffix = "";
                // 判断是最后一个字段
                if(tag == tableColumnSize) {
                    saveColumn.append(columnName);
                    suffix = "}\n";
                } else {
                    saveColumn.append(columnName + " ,");
                    suffix = "},\n";
                }
                saveValue.append("\t  ").append("#{ ").append(javaProperty)
                        .append(",jdbcType=")
                        .append(jdbcProperty)
                        .append(suffix);
                batchSaveValue.append("\t  ").append("#{ item.").append(javaProperty)
                        .append(",jdbcType=")
                        .append(jdbcProperty)
                        .append(suffix);

                // 时间格式用now()作为值
                /*
                 * if(Types.TIMESTAMP == introspectedColumn.getJdbcType()){
                 * saveValue.append(", now()"); }else{ saveValue.append(
                 * ", #{item.").append(javaProperty).append("}"); }
                 */

                updateSQL.append("      <if test=\"null != ").append(javaProperty).append("\">");

                updateSQL.append(columnName).append(" = #{ ").append(javaProperty)
                        .append(",jdbcType=")
                        .append(jdbcProperty);

                updateSQL.append("}");
                if(tag != tableColumnSize){
                    updateSQL.append(",</if>");
                }else {
                    updateSQL.append("</if>");
                    updateSQL.append("\n\t</set>");
                }
                updateSQL.append("\n");
            }
        }
        String columns = columnSQL.substring(0, columnSQL.length() - 1);
        columns+=" ]]>";
        rootElement.addElement(createSql(tableName+"Columns", columns));

        String whereSQL = MessageFormat.format("<where>\n{0}\t</where>", ifSQL.toString());
        rootElement.addElement(createSql(tableName+"DynamicWhere", whereSQL));
        String whereLikeSQL = MessageFormat.format("<where>\n{0}\t</where>", likeIfSQL.toString());
        rootElement.addElement(createSql(tableName+"DynamicLikeWhere", whereLikeSQL));

        rootElement.addElement(createSelect("getById", tableName, pkColumn));
        rootElement.addElement(createSelect("getOne", tableName, null));
        rootElement.addElement(createSelect("list", tableName, null));
        rootElement.addElement(createSelect("listPage", tableName, null));
        rootElement.addElement(createSelect("listLikePage", tableName, null));
        rootElement.addElement(createSelect("countLike", tableName, null));
        rootElement.addElement(createSelect("count", tableName, null));

        rootElement.addElement(createSql("save_" + tableName + "_columns", saveColumn.append("\n\t) values").toString()));
        rootElement.addElement(createSql("save_" + tableName + "_values", saveValue.append("\t)").toString()));
        rootElement.addElement(createSql("batch_save_" + tableName + "_values", batchSaveValue.append("\t)").toString()));
        rootElement.addElement(createSave("save", pkColumn));
        rootElement.addElement(createSave("batchSave", null));

        updateSQL.append("\twhere ").append(pkColumn.getActualColumnName()).append(" = #{").append(pkColumn.getJavaProperty()).append("}");
        rootElement.addElement(createSql(tableName+"Update", updateSQL.toString()));

        rootElement.addElement(createUpdate("update"));
        rootElement.addElement(createUpdate("batchUpdate"));

        rootElement.addElement(createDelById(tableName,pkColumn,"delById"));
        rootElement.addElement(createDels(tableName, pkColumn, "delArray", "array"));
        rootElement.addElement(createDels(tableName, pkColumn, "delList", "list"));
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    private Element createDelById(String tableName, IntrospectedColumn pkColumn , String method) {
        XmlElement delete = new XmlElement("delete");
        delete.addAttribute(new Attribute("id", method));
        StringBuilder deleteStr = new StringBuilder("<![CDATA[ delete from ").append("`" + tableName + "`").append(" where ").append(pkColumn.getActualColumnName())
                .append(" = #{").append(pkColumn.getActualColumnName()).append("} ]]>");
        delete.addElement(new TextElement(deleteStr.toString()));
        return delete;
    }

    /**
     * 公共SQL
     * @param id
     * @param sqlStr
     * @return
     */
    private XmlElement createSql(String id, String sqlStr) {
        XmlElement sql = new XmlElement("sql");
        sql.addAttribute(new Attribute("id", id));
        sql.addElement(new TextElement(sqlStr));
        return sql;
    }

    /**
     * 查询
     * @param id
     * @param tableName
     * @param pkColumn
     * @return
     */
    private XmlElement createSelect(String id, String tableName, IntrospectedColumn pkColumn) {
        XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", id));
        StringBuilder selectStr = new StringBuilder();
        if("count".equals(id) || "countLike".equals(id)){
            select.addAttribute(new Attribute("resultType", "java.lang.Long"));
            select.addAttribute(new Attribute("parameterType", entityType.getFullyQualifiedName()));
            selectStr.append("select count(1) from ").append("`" + tableName + "`");
        }else{
            select.addAttribute(new Attribute("resultMap", "BaseResultMap"));
            selectStr.append("select <include refid=\""+tableName+"Columns\" /> from  ").append( "`" + tableName + "`");
        }
        if (null != pkColumn) {
            select.addAttribute(new Attribute("parameterType",pkColumn.getFullyQualifiedJavaType().toString()));
            selectStr.append(" where ").append(pkColumn.getActualColumnName()).append(" = #{").append(pkColumn.getJavaProperty()).append("}");
        } else {
            if("countLike".equals(id)){
                selectStr.append(" <include refid=\""+tableName+"DynamicLikeWhere\" />");
            }else if("listLikePage".equals(id)){
                selectStr.append(" <include refid=\""+tableName+"DynamicLikeWhere\" />");
                selectStr.append("\n\t<if test=\"orderBy != null\">\n" +
                        "            order by ${orderBy}\n" +
                        "        </if>\n" +
                        "        <if test=\"fromIndex != null and fromIndex > -1 and pageSize != null and pageSize > 0\">\n" +
                        "            limit #{fromIndex},#{pageSize}\n" +
                        "        </if>");
                select.addAttribute(new Attribute("parameterType","map"));
            }else{
                selectStr.append(" <include refid=\""+tableName+"DynamicWhere\" />");
            }
        }
        if ("listPage".equals(id)) {
            selectStr.append("\n\t<if test=\"orderBy != null\">\n" +
                    "            order by ${orderBy}\n" +
                    "        </if>\n" +
                    "        <if test=\"fromIndex != null and fromIndex > -1 and pageSize != null and pageSize > 0\">\n" +
                    "            limit #{fromIndex},#{pageSize}\n" +
                    "        </if>");
            select.addAttribute(new Attribute("parameterType","map"));
        }
        if("list".equals(id)){
            select.addAttribute(new Attribute("parameterType","map"));
            selectStr.append("\n\t<if test=\"orderBy != null and orderBy != ''\">\n" +
                    "          order by ${orderBy}\n" +
                    "      </if>");
        }
        if ("getOne".equals(id)) {
            selectStr.append(" limit 0 , 1");
            select.addAttribute(new Attribute("parameterType",entityType.getFullyQualifiedName()));
        }
        select.addElement(new TextElement(selectStr.toString()+""));
        return select;
    }

    /**
     * 保存
     * @param id
     * @param pkColumn
     * @return
     */
    private XmlElement createSave(String id, IntrospectedColumn pkColumn) {
        XmlElement save = new XmlElement("insert");
        save.addAttribute(new Attribute("id", id));

        if (null != pkColumn) {
            save.addAttribute(new Attribute("parameterType", entityType.getFullyQualifiedName()));
            save.addAttribute(new Attribute("keyProperty", pkColumn.getJavaProperty()));
            save.addAttribute(new Attribute("useGeneratedKeys", "true"));
            save.addElement(new TextElement("<include refid=\"save_"+tableName+"_columns\" /><include refid=\"save_"+tableName+"_values\" />"));
        } else {
            save.addAttribute(new Attribute("parameterType", "list"));
            StringBuilder saveStr = new StringBuilder(
                    "<include refid=\"save_" + tableName + "_columns\"/>\n\t")
                    .append("<foreach collection=\"list\" index=\"index\" item=\"item\" separator=\",\">\n\t" +
                            "\t<include refid=\"batch_save_"+tableName+ "_values\"/>\n\t" +
                            "</foreach>");
//                    .append("<include refid=\"save_"+tableName+"_columns\" /><include refid=\"save_"+tableName+"_values\" />\n\t</foreach>");
            save.addElement(new TextElement(saveStr.toString()));
        }
        return save;
    }

    /**
     * 更新
     * @param id
     * @return
     */
    private XmlElement createUpdate(String id) {
        XmlElement update = new XmlElement("update");
        update.addAttribute(new Attribute("id", id));
        if ("update".equals(id)) {
            update.addAttribute(new Attribute("parameterType", entityType.getFullyQualifiedName()));
            update.addElement(new TextElement("<include refid=\""+tableName+"Update\" />"));
        } else {
            update.addAttribute(new Attribute("parameterType", "list"));
            update.addElement(new TextElement(
                    "<foreach collection=\"list\" index=\"index\" item=\"item\" open=\"\" separator=\";\" close=\"\">\n\t  " +
                            "\t<include refid=\"" + tableName+"Update\" />\n\t" +
                            "</foreach>"));
        }
        return update;
    }

    /**
     * 删除
     * @param tableName
     * @param pkColumn
     * @param method
     * @param type
     * @return
     */
    private XmlElement createDels(String tableName, IntrospectedColumn pkColumn, String method, String type) {
        XmlElement delete = new XmlElement("delete");
        delete.addAttribute(new Attribute("id", method));
        StringBuilder deleteStr = new StringBuilder("delete from ").append("`" + tableName + "`").append(" where ").append(pkColumn.getActualColumnName())
                .append(" in\n\t")
                .append("<foreach collection=\"").append(type)
                .append("\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach>");
        delete.addElement(new TextElement(deleteStr.toString()));
        return delete;
    }

    // 以下设置为false,取消生成默认增删查改xml
    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectAllMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectAllElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }
}
