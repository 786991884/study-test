package com.xubh.pattern.register;

import com.xubh.pattern.event.RegisterEvent;
import com.xubh.pattern.pojo.User;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class IndexRegisterListener implements ApplicationListener<RegisterEvent> {
    @Async
    @Override
    public void onApplicationEvent(final RegisterEvent event) {
        System.out.println("注册成功，索引用户信息：" + ((User) event.getSource()).getUsername());
    }
}
