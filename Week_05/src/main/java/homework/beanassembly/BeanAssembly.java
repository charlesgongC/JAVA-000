package homework.beanassembly;

import homework.beanassembly.JavaConfig.BeanConfiguration;
import homework.beanassembly.bean.Teacher;
import homework.beanassembly.componentscan.ComponentScanConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanAssembly {
    public static void main(String[] args) {
        byXml();

        byAnnotation();

        byComponentScan();
    }

    private static void byXml() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        Teacher teacher = ctx.getBean(Teacher.class);
        System.out.println("byXml");
        System.out.println(teacher);
    }

    private static void byAnnotation() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        Teacher teacher = ctx.getBean(Teacher.class);
        System.out.println("byAnnotation");
        System.out.println(teacher);
    }

    private static void byComponentScan() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ComponentScanConfiguration.class);
        String[] definitionNames = ctx.getBeanDefinitionNames();
        System.out.println("byComponentScan");
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }

}
