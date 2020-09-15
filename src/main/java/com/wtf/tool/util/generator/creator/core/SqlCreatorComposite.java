package com.wtf.tool.util.generator.creator.core;

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
        CREATOR_LIST.add(new ColumnSqlCreator("Columns",true));
        CREATOR_LIST.add(new WhereIfSqlCreator("DynamicWhere",true));
        CREATOR_LIST.add(new WhereIfLikeSqlCreator("", true));
        CREATOR_LIST.add(new CountSqlCreator("count", true));
        CREATOR_LIST.add(new CountLikeSqlCreator("countLike", true));
        CREATOR_LIST.add(new ListPageSqlCreator("listPage", true));
        CREATOR_LIST.add(new ListLikePageSqlCreator("listLikePage", true));
        CREATOR_LIST.add(new ListSqlCreator("list", true));
        CREATOR_LIST.add(new GetByIdSqlCreator("getById", true));
        CREATOR_LIST.add(new GetOneSqlCreator("getOne", true));

        CREATOR_LIST.add(new SaveColumnSqlCreator("saveColumn", true));
        CREATOR_LIST.add(new SaveValueSqlCreator("saveValue", true));
        CREATOR_LIST.add(new SaveBatchColumnSqlCreator("saveBatchColumn", true));
        CREATOR_LIST.add(new SaveSqlCreator("save", true));
        CREATOR_LIST.add(new SaveBatchSqlCreator("saveBatch", true));

        CREATOR_LIST.add(new UpdateColumnSqlCreator("Update", true));
        CREATOR_LIST.add(new UpdateSqlCreator("update", true));
        CREATOR_LIST.add(new UpdateBatchSqlCreator("updateBatch", true));

        CREATOR_LIST.add(new DeleteSqlCreator("delete", true));



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
