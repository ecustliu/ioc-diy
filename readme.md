# readme
## 实现简单的ioc功能
### 1.自定义注解
Component注解:作用于类上，判断是否加入ioc容器
```java
/*
    作用于类上面
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
    String value() default "";//可缺省，默认为“”
}
```
Value注解 基本数据类型注入
```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {
    String value() ;//不可缺省
}
```
Autowired注解 
* 默认是根据类型注入
* 假如有Qualifier，根据name注入

Autowired
```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
}
```
Qualifier
```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Qualifier {
    String value() default "";//可缺省，默认为“”
}

```
### 2. 扫描组件
详见com.spring.util.FindAllClassUtil，用于扫描所有类

### 3. 创建对象
依次遍历所有Class，为所有被Component注解的类加入ioc容器
```java
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
```
### 4. 为每个对象注入属性
#### 4.1 Value注解，byName方式
```java
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
```
#### 4.2 Autowired
```java
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
```
