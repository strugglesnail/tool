package com.wtf.tool.util.generator.creator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
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
     * 创建查询SQL
     * @param id
     * @param table
     * @return
     */
    private XmlElement buildSelect(String id, IntrospectedTable table) {
        // 表名
        String tableName = table.getFullyQualifiedTableNameAtRuntime();
        // 实体类
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        // 主键
        IntrospectedColumn pkColumn = table.getPrimaryKeyColumns().get(0);

        XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", id));
        StringBuilder selectStr = new StringBuilder();

        if("count".equals(id) || "countLike".equals(id)){
//            select.addAttribute(new Attribute("resultType", "java.lang.Long"));
//            select.addAttribute(new Attribute("parameterType", entityType.getFullyQualifiedName()));
//            selectStr.append("select count(1) from ").append("`" + tableName + "`");
        } else {
            select.addAttribute(new Attribute("resultMap", "BaseResultMap"));
            selectStr.append("select <include refid=\""+tableName+"Columns\" /> from  ").append( "`" + tableName + "`");
        }
        if (null != pkColumn) {
            select.addAttribute(new Attribute("parameterType", pkColumn.getFullyQualifiedJavaType().toString()));
            selectStr.append(" where ").append(pkColumn.getActualColumnName()).append(" = #{").append(pkColumn.getJavaProperty()).append("}");
        } else {
            if ("countLike".equals(id)) {
//                selectStr.append(" <include refid=\""+tableName+"DynamicLikeWhere\" />");
            } else if ("listLikePage".equals(id)){
//                selectStr.append(" <include refid=\"" +tableName+ "DynamicLikeWhere\" />");
//                selectStr.append("\n\t<if test=\"orderBy != null\">\n" +
//                        "            order by ${orderBy}\n" +
//                        "        </if>\n" +
//                        "        <if test=\"fromIndex != null and fromIndex > -1 and pageSize != null and pageSize > 0\">\n" +
//                        "            limit #{fromIndex},#{pageSize}\n" +
//                        "        </if>");
//                select.addAttribute(new Attribute("parameterType","map"));
            } else {
//                selectStr.append(" <include refid=\""+tableName+"DynamicWhere\" />");
            }
        }

        if ("listPage".equals(id)) {
//            selectStr.append("\n\t<if test=\"orderBy != null\">\n" +
//                    "            order by ${orderBy}\n" +
//                    "        </if>\n" +
//                    "        <if test=\"fromIndex != null and fromIndex > -1 and pageSize != null and pageSize > 0\">\n" +
//                    "            limit #{fromIndex},#{pageSize}\n" +
//                    "        </if>");
//            select.addAttribute(new Attribute("parameterType","map"));
        }

        if("list".equals(id)){
//            select.addAttribute(new Attribute("parameterType","map"));
//            selectStr.append("\n\t<if test=\"orderBy != null and orderBy != ''\">\n" +
//                    "          order by ${orderBy}\n" +
//                    "      </if>");
        }

        if ("getOne".equals(id)) {
//            selectStr.append(" limit 0 , 1");
//            select.addAttribute(new Attribute("parameterType",entityType.getFullyQualifiedName()));
        }

        select.addElement(new TextElement(selectStr.toString()+""));
        return select;
    }
}
