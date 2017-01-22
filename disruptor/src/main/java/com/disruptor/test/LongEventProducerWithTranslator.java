package com.disruptor.test;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * 在版本3.0的Disruptor中，添加了一个更丰富的Lambda样式的API，
 * 通过将这种复杂性封装在Ring缓冲区中来帮助开发人员，
 * 因此post-3.0发布消息的首选方法是通过API的事件发布器/事件转换器部分。 例如。
 * @author Lenovo
 * @date 2016-10-19
 * @modify
 * @copyright
 */
public class LongEventProducerWithTranslator {
    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR =
            new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
                public void translateTo(LongEvent event, long sequence, ByteBuffer bb) {
                    event.set(bb.getLong(0));
                }
            };

    public void onData(ByteBuffer bb) {
        ringBuffer.publishEvent(TRANSLATOR, bb);
    }
}