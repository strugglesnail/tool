package com.wtf.tool.util.generator.creator.core;

import com.wtf.tool.util.generator.creator.SqlEnum;
import com.wtf.tool.util.generator.creator.sqlCreator.*;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.xml.Document;

import java.util.*;

/**
*
* @author: wang_tengfei
* @date: 2020/9/12
*/
public class SqlCreatorComposite extends AbstractCreator {

    // 创建器列表
    private static final List<SqlCreator> CREATOR_LIST = new LinkedList<>();

    static {

        // 公共SQL变量
        CREATOR_LIST.add(new ColumnSqlCreator(SqlEnum.ATTRIBUTE_COLUMN.getAttributeId(), SqlEnum.ATTRIBUTE_COLUMN.isCreate()));
        CREATOR_LIST.add(new WhereIfSqlCreator(SqlEnum.ATTRIBUTE_DYNAMIC_WHERE.getAttributeId(), SqlEnum.ATTRIBUTE_DYNAMIC_WHERE.isCreate()));
        CREATOR_LIST.add(new WhereIfLikeSqlCreator(SqlEnum.ATTRIBUTE_DYNAMIC_LIKE_WHERE.getAttributeId(), SqlEnum.ATTRIBUTE_DYNAMIC_LIKE_WHERE.isCreate()));

        // 查询生成器
        CREATOR_LIST.add(new ListPageSqlCreator(SqlEnum.ATTRIBUTE_LIST_PAGE.getAttributeId(), SqlEnum.ATTRIBUTE_LIST_PAGE.isCreate()));
        CREATOR_LIST.add(new ListLikePageSqlCreator(SqlEnum.ATTRIBUTE_LIST_LIKE_PAGE.getAttributeId(), SqlEnum.ATTRIBUTE_LIST_LIKE_PAGE.isCreate()));
        CREATOR_LIST.add(new ListSqlCreator(SqlEnum.ATTRIBUTE_LIST.getAttributeId(), SqlEnum.ATTRIBUTE_LIST.isCreate()));
        CREATOR_LIST.add(new GetByIdSqlCreator(SqlEnum.ATTRIBUTE_GETBYID.getAttributeId(), SqlEnum.ATTRIBUTE_GETBYID.isCreate()));
        CREATOR_LIST.add(new GetOneSqlCreator(SqlEnum.ATTRIBUTE_GET_ONE.getAttributeId(), SqlEnum.ATTRIBUTE_GET_ONE.isCreate()));
        CREATOR_LIST.add(new CountSqlCreator(SqlEnum.ATTRIBUTE_COUNT.getAttributeId(), SqlEnum.ATTRIBUTE_COUNT.isCreate()));
        CREATOR_LIST.add(new CountLikeSqlCreator(SqlEnum.ATTRIBUTE_COUNT_LIKE.getAttributeId(), SqlEnum.ATTRIBUTE_COUNT_LIKE.isCreate()));


        // 新增生成器
        CREATOR_LIST.add(new SaveColumnSqlCreator(SqlEnum.ATTRIBUTE_SAVE_COLUMN.getAttributeId(), SqlEnum.ATTRIBUTE_SAVE_COLUMN.isCreate()));
        CREATOR_LIST.add(new SaveValueSqlCreator(SqlEnum.ATTRIBUTE_SAVE_VALUE.getAttributeId(), SqlEnum.ATTRIBUTE_SAVE_VALUE.isCreate()));
        CREATOR_LIST.add(new SaveBatchColumnSqlCreator(SqlEnum.ATTRIBUTE_SAVE_BATCH_COLUMN.getAttributeId(), SqlEnum.ATTRIBUTE_SAVE_BATCH_COLUMN.isCreate()));
        CREATOR_LIST.add(new SaveSqlCreator(SqlEnum.ATTRIBUTE_SAVE.getAttributeId(), SqlEnum.ATTRIBUTE_SAVE.isCreate()));
        CREATOR_LIST.add(new SaveBatchSqlCreator(SqlEnum.ATTRIBUTE_SAVE_BATCH.getAttributeId(), SqlEnum.ATTRIBUTE_SAVE_BATCH.isCreate()));

        // 修改生成器
        CREATOR_LIST.add(new UpdateColumnSqlCreator(SqlEnum.ATTRIBUTE_UPDATE_COLUMN.getAttributeId(), SqlEnum.ATTRIBUTE_UPDATE_COLUMN.isCreate()));
        CREATOR_LIST.add(new UpdateBatchColumnSqlCreator(SqlEnum.ATTRIBUTE_UPDATE_BATCH_COLUMN.getAttributeId(), SqlEnum.ATTRIBUTE_UPDATE_BATCH_COLUMN.isCreate()));
        CREATOR_LIST.add(new UpdateSqlCreator(SqlEnum.ATTRIBUTE_UPDATE.getAttributeId(), SqlEnum.ATTRIBUTE_UPDATE.isCreate()));
        CREATOR_LIST.add(new UpdateBatchSqlCreator(SqlEnum.ATTRIBUTE_UPDATE_BATCH.getAttributeId(), SqlEnum.ATTRIBUTE_UPDATE_BATCH.isCreate()));

        // 删除生成器
        CREATOR_LIST.add(new DeleteSqlCreator(SqlEnum.ATTRIBUTE_DELETE.getAttributeId(), SqlEnum.ATTRIBUTE_DELETE.isCreate()));



    }



    //创建Mapper.xml文件
    @Override
    protected void createSqlInternal(Document document, IntrospectedTable table) {
        for (SqlCreator creator : CREATOR_LIST) {
            if (creator.isCreate()) {
                creator.createSql(document, table);
            }
        }
    }

    // 创建Dao文件
    @Override
    protected void createDaoInternal(Interface interfaze, IntrospectedTable table) {
        for (SqlCreator creator : CREATOR_LIST) {
            if (creator.isCreate()) {
                creator.createDao(interfaze, table);
            }
        }
    }
}
