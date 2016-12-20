package com.xubh.test.processors;

import com.xubh.event.Processor;
import com.xubh.test.StudentTaskData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 选择题
 */
public class ChooseItemProcessor implements Processor<StudentTaskData> {
    private Logger logger = LoggerFactory.getLogger(ChooseItemProcessor.class);

    public Class<StudentTaskData> getObjClazz() {
        return StudentTaskData.class;
    }

    public void process(StudentTaskData data) {
        Map<String, Object> student = data.getStudent();
        logger.info(student.get("username") + "开始做选择题...");
    }
}
