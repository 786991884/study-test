package com.xubh.test;

import com.xubh.event.EventTask;

import java.util.List;
import java.util.Map;

public class ExamTask extends EventTask<Map<String, Object>> {
    public ExamTask(List<Map<String, Object>> students) {
        super(students);
    }

    public Object get() {
        return new StudentTaskData(cur);
    }
}
