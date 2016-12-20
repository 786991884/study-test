package com.xubh.event;

import com.lmax.disruptor.EventTranslator;

/**
 * 事件转化器disruptor通过该转化器将事件转发到不同的处理器中
 */
public class DisruptorEventTranslator implements EventTranslator<ValueEvent> {
    private ValueEvent event;

    public DisruptorEventTranslator(ValueEvent event) {
        this.event = event;
    }

    public void translateTo(ValueEvent valueEvent, long l) {
        valueEvent.copy(event);
    }
}
