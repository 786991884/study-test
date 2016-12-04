package com.xubh.jvm.classloader;

public class Singleton {
    private static Singleton singleton = new Singleton();
    private static int counter1;
    private static int counter2 = 0;

    public Singleton() {
        counter1++;
        counter2++;
    }

    public static int getCounter1() {
        return counter1;
    }

    public static int getCounter2() {
        return counter2;
    }

    public static Singleton getInstance() {
        return singleton;
    }
}
