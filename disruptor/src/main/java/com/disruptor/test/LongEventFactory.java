package com.disruptor.test;

import com.lmax.disruptor.EventFactory;

/**
 * 为了允许Disruptor为我们预分配这些事件，我们需要一个将执行构建的事件工厂
 *
 * @author Lenovo
 * @date 2016-10-19
 * @modify
 * @copyright
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    public LongEvent newInstance() {
        return new LongEvent();
    }
}