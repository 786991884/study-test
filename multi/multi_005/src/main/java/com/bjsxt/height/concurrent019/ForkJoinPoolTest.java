package com.bjsxt.height.concurrent019;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * 一、RecursiveAction
 * 下面以一个没有返回值的大任务为例，介绍一下RecursiveAction的用法。
 * 大任务是：打印0-200的数值。
 * 小任务是：每次只能打印50个数值。
 *
 * @author Lenovo
 * @date 2016-11-30
 * @modify
 * @copyright
 */
//RecursiveAction为ForkJoinTask的抽象子类，没有返回值的任务
public class ForkJoinPoolTest extends RecursiveAction {
    // 每个"小任务"最多只打印50个数
    private static final int MAX = 50;

    private int start;
    private int end;

    ForkJoinPoolTest(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        // 当end-start的值小于MAX时候，开始打印
        if ((end - start) < MAX) {
            for (int i = start; i < end; i++) {
                System.out.println(Thread.currentThread().getName() + "的i值:"
                        + i);
            }
        } else {
            // 将大任务分解成两个小任务
            int middle = (start + end) / 2;
            ForkJoinPoolTest left = new ForkJoinPoolTest(start, middle);
            ForkJoinPoolTest right = new ForkJoinPoolTest(middle, end);
            // 并行执行两个小任务
            left.fork();
            right.fork();
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 创建包含Runtime.getRuntime().availableProcessors()返回值作为个数的并行线程的ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 提交可分解的PrintTask任务
        forkJoinPool.submit(new ForkJoinPoolTest(0, 200));
        forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);//阻塞当前线程直到 ForkJoinPool 中所有的任务都执行结束
        // 关闭线程池
        forkJoinPool.shutdown();
    }
}
