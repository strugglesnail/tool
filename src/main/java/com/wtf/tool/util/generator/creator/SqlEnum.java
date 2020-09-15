package com.wtf.tool.util.generator.creator;

public enum SqlEnum {

    ATTRIBUTE_COLUMN("Columns", true),
    ATTRIBUTE_DYNAMIC_WHERE("DynamicWhere", true),
    ATTRIBUTE_DYNAMIC_LIKE_WHERE("DynamicLikeWhere", true),

    ATTRIBUTE_COUNT("count", true),
    ATTRIBUTE_COUNT_LIKE("countLike", true),
    ATTRIBUTE_LIST_PAGE("listPage", true),
    ATTRIBUTE_LIST_LIKE_PAGE("listLikePage", true),
    ATTRIBUTE_LIST("list", true),
    ATTRIBUTE_GETBYID("getById", true),
    ATTRIBUTE_GET_ONE("getOne", true),

    ATTRIBUTE_SAVE_COLUMN("saveColumn", true),
    ATTRIBUTE_SAVE_VALUE("saveValue", true),
    ATTRIBUTE_SAVE_BATCH_COLUMN("saveBatchColumn", true),
    ATTRIBUTE_SAVE("save", true),
    ATTRIBUTE_SAVE_BATCH("saveBatch", true),

    ATTRIBUTE_UPDATE_COLUMN("Update", true),
    ATTRIBUTE_UPDATE_BATCH_COLUMN("UpdateBatch", true),
    ATTRIBUTE_UPDATE("update", true),
    ATTRIBUTE_UPDATE_BATCH("updateBatch", true),

    ATTRIBUTE_DELETE("delete", true);

    private String attributeId;
    private boolean isCreate;

    SqlEnum(String attributeId, boolean isCreate) {
        this.attributeId = attributeId;
        this.isCreate = isCreate;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public boolean isCreate() {
        return isCreate;
    }
}
