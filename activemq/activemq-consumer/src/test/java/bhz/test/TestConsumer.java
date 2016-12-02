
package bhz.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 */
public class TestConsumer {

    public static void main(String[] args) {
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring-context.xml"});
            context.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
