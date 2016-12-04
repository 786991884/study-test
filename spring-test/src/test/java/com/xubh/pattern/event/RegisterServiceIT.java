package com.xubh.pattern.event;

import com.xubh.pattern.service.RegisterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试事件监听,事件被异步调用@Async
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config-register.xml"})
public class RegisterServiceIT {

    @Autowired
    private RegisterService registerService;

    @Test
    public void testRegister() {
        registerService.register("long", "123");
    }
}
