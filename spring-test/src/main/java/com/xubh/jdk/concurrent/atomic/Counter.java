package com.xubh.jdk.concurrent.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    public volatile static int count = 0;

    public static AtomicInteger countAtomic = new AtomicInteger(0);

    public synchronized static void synchFunctioninc() {
        // 这里延迟5毫秒，使得结果明显
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
        }
        count++;
    }

    public static void synchObjectinc() {
        // 这里延迟5毫秒，使得结果明显
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
        }
        synchronized (Counter.class) {
            count++;
        }
    }

    public static void atomicinc() {
        // 这里延迟5毫秒，使得结果明显
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
        }
        countAtomic.getAndIncrement();
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        final CountDownLatch latch = new CountDownLatch(1000);
        // 同时启动1000个线程，去进行i++计算，看看实际结果
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Counter.synchFunctioninc();
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
        // 这里每次运行的值都有可能不同,可能为1000
        long end = System.currentTimeMillis();
        System.out.println("运行结果:Counter.count=" + Counter.count + " synch function time elapse:" + (end - start));

        count = 0;
        start = System.currentTimeMillis();
        final CountDownLatch latch1 = new CountDownLatch(1000);
        // 同时启动1000个线程，去进行i++计算，看看实际结果
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Counter.synchObjectinc();
                    latch1.countDown();
                }
            }).start();
        }
        latch1.await();
        // 这里每次运行的值都有可能不同,可能为1000
        end = System.currentTimeMillis();
        System.out.println("运行结果:Counter.count=" + Counter.count + " synch object time elapse:" + (end - start));


        count = 0;
        start = System.currentTimeMillis();
        final CountDownLatch latch2 = new CountDownLatch(1000);
        // 同时启动1000个线程，去进行i++计算，看看实际结果
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Counter.atomicinc();
                    latch2.countDown();
                }
            }).start();
        }
        latch2.await();
        // 这里每次运行的值都有可能不同,可能为1000
        end = System.currentTimeMillis();
        System.out.println("运行结果:Counter.count=" + Counter.countAtomic + " atomic time elapse:" + (end - start));
    }
}
