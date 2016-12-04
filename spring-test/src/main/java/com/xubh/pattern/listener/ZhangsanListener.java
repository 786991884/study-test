package com.xubh.pattern.listener;

import com.xubh.pattern.event.ContentEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ZhangsanListener implements ApplicationListener<ContentEvent> {

    @Override
    public void onApplicationEvent(final ContentEvent event) {
        System.out.println("张三收到了新的内容：" + event.getSource());
    }
}
