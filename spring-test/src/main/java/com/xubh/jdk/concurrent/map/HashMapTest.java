package com.xubh.jdk.concurrent.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HashMapTest {

    public static void main(String[] args) {


        Map<String, String> map = new HashMap<String, String>();

        Random rd = new Random();

        long time = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            int ird = rd.nextInt(1000000);
            map.put("s" + ird, "woca");
        }

        System.out.println(System.currentTimeMillis() - time);


        Map<String, String> map1 = new HashMap<String, String>(1000000);

        Random rd1 = new Random();

        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            int ird = rd1.nextInt(1000000);
            map1.put("aa" + ird, "woca");
        }
        System.out.println(System.currentTimeMillis() - time1);
    }
}
