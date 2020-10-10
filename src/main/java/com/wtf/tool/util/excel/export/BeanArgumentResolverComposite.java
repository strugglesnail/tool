package com.wtf.tool.util.excel.export;

import com.wtf.tool.util.excel.export.param.BeanParameter;
import com.wtf.tool.util.excel.export.resolver.bean.BeanArgumentResolver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bean解析器集合
 */
public class BeanArgumentResolverComposite implements BeanArgumentResolver {

    private final List<BeanArgumentResolver> argumentResolvers = new LinkedList<>();
    private final Map<BeanParameter, BeanArgumentResolver> argumentResolversCache = new ConcurrentHashMap<>();



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
        return this.getArgumentResolver(parameter) != null;
    }

    @Override
    public Object resolverBean(BeanParameter parameter) {
        BeanArgumentResolver argumentResolver = this.getArgumentResolver(parameter);
        if (argumentResolver == null) {
            throw new IllegalArgumentException("Unknown bean annotation in class [" + parameter.getClassType() + "]");
        } else {
            return argumentResolver.resolverBean(parameter);
        }
    }

    public BeanArgumentResolver getArgumentResolver(BeanParameter parameter) {
        BeanArgumentResolver result = argumentResolversCache.get(parameter);
        if (result == null) {
            Iterator iterator = this.argumentResolvers.iterator();
            while (iterator.hasNext()) {
                BeanArgumentResolver argumentResolver = (BeanArgumentResolver)iterator.next();
                if (argumentResolver.supportsBean(parameter)) {
                    result = argumentResolver;
                    argumentResolversCache.put(parameter, argumentResolver);
                    break;
                }
            }
        }
        if (result == null) {
            throw new IllegalArgumentException("No suitable resolver for argument [" + parameter.getClassType() + "]");
        }
        return result;
    }
}
