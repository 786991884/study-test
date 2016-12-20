package com.xubh.utils;

import java.util.UUID;

/**
 * 唯一序列
 */
public class OnlyIdBuilder {
    public static String build() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
