package bhz.dubbo.dependency.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {

    public static void main(String[] args) {
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                    new String[]{"dependency-provider.xml"});
            context.start();

            System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}