package com.xubh.mongo.crud;

import com.xubh.util.DateUtil;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MongoTest {

    final static AtomicInteger count = new AtomicInteger();
    private MongoTemplate mongoTemplate = MongoDao.getInstance();

    public static void main(String[] args) {
        MongoTest mt = new MongoTest();

        mt.query();

        //mt.save();
    }

    private void save() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < 100000; j++) {
                        UserPojo userPojo = new UserPojo();
                        userPojo.set_name("速度快");
                        userPojo.set_age(10);
                        userPojo.set_date(DateUtil.getDate());
                        userPojo.set_create_time(DateUtil.getNowDate("yyyy-MM-dd HH:mm:ss"));
                        userPojo.set_timestamp(System.currentTimeMillis());
                        mongoTemplate.save(userPojo, "user_info");
                        count.incrementAndGet();
                        System.out.println(count.get() + "/100000");
                    }
                }
            }).start();
        }
    }

    private void query() {
        Query query = new Query();
        long time = System.currentTimeMillis();
        query.addCriteria(Criteria.where("_create_time").gte("2014-11-07 11:38:42").andOperator(Criteria.where("_create_time").lte("2014-11-07 11:43:49")));
        List<UserPojo> list = mongoTemplate.find(query, UserPojo.class, "user_info");
        System.out.println("_create_time:" + (System.currentTimeMillis() - time) + ",size:" + list.size());

        long timestamp = System.currentTimeMillis();
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("_timestamp").gte(Long.valueOf("1415331522948")).andOperator(Criteria.where("_timestamp").lte(Long.valueOf("1415331829075"))));
        List<UserPojo> list1 = mongoTemplate.find(query1, UserPojo.class, "user_info");
        System.out.println("timestamp:" + (System.currentTimeMillis() - timestamp) + ",size:" + list1.size());

        long date = System.currentTimeMillis();
        Query query2 = new Query();
        query1.addCriteria(Criteria.where("_date").gte(DateUtil.getTimestampToDate(Long.valueOf("1415331522948"))).andOperator(Criteria.where("_date").lte(DateUtil.getTimestampToDate(Long.valueOf("1415331829075")))));
        List<UserPojo> list2 = mongoTemplate.find(query2, UserPojo.class, "user_info");
        System.out.println("date:" + (System.currentTimeMillis() - date) + ",size:" + list2.size());
    }

    //1415331829075,2014/11/7 3:43:49,2014-11-07 11:43:49
}
