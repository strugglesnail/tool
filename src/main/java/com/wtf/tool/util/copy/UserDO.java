package com.wtf.tool.util.copy;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;

public class UserDO {

    public UserDO() {
    }

    public UserDO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws NoSuchMethodException, IntrospectionException {
        Class<? extends UserDO> aClass = new UserDO(1L, "11").getClass();
        Constructor<? extends UserDO> constructor = aClass.getDeclaredConstructor();
        Constructor<? extends UserDO> constructor1 = aClass.getDeclaredConstructor(Long.class, String.class);
//        System.out.println(constructor.getName());
//        System.out.println(constructor1);

        BeanInfo beanInfo = Introspector.getBeanInfo(UserDO.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd: propertyDescriptors) {
            System.out.println(pd.getWriteMethod());
            System.out.println(pd.getReadMethod().getName());
        }

    }
}
