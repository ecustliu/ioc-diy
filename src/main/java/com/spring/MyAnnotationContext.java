package com.spring;

import com.spring.annotation.Autowired;
import com.spring.annotation.Component;
import com.spring.annotation.Qualifier;
import com.spring.annotation.Value;
import com.spring.util.FindAllClassUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/*

 */
public class MyAnnotationContext {
    private final HashMap<String, Object> ioc = new HashMap<>();//beanName-->对象

    public MyAnnotationContext(String pack) {//包路径
        Set<Class<?>> classes = FindAllClassUtil.getClasses(pack);
        createObjects(classes);//创造添加Component注解的对象（放入ioc容器中，key是对象名，value存放该类的对象）

        //ioc自动注入：Value表示基本类型注入，Autowired表示Bytype
        for (String beanName : ioc.keySet()) {
            Object object = ioc.get(beanName);
            injectProperty(object);
        }
    }

    public Set<String> getBeanNames() {
        return ioc.keySet();
    }

    public Object getBean(String beanName) {
        return ioc.get(beanName);
    }

    public Collection<Object> getBeans() {
        return ioc.values();
    }


    //根据已经获得的对象object, 利用反射注入属性
    private void injectProperty(Object object) {
        //获取Class
        Class clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();//获取所有成员变量信息
        for (Field field : declaredFields) {
            //获取属性上面的注解
            Value valueAnno = field.getAnnotation(Value.class);
            Autowired autowiredAnno = field.getAnnotation(Autowired.class);
            if (valueAnno != null) {//先看是不是Value注解
                injectByValue(field, valueAnno, object);
            } else if (autowiredAnno != null) {//再看是不是Autowired注解
                injectByAutowired(field, object);
            }
        }

    }

    private void injectByAutowired(Field field, Object object) {
        //Qualifier注解 byName的方式
        Qualifier qualifierAnno = field.getAnnotation(Qualifier.class);
        if (qualifierAnno != null) {//byName
            String name = qualifierAnno.value();
            field.setAccessible(true);
            try {
                field.set(object, ioc.get(name));//field.set(对象,属性名)
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        } else {//byType
            //遍历ioc容器中所有对象
            for (String iocBeanName : ioc.keySet()) {
                if (ioc.get(iocBeanName).getClass() == field.getType()) {//属性类型在ioc容器中出现
                    field.setAccessible(true);
                    try {
                        field.set(object, ioc.get(iocBeanName));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void injectByValue(Field field, Value valueAnno, Object object) {
        String value = valueAnno.value();//获取Value注解的值，这个就是属性的值
        field.setAccessible(true);
        Object val = null;
        switch (field.getType().getSimpleName()) {//判断类型名
            case "Integer":
                val = Integer.parseInt(value);
                break;
            case "String":
                val = value;
                break;
            case "Float":
                val = Float.parseFloat(value);
        }
        try {
            field.set(object, val);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private void createObjects(Set<Class<?>> classes) {
        for (Class clazz : classes) {
            Annotation componentAno = clazz.getAnnotation(Component.class);
            if (componentAno != null) {
                Object object = null;
                try {
                    object = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                String simpleName = clazz.getSimpleName();
                String beanName = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
                ioc.put(beanName, object);
            }
        }
    }
}
