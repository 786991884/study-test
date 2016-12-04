package com.xubh.pattern.service;

import com.xubh.pattern.event.RegisterEvent;
import com.xubh.pattern.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 注册服务,发布注册成功事件
 */
@Service
public class RegisterService {

    protected static final Logger logger = LoggerFactory.getLogger(RegisterService.class);

    @Autowired
    private ApplicationContext applicationContext;

    public void register(String username, String password) {
        logger.info(username + "注册成功！");
        publishRegisterEvent(new User(username, password));
    }

    private void publishRegisterEvent(User user) {
        applicationContext.publishEvent(new RegisterEvent(user));
    }
}
