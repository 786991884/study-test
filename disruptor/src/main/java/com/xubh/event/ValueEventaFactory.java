package com.xubh.event;

import com.lmax.disruptor.EventFactory;

/**
 * 构建事件对象的工厂
 */
public class ValueEventaFactory implements EventFactory<ValueEvent> {
    public ValueEvent newInstance() {
        return new ValueEvent();
    }
}
