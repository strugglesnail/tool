package com.wtf.tool.util.generator.creator.core;

import com.wtf.tool.util.generator.creator.AbstractSqlCreator;
import com.wtf.tool.util.generator.creator.sqlCreator.*;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Document;

import java.util.*;

/**
*
* @author: wang_tengfei
* @date: 2020/9/12
*/
public class SqlCreatorComposite extends AbstractSqlCreator {

    // 创建器列表
    private static final List<SqlCreator> CREATOR_LIST = new LinkedList<>();

    static {
        CREATOR_LIST.add(new ColumnSqlCreator(true));
        CREATOR_LIST.add(new WhereIfSqlCreator(true));
        CREATOR_LIST.add(new WhereIfLikeSqlCreator(true));
        CREATOR_LIST.add(new CountSqlCreator(true));
        CREATOR_LIST.add(new CountLikeSqlCreator(true));
        CREATOR_LIST.add(new ListPageSqlCreator(true));
        CREATOR_LIST.add(new ListLikePageSqlCreator(true));
        CREATOR_LIST.add(new ListSqlCreator(true));
        CREATOR_LIST.add(new GetByIdSqlCreator(true));
        CREATOR_LIST.add(new GetOneSqlCreator(true));

        CREATOR_LIST.add(new SaveColumnSqlCreator(true));
        CREATOR_LIST.add(new SaveValueSqlCreator(true));
        CREATOR_LIST.add(new SaveBatchColumnSqlCreator(true));
        CREATOR_LIST.add(new SaveSqlCreator(true));
        CREATOR_LIST.add(new SaveBatchSqlCreator(true));

        CREATOR_LIST.add(new UpdateColumnSqlCreator(true));
        CREATOR_LIST.add(new UpdateSqlCreator(true));
        CREATOR_LIST.add(new UpdateBatchSqlCreator(true));

        CREATOR_LIST.add(new DeleteSqlCreator(true));



    }



    @Override
    protected void createSqlInternal(Document document, IntrospectedTable table) {
        for (SqlCreator creator : CREATOR_LIST) {
            if (creator.isCreate()) {
                creator.createSql(document, table);
            }
        }
    }
}
