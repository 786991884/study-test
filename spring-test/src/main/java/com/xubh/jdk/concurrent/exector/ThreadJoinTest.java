package com.xubh.jdk.concurrent.exector;

public class ThreadJoinTest {

    private final static int GROUP_SIZE = 5;

    public static void main(String[] args) throws InterruptedException {
        Thread[] threadGroup1 = new Thread[5];
        Thread[] threadGroup2 = new Thread[5];
        for (int i = 0; i < GROUP_SIZE; i++) {
            final int num = i;
            threadGroup1[i] = new Thread() {
                public void run() {
                    int j = 0;
                    while (j++ < 10) {
                        System.out.println("我是1号组线程：" + num + " 这个是我第：" + j + " 次运行！");
                    }
                }
            };
            threadGroup2[i] = new Thread() {
                public void run() {
                    int j = 0;
                    while (j++ < 10) {
                        System.out.println("我是2号组线程：" + num + " 这个是我第：" + j + " 次运行！");
                    }
                }
            };
            threadGroup1[i].start();
        }
        for (int i = 0; i < GROUP_SIZE; i++) {
            threadGroup1[i].join();
        }
        System.out.println("-==================>线程组1执行完了，该轮到俺了！");
        for (int i = 0; i < GROUP_SIZE; i++) {
            threadGroup2[i].start();
        }
        for (int i = 0; i < GROUP_SIZE; i++) {
            threadGroup2[i].join();
        }
        System.out.println("全部结束啦！哈哈，回家喝稀饭！");
    }
}
