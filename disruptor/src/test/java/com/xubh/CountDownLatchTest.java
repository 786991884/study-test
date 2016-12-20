package com.xubh;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    @Test
    public void test() throws Exception {
        CountDownLatch latch = new CountDownLatch(5);
        System.out.println(latch.getCount());
        latch.countDown();
        System.out.println(latch.getCount());
    }
}
