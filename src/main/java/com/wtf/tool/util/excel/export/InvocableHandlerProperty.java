package com.wtf.tool.util.excel.export;


public class InvocableHandlerProperty extends HandlerProperty {

    private PropertyArgumentResolverComposite propertyArgumentResolverComposite = new PropertyArgumentResolverComposite();


    public InvocableHandlerProperty(HandlerProperty propertyParameter) {
        super(propertyParameter);
    }



    public void setPropertyArgumentResolverComposite(PropertyArgumentResolverComposite propertyArgumentResolverComposite) {
        this.propertyArgumentResolverComposite = propertyArgumentResolverComposite;
    }
}
