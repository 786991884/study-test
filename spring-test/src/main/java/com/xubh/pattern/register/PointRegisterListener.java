package com.xubh.pattern.register;

import com.xubh.pattern.event.RegisterEvent;
import com.xubh.pattern.pojo.User;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PointRegisterListener implements ApplicationListener<RegisterEvent> {
    @Async
    @Override
    public void onApplicationEvent(final RegisterEvent event) {
        System.out.println("注册成功，赠送积分给：" + ((User) event.getSource()).getUsername());
    }
}
