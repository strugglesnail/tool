package com.wtf.tool.util.excel.export;


public class InvocableHandlerProperty {

    private PropertyArgumentResolverComposite propertyArgumentResolverComposite = new PropertyArgumentResolverComposite();


    public void setPropertyArgumentResolverComposite(PropertyArgumentResolverComposite propertyArgumentResolverComposite) {
        this.propertyArgumentResolverComposite = propertyArgumentResolverComposite;
    }

    public void handlerProperty(PropertyParameter parameter) {
        if (this.propertyArgumentResolverComposite.supportsProperty(parameter)) {
            this.propertyArgumentResolverComposite.resolverProperty(parameter);
        }
    }
    public void handlerTitle(PropertyParameter parameter) {
        if (this.propertyArgumentResolverComposite.supportsProperty(parameter)) {
            this.propertyArgumentResolverComposite.setTitle(parameter);
        }
    }
}
