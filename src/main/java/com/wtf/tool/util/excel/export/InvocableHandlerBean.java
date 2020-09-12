package com.wtf.tool.util.excel.export;


public class InvocableHandlerBean {

    private BeanArgumentResolverComposite classArgumentResolverComposite = new BeanArgumentResolverComposite();




    public void setBeanArgumentResolverComposite(BeanArgumentResolverComposite classArgumentResolverComposite) {
        this.classArgumentResolverComposite = classArgumentResolverComposite;
    }

    public void setHandlerBean(BeanParameter parameter) {
        this.classArgumentResolverComposite.resolverBean(parameter);
    }
}
