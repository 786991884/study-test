package com.disruptor.test;

import com.lmax.disruptor.EventHandler;

/**
 * 一旦我们定义了事件，我们需要创建一个消费者来处理这些事件。 在我们的例子中，我们想要做的就是在控制台输出值。
 *
 * @author Lenovo
 * @date 2016-10-19
 * @modify
 * @copyright
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println("Event: " + event + " " + event.getValue());
    }
}