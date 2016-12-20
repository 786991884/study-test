package com.xubh.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常格式化输出
 */
public class ThrowableParser {

    public static String toString(Throwable t) {
        if (t == null)
            return "Throwable is null";
        StringWriter swriter = new StringWriter();
        PrintWriter pwriter = new PrintWriter(swriter);
        t.printStackTrace(pwriter);
        String s = swriter.toString();
        pwriter.close();
        return s;
    }

    public static String toString(Exception t) {
        return toString(t.getCause() == null ? t : t.getCause());
    }
}
