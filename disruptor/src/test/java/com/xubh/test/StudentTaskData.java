package com.xubh.test;

import java.util.Map;

public class StudentTaskData {
    private Map<String, Object> student;

    public StudentTaskData(Map<String, Object> student) {
        this.student = student;
    }

    public Map<String, Object> getStudent() {
        return student;
    }

    public void setStudent(Map<String, Object> student) {
        this.student = student;
    }
}
