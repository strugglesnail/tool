package com.wtf.tool.bytecode.model;

/**
 * @author strugglesnail
 * @date 2021/2/2
 * @desc
 */
public abstract class Repository<T> {
    public abstract T queryData(int id);
}
