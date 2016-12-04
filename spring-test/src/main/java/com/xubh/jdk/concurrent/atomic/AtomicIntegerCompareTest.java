package com.xubh.jdk.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerCompareTest {

    private int value;

    public AtomicIntegerCompareTest(int value) {
        this.value = value;
    }

    public static void main(String args[]) {
        long start = System.currentTimeMillis();

        final AtomicIntegerCompareTest test = new AtomicIntegerCompareTest(0);

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        for (int i = 0; i < 100; i++) {
                            test.increase();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        long end = System.currentTimeMillis();
        System.out.println("time elapse:" + (end - start) + "    value:" + test.value);

        long start1 = System.currentTimeMillis();

        final AtomicInteger atomic = new AtomicInteger(0);

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        for (int i = 0; i < 100; i++) {
                            atomic.incrementAndGet();
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        long end1 = System.currentTimeMillis();
        System.out.println("time elapse:" + (end1 - start1) + "    value:" + test.value);
    }

    public synchronized int increase() {
        return value++;
    }

}
