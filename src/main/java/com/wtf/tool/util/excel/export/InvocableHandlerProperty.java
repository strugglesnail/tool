package com.wtf.tool.util.excel.export;

import com.wtf.tool.util.excel.export.param.PropertyParameter;

/**
 * @auther: strugglesnail
 * @date: 2020/10/8 10:51
 * @desc: 属性处理器
 */
public class InvocableHandlerProperty {

    private PropertyArgumentResolverComposite propertyArgumentResolverComposite = new PropertyArgumentResolverComposite();


    // 初始化属性解析器组件
    public void setPropertyArgumentResolverComposite(PropertyArgumentResolverComposite propertyArgumentResolverComposite) {
        this.propertyArgumentResolverComposite = propertyArgumentResolverComposite;
    }

    // 解析属性
    public void handlerProperty(PropertyParameter parameter) {
        if (this.propertyArgumentResolverComposite.supportsProperty(parameter)) {
            this.propertyArgumentResolverComposite.resolverProperty(parameter);
        }
    }

    // 设置标题
    public void handlerTitle(PropertyParameter parameter) {
        if (this.propertyArgumentResolverComposite.supportsProperty(parameter)) {
            this.propertyArgumentResolverComposite.setTitle(parameter);
        }
    }
}
