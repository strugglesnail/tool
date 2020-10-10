package com.wtf.tool.util.excel.export;

import com.wtf.tool.util.excel.export.param.PropertyParameter;
import com.wtf.tool.util.excel.export.resolver.prop.PropertyArgumentResolver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 属性参数解析器
 */
public class PropertyArgumentResolverComposite implements PropertyArgumentResolver {

    private final List<PropertyArgumentResolver> argumentResolvers = new LinkedList<>();
    private final Map<PropertyParameter, PropertyArgumentResolver> argumentResolversCache = new ConcurrentHashMap<>();


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
    public PropertyArgumentResolverComposite addResolvers(List<PropertyArgumentResolver> resolvers) {
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
    public boolean supportsProperty(PropertyParameter parameter) {
        return this.getArgumentResolver(parameter) != null;
    }

    @Override
    public Object resolverProperty(PropertyParameter parameter) {
        PropertyArgumentResolver argumentResolver = this.getArgumentResolver(parameter);
        if (argumentResolver == null) {
            throw new IllegalArgumentException("Unknown property type [" + parameter.getField().getName() + "]");
        } else {
            return argumentResolver.resolverProperty(parameter);
        }
    }

    @Override
    public void setTitle(PropertyParameter parameter) {
        PropertyArgumentResolver argumentResolver = this.getArgumentResolver(parameter);
        if (argumentResolver == null) {
            throw new IllegalArgumentException("Unknown property type [" + parameter.getField().getName() + "]");
        } else {
            argumentResolver.setTitle(parameter);
        }
    }

    public PropertyArgumentResolver getArgumentResolver(PropertyParameter parameter) {
        PropertyArgumentResolver result = argumentResolversCache.get(parameter);
        if (result == null) {
            Iterator iterator = this.argumentResolvers.iterator();
            while (iterator.hasNext()) {
                PropertyArgumentResolver argumentResolver = (PropertyArgumentResolver)iterator.next();
                if (argumentResolver.supportsProperty(parameter)) {
                    result = argumentResolver;
                    argumentResolversCache.put(parameter, argumentResolver);
                    break;
                }
            }
        }
        return result;
    }
}
