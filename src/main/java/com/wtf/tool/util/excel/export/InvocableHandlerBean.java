package com.wtf.tool.util.excel.export;


import com.wtf.tool.util.excel.export.param.BeanParameter;

public class InvocableHandlerBean {

    private BeanArgumentResolverComposite beanArgumentResolverComposite = new BeanArgumentResolverComposite();




    public void setBeanArgumentResolverComposite(BeanArgumentResolverComposite beanArgumentResolverComposite) {
        this.beanArgumentResolverComposite = beanArgumentResolverComposite;
    }

    public void setHandlerBean(BeanParameter parameter) {
        this.beanArgumentResolverComposite.resolverBean(parameter);
    }
}
