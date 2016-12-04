package bhz.netty.ende4.tcpserver;

import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Startup {
    public static void main(String[] args) {
        final AbstractXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Runtime.getRuntime().addShutdownHook(new Thread("shutdown-hook") {
            public void run() {
                context.close();
            }
        });
    }
}
