package com.spring;

public class BeanDefinition {
    private String beanName;
    private Class clazz;

    public BeanDefinition(String beanName, Class clazz) {
        this.beanName = beanName;
        this.clazz = clazz;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
