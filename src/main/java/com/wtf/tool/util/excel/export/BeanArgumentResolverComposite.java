package com.wtf.tool.util.excel.export;

import com.wtf.tool.util.excel.export.resolver.BeanArgumentResolver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BeanArgumentResolverComposite implements BeanArgumentResolver {

    private final List<BeanArgumentResolver> argumentResolvers = new LinkedList<>();


    public BeanArgumentResolverComposite addResolver(BeanArgumentResolver resolver) {
        this.argumentResolvers.add(resolver);
        return this;
    }

    public BeanArgumentResolverComposite addResolvers(BeanArgumentResolver... resolvers) {
        if (resolvers != null) {
            Collections.addAll(this.argumentResolvers, resolvers);
        }
        return this;
    }
    public BeanArgumentResolverComposite addResolvers(List<? extends BeanArgumentResolver> resolvers) {
        if (resolvers != null) {
            this.argumentResolvers.addAll(resolvers);
        }
        return this;
    }

    public List<BeanArgumentResolver> getResolvers() {
        return Collections.unmodifiableList(this.argumentResolvers);
    }

    public void clear() {
        this.argumentResolvers.clear();
    }


    @Override
    public boolean supportsBean(BeanParameter parameter) {
        return false;
    }

    @Override
    public Object resolverBean(BeanParameter parameter) {
        return null;
    }
}
