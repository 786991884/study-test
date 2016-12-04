package com.xubh.pattern.event;

import com.xubh.pattern.pojo.User;
import org.springframework.context.ApplicationEvent;

/**
 * 注册监听事件
 */
public class RegisterEvent extends ApplicationEvent {

    public RegisterEvent(User source) {
        super(source);
    }
}
