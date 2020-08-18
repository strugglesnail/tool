package com.wtf.tool.model;

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
    public UserDO(Long id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    private Long id;

    private String name;

    private String date;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
