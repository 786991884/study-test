package bhz.dubbo.dependency.test;

import bhz.dubbo.dependency.provider.DependencyService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"dependency-consumer.xml"});
        context.start();

        DependencyService dependencyService = (DependencyService) context.getBean("dependencyService");
        //调用该服务
        System.out.println(dependencyService.dependency());

        System.in.read();
    }

}