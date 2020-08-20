package com.wtf.tool.util.excel.export;

import com.wtf.tool.util.excel.export.resolver.PropertyArgumentResolver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PropertyArgumentResolverComposite implements PropertyArgumentResolver {

    private final List<PropertyArgumentResolver> argumentResolvers = new LinkedList<>();


    public PropertyArgumentResolverComposite addResolver(PropertyArgumentResolver resolver) {
        this.argumentResolvers.add(resolver);
        return this;
    }

    public PropertyArgumentResolverComposite addResolvers(PropertyArgumentResolver... resolvers) {
        if (resolvers != null) {
            Collections.addAll(this.argumentResolvers, resolvers);
        }
        return this;
    }
    public PropertyArgumentResolverComposite addResolvers(List<? extends PropertyArgumentResolver> resolvers) {
        if (resolvers != null) {
            this.argumentResolvers.addAll(resolvers);
        }
        return this;
    }

    public List<PropertyArgumentResolver> getResolvers() {
        return Collections.unmodifiableList(this.argumentResolvers);
    }

    public void clear() {
        this.argumentResolvers.clear();
    }


    @Override
    public boolean supportsField(PropertyParameter parameter) {
        return false;
    }

    @Override
    public Object resolverField(PropertyParameter parameter) {
        return null;
    }
}
