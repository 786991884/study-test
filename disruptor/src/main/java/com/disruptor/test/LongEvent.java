package com.disruptor.test;

/**
 * 为了开始使用Disruptor，我们将考虑一个非常简单和困难的例子，它将一个长的值从一个生产者传递给一个消费者，
 * 消费者只需打印出这个值。 首先，我们将定义将携带数据的事件。
 *
 * @author Lenovo
 * @date 2016-10-19
 * @modify
 * @copyright
 */
public class LongEvent {
    private long value;

    public long getValue() {
        return value;
    }

    public void set(long value) {
        this.value = value;
    }
}