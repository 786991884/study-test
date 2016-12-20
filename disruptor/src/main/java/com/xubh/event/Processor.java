package com.xubh.event;

public interface Processor<T> {
    Class<T> getObjClazz();

    void process(T data);
}
