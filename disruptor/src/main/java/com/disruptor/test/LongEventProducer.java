package com.disruptor.test;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * 我们将需要一个这些事件的源，为了举例，我将假定数据来自某种I / O设备，例如。 网络或文件形式的ByteBuffer。
 *
 * @author Lenovo
 * @date 2016-10-19
 * @modify
 * @copyright
 */
public class LongEventProducer {
    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer bb) {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try {
            LongEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
            // for the sequence
            event.set(bb.getLong(0));  // Fill with data
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}