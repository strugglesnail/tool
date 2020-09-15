package com.wtf.tool.util.generator.creator;

import com.wtf.tool.util.generator.GenCommentGenerator;
import com.wtf.tool.util.generator.creator.core.SqlCreatorComposite;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Context;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class CustomPlugin extends PluginAdapter {
    private Set<String> mappers = new HashSet<String>();

    private final SqlCreatorComposite composite = new SqlCreatorComposite();

    // 注释生成器
    private CommentGeneratorConfiguration commentCfg;
    private FullyQualifiedJavaType entityType;

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

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String mappers = this.properties.getProperty("mappers");
        for (String mapper : mappers.split(",")) {
            this.mappers.add(mapper);
        }
    }

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable table) {
        // 获取实体类
        entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        // import接口
//        for (String mapper : mappers) {
//            interfaze.addImportedType(new FullyQualifiedJavaType(mapper));
//            interfaze.addSuperInterface(new FullyQualifiedJavaType(mapper + "<" + entityType.getShortName() + ">"));
//        }
        Method method = new Method();
        method.setName("count");
        method.addParameter(new Parameter(entityType, entityType.getShortName()));
        method.setReturnType(new FullyQualifiedJavaType("Long"));
        interfaze.addMethod(method);
        Method method1 = new Method();
        method1.setName("countLike");
        method1.addParameter(new Parameter(entityType, entityType.getShortName()));
        method1.setReturnType(new FullyQualifiedJavaType("Long"));
        interfaze.addMethod(method1);
        // import实体类
        interfaze.addImportedType(entityType);
        return super.clientGenerated(interfaze, topLevelClass, table);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable table) {

        composite.createSql(document, table);
        return super.sqlMapDocumentGenerated(document, table);
    }

















    // 以下设置为false,取消生成默认增删查改xml
    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
//        method.setName("count");
//        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
//        interfaze
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