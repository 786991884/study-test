package com.xubh.jvm.gc;

public class TestFinally {

    public static void main(String[] args) {
        System.out.println("b:" + TestFinally.test());
    }

    static int test() {
        int x = 1;
        try {
            return x;
        } finally {
            ++x;
            System.out.println("a:" + x);
        }
    }
}
