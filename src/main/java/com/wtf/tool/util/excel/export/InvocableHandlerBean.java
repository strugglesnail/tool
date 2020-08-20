package com.wtf.tool.util.excel.export;


public class InvocableHandlerBean extends HandlerBean {

    private BeanArgumentResolverComposite classArgumentResolverComposite = new BeanArgumentResolverComposite();


    public InvocableHandlerBean(HandlerBean handlerBean) {
        super(handlerBean);
    }



    public void setBeanArgumentResolverComposite(BeanArgumentResolverComposite classArgumentResolverComposite) {
        this.classArgumentResolverComposite = classArgumentResolverComposite;
    }
}
