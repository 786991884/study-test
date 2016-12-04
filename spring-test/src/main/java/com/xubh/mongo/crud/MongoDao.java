package com.xubh.mongo.crud;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongoDao {

    private static volatile MongoTemplate mongoTemplate = null;

    private MongoDao() {
    }

    public static MongoTemplate getInstance() {
        if (mongoTemplate == null) {
            synchronized (MongoDao.class) {
                if (mongoTemplate == null) {
                    ApplicationContext context = new FileSystemXmlApplicationContext(
                            "src/main/resources/application-mongo.xml");
                    mongoTemplate = (MongoTemplate) context.getBean("mongoTemplate");
                }
            }
        }
        return mongoTemplate;
    }
}
