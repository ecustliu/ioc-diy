import com.spring.MyAnnotationContext;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        MyAnnotationContext context = new MyAnnotationContext("com.spring");
        Set<String> beanNames = context.getBeanNames();
        /*
            测试Component：只有这个注解才会自动创建对象 Component注解中value为空，默认对象名就是类名首字母小写，否者就是value值
            测试Value 给基本类型赋值
            测试Autowired：
                1. 当没有Qualifier注解，默认byType方式
                2. 当有Qualifier注解，注解中的value就是属性名
         */
        for(String beanName:beanNames){
            System.out.println("beanName="+beanName+",bean="+context.getBean(beanName));
        }

    }
}
