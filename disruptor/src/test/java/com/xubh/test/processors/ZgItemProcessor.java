package com.xubh.test.processors;

import com.xubh.event.Processor;
import com.xubh.test.StudentTaskData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ZgItemProcessor implements Processor<StudentTaskData> {
    private Logger logger = LoggerFactory.getLogger(ZgItemProcessor.class);

    public Class<StudentTaskData> getObjClazz() {
        return StudentTaskData.class;
    }

    public void process(StudentTaskData data) {
        Map<String, Object> student = data.getStudent();
        logger.info(student.get("username") + "开始做解答题...");
    }
}
