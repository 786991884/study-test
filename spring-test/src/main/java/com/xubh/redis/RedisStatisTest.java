package com.xubh.redis;

import com.xubh.util.DateUtil;
import com.xubh.util.PropertiesUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;


public class RedisStatisTest {

    public static List<String> list = new ArrayList<String>();
    public static AtomicInteger count1 = new AtomicInteger();
    public static AtomicInteger count2 = new AtomicInteger();
    public static AtomicInteger count3 = new AtomicInteger();
    public static AtomicInteger count4 = new AtomicInteger();
    public static AtomicInteger count5 = new AtomicInteger();
    MongoTemplate mongoTemplate;

    @Before
    public void beforeTest() {
        try {
            list.add("channel1");
            list.add("channel2");
            list.add("channel3");
            list.add("channel4");
            list.add("channel5");

            //ApplicationContext context = new FileSystemXmlApplicationContext("src/main/webapp/spring-configuration/*.xml");
            //ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/*.xml");
            //mongoTemplate = (MongoTemplate) context.getBean("mongoTemplate");
            PropertiesUtil.initProperties();
            RedisDBUtil.init();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }

    @Test
    public void testStatis() {
        final String date = DateUtil.getNowDate("yyyy-MM-dd") + "channel";

        this.clear(date);

        while (true) {
            int count = 100;
            final CountDownLatch latch = new CountDownLatch(count);
            for (int i = 0; i < count; i++) {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Random rd = new Random();
                            int ird = rd.nextInt(5);
                            //System.out.println("ird:"+ird);
                            if (ird == 0) {
                                count1.incrementAndGet();
                            }
                            if (ird == 1) {
                                count2.incrementAndGet();
                            }
                            if (ird == 2) {
                                count3.incrementAndGet();
                            }
                            if (ird == 3) {
                                count4.incrementAndGet();
                            }
                            if (ird == 4) {
                                count5.incrementAndGet();
                            }
                            //RedisDBUtil.setHashValue(date, list.get(ird), RedisDBUtil.incr(list.get(ird))+"");

                            RedisDBUtil.setHashHincr(date, list.get(ird), 1);
                            latch.countDown();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            try {
                latch.await();

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            System.out.println("---------------------- real data ---------------------------------");
            System.out.println("channel1" + ":" + count1.get());
            System.out.println("channel2" + ":" + count2.get());
            System.out.println("channel3" + ":" + count3.get());
            System.out.println("channel4" + ":" + count4.get());
            System.out.println("channel5" + ":" + count5.get());
            System.out.println("------------------------------------------------------------------");

            this.desc(date);
        }
    }

    public void desc(String key) {
        System.out.println("----------------------- redis data -------------------------------");
        Map<String, String> channelResult = RedisDBUtil.getAllKeys(key);
        for (Entry<String, String> temp : channelResult.entrySet()) {
            if (temp.getKey().equals("channel1")) {
                System.out.println(temp.getKey() + ":" + temp.getValue() + "--->" + (Integer.valueOf(temp.getValue()) == count1.get() ? "true" : "false"));
            }
            if (temp.getKey().equals("channel2")) {
                System.out.println(temp.getKey() + ":" + temp.getValue() + "--->" + (Integer.valueOf(temp.getValue()) == count2.get() ? "true" : "false"));
            }
            if (temp.getKey().equals("channel3")) {
                System.out.println(temp.getKey() + ":" + temp.getValue() + "--->" + (Integer.valueOf(temp.getValue()) == count3.get() ? "true" : "false"));
            }
            if (temp.getKey().equals("channel4")) {
                System.out.println(temp.getKey() + ":" + temp.getValue() + "--->" + (Integer.valueOf(temp.getValue()) == count4.get() ? "true" : "false"));
            }
            if (temp.getKey().equals("channel5")) {
                System.out.println(temp.getKey() + ":" + temp.getValue() + "--->" + (Integer.valueOf(temp.getValue()) == count5.get() ? "true" : "false"));
            }
        }
        System.out.println("------------------------------------------------------------------");
    }

    public void clear(String key) {
        System.out.println("----------------------- clear key -------------------------------");
        Map<String, String> channelResult = RedisDBUtil.getAllKeys(key);
        for (Entry<String, String> temp : channelResult.entrySet()) {
            RedisDBUtil.del(temp.getKey());
            System.out.println(temp.getKey() + ":" + temp.getValue());
        }
        RedisDBUtil.del(key);
        System.out.println("----------------------- clear ok---------------------------------");
    }
}
